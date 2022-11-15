package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.regex.Pattern


    var firebaseDatabase: FirebaseDatabase? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val username = findViewById<EditText>(R.id.et1)
        val password = findViewById<EditText>(R.id.et2)
        val TIL2 = findViewById<TextInputLayout>(R.id.textinput2)
        val Sign_Up = findViewById<Button>(R.id.btn2)
        TIL2.setOnClickListener { view: View? ->
            Toast.makeText(
                this@MainActivity,
                "Password Visible",
                Toast.LENGTH_SHORT
            ).show()
        }
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val pass = charSequence.toString()
                if (pass.length >= 10) {
                    val pattern = Pattern.compile("[^a-zA-Z0-9]")
                    val matcher = pattern.matcher(pass)
                    val ispasspl = matcher.find()
                    if (ispasspl) {
                        TIL2.helperText = "Strong Password"
                        TIL2.error = ""
                    } else {
                        TIL2.helperText = ""
                        TIL2.error = "Weak Password. Include minimum 1 Special char."
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        val Login = findViewById<Button>(R.id.btn1)
        Login.setOnClickListener { view: View? ->
            if (Objects.requireNonNull(username.text)
                    .toString() == MainActivity2.user && Objects.requireNonNull(password.text)
                    .toString() == MainActivity2.confirmpass
            ) {
                Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Login Failed !!!!", Toast.LENGTH_SHORT).show()
            }
        }

        Sign_Up.setOnClickListener { view: View? ->
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

}