package com.example.medicareconnect

import android.content.Intent
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var radioGroupRole: RadioGroup
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        edUsername = findViewById(R.id.edUsername)
        edPassword = findViewById(R.id.edPassword)
        edConfirmPassword = findViewById(R.id.edConfirmPassword)
        radioGroupRole = findViewById(R.id.radioGroupRole)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = edUsername.text.toString().trim()
            val password = edPassword.text.toString()
            val selectedId = radioGroupRole.checkedRadioButtonId
            val confirm = edConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val role = when (selectedId) {
                R.id.radioDoctor -> "doctor"
                R.id.radioPatient -> "patient"
                else -> "patient"
            }

//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, LoginActivity::class.java))
//                        finish()
//                    } else {
//                        val message = task.exception?.message ?: "Lỗi không xác định"
//                        Toast.makeText(this, "Đăng ký thất bại: $message", Toast.LENGTH_SHORT).show()
//                    }
//                }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        val user = hashMapOf(
                            "email" to email,
                            "role" to role
                        )

                        // Lưu vào Firestore
                        db.collection("users").document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Lỗi khi lưu vai trò: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        val message = task.exception?.message ?: "Lỗi không xác định"
                        Toast.makeText(this, "Đăng ký thất bại: $message", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val btnToLogin : TextView = findViewById(R.id.tvToLogin)
        btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}