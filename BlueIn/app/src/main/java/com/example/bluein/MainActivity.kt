package com.example.bluein

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.*
import java.io.IOException
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mba : BluetoothAdapter ? = null

    var count:Int = 0
    var uuid:UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private fun aDD(count : Int,data:String):Boolean{
        var tl : TableLayout = findViewById(R.id.main_list)
        var row : TableRow = TableRow(this)
        var tv : TextView = TextView(this)
        var tim : TextView = TextView(this)
        var msg : TextView = TextView(this)
        tv.setText((count+1).toString())
        tl.addView(row)
        row.addView(tv)
        tim.setId(1000+count+1.toInt())
        val date=Calendar.getInstance().time
        tim.setText(date.toString())
        row.addView(tim)
        msg.setId(2000+count+1.toInt())
        msg.setText(data)
        row.addView(msg)
        return true
    }
    //var myUUID: UUID= UUID.fromString(uuid.toString())
    var btSocket : BluetoothSocket?=null
    private fun checkBt(): Boolean {
        Toast.makeText(applicationContext,"going online",Toast.LENGTH_SHORT).show()
        mba=BluetoothAdapter.getDefaultAdapter()
        var verdict: Boolean = false
        if (!mba?.enable()!!){
            Toast.makeText(applicationContext,"Bluetooth is off",Toast.LENGTH_SHORT).show()
            finish()
        }
        else if (mba == null){
            Toast.makeText(applicationContext,"Bluetooth is null",Toast.LENGTH_SHORT).show()
            verdict=false
        }
        else{verdict=true}
        return verdict

    }
    private fun connect():Boolean{
        val device = mba?.getRemoteDevice("00:19:07:34:C3:FD")
        Log.d("","Connecting to ...$device")
        Toast.makeText(applicationContext,"Connecting to ... ${device?.name}  mac: ${device?.uuids?.get(0)}, address: ${device?.address}",Toast.LENGTH_LONG).show()
        mba?.cancelDiscovery()
        var verdict:Boolean=false
        try{
            btSocket = device?.createRfcommSocketToServiceRecord(uuid)
            btSocket?.connect()
            verdict= true
        } catch (e: IOException) {
        try {
            btSocket?.close()
        } catch (e2: IOException) {
            Log.d("", "Unable to end the connection")
            Toast.makeText(applicationContext, "Unable to end the connection", Toast.LENGTH_SHORT).show()
        }

        Log.d("", "Socket creation failed")
        Toast.makeText(applicationContext, "Socket creation failed", Toast.LENGTH_SHORT).show()
        }
        return verdict
    }
    fun inp(count: Int):Boolean{
        var input: InputStream? = btSocket?.inputStream
        var dataByte = ByteArray(8)
        var verdict = false
        if (input?.available()!! >0){
            input.read(dataByte)
            val data :String = String(dataByte)
            verdict=aDD(count, data)
        }

        return verdict

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mba=BluetoothAdapter.getDefaultAdapter()
        if (checkBt()){
            connect()
        }/*
        while(true){
            if(inp(count=count)){
                count+=1
            }
        }*/

    }

}
