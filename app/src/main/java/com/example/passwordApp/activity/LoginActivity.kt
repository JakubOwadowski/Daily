package com.example.passwordApp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.passwordApp.R
import com.example.passwordApp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private var login: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.logInButton.setOnClickListener { handleButton() }
        binding.changeModeButton.setOnClickListener { handleModeSwap() }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            goToHomeScreen()
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "sign in:success")
                    goToHomeScreen()
                } else {
                    Log.w(TAG, "sign in:failure", task.exception)
                    Toast.makeText(baseContext, resources.getString(R.string.signUp_failed),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "log in:success")
                    goToHomeScreen()
                } else {
                    Log.w(TAG, "log in:failure", task.exception)
                    Toast.makeText(
                        baseContext, resources.getString(R.string.signUp_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun handleButton() {
        if (binding.editTextTextUsername.text.toString() == "") {
            Toast.makeText(baseContext, resources.getString(R.string.username_cannot_be_blank), Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.editTextTextPassword.text.toString() == "") {
            Toast.makeText(baseContext, resources.getString(R.string.password_cannot_be_blank), Toast.LENGTH_SHORT).show()
            return
        }
        if (login) {
            logIn(binding.editTextTextUsername.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        } else {
            signUp(binding.editTextTextUsername.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        }
    }

    private fun handleModeSwap() {
        if (login) {
            login = false
            binding.logInButton.text = resources.getString(R.string.signUp)
            binding.changeModeButton.text = resources.getString(R.string.logIn_prompt)
        } else {
            login = true
            binding.logInButton.text = resources.getString(R.string.logIn)
            binding.changeModeButton.text = resources.getString(R.string.signIn_prompt)
        }
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}