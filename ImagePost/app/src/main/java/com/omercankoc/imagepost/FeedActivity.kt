package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
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
}