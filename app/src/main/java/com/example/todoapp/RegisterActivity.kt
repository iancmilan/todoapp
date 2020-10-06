package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_register_user.setOnClickListener({
            val view = layoutInflater.inflate(R.layout.activity_register, null)
            val email = view.findViewById<EditText>(R.id.register_email)
            val password = view.findViewById<EditText>(R.id.register_password)
            val confirm_password = view.findViewById<EditText>(R.id.register_password_confirm)

        })
    }
}