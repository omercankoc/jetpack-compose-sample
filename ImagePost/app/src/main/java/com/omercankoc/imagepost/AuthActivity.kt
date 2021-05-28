package com.omercankoc.imagepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        // Kullanici daha onceden giris yapmis ise FeedActivity'e git.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
        }
    }

    fun signIn(view : View){
        // EMail ve Password verileri al ve kullanici giris islemini yap.
        var email = editTextTextEmailAddress.text.toString()
        var password = editTextTextPassword.text.toString()
        // Kullanici girisinin basarili olup olmamasini kontrol et.
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            // Giris basarili oldu ise FeedActivity'e git.
            if(task.isSuccessful){
                // Selam Cak!
                Toast.makeText(applicationContext,"Hello ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG)
                    .show()
                // FeedActivity'e git.
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish() // Geri donulmemesi icin Activity'i oldur.
            }
        }.addOnFailureListener { exception ->
            // Giris islemi basarili olmaz ise kullaniciya bildir.
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun signUp(view : View){
        // EMail ve Password verileri al ve kullanici olustur.
        var email = editTextTextEmailAddress.text.toString()
        var password = editTextTextPassword.text.toString()
        // Kullanici kayitinin basarili olup olmamasini kontrol et.
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            // Kayit basarili oldu ise FeedActivity'e git.
            if(task.isSuccessful){
                // FeedActivity'e git.
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish() // Geri donulmemesi icin Activity'i oldur.
            }
        }.addOnFailureListener { exception ->
            // Kayit islemi basarili olmaz ise kullaniciya bildir.
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}