package com.example.redpanda2023_kattee

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        analytics = Firebase.analytics
        val bundle = Bundle()

        val btnPurchase = findViewById<Button>(R.id.btn_Purchase) as Button

        btnPurchase.setOnClickListener {
            Toast.makeText(this@MainActivity, "Check Out.", Toast.LENGTH_SHORT).show()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1001")
            bundle.putDouble(FirebaseAnalytics.Param.VALUE, 99.99)
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "THB")
            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345")
            analytics.logEvent(FirebaseAnalytics.Event.PURCHASE,bundle)

        }

        val btnEcommercePurchase = findViewById<Button>(R.id.btn_Ecommerce_Purchase) as Button

        btnEcommercePurchase.setOnClickListener {
            Toast.makeText(this@MainActivity, "Check Out.", Toast.LENGTH_SHORT).show()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3003")
            bundle.putDouble(FirebaseAnalytics.Param.VALUE, 555.44)
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "SGD")
            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T54321")
            analytics.logEvent("ecommerce_purchase",bundle)

        }

        val btnlevelup = findViewById<Button>(R.id.btn_level_up)
        btnlevelup.setOnClickListener {
            Toast.makeText(this@MainActivity, "You Level Up.", Toast.LENGTH_SHORT).show()
            bundle.putInt("DepositAmount", 777)
            analytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP, bundle)
        }
        val btnlevelup2 = findViewById<Button>(R.id.btn_level_up2)
        btnlevelup2.setOnClickListener {
            Toast.makeText(this@MainActivity, "You Level Up.", Toast.LENGTH_SHORT).show()
            bundle.putInt("DepositAmount", 999)
            analytics.logEvent("level_up5",bundle)
        }

        val btnChecknetwork = findViewById<Button>(R.id.btn_checknetwork) as Button
        btnChecknetwork.setOnClickListener {
            val params = Bundle()
            val currentTimestamp = System.currentTimeMillis().toString()
            val txt_networkStatus = findViewById<EditText>(R.id.txt_networkStatus)
            when(isConnected( this)){
                0 -> {
                    Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show()
                    txt_networkStatus.setText("Not Connected - No Internet").toString()
                    params.putString("Timestamp", currentTimestamp)
                    analytics.logEvent("Not_Connected", params)
                }
                1 -> {
                    Toast.makeText(this, "WIFI", Toast.LENGTH_SHORT).show()
                    txt_networkStatus.setText("Connected - WIFI").toString()
                    params.putString("Timestamp", currentTimestamp)
                   analytics.logEvent("Connected_WIFI", params)
                }
                2 -> {
                    Toast.makeText(this, "Cellular", Toast.LENGTH_SHORT).show()
                    txt_networkStatus.setText("Connected - Cellular").toString()
                    params.putString("Timestamp", currentTimestamp)
                    analytics.logEvent("Connected_Cellular", params)
                }
            }
        }
    }

    private fun isConnected(context: Context): Int {
        var connectionType = 0
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            val bundle = Bundle()
            networkCapabilities?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    connectionType = 1
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    connectionType = 2
                }
            }
        return connectionType
    }
}