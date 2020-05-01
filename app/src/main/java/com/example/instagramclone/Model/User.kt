package com.example.instagramclone.Model

class User {
    private var fullname:String=""
    private var username:String=""
    private var image:String=""
    private var uid:String=""
    private var bio:String=""

constructor()

   constructor(fullname: String, username: String, image: String, uid: String, bio: String) {
        this.fullname = fullname
        this.username = username
        this.image = image
        this.uid = uid
        this.bio = bio

    }


    fun getusername():String{
        return  username

    }
fun setusername(username: String){
    this.username=username

}
    fun getfullname():String{
        return  fullname

    }
    fun setfullname(fullname:String){
        this.fullname=fullname

    }
    fun getbio():String{
        return  bio

    }
    fun setbio(bio: String){
        this.bio=bio

    }
    fun getimage():String{
        return  image

    }
    fun setimage(image: String){
        this.image=image

    }
    fun getUID():String{
        return  uid

    }
    fun setuid(uid: String){
        this.uid=uid
    }







}
