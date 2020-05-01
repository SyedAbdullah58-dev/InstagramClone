package com.example.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_sing_in.*

class SingIn : AppCompatActivity() {
lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        val Progressdilog= ProgressDialog(this)


        signup_button.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))



        }








    Login_authen.setOnClickListener {
        val user_email = email_authentication.text.toString()
        val pass = password_authentication.text.toString()

        when {
            TextUtils.isEmpty(user_email) -> {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    email_authentication.setError("Enter Valid Email")
                    email_authentication.requestFocus()
                    return@setOnClickListener

                }
                return@setOnClickListener
            }
            TextUtils.isEmpty(pass) -> {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else -> {

                Progressdilog.setTitle("Signing in")
                Progressdilog.setMessage("This may take a While, wait...")
                Progressdilog.setCanceledOnTouchOutside(false)
                Progressdilog.show()

auth=FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(user_email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Email : ", "signInWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(
                                this, "Login successful sir",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("password", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT

                            ).show()
Progressdilog.dismiss()
                        }
                    }
            }
        }
    }



    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser!=null){
            val bawa=FirebaseAuth.getInstance().currentUser

            Toast.makeText(this,"welcome Back",Toast.LENGTH_SHORT).show()
            val int=Intent(this,MainActivity::class.java)
            int.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(int)


        }

    }
}

