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

class PasswordDetailFragment : Fragment() {
    private var site: String? = null
    private var password: String? = null
    private lateinit var siteTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var deleteButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            site = it.getString("Site")
            password = it.getString("Password")
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        siteTextView = view.findViewById(R.id.siteNameTextView)
        (getString(R.string.site_name) + ": " + site).also { siteTextView.text = it }
        passwordTextView = view.findViewById(R.id.passwordTextView)
        (getString(R.string.password) + ": " + password).also { passwordTextView.text = it }
        deleteButton = view.findViewById(R.id.deletePasswordButton)
        deleteButton.setOnClickListener { handleDelete() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_return) {
            requireActivity().finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun handleDelete() {
        auth = Firebase.auth
        val currentUser = auth.currentUser?.uid
        database = FirebaseDatabase
            .getInstance("https://passwordsapp-320e7-default-rtdb.europe-west1.firebasedatabase.app").reference
        if (currentUser != null && site != null) {

            database.child(currentUser).child(site!!).setValue(null)
        }
        requireActivity().finish()
    }

    companion object {
        @JvmStatic
        fun newInstance(site: String, password: String) =
            PasswordDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("Site", site)
                    putString("Password", password)
                }
            }
    }
}