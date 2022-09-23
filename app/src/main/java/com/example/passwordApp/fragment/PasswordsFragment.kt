package com.example.passwordApp.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordApp.R
import com.example.passwordApp.activity.AddPasswordActivity
import com.example.passwordApp.adapter.PasswordsAdapter
import com.example.passwordApp.data.Password
import com.example.passwordApp.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class PasswordsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PasswordsAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passwords, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.passwordsRecyclerView)

        fab = view.findViewById(R.id.addPasswordButton)
        fab.setOnClickListener { handleAddPassword() }
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth
        val currentUser = auth.currentUser?.uid
        database = FirebaseDatabase
            .getInstance("https://passwordsapp-320e7-default-rtdb.europe-west1.firebasedatabase.app").reference
        currentUser?.let { it ->
            database.child(it).get().addOnSuccessListener {
                val data = it.value
                    .toString()
                    .replace("{", "")
                    .replace("}", "")
                    .replace(",", "")
                    .split(" ")
                val passwords = emptyList<Password>().toMutableList()
                for (instance in data) {
                    val record = instance.split("=")
                    passwords += Password(currentUser, record[0], record[1])
                }
                adapter = PasswordsAdapter(passwords)
                val orientation = resources.configuration.orientation
                if (Utils.largeScreen(requireContext())) {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        recyclerView.layoutManager = GridLayoutManager(context, 5)
                    } else {
                        recyclerView.layoutManager = GridLayoutManager(context, 3)
                    }
                } else {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        recyclerView.layoutManager = GridLayoutManager(context, 3)
                    } else {
                        recyclerView.layoutManager = GridLayoutManager(context, 1)
                    }
                }

                recyclerView.adapter = adapter
            }.addOnFailureListener {
                println("Error getting data$it")
            }
        }
    }

    private fun handleAddPassword() {
        val intent = Intent(requireContext(), AddPasswordActivity::class.java)
        startActivity(intent)
    }
}