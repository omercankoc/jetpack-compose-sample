package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class UploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
    }

    fun upload(view : View){

    }

    // Upload yapmadan FeedActivity'e geri git.
    fun back (view : View){
        val intent = Intent(applicationContext,FeedActivity::class.java)
        startActivity(intent)
        finish()
    }
}