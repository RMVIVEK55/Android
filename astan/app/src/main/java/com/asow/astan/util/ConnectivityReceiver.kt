package com.asow.astan.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

open class ConnectivityReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ///var bo:Boolean=inte
        connectivityReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context!!))
    }
    private fun isConnectedOrConnecting(context: Context): Boolean {

        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}