package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val prof_name = findViewById<TextView>(R.id.profile_name)
        val prof_email = findViewById<TextView>(R.id.profile_email)

        prof_name.text = currentUser?.displayName
        prof_email.text = currentUser?.email
    }
}