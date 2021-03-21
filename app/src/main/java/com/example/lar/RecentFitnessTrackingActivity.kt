package com.example.lar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_recentfitnesstracking.*

class RecentFitnessTrackingActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recentfitnesstracking)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Tracking Data")

        LoadProfile()


    }

    private fun LoadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

//        recentfitnessdata_calories_intake.text = "Calories Intake: " + user?.email
//       recentfitnessdata_current_session_time.text = "Current Session Time: " + user?.email
//        recentfitnessdata_weight.text = "Current Weight: " + user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                recentfitnessdata_calories_intake.text  = "Calories Intake: " +  snapshot.child("calories_intake").value.toString()
                recentfitnessdata_current_session_time.text = "Current Session Time: " + snapshot.child("current_session_time").value.toString()
                recentfitnessdata_weight.text  = "Current Weight: " +  snapshot.child("weight").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        recentfitnessdata_Edit.setOnClickListener {
            startActivity(Intent(this@RecentFitnessTrackingActivity, InputFitnessDataActivity::class.java))

        }
    }


}