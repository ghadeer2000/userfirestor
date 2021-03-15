package com.example.myfirebaseapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val db = FirebaseFirestore.getInstance()
        db = Firebase.firestore

        save.setOnClickListener {
            if (username.text.isNotEmpty() && username.text.isNotEmpty() && username.text.isNotEmpty()) {

                var name = username.text.toString()
                var phone = number.text.toString()
                var addressuser = address.text.toString()
                var id = UUID.randomUUID().toString()
                addUser(id, name, phone, addressuser)

                username.text.clear()
                number.text.clear()
                address.text.clear()

            }

        }
        getdata.setOnClickListener {
            progressDialog = ProgressDialog(this)
            progressDialog.setMessage("uploading image....")
            progressDialog.setCancelable(false)
            progressDialog.show()

            var i = Intent(this, UsersActivity::class.java)
            startActivity(i)
            progressDialog.dismiss()

        }


    }

    private fun addUser(id: String, name: String, number: String, address: String) {
        var user = hashMapOf("id" to id, "name" to name, "number" to number, "address" to address)
        db!!.collection("usermaps").add(user).addOnSuccessListener { documentReference ->
            Log.e("TAG", "success")

        }.addOnFailureListener { exception ->
            Log.e("TAG", "Failed $exception")
        }
    }


}
//class MainActivity : AppCompatActivity() {
//
//    var db: FirebaseFirestore? = null
//    lateinit var auth: FirebaseAuth
//    lateinit var usern: String
//    lateinit var numbern: String
//    lateinit var addressn: String
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        auth = Firebase.auth
//        db = FirebaseFirestore.getInstance()
//        usern = username.text.toString()
//        numbern = number.text.toString()
//        addressn = address.text.toString()
//        var usermap = HashMap<String, Any>()
//
//        save.setOnClickListener {
////            if (usern != null && number != null && address != null) {
//                addUser(UUID.randomUUID().toString(), usern, numbern, addressn)
//
////                usermap.put("name", user)
////                usermap.put("number", number)
////                usermap.put("address", address)
////                db!!.collection("usermaps").add(list).addOnSuccessListener { documentReference ->
////                    Log.e("Success", documentReference.id)
////                Log.e("Success", "usermaps")
////                Log.e("Success", "$usern")
////                    addUser(UUID.randomUUID().toString(), user, number, address)
//                var intent = Intent(this, UsersActivity::class.java)
//                startActivity(intent)
////                }.addOnFailureListener { exception ->
////                    Log.e("failed", "error")
////
////                }
//
////                db!!.collection("usermaps")
//
////            }
//        }
//    }
//
//
//    //    private fun addUser(id: String, name: String, number: String, address: String) {
////        var user =
////            hashMapOf(
////                "id" to id,
////                "name" to name,
////                "number" to number,
////                "address" to address
////            )
////
////        db!!.collection("usermaps").add(user).addOnSuccessListener { documentReference ->
////            Log.e("Document ID", documentReference.id)
////
////        }.addOnFailureListener { exception ->
////
////        }
////
////    }
//    private fun addUser(id: String,name: String, number: String, address: String) {
//        var user =
//            hashMapOf(
//                "id" to id,
//                "name" to name,
//                "number" to number,
//                "address" to address
//            )
//        db!!.collection("usermaps").add(user).addOnSuccessListener { documentReference ->
//            Log.e("TAG", " successf")
//
//        }.addOnFailureListener { exception ->
//            Log.e("TAG", " Failed $exception")
//        }
//    }
//
//}
//
