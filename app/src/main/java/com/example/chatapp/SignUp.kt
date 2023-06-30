package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.mbms.StreamingServiceInfo
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignUp : AppCompatActivity() {

    private lateinit var inp_email : EditText
    private lateinit var inp_pwd : EditText
    private lateinit var inp_name : EditText
    private lateinit var btn_signup : Button
    private lateinit var mauth: FirebaseAuth

    private lateinit var mdbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mauth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        inp_email = findViewById(R.id.input_email)
        inp_pwd = findViewById((R.id.input_pwd))
        inp_name = findViewById(R.id.input_name)
        btn_signup = findViewById(R.id.signup_button)

        btn_signup.setOnClickListener(){
            val name = inp_name.text.toString()
            val email = inp_email.text.toString()
            val pwd = inp_pwd.text.toString()

            signup(name,email,pwd)
        }

    }

    private fun signup(name:String,email : String, password :String){
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)

                    addtodb(name,email,mauth.currentUser?.uid!!,day.toString()+'/'+month.toString()+'/'+year.toString())
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Error sorry!",Toast.LENGTH_SHORT).show()

                }
            }
        }

    private fun addtodb(name: String, email: String, uid: String,date: String){
        mdbref = FirebaseDatabase.getInstance().getReference()
        mdbref.child("user").child(uid).setValue(User(name,email,uid,date))
    }

}