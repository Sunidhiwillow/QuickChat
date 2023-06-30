package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class UserAdapter(val context: Context,val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current_User = userList[position]
        holder.textName.text = current_User.name
        holder.textDate.text = current_User.date

        // allowing them to click

        holder.itemView.setOnClickListener(){
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name",current_User.name)
            intent.putExtra("uid",current_User.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val textName = itemview.findViewById<TextView>(R.id.txt_name)
        val textDate = itemview.findViewById<TextView>(R.id.txt_logged_in_since)
    }

}