package com.asow.astan.util

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreSingleTon() {


    companion object {


        @SuppressLint("StaticFieldLeak")
        var instancex: FirebaseFirestore? = null

        fun getinstancex(): FirebaseFirestore? {
            if (instancex == null) {
                instancex = FirebaseFirestore.getInstance()
                return instancex
            } else
                return instancex
        }
    }
}