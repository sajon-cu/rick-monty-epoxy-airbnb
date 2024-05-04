package com.sajon.dev.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<AppCompatTextView>(R.id.nameTextView)
        val aliveText = findViewById<AppCompatTextView>(R.id.aliveTextView)
        val originTextView = findViewById<AppCompatTextView>(R.id.originTextView)
        val speciesTextView = findViewById<AppCompatTextView>(R.id.speciesTextView)
        val headerImageView = findViewById<AppCompatImageView>(R.id.headerImageView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(provideHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        rickAndMortyService.getCharacterById(10).enqueue(object : Callback<GetCharacterByIdResponse> {
            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                Log.d("MainActivity", "onResponse: ${response.toString()}")
                if(!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Unsuccessful network call!!", Toast.LENGTH_LONG).show()
                    return
                }

                trustAllCertificates()

                val body= response.body()!!
                Log.d("MainActivity", "onResponse: $body")
                //Picasso.get().load(body.image).into(headerImageView)
                Glide.with(headerImageView).load(body.image).into(headerImageView)
                nameTextView.text = body.name
                originTextView.text = body.origin.name
                speciesTextView.text = body.species
            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.d("MainActivity", "onResponse: ${t.message}}")
            }
        })
    }

    class TrustAllCertificates : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    }

    // Install the trust manager to trust all certificates
    fun trustAllCertificates() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(TrustAllCertificates())
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun provideHttpClient(): OkHttpClient {
        val pool = ConnectionPool()

        // Create a trust manager that does not validate certificate chains
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .connectionPool(pool)
                // .addInterceptor(httpLoginInterceptor)
                .build()
        } catch(exception: Exception) {
            return OkHttpClient.Builder()
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .connectionPool(pool)
                // .addInterceptor(httpLoginInterceptor)
                .build()
        }
    }
}