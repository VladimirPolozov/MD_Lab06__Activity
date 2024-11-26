package com.example.md_lab06__activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageButton: Button = findViewById(R.id.btn_show_pic)
        imageButton.setOnClickListener {
            val intent = Intent(this, PicActivity::class.java)
            intent.putExtra("picLink", "https://i.ytimg.com/vi/oWdr9eswCes/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgUyhAMA8=&rs=AOn4CLDNQyohSqmZXfM4iLk0aI_t2Vg1-w")
            startActivity(intent)
        }
    }
}