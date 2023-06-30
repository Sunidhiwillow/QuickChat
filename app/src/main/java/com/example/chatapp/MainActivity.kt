package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var userrecyclerview : RecyclerView
    private lateinit var userlist : ArrayList<User>
    private lateinit var adapter: UserAdapter

    private lateinit var mauth: FirebaseAuth
    private lateinit var mdbref: DatabaseReference

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mauth = FirebaseAuth.getInstance()

//        Log.d("Year", (day).toString())

        // Nav bar

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navview : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile -> {Toast.makeText(applicationContext,"Profile",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }

                R.id.logout -> {
                    Toast.makeText(applicationContext,"User successfully logged out",Toast.LENGTH_SHORT).show()
                    mauth.signOut()
                    val intent = Intent(this,LogIn::class.java)
                    finish()
                    startActivity(intent)
                }

                R.id.github -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/5hrivathsa")))
                }
            }
            true
        }

        // Firebase

        mdbref = FirebaseDatabase.getInstance().getReference()

        userlist = ArrayList()
        adapter = UserAdapter(this, userlist)

        userrecyclerview = findViewById(R.id.user_recyclerview)

        userrecyclerview.layoutManager = LinearLayoutManager(this)
        userrecyclerview.adapter = adapter

        mdbref.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                userlist.clear()
                for (x in snapshot.children){
                    val curruser = x.getValue(User ::class.java)

                    if (mauth.currentUser?.uid != (curruser as User).uid) {
                        userlist.add(curruser)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}