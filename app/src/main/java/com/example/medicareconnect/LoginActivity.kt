package com.example.medicareconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnToRegister: TextView
    private lateinit var btnGoogleSignIn: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById(R.id.etUsername)
        edtPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
//        btnGoogleSignIn.setOnClickListener {
//            googleSignInClient.signOut().addOnCompleteListener {
//                val signInIntent = googleSignInClient.signInIntent
//                startActivityForResult(signInIntent, RC_SIGN_IN)
//            }
//        }
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (email == "admin" && password == "admin"){
                startActivity(Intent(this, DoctorActivity::class.java))
                finish()
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    val error = task.exception?.message ?: "Lỗi không xác định"
                    Toast.makeText(this, "Đăng nhập thất bại: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}