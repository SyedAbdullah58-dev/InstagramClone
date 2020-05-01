package com.example.instagramclone.UserAdapter


import android.content.Context


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import com.example.instagramclone.Fragments.profileFragment
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class UserAdapter (private var mycontext:Context,private var myuser:List<User>,private var isFragment: Boolean=false):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view=LayoutInflater.from(mycontext).inflate(R.layout.user_layout,parent,false)

   return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return myuser.size
    }


    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Imageview=itemView.findViewById<ImageView>(R.id.profile_search)
        val username=itemView.findViewById<TextView>(R.id.profile_search_text)
        val profilename=itemView.findViewById<TextView>(R.id.profile_search_text2)
        val followbutton=itemView.findViewById<Button>(R.id.follow_button)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user=myuser[position]
        holder.username.text=user.getusername()
        holder.profilename.text=user.getfullname()
        val baawa =holder.profilename.text
        println("mera bawa : $baawa")


        Picasso.get().load(user.getimage()).placeholder(R.drawable.profile)


        checkfollowStatus(user.getUID(),holder.followbutton)
        holder.itemView.setOnClickListener {
            val pref= mycontext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit()
            pref.putString("profileID",user.getUID())
            pref.apply()
            (mycontext as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.mainframe,profileFragment()).commit()

        }

holder.followbutton.setOnClickListener {

    if (holder.followbutton.text.toString() == "Follow") {
        var bawa=FirebaseAuth.getInstance().currentUser
        bawa?.uid.let {
            FirebaseDatabase.getInstance().reference.child("Follow").child(it.toString())
                .child("Following").child(user.getUID()).setValue(true).addOnCompleteListener {
                    if(it.isSuccessful){
                        bawa?.uid.let {
                            FirebaseDatabase.getInstance().reference.child("Follow").child(user.getUID()).child("Following").child(it.toString()).setValue(true)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                    }

                                }
                        }

                    }


        }

    }
}
    else
    {

        var bawa=FirebaseAuth.getInstance().currentUser
        bawa?.uid.let {
            FirebaseDatabase.getInstance().reference.child("Follow").child(it.toString())
                .child("Following").child(user.getUID()).removeValue().addOnCompleteListener {
                    if(it.isSuccessful){
                        bawa?.uid.let {
                            FirebaseDatabase.getInstance().reference.child("Follow").child(user.getUID()).child("Following").child(it.toString()).removeValue()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                    }
                                }
                        }
                    }
                }
        }
    }
    }
}

private fun checkfollowStatus(uid:String,followbutton:Button){
    var bawa=FirebaseAuth.getInstance().currentUser


  var reftouser=  bawa?.uid.let {
      FirebaseDatabase.getInstance().reference.child("Follow").child(it.toString())
          .child("Following")

  }
    reftouser.addValueEventListener(object :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onDataChange(p0: DataSnapshot) {
            if(p0.child(uid).exists()){
                followbutton.setText("Following")
            }
else{
                followbutton.setText("Follow")
            }
        }
    })
}
}

