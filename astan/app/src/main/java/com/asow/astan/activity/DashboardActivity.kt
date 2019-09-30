package com.asow.astan.activity

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.*

import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.asow.astan.R
import com.asow.astan.fragment.JobFragment
import com.asow.astan.fragment.PostJobFragment

import com.asow.astan.fragment.TopListfragment
import com.asow.astan.util.AppPreference
import com.asow.astan.util.ConnectivityReceiver
import com.asow.astan.util.FirestoreSingleTon
import kotlinx.android.synthetic.main.nav_header_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ConnectivityReceiver.ConnectivityReceiverListener {
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isCheckConn = isConnected
        if (!isCheckConn) {
            geoNetView()

        }

    }

    private var myname: String = ""
    private var version: String = ""
    private var link: String = ""
    private lateinit var imgFb: ImageView
    private lateinit var profile: ImageView
    private lateinit var txtTitle: TextView
    private var isCheckConn: Boolean = false
    private var isReceiverRegistered: Boolean = false
    private var boardCast=ConnectivityReceiver()

    private var intetFile=IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        registerReceiver(boardCast,intetFile)
        isReceiverRegistered = true
        ConnectivityReceiver.connectivityReceiverListener = this
        ConnectivityReceiver.connectivityReceiverListener = this
        imgFb = findViewById<ImageView>(R.id.img_fb)

        txtTitle = findViewById(R.id.tv_navi_title)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener(this)
        FirestoreSingleTon.getinstancex()!!.collection("APK").document("apk")
            .get()
            .addOnSuccessListener { s ->
                version = s.getString("v").toString()
                link = s.getString("l").toString()
                tv_version.text = "Version " + version
                tv_my_name.text =
                    AppPreference.getInstance(this@DashboardActivity)!!.getString(AppPreference.StringKeys.PD_USER_ID)
            }
        txtTitle.text = "Job Dashborad"
        setFragment(JobFragment())
        imgFb.setOnClickListener {
            try {
                var uri = Uri.parse("https://www.facebook.com/arniastans/")
                val launchBrowser = Intent(Intent.ACTION_VIEW, uri)
                startActivity(launchBrowser)
            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity, "link error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(boardCast,intetFile)
        isReceiverRegistered = true
        ConnectivityReceiver.connectivityReceiverListener = this
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onStop() {
        super.onStop()
        if (isReceiverRegistered) {
            unregisterReceiver(boardCast)
            isReceiverRegistered = false
        }
    }
    override fun onPause() {
        if (isReceiverRegistered) {
            unregisterReceiver(boardCast)
            isReceiverRegistered = false

        }
        super.onPause()
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

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (count == 0) {

        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                if (isCheckConn) {
                    val inte = Intent(Intent.ACTION_SENDTO)
                    inte.data = Uri.parse("mailto:" + "rmvivek55@gmail.com")
                    inte.putExtra(Intent.EXTRA_SUBJECT, "From astan app")
                    if (inte.resolveActivity(packageManager) != null) {
                        startActivity(inte)
                    }
                    true
                } else {
                    geoNetView()
                    true
                }
            }
            R.id.action_logout -> {
                val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                AppPreference.getInstance(this@DashboardActivity)!!.putString(
                    AppPreference.StringKeys.PD_USER_ID,
                    ""
                )
                AppPreference.getInstance(this@DashboardActivity)!!.putBoolean(
                    AppPreference.BooleanKeys.LOGGED_IN, false
                )
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if (isCheckConn) {
            when (item.itemId) {
                R.id.nav_job -> {
                    txtTitle.text = "Job Dashborad"
                    setFragment(JobFragment())
                }
                R.id.nav_top -> {
                    txtTitle.text = "Top Ranks"
                    setFragment(TopListfragment(false, 0))
                }
                R.id.nav_metting -> {
                    txtTitle.text = "Meeting"
                    setFragment(PostJobFragment("metting", false, null, null, null))
                }
                R.id.nav_book -> {
                    txtTitle.text = "Book Request"
                    setFragment(PostJobFragment("book", false, null, null, null))
                }
                R.id.nav_photo -> {
                    showFb()
                }
                R.id.nav_competition -> {
                    txtTitle.text = "Competition"
                    setFragment(PostJobFragment("competition", false, null, null, null))
                }
                R.id.nav_blood -> {
                    txtTitle.text = "blood doners"
                    setFragment(PostJobFragment("blood", false, null, null, null))
                }
                R.id.nav_apk -> {
                    goDownload()
                }
            }
        } else {
            geoNetView()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFb() {
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Need to astan fb page admin permission")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Ok") { dialogInterface, which ->

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.show()
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
                tv_version.text = "Version " + version
                tv_my_name.text =
                    AppPreference.getInstance(this@DashboardActivity)!!.getString(AppPreference.StringKeys.PD_USER_ID)
            }

        download.setOnClickListener {
            try {

                var uri = Uri.parse(download.text.toString())

                val launchBrowser = Intent(Intent.ACTION_VIEW, uri)
                startActivity(launchBrowser)
                Toast.makeText(this@DashboardActivity, "Goto file manager-> download-> select apk", Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this, "Web link error", Toast.LENGTH_SHORT).show()
            }


            dialog.dismiss()
        }
        imgClose.setOnClickListener {
            txtTitle.text = "Job Dashborad"
            setFragment(JobFragment())

            dialog.dismiss()
        }
        dialog.show()
    }


    fun setFragment(frg: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.framelayout_dashboard, frg).commit()
    }

}


