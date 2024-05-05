package com.sajon.dev.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        observeViewModel()
        sharedViewModel.refreshCharacter(23)
    }

    private fun initRecyclerView() {
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun observeViewModel() {
        sharedViewModel.characterByIdResponse.observe(this) { response ->
            if(response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful network call!!", Toast.LENGTH_LONG).show()
                return@observe
            }

            epoxyController.characterResponse = response
        }
    }
}