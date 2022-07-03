package jacs.apps.monkeytest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login = findViewById<Button>(R.id.login)
        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val sharedPref = this.getSharedPreferences("githubactions", Context.MODE_PRIVATE)
        val gotoMonkeyTest = sharedPref.getBoolean("loggedin", false)
        if (gotoMonkeyTest) {
            val intent = Intent(this, MainActivity3::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        login.setOnClickListener {
            if (email.text.toString() == "copypasteearth@gmail.com" && password.text.toString() == "test123") {
                startActivity(Intent(this, MainActivity2::class.java))
            }
        }

    }
}