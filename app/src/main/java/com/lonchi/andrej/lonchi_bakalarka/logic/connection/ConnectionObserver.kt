package com.lonchi.andrej.lonchi_bakalarka.logic.connection

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ConnectionObserver(val context: Context) : LiveData<ConnectionObserver.ConnectionState>() {
    sealed class ConnectionState(private val connected: Boolean) {
        class Unavailable : ConnectionState(false)
        open class Connected(private val type: Int) : ConnectionState(true)
        class MobileOnly : Connected(1)
        class Wifi : Connected(1)
        class Ethernet : Connected(2)
        class OtherConnection : Connected(3)
    }

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkAvailableRequest() {
        val builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(builder.build(), getConnectivityManagerCallback())
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                updateConnection()
            }

            override fun onLost(network: Network?) {
                updateConnection()
            }
        }
        return connectivityManagerCallback
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    private fun updateConnection() =
        postValue(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) parseNetworkCapabilities(connectivityManager.activeNetwork)
            else parseLegacyNetwork(connectivityManager.activeNetworkInfo)
        )

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun parseLegacyNetwork(networkInfo: NetworkInfo?): ConnectionState =
        if (networkInfo?.isConnected == true) {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_ETHERNET -> ConnectionState.Ethernet()
                ConnectivityManager.TYPE_WIFI -> ConnectionState.Wifi()
                ConnectivityManager.TYPE_MOBILE -> ConnectionState.MobileOnly()
                else -> ConnectionState.OtherConnection()
            }
        } else {
            ConnectionState.Unavailable()
        }

    @TargetApi(Build.VERSION_CODES.M)
    fun parseNetworkCapabilities(activeNetwork: Network?): ConnectionState {
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return when {
            capabilities == null -> ConnectionState.Unavailable()
            !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> ConnectionState.Unavailable()
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionState.Ethernet()
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionState.Wifi()
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionState.MobileOnly()
            else -> ConnectionState.OtherConnection()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                getConnectivityManagerCallback()
            )
            else -> lollipopNetworkAvailableRequest()
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    fun isAnyNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}