package com.example.instagramclone.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import com.example.instagramclone.AccountSetting
import com.example.instagramclone.Model.User

import com.example.instagramclone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class profileFragment : Fragment() {
lateinit var profileid:String
 lateinit   var fullstring:String
    lateinit var Firebase:FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.edit_profile_settings.setOnClickListener {
            val buttext = edit_profile_settings.text.toString()
            when {
                buttext == "Edit Profile" -> {
                    startActivity(Intent(context, AccountSetting::class.java))
                }
                buttext == "Follow" -> {
                    var bawa = FirebaseAuth.getInstance().currentUser
                    var reftouser = bawa?.uid.let {
                        FirebaseDatabase.getInstance().reference.child("Follow")
                            .child(it.toString())
                            .child("Following").child(profileid).setValue(true)
                    }

                }

            }


            Firebase = FirebaseAuth.getInstance().currentUser!!
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
            println("prefrences : ${pref.toString()}")
            if (pref != null) {
                this.profileid = pref.getString("profileID", "none").toString()
                println("myprofileid : $profileid")
            }
            if (profileid == Firebase.uid) {
                view.edit_profile_settings.text = "Edit Profile"
            } else if (profileid != Firebase.uid) {
                checkfollowandFollowingButtonstatus()
            }
            getFollowers()
            getFollowing()
            // userinfo()

        }

        return view
    }



private fun checkfollowandFollowingButtonstatus(){


    var reftouser=
        FirebaseDatabase.getInstance().reference.child("Follow").child(profileid)
            .child("Following")


    reftouser.addValueEventListener(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            TODO("Not yet implemented")
        }
        override fun onDataChange(p0: DataSnapshot) {
            if(p0.child(profileid).exists()){
                view?.edit_profile_settings?.text="Following"
            }
else{
                view?.edit_profile_settings?.text="Follow"

            }

        }


    })
}
    private fun getFollowers(){


        var followerRef= FirebaseDatabase.getInstance().reference.child("Follow").child(profileid)
                .child("Following")

        followerRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                view?.NO_of_followers?.text=p0.childrenCount.toString()
                }
            }
            }
        )}

    private fun getFollowing(){

        val followerRef= FirebaseDatabase.getInstance().reference.child("Follow").child(profileid)
                .child("Following")

        followerRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    view?.NO_of_followings?.text=p0.childrenCount.toString()
                }
            }
        }
        )


    }
        private fun userinfo() {
            val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(profileid)
            println("profileid is : $profileid")
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0.exists()) {

                        val user = p0.getValue(User::class.java)
                        println("pehly")
                        println("dodo : ${Firebase.uid} and ${user?.getUID()}")

                        println("baad")



                        println("mera user :$user ")
                        view?.profile_user_name?.text = user?.getusername().toString()
                        var bawa = user?.getusername()
                        println("username: $bawa")
                        view?.fullname?.text = user?.getfullname()
                        view?.bio_profile?.text = user?.getbio()
                        var dodo = user?.getbio()
                        //        Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(view?.profile_image_nice)
                    }


                }
            }
            )


        }

    override fun onStop() {
        super.onStop()
        val pref=context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileID",Firebase.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref=context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileID",Firebase.uid)
        pref?.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        val pref=context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileID",Firebase.uid)
        pref?.apply()
    }

    override fun onStart() {
        super.onStart()
val bawa=        FirebaseAuth.getInstance().currentUser?.uid
        Toast.makeText(context,"me hon ${bawa.toString()} and i get ${bawa?.get(3)}",Toast.LENGTH_LONG).show()
    }
}





