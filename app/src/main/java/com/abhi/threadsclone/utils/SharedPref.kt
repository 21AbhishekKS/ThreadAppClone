package com.abhi.threadsclone.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPref {

    fun storeData(name : String,email : String , bio : String, userName : String, imageUrl : String,context: Context){
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        val editer = sharedPreferences.edit()

        editer.putString("name",name)
        editer.putString("email",email)
        editer.putString("bio",bio)
        editer.putString("userName",userName)
        editer.putString("imageUrl",imageUrl)
        editer.apply()
    }

    fun getUserName(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("userName","")!!
    }

    fun getName(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }

    fun getemail(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }

    fun getbio(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }

    fun getimageUrl(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl","")!!
    }

}