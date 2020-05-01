package com.example.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.instagramclone.Fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_sing_in.*
import kotlinx.coroutines.delay
import java.util.regex.Pattern

class Signup : AppCompatActivity() {
lateinit var  auth:FirebaseAuth
    lateinit var  Progressdilog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Login_now.setOnClickListener {
            startActivity(Intent(this,SingIn::class.java))

        }


        regbutton.setOnClickListener {
            val name=fullname_reg.text.toString()
            val username=username_reg.text.toString()
            val email=email_reg.text.toString()
            val password=password_reg.text.toString()

            when{
                TextUtils.isEmpty(name)-> {Toast.makeText(this,"Full name is required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener}
                TextUtils.isEmpty(username)->{ Toast.makeText(this,"User name is required",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener}
                TextUtils.isEmpty(email)-> {Toast.makeText(this,"Email is required",Toast.LENGTH_SHORT).show()
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        email_reg.setError("Please enter a valid email")
                        email_reg.requestFocus()
                        return@setOnClickListener
                    }


                    return@setOnClickListener
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                else -> {
                    auth = FirebaseAuth.getInstance()

                    Progressdilog=ProgressDialog(this)
                    Progressdilog.setTitle("Signup")
                    Progressdilog.setMessage("This may take a While, wait...")
                    Progressdilog.setCanceledOnTouchOutside(false)
                    Progressdilog.show()


                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                        if (it.isSuccessful) {
                            saveData(name,username,email)


                            Toast.makeText(this, "account created successfully", Toast.LENGTH_SHORT).show()
FirebaseAuth.getInstance().signOut()
                            val intent = Intent(this, SingIn::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "Unable to create account", Toast.LENGTH_SHORT)
                                .show()
                            Toast.makeText(
                                this,
                                "Check your connection or \n increase password length",
                                Toast.LENGTH_SHORT
                            ).show()
                            Progressdilog.dismiss()

                        }
                        return@addOnCompleteListener

                    }


                }


            }


        }


    }
    private fun saveData(fullname:String,username:String,usermail:String){
        val currentuserid=auth.currentUser?.uid
        val userref= FirebaseDatabase.getInstance().reference.child("/Users")

        val usermap=HashMap<String,Any>()
        usermap["userid"]=currentuserid.toString()
        usermap["fullname"]=fullname.toLowerCase()
        usermap["username"]=username.toLowerCase()
        usermap["email"]=usermail
        usermap["bio"]="Hey, I am a Human What about you !"
        usermap["image"]="gs://instagramclone-f3788.appspot.com/profile.png"
        userref.child(currentuserid.toString()).setValue(usermap).addOnCompleteListener {
            if(it.isSuccessful){

               Toast.makeText(this,"data Saved as $currentuserid",Toast.LENGTH_LONG).show()

            }
            else
            {
Toast.makeText(this,"Data not saved",Toast.LENGTH_LONG).show()
                Progressdilog.dismiss()
            }

        }

    }


}

