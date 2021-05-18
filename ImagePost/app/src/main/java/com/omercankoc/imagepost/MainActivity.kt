package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Firebase Auth nesnesi olustur.
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Auth nesnesini tanimla.
        auth = FirebaseAuth.getInstance()

        // Kullanici bir kere giris yaptiktan sonraki acilislarda
        // direk FeeddActivity'e yonlendir.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(applicationContext, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Kullanici girisi yap.
    fun singIn(view: View) {

        // Kullanicidan email ve password bilgisini al.
        val email = editTextEMail.text.toString()
        var password = editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){ // basarili ise FeedActivity'e git.
                Toast.makeText(applicationContext,"Hello ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception -> // Hata, istisna olur ise hata mesajini goster.
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    // Kullanici olustur.
    fun singUp(view: View) {

        // Kullanicidan email ve password bilgisini al.
        val email = editTextEMail.text.toString()
        var password = editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){ // basarili ise FeedActivity'e git.
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception -> // Hata, istisna olur ise hata mesajini goster.
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}