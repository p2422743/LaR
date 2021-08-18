package com.example.lar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_editableprofile.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*

class EditableProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editableprofile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        LoadProfile()


    }

    private fun LoadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)


        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                editableprofile_name.hint = snapshot.child("name").value.toString()
                editableprofile_age.hint = snapshot.child("age").value.toString()
                editableprofile_gender.prompt = snapshot.child("gender").value.toString()
                editableprofile_current_weight.hint = snapshot.child("current_weight").value.toString()
                editableprofile_target_weight.hint = snapshot.child("target_weight").value.toString()

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val genders = resources.getStringArray(R.array.spinnerGender)
        val spinner = findViewById<Spinner>(R.id.editableprofile_gender)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    (parent.getChildAt(0) as TextView).setTextColor(Color.BLACK)
                    (parent.getChildAt(0) as TextView).textSize = 25f
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(this@EditableProfileActivity, "Select a gender.", Toast.LENGTH_LONG).show()
                }
            }
        }


        val currentUser = auth.currentUser
        val currentUserDB = databaseReference?.child((currentUser?.uid!!))


        editableprofile_save.setOnClickListener {
            if (TextUtils.isEmpty(editableprofile_name.text.toString())) {
                editableprofile_name.setError("Cannot leave profile name blank!");
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(editableprofile_age.text.toString())) {
                editableprofile_age.setError("Cannot leave age blank!");
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(editableprofile_current_weight.text.toString())) {
                editableprofile_current_weight.setError("Cannot leave current weight blank!");
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(editableprofile_target_weight.text.toString())) {
                editableprofile_target_weight.setError("Cannot leave target weight blank!");
                return@setOnClickListener
            } else if (editableprofile_name.length() > 20) {
                editableprofile_name.setError("Name is too long!");
                return@setOnClickListener
            } else if (editableprofile_age.length() > 3) {
                editableprofile_age.setError("Age is too long!");
                return@setOnClickListener
            } else if (editableprofile_current_weight.length() > 3) {
                editableprofile_current_weight.setError("Current weight is invalid!");
                return@setOnClickListener
            } else if (editableprofile_target_weight.length() > 3) {
                editableprofile_target_weight.setError("Target weight is invalid!");
                return@setOnClickListener
            } else {
                currentUserDB?.child("age")?.setValue(editableprofile_age.text.toString())
                currentUserDB?.child("name")?.setValue(editableprofile_name.text.toString())
                currentUserDB?.child("current_weight")?.setValue(editableprofile_current_weight.text.toString())
                currentUserDB?.child("target_weight")?.setValue(editableprofile_target_weight.text.toString())
                currentUserDB?.child("gender")?.setValue(editableprofile_gender.selectedItem.toString())
                Toast.makeText(this@EditableProfileActivity, "Profile save successful! ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@EditableProfileActivity, ProfileActivity::class.java))
            }
        }
    }
}









