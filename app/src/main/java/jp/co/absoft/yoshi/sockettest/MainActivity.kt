package jp.co.absoft.yoshi.sockettest

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import io.socket.client.IO
import io.socket.client.Socket

import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val socket = IO.socket("http://127.0.0.1:8080")

    /*
        onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b:Button = findViewById(R.id.button)
        b.setOnClickListener{
            socket.emit("from_client", "button pushed!! from android")
        }
    }

    /*
        onResume
     */
    override fun onResume() {
        super.onResume()
        Async().execute()
    }

    inner class Async : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

        }

        override fun doInBackground(vararg param: String?): String {
            socket.connect()
                .on(Socket.EVENT_CONNECT,{ Log.d("tag","connected") })
                .on(Socket.EVENT_DISCONNECT,{ Log.d("tag","disconnected") })
                .on("from_server",  { args -> publishProgress(args[0] as String) })

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            val s : String = editText.getText().toString()
            editText.setText(values[0].toString() + "\n"+  s )
        }

        override fun onPostExecute(result: String?) {

        }
    }



}
