package com.example.bluein

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mba : BluetoothAdapter ? = null
    var tManager : TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @SuppressLint("MissingPermission")
    var uuid = tManager.getDeviceId()
    
    var myUUID: UUID= UUID.fromString(uuid.toString())
    var btSocket : BluetoothSocket?=null
    private fun CheckBt(){
        Toast.makeText(applicationContext,"going online",Toast.LENGTH_SHORT).show()
        mba=BluetoothAdapter.getDefaultAdapter()

        if (!mba?.enable()!!){
            Toast.makeText(applicationContext,"Bluetooth is off",Toast.LENGTH_SHORT).show()
            finish()
        }
        if (mba == null){
            Toast.makeText(applicationContext,"Bluetooth is null",Toast.LENGTH_SHORT).show()
        }

    }
    fun connect(){
        val device = mba?.getRemoteDevice("00:19:07:34:C3:FD")
        Log.d("","Connecting to ...$device")
        Toast.makeText(applicationContext,"Connecting to ... ${device?.name}  mac: ${device?.uuids?.get(0)}, address: ${device?.address}",Toast.LENGTH_LONG).show()
        mba?.cancelDiscovery()
        try{
            btSocket = device?.createRfcommSocketToServiceRecord(myUUID)
            btSocket?.  connect()
        }catch (e2:IOException){
            Log.d("","Unable to connect")
            Toast.makeText(applicationContext,"Unable to connect",Toast.LENGTH_SHORT).show()
        }
        Log.d("","Socket creation failed")
        Toast.makeText(applicationContext,"Socket creation failed",Toast.LENGTH_SHORT).show()



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mba=BluetoothAdapter.getDefaultAdapter()

    }

}
