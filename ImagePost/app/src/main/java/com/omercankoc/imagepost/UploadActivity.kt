package com.omercankoc.imagepost

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.util.*

class UploadActivity : AppCompatActivity() {

    var selectedPicture : Uri? = null
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    // Kullanicidan izin alindiktan sonra Galeri'ye git.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Gidilen Galeri'de secim yapildiktan sonra image'i ilgili imageView'a aktar.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            selectedPicture = data.data

            try {
                if(selectedPicture != null){
                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(contentResolver,selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        imageViewSelect.setImageBitmap(bitmap)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                        imageViewSelect.setImageBitmap(bitmap)
                    }
                }
            } catch (e : Exception){
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    // ImageView'a tiklandiginda...
    fun select(view : View){

        // Kullanicidan Galeri'ye erismek icin gerekli izin verilmemis ise izin iste.
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        // Izin daha onceden verilmis ise Galeri'ye git.
        else {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
        }
    }

    fun upload(view : View){

        // UUID -> image name olustur.
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage = FirebaseStorage.getInstance()
        val reference = storage.reference
        val imagesReferance = reference
            .child("images")
            .child(imageName) // Klasorle.

        if(selectedPicture != null){
            imagesReferance.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->

                // Basarili oldu ise firebase'e kaydet.

                // Random atanan image id verisini al.
                val uploadedPictureReferance = FirebaseStorage.getInstance()
                    .reference.child("images")
                    .child(imageName)
                uploadedPictureReferance.downloadUrl.addOnSuccessListener { uri ->

                    // Image Verisini ata.
                    val downloadUrl = uri.toString()

                    // Post kaydetmek icin hashMap olustur.
                    val postMap = hashMapOf<String,Any>()
                    postMap.put("downloadUrl",downloadUrl)
                    postMap.put("userEMail",auth.currentUser!!.email.toString())
                    postMap.put("comment",editTextTextComment.text.toString())
                    postMap.put("date",com.google.firebase.Timestamp.now())

                    // Postu kaydet ve yonlendir.
                    database.collection("Posts")
                        .add(postMap)
                        .addOnCompleteListener {task ->
                            // YUkleme tamamlandi ve basarili ise FeedActivity'e git.
                            if(task.isComplete && task.isSuccessful){
                                val intent = Intent(applicationContext,FeedActivity::class.java)
                                startActivity(intent)
                                finish()
                            } // tamamlanmadi ve basarisiz ise hata mesajini goster.
                        }.addOnFailureListener { exception ->
                            Toast.makeText(applicationContext,
                                exception.localizedMessage.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }

    // Upload yapmadan FeedActivity'e geri git.
    fun back (view : View){
        val intent = Intent(applicationContext,FeedActivity::class.java)
        startActivity(intent)
        finish()
    }
}