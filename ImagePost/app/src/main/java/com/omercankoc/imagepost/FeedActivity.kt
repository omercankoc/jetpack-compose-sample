package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class FeedActivity : AppCompatActivity() {

    // Auth nesnesi olustur.
    private lateinit var auth : FirebaseAuth

    // Menuyu arayuze bagla.(Menu Inflater)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInference = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Menude secilen itemlerin ilgili operasyonlarini gerceklestir.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addPost){
            // Post ekleme arayuzune git.
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)
            //finish()
        } else if(item.itemId == R.id.logOut){
            // Cikis yap ve Giris arayuzune ekranina git.
            auth.signOut()
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
    }


}