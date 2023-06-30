package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var msgRecyclerView : RecyclerView
    private lateinit var msgBox : EditText
    private lateinit var send_btn : ImageButton

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagelist: ArrayList<Message>

    private lateinit var mdbref: DatabaseReference

    // Unique room for sender and rec.

    var recroom: String? = null
    var sendroom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var name = intent.getStringExtra("name")
        var recuid = intent.getStringExtra("uid")

        var senuid = FirebaseAuth.getInstance().currentUser?.uid

        supportActionBar?.title = name
        mdbref = FirebaseDatabase.getInstance().getReference()

        sendroom = recuid+senuid
        recroom = senuid+recuid

        msgRecyclerView = findViewById(R.id.chat_layout)
        msgBox = findViewById(R.id.msg_box)
        send_btn = findViewById(R.id.send_btn)

        messagelist = ArrayList()
        messageAdapter = MessageAdapter(this,messagelist)

        msgRecyclerView.layoutManager = LinearLayoutManager(this)
        msgRecyclerView.adapter = messageAdapter

        // Adding data to rec view

        mdbref.child("chats").child(sendroom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()

                    for(x in snapshot.children){
                        val message = x.getValue(Message :: class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        send_btn.setOnClickListener(){
            val msg = msgBox.text.toString()
            val msgobj = Message(msg,senuid.toString())

            mdbref.child("chats").child(sendroom!!).child("messages").push()
                .setValue(msgobj)
            mdbref.child("chats").child(recroom!!).child("messages").push()
                .setValue(msgobj)

            msgBox.setText("")
        }
    }
}