package com.example.todoapp

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.todoapp.DTO.Usuario
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        dbHandler = DBHandler(this)

        val email = findViewById<TextInputEditText>(R.id.register_email_edit_text)
        val password = findViewById<TextInputEditText>(R.id.register_password_edit_text)
        val confirm_password = findViewById<TextInputEditText>(R.id.register_password_confirm_edit_text)

        cadastrar_button.setOnClickListener {
            if (checkIfPasswordAndConfirmIsIgual(password, confirm_password)) {
                createUser(email, password)
            } else {

                Toast.makeText(this, "Favor inserir a senha e confirma-la", Toast.LENGTH_SHORT)
            }
        }

        cancel_button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun checkIfPasswordAndConfirmIsIgual(password: TextInputEditText?, confirm_password: TextInputEditText?): Boolean{
        return password?.text?.length!! > 0  && password?.text.toString() == confirm_password?.text.toString()
    }

    private fun createUser(email: TextInputEditText, password: TextInputEditText){
        val usuario = Usuario()
        usuario.email = email.text.toString()
        usuario.senha = password.text.toString()
        try {
            val response = dbHandler.createUser(usuario)
            if (response){
                startActivity(Intent(this, DashboardActivity::class.java))
            }
        } catch (e: Exception){
            Toast.makeText(this, "Erro ao Cadastrar Usário, tente novamente mas tarde", Toast.LENGTH_SHORT)
        }
    }
}