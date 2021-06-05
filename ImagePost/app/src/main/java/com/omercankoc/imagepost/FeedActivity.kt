package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*
import java.sql.Timestamp

class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore

    var userEMails : ArrayList<String> = ArrayList()
    var userComments : ArrayList<String> = ArrayList()
    var userImages : ArrayList<String> = ArrayList()

    var adapter : RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        getDataFromFirestore()

        // Recycle View tanimla ve yapilandir.
        var layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager
        adapter = RecyclerAdapter(userEMails,userComments,userImages)
        recycleView.adapter = adapter
    }

    // Menuyu arayuze bagla. (Inflater)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    // Baglanan menude secilen itemin operasyonunu belirle.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // UploadActivity'ye git.
        if(item.itemId == R.id.add_post){
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Cikis yap.
        else if(item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(applicationContext,AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    // Collection'daki postlari getir ve ilgili view'lara tarihe gore aktar.
    fun getDataFromFirestore(){
        database.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(applicationContext,error.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else {
                if(value != null){
                    if(!value.isEmpty){
                        // Verileri ilgili dizileri temizle.
                        userEMails.clear()
                        userComments.clear()
                        userImages.clear()

                        val documents = value.documents
                        for(document in documents){
                            var comment = document.get("comment") as String
                            var userEMail = document.get("userEMail") as String
                            var downloadUrl = document.get("downloadUrl") as String
                            var timeStamp = document.get("date") as com.google.firebase.Timestamp
                            val date = timeStamp.toDate()

                            // Verileri ilgili dizilere aktar.
                            userEMails.add(userEMail)
                            userComments.add(comment)
                            userImages.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}