package com.example.lar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(this@LoginActivity, HomepageActivity::class.java))
            finish()
        }
        login()


    }

    private fun login() {
        login_button.setOnClickListener {
            if(TextUtils.isEmpty(login_email.text.toString())){
                login_email.setError("Please enter an email.")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(login_password.text.toString())){
                login_password.setError("Please enter a password.")
                    return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(login_email.text.toString(),login_password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, HomepageActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()

                    }
                }
        }
        registerText.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))

        }

    }
}