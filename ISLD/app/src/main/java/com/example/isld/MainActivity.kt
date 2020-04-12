package com.example.isld

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.get

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.view.*


class MainActivity : AppCompatActivity() {
    private var count : Int = 1

    private fun aDD(count : Int):Boolean{
        var tl : TableLayout = findViewById(R.id.main_list)
        var row : TableRow = TableRow(this)
        var tv : TextView = TextView(this)
        var ite : EditText = EditText(this)
        var qua : EditText = EditText(this)
        tv.setText((count+1).toString())
        tl.addView(row)
        row.addView(tv)
        ite.setId(1000+count+1.toInt())
        row.addView(ite)
        qua.setId(2000+count+1.toInt())
        row.addView(qua)
        return true
    }

    private fun collectMess():String{
        var tableData : TableLayout = findViewById<TableLayout>(R.id.main_list)
        var msg : String = ""
        var Name : EditText = findViewById(R.id.name)
        msg+=(Name.text).toString()
        msg+=" \n"
        var addr : EditText = findViewById(R.id.address)
        msg+=(addr.text).toString()
        msg+=" \n"
        var mRow = tableData.getChildAt(1.toInt())
        var t2 : EditText = mRow.findViewById(R.id.item1)
        var t3 : EditText = mRow.findViewById(R.id.quan1)
        msg += 1.toString()+" - "+(t2.text).toString()+"  "+(t3.text).toString()
        msg+="\n"
        for (i in 2..((tableData.childCount))){
            var mRow = tableData.getChildAt(i.toInt())
            var t2 : EditText = mRow.findViewById(1000+i.toInt())
            var t3 : EditText = mRow.findViewById(2000+i.toInt())
            msg += i.toString()+" - "+(t2.text).toString()+"  "+(t3.text).toString()
            msg+="\n"

        }
        return msg
    }
    private fun sendMess(msg:String){
        var phone : TextView = findViewById(R.id.phone)
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "https://api.whatsapp.com/send?phone=91"+(phone.text).toString()+"&text="+msg.toString()
            )
        )
        intent.setPackage("com.whatsapp")
        startActivity(intent)
        finish()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        aDD(count=count)
        count++
        aDD(count=count)
        count++
        aDD(count=count)
        count++
        aDD(count=count)
        count++
        val btn_AR : Button = findViewById<Button>(R.id.AR)
        btn_AR.setOnClickListener{
            aDD(count=count)
            count++
        }
        val btn_WM : Button = findViewById<Button>(R.id.WM)
        btn_WM.setOnClickListener{
            var tableData : TableLayout = findViewById<TableLayout>(R.id.main_list)
            var msg : String = ""
            var Name : EditText = findViewById(R.id.name)
            msg+=(Name.text).toString()
            msg+=" \n"
            var addr : EditText = findViewById(R.id.address)
            msg+=(addr.text).toString()
            msg+=" \n"
            var mRow = tableData.getChildAt(1.toInt())
            var t2 : EditText = mRow.findViewById(R.id.item1)
            var t3 : EditText = mRow.findViewById(R.id.quan1)
            msg += 1.toString()+" - "+(t2.text).toString()+"  "+(t3.text).toString()
            msg+="\n"
            sendMess(msg=msg)

            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
