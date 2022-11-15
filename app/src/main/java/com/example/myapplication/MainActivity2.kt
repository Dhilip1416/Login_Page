package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.util.*
import java.util.regex.Pattern


var databaseReference: DatabaseReference? = null
var dataBaseInfo: DataBaseInfo? = null

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val username = findViewById<EditText>(R.id.et1)
        val password = findViewById<EditText>(R.id.et2)
        val confirm_password = findViewById<EditText>(R.id.et3)
        val etemail = findViewById<EditText>(R.id.email)
        val Tl2 = findViewById<TextInputLayout>(R.id.textinput2)
        val Sign_up = findViewById<Button>(R.id.btn)

        val firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("LoginInfo")
        dataBaseInfo = DataBaseInfo()

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val pass = charSequence.toString()
                if (pass.length >= 1) {
                    val pattern = Pattern.compile("[^a-zA-Z0-9]")
                    val matcher = pattern.matcher(pass)
                    val ispasspl = matcher.find()
                    if (ispasspl) {
                        Tl2.setHelperText("Strong Password")
                        Tl2.setError("")
                    } else {
                        Tl2.setHelperText("")
                        Tl2.setError("Weak Password. Include minimum 1 Special char.")
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        Sign_up.setOnClickListener { view: View? ->
            pass = Objects.requireNonNull(password.text).toString().trim { it <= ' ' }
            confirmpass =
                Objects.requireNonNull(confirm_password.text).toString().trim { it <= ' ' }
            email = Objects.requireNonNull(etemail.text).toString()
            user = Objects.requireNonNull(username.text).toString()
            if (user!!.isEmpty()) {
                Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show()
            }
            if (pass!!.isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }
            if (confirmpass!!.isEmpty()) {
                Toast.makeText(this, "Enter Confirm_Password", Toast.LENGTH_SHORT).show()
            }
            if (confirmpass != pass) {
                Toast.makeText(
                    this,
                    "Password and Confirm_password is not same",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!email!!.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Log.d("Email", "Verified")
            }
            else {
                Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show()
            }
            if (user!!.isNotEmpty() && confirmpass!!.isNotEmpty() && pass!!.isNotEmpty() && confirmpass == pass
                && email!!.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            ) {
                Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
                addDatatoFirebase(user, confirmpass, email)
                finish()
            } else {
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun addDatatoFirebase(name: String?, phone: String?, address: String?) {
        dataBaseInfo!!.userName = user
        dataBaseInfo!!.userpass = confirmpass
        dataBaseInfo!!.setemailAddress(email)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                databaseReference!!.setValue(dataBaseInfo)
                Log.d("Data", "Data Saved")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data ", "Data Not Saved")
            }
        })
    }

    companion object {
        var pass: String? = null
        var confirmpass: String? = null
        var email: String? = null
        var user: String? = null
    }
}