package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.todoapp.DTO.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dbHandler = DBHandler(this)


        next_button.setOnClickListener({
            val view = layoutInflater.inflate(R.layout.activity_login, null)
            val email = view.findViewById<TextInputEditText>(R.id.email_edit_text)
            val senha = view.findViewById<TextInputEditText>(R.id.password_edit_text)
            Log.d("Print", email.text.toString())
            if (isPasswordValid(email.text!!)){
                startActivity(Intent(this, DashboardActivity::class.java))
            }else {
                Toast.makeText(application, "Informar email ${email.text} e senha ${senha.text}", Toast.LENGTH_LONG).show()
            }
        })

        register_button.setOnClickListener({
            startActivity(Intent(this, RegisterActivity::class.java))
        })
    }

    private fun isPasswordValid(text: Editable?): Boolean{
        return text != null && text.length >= 4
    }
}