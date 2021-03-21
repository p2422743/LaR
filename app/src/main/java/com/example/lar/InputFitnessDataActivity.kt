package com.example.lar

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ServerValue.TIMESTAMP
import kotlinx.android.synthetic.main.activity_inputfitnessdata.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.sql.Timestamp
import java.util.*


class InputFitnessDataActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inputfitnessdata)




        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Tracking Data")

        LoadProfile()


    }


    private fun LoadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)





        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val currentUser = auth.currentUser
        val currentUserDB = databaseReference?.child((currentUser?.uid!!))
        currentUserDB?.child("TimeStamp")?.setValue(TIMESTAMP)

        inputfitnessdata_save.setOnClickListener {
            if (TextUtils.isEmpty(inputfitnessdata_calories_intake.text.toString())) {
            } else
                currentUserDB?.child("calories_intake")?.setValue(inputfitnessdata_calories_intake.text.toString())
            Toast.makeText(this@InputFitnessDataActivity, "Profile save successful! ", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@InputFitnessDataActivity, RecentFitnessTrackingActivity::class.java))

            finish()

            if (TextUtils.isEmpty(inputfitnessdata_current_session_time.text.toString())) {
            } else {
                currentUserDB?.child("current_session_time")?.setValue(inputfitnessdata_current_session_time.text.toString())
                Toast.makeText(this@InputFitnessDataActivity, "Profile save successful! ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@InputFitnessDataActivity, RecentFitnessTrackingActivity::class.java))
                finish()
            }

            if (TextUtils.isEmpty(inputfitnessdata_weight.text.toString())) {
            } else {
                currentUserDB?.child("weight")?.setValue(inputfitnessdata_current_session_time.text.toString())
                Toast.makeText(this@InputFitnessDataActivity, "Profile save successful! ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@InputFitnessDataActivity, RecentFitnessTrackingActivity::class.java))
                ServerValue.TIMESTAMP
                finish()
            }
        }
    }


}