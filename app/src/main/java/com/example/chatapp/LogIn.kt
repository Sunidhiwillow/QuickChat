package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.ButtonBarLayout
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var inp_email : EditText
    private lateinit var inp_pwd : EditText
    private lateinit var btn_login : Button
    private lateinit var btn_signup : Button
    private lateinit var mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()
        mauth = FirebaseAuth.getInstance()

        inp_email = findViewById(R.id.input_email)
        inp_pwd = findViewById((R.id.input_pwd))
        btn_login = findViewById(R.id.login_button)
        btn_signup = findViewById(R.id.signup_button)

        btn_signup.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }

        btn_login.setOnClickListener(){
            val email = inp_email.text.toString()
            val pwd = inp_pwd.text.toString()

            login(email,pwd)
        }
    }

    private fun login(email : String, password:String){
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist! Try signing in!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}