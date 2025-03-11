package com.example.dnmotors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.bRegister).setOnClickListener {
            val name = findViewById<EditText>(R.id.etName).text.toString().trim()
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()
            val phone = findViewById<EditText>(R.id.etPhone).text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {
                auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods ?: emptyList()
                        if (signInMethods.isEmpty()) {
                            registerUser(email, password, name, phone)
                        } else {
                            Toast.makeText(this, "Email is already registered", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, name: String, phone: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "phone" to phone
                )
                FirebaseFirestore.getInstance().collection("users").document(auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d("MyLog", "User registered successfully: ${auth.currentUser?.uid}")
                        checkAuthState()
//                        finish() // Закрываем активность после успешной регистрации

                    }
                    .addOnFailureListener {
                        Log.d("MyLog", "Firestore write failed: ${it.message}")
                    }
            } else {
                Log.d("MyLog", "Registration failed: ${task.exception?.message}")
                Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAuthState() {
        Log.d("MyLog", "Checking auth state: ${auth.currentUser?.uid}")
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("OPEN_FRAGMENT", "CarFragment") // Передаём ключ
            startActivity(intent)
            finish()
        } else {
            Log.d("MyLog", "Auth state check failed: user is null")
        }
    }

}
