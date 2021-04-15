package com.example.lar

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*


class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        LoadProfile()


    }

    private fun LoadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        profile_name.text = "Name: " + user?.email
        profile_age.text = "Age: " + user?.email
        profile_gender.text = "Gender: " + user?.email
        profile_email.text = "Email: " + user?.email
        profile_current_weight.text = "Current Weight: " + user?.email
        profile_target_weight.text = "Target Weight: " + user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                profile_name.text = snapshot.child("name").value.toString()
                profile_email.text = snapshot.child("email").value.toString()
                profile_age.text = snapshot.child("age").value.toString()
                profile_gender.text = snapshot.child("gender").value.toString()
                profile_current_weight.text = snapshot.child("current_weight").value.toString()
                profile_target_weight.text = snapshot.child("target_weight").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
//            startActivity(Intent(this@ProfileActivity, ProfileActivityEdit::class.java))
//
//        }

//        profile_edit.setOnClickListener {
//            if (TextUtils.isEmpty(profile_age.text.toString())) {
//                profile_age.setError("Please enter a valid age.")
//                return@setOnClickListener
//            } else if (TextUtils.isEmpty(profile_gender.toString())) {
//            }
//            val currentUser = auth.currentUser
//            val currentUserDB = databaseReference?.child((currentUser?.uid!!))
//            currentUserDB?.child("age")?.setValue(profile_age.text.toString())
//            currentUserDB?.child("gender")?.setValue(profile_gender.isSelected.toString())
//            Toast.makeText(this@ProfileActivity, "Profile save successful! ", Toast.LENGTH_LONG).show()
//            finish()
//        }
        profile_edit.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, EditableProfileActivity::class.java))

        }
    }
}
