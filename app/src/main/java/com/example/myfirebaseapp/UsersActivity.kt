package com.example.myfirebaseapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    lateinit var progressDialog: ProgressDialog
    var adapter: FirestoreRecyclerAdapter<User, UserViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        db = Firebase.firestore
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("uploading image....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        Handler().postDelayed({progressDialog.dismiss()},2000)
        getAllUser()
//        progressDialog.dismiss()

    }

    fun getAllUser() {
        val query = db!!.collection("usermaps")
        val options =
            FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<User, UserViewHolder >(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): UserViewHolder {
                var view = LayoutInflater.from(this@UsersActivity)
                    .inflate(R.layout.user_item, parent, false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                holder.name.text = model.name
                holder.phone.text = model.number
                holder.address.text = model.address
//                holder.delete.setOnClickListener {
//
//                }
                holder.delete.setOnClickListener {
                    var alertDialog= AlertDialog.Builder(this@UsersActivity)
                    alertDialog.setTitle("Delete")
                    alertDialog.setMessage("Are you sure?")
                    alertDialog.setCancelable(false)
                    alertDialog.setIcon(R.drawable.ic_delete_black_24dp)

                    alertDialog.setPositiveButton("Yes") { dialogInterface, i ->
                        db!!.collection("usermaps").whereEqualTo("id",model.id)
                            .get()
                            .addOnSuccessListener { querySnapshot ->

                                db!!.collection("usermaps").document(querySnapshot.documents[0].id)
                                    .delete()
                                    .addOnSuccessListener { querySnapshot ->
                                        Toast.makeText(
                                            applicationContext,
                                            "deleted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }.addOnFailureListener { exception ->
                                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            }
//                        notifyItemRangeChanged(position,data.size)
                        notifyDataSetChanged()
                        Toast.makeText(applicationContext, "Yes", Toast.LENGTH_SHORT).show()
                    }

                    alertDialog.setNegativeButton("No") { dialogInterface, i ->
                        Toast.makeText(applicationContext, "No", Toast.LENGTH_SHORT).show()
                    }

                    alertDialog.setNeutralButton("Cancel") { dialogInterface, i ->
                        Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
                    }

                    alertDialog.create().show()

                }

            }

//
        }
        all_user_recycle.layoutManager = LinearLayoutManager(this)
        all_user_recycle.adapter = adapter

    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.tv_name
        var phone = view.tv_phone
        var address = view.tv_address
        var delete = view.delete
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


}