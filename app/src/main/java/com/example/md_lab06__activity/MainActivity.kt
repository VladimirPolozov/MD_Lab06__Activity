package com.example.md_lab06__activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException

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

        Timber.plant(Timber.DebugTree())
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1").build()
        val urls: MutableList<String> = mutableListOf()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body
                val json = responseBody.string()
                val data = Gson().fromJson(json, Wrapper::class.java)
                val photos = data.photos.photo
                for (photoIndex in photos.indices step 5) {
                    urls.add("https://farm${photos[photoIndex].farm}.staticflickr.com/${photos[photoIndex].server}/${photos[photoIndex].id}_${photos[photoIndex].secret}_z.jpg")
                }

                withContext(Dispatchers.Main) {
                    displayImages(urls)
                }
            } catch (e: IOException) {
                Timber.e("Ошибка: ${e.message}")
            }
        }
    }

    private fun displayImages(linksList: List<String>) {
        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = GridListAdapter(linksList, this)
    }

    fun onImageClick(link: String) {
        val intent = Intent(this, PicViewer::class.java)
        intent.putExtra("picLink", link)
        startActivity(intent)
        Timber.i(link)
    }
}