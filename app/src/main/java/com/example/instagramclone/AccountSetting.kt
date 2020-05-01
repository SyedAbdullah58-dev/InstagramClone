package com.example.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSetting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        Logout.setOnClickListener {

            val Progressdilog= ProgressDialog(this)
            Progressdilog.setTitle("Logging Out")
            Progressdilog.setMessage("This may take a While, wait...")
            Progressdilog.setCanceledOnTouchOutside(false)
            Progressdilog.show()
            FirebaseAuth.getInstance().signOut()
            val into=Intent(this,SingIn::class.java)
            into.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(into)


        }
    }
}
