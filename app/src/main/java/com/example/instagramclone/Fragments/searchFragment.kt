package com.example.instagramclone.Fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Model.User

import com.example.instagramclone.R
import com.example.instagramclone.UserAdapter.UserAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search.view.search_field

/**
 * A simple [Fragment] subclass.
 */
class searchFragment : Fragment() {
private var recyclerView:RecyclerView?=null
    private var userAdapter:UserAdapter?=null
    private var myuser:MutableList<User>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view1=inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView=view1.findViewById(R.id.search_rec)
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager=LinearLayoutManager(context)
        myuser=ArrayList()
        userAdapter=context?.let { UserAdapter(it,myuser as ArrayList<User>,true) }
        recyclerView?.adapter=userAdapter
println("Search k andar")

view1?.search_field?.addTextChangedListener(object:TextWatcher {

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (search_field.text.toString().isEmpty()) {
searchFragment()
        } else {
            println("on text changed rec vis")
            recyclerView?.visibility = View.VISIBLE
            ReteriveUser()
            searcuser(s.toString().toLowerCase())

        }

    }

})






        return view1
searchFragment()

    }

    private fun starter(){


    }



    private fun ReteriveUser(){
    println("retrive user k ander")
    Log.d("searchuser","in retrive user")
       val userRef=FirebaseDatabase.getInstance().getReference().child("User")
    userRef.addValueEventListener(object:ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            Log.d("searchuser","Data cancelled")
        }

        override fun onDataChange(p0: DataSnapshot) {
            println("data change k ander")
            if(search_field.text.toString()==""){

                myuser?.clear()
                for (snapshot in p0.children){
                    val user=snapshot.getValue(User::class.java)
                    if(user!=null){

                        myuser?.add(user)
                        println("addedd user : $user")
                        println("users are : $myuser")
                    }

                }

            }
        }
    })

    }

    private fun searcuser(input:String){
        Log.d("searchuser","in search user")
        val query= FirebaseDatabase.getInstance().reference.child("Users")
            .orderByChild("fullname").startAt(input).endAt(input + "\uf8ff")


        query.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            Log.d("searchuser","Data cancelled")

            }

            override fun onDataChange(p0: DataSnapshot) {
                myuser?.clear()

                    for (snapshot in p0.children){
                        println("snapshot for of seachuser")
                        val user=snapshot.getValue(User::class.java)
                        if(user!=null){
                            myuser?.add(user)
                            return
                        }

                    }


            }
        })

    }

}
