package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.todoapp.DTO.Usuario
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    private var usuario = Usuario()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dbHandler = DBHandler(this)

        val email = findViewById<TextInputEditText>(R.id.email_edit_text)
        val senha = findViewById<TextInputEditText>(R.id.password_edit_text)


        cadastrar_button.setOnClickListener {
            if (isLoginAndPasswordValid(email, senha)) {
                login(email, senha)
            } else {
                Toast.makeText(
                    application,
                    "Email ou senha inválido, tente novamente!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        register_button.setOnClickListener({
            startActivity(Intent(this, RegisterActivity::class.java))
        })
    }

    private fun isLoginAndPasswordValid(email: TextInputEditText, senha: TextInputEditText): Boolean{
        if (email?.text?.length != 0 && senha?.text?.length != 0){
            return true
        }
        else{
            email.setError("Inserir email")
            senha.setError("Inserir senha")
            return false
        }
    }

    private fun login(email: TextInputEditText, senha: TextInputEditText){
            val usuario = Usuario();
            usuario.email = email.text.toString()
            usuario.senha = senha.text.toString()
            val response = dbHandler.makeLogin(usuario)
            if(response){
                startActivity(Intent(this, DashboardActivity::class.java))
            }else{
                Toast.makeText(application, "Email ou senha invalido, tente novamente ou cadastre-se", Toast.LENGTH_LONG)
            }
    }
}