package com.example.passwordApp.fragment

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.passwordApp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddPasswordFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var site: TextView
    private lateinit var password: TextView
    private lateinit var acceptButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_password, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        site = view.findViewById(R.id.SiteNameTextView)
        password = view.findViewById(R.id.PasswordTextView)
        acceptButton = view.findViewById(R.id.acceptButton)
        acceptButton.setOnClickListener { writePassword() }
        database = FirebaseDatabase
            .getInstance("https://passwordsapp-320e7-default-rtdb.europe-west1.firebasedatabase.app").reference
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_return) {
            requireActivity().finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun writePassword() {
        val currentUser = auth.currentUser?.uid
        try {
            if (currentUser != null) {
                database
                    .child(currentUser)
                    .child(site.text.toString())
                    .setValue(password.text.toString())
            }
        } catch (e: Exception) {
            println(e)
        }
        requireActivity().finish()
    }
}