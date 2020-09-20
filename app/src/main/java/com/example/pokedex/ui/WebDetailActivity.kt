package com.example.pokedex.ui

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R
import kotlinx.android.synthetic.main.activity_web_detail.*

class WebDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_detail)
        val name = intent.getStringExtra("name")

        detail_web.webChromeClient = object : WebChromeClient(){

        }

        detail_web.webViewClient = object : WebViewClient(){

        }

        val settings = detail_web.settings
        settings.javaScriptEnabled=true
        detail_web.loadUrl("https://pokemon.fandom.com/es/wiki/$name")
    }
}
