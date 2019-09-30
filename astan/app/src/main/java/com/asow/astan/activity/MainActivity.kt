package com.asow.astan.activity

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.asow.astan.R
import com.asow.astan.adapter.JobTabAdapter
import com.asow.astan.util.AppPreference
import com.asow.astan.util.ConnectivityReceiver
import com.asow.astan.util.FirestoreSingleTon

import kotlinx.android.synthetic.main.activity_main.*

import java.util.*

import java.util.Timer as Timer1


class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnection = isConnected

    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private var auth = FirebaseAuth.getInstance()
    private var count: Int = 0
    private var isConnection = false
    private var isReceiverRegistered = false
    private var str_device: String = ""
    private var boardCast=ConnectivityReceiver()

    private var intetFile=IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    var timer: Timer1 = java.util.Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionbar = actionBar
        actionbar?.hide()
        registerReceiver(boardCast,intetFile)
        isReceiverRegistered = true
        ConnectivityReceiver.connectivityReceiverListener = this
        val i = intent
        if (i != null) {
            val bundle = i.getExtras()
            if (bundle != null) {
                val action = bundle!!.getString("notification")
                if (action == "yes")
                    goDownload()
            }
        }
//        if (Build.VERSION.SDK_INT < 16) {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        } else {
//
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//            val actionbar = actionBar
//            actionbar?.hide()
//        }
        btn_google_sign.visibility = View.GONE
        img_main.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {
            img_main.visibility = View.GONE
            btn_google_sign.visibility = View.VISIBLE

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.asow.astan.R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            vp_main.adapter = JobTabAdapter(supportFragmentManager, true)
            var timerTask = object : TimerTask() {
                override fun run() {
                    count = count.inc()
                    vp_main.post(Runnable { vp_main.setCurrentItem((count - 1)) })
                    if (count == 6)
                        count = 0
                }
            }

            timer.schedule(timerTask, 3000, 3000)
            btn_google_sign.setOnClickListener {
                if (isConnection)
                    signIn()
                else
                    geoNetView()
            }

        }, 3000)
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onPause() {
        if (isReceiverRegistered) {
            unregisterReceiver(boardCast)
            isReceiverRegistered = false

        }
        super.onPause()
    }
    private fun goDownload() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.apk_download)
        val vesion = dialog.findViewById(R.id.tv_alert_version) as TextView
        val imgClose = dialog.findViewById<TextView>(R.id.tv_alrt_dwm_close)
        val download = dialog.findViewById(R.id.tv_link_apk) as TextView
        FirestoreSingleTon.getinstancex()!!.collection("APK").document("apk")
            .get()
            .addOnSuccessListener { s ->
                vesion.text = s.getString("v").toString()
                download.text = s.getString("l").toString()


            }

        download.setOnClickListener {
            try {

                var uri = Uri.parse(download.text.toString())

                val launchBrowser = Intent(Intent.ACTION_VIEW, uri)
                startActivity(launchBrowser)
                Toast.makeText(this@MainActivity, "Goto file manager-> download-> select apk", Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this, "Web link error", Toast.LENGTH_SHORT).show()
            }


            dialog.dismiss()
        }
        imgClose.setOnClickListener {


            dialog.dismiss()
        }
        dialog.show()
    }

    fun geoNetView() {
        val dialog = Dialog(this)
        val windowx = dialog.getWindow();
        windowx.setGravity(Gravity.BOTTOM)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.view_check_net)
        val wmlp = getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM
        val ok = dialog.findViewById(R.id.tv_alert_net_ok) as TextView
        ok.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()

      isReceiverRegistered=true
           registerReceiver(boardCast,intetFile)

        ConnectivityReceiver.connectivityReceiverListener = this
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        }
    }

    private fun signIn() {
        pb_job.visibility = View.VISIBLE
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 101)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                pb_job.visibility = View.GONE
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    pb_job.visibility = View.GONE
                    val user = auth.currentUser
                    // Sign in success, update UI with the signed-in user's information
                    AppPreference.getInstance(this@MainActivity)!!.putString(
                        AppPreference.StringKeys.PD_USER_ID,
                        user!!.email!!
                    )
                    AppPreference.getInstance(this@MainActivity)!!.putBoolean(
                        AppPreference.BooleanKeys.LOGGED_IN, true
                    )


                    Toast.makeText(
                        this@MainActivity,
                        AppPreference.getInstance(this@MainActivity)!!.getString(AppPreference.StringKeys.PD_USER_ID),
                        Toast.LENGTH_SHORT
                    ).show()
                    val dashboardActivity = Intent(this@MainActivity, DashboardActivity::class.java)
                    startActivity(dashboardActivity)
                    finish()


                } else {
                    pb_job.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Authentication Failed.", Toast.LENGTH_SHORT).show()

                }

                // ...
            }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}


