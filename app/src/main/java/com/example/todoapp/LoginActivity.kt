package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
            val email = view.findViewById<TextInputLayout>(R.id.email_text_input)
            val senha = view.findViewById<TextInputLayout>(R.id.email_text_input)
            if (!isPasswordValid(email.editText?.text!!)){
                startActivity(Intent(this, DashboardActivity::class.java))
            }else {
                Toast.makeText(application, "Informar email ${email.editText?.text} e senha ${senha.editText?.text}", Toast.LENGTH_LONG).show()
            }
        })

        cancel_button.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        })
    }

    private fun isPasswordValid(text: Editable?): Boolean{
        return text != null && text.length >= 4
    }
}