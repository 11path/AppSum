package com.example.appsum2


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Referenciar los elementos de la interfaz
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        val loginButton: Button = findViewById(R.id.loginButton)

        // Configurar la acción del botón de login
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    // Método para manejar el inicio de sesión
    private fun loginUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si la autenticación es exitosa, ir a MenuActivity
                    val user: FirebaseUser? = mAuth.currentUser
                    val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Si la autenticación falla, mostrar un mensaje
                    Toast.makeText(this@LoginActivity, "Autenticación Fallida.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
