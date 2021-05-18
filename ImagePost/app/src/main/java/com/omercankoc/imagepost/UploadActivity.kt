package com.omercankoc.imagepost

import android.app.Activity
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
import java.sql.Timestamp
import java.util.*
import java.util.jar.Manifest

class UploadActivity : AppCompatActivity() {

    var selectedPicture : Uri? = null
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    // Galeriye gitmek icin izin iste sonra geleriye git, eger izin verildi ise galeriye git.
    fun selectImage(view : View){

        // Izin verilmemis ise izin al.
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        // Izin daha onceden verilmis ise galeriye git.
        else {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    // Izin verildikten sonra galeri'ye git.
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

    // Galeriye gittikten sonra secilen image icin bitmap decode islemini yap ve ilgili view'a aktar.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            selectedPicture = data.data

            try {
                if(selectedPicture != null){

                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(contentResolver,selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        imageViewPost.setImageBitmap(bitmap)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                        imageViewPost.setImageBitmap(bitmap)
                    }
                }
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Gorseli ve yorumu, yukleyen bilgisi ve tarih ile beraber firebase'e yukle.
    fun upload(view : View){

        // Resim adi icin random isim olustur. <UUID>
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage = FirebaseStorage.getInstance()
        val referance = storage.reference
        val imagesReference = referance.child("images").child(imageName)

        if(selectedPicture != null){
            imagesReference.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->
                val uploadedPictureReference = FirebaseStorage.getInstance()
                    .reference
                    .child("images")
                    .child(imageName)
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    println(downloadUrl)

                    // Verileri toparla.
                    val postMap = hashMapOf<String,Any>()
                    postMap.put("downloadUrl",downloadUrl)
                    postMap.put("userEmail",auth.currentUser!!.email.toString())
                    postMap.put("comment",editTextComment.text.toString())
                    postMap.put("date",com.google.firebase.Timestamp.now())

                    // DB'de ilgili Collection'a kaydetmek icin operasyonu dinle.
                    db.collection("Posts").add(postMap).addOnCompleteListener { task ->
                        // Yukleme tamamlandi ise ve basarili ise FeedActivity'ye git.
                        if(task.isComplete && task.isSuccessful){
                            finish()
                        }
                    }.addOnFailureListener { exception -> // Hata var ise goster.
                        Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}