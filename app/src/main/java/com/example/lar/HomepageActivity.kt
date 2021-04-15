package com.example.lar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class HomepageActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        LoadProfile()


    }

    private fun LoadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        homepage_name.text = "Welcome " + user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                homepage_name.text = "Welcome " + snapshot.child("name").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
//                startActivity(Intent(this@HomepageActivity, LoginActivity::class.java))
//                finish()
            }
        })
        homepage_user.setOnClickListener {
            startActivity(Intent(this@HomepageActivity, ProfileActivity::class.java))
        }
        homepage_logout_button.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@HomepageActivity, LoginActivity::class.java))
        }
        homepage_input_fitness_data.setOnClickListener {
            startActivity(Intent(this@HomepageActivity, InputFitnessDataActivity::class.java))
        }
        homepage_recent_fitness_data.setOnClickListener {
            startActivity(Intent(this@HomepageActivity, RecentFitnessTrackingActivity::class.java))
        }
    }


}