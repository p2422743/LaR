package com.example.lar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register() {
        register_submit.setOnClickListener {
            if (TextUtils.isEmpty(register_name.text.toString())) {
                register_name.setError("Please enter a valid name.")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(register_email.text.toString())) {
                register_email.setError("Please enter your email.")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(register_password.text.toString())) {
                register_password.setError("Please enter your password.")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(register_email.text.toString(), register_password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUserDB = databaseReference?.child((currentUser?.uid!!))
                        currentUserDB?.child("name")?.setValue(register_name.text.toString())
                        currentUserDB?.child("email")?.setValue(register_email.text.toString())
                        Toast.makeText(this@RegistrationActivity, "Registration successful! ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
