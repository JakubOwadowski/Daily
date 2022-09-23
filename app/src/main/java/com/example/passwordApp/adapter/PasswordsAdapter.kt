package com.example.passwordApp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordApp.R
import com.example.passwordApp.activity.PasswordDetailActivity
import com.example.passwordApp.data.Password

class PasswordsAdapter(private val passwords: List<Password>) :
    RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return PasswordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.passowrds_item_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val password = passwords[position]
        holder.siteNameView.text = password.site
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PasswordDetailActivity::class.java)
            intent.putExtra("Site", password.site)
            intent.putExtra("Password", password.password)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var siteNameView: TextView = itemView.findViewById(R.id.siteName)
    }
}