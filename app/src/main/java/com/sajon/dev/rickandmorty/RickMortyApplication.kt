package com.sajon.dev.rickandmorty

import android.app.Application
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RickMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        trustAllCertificates()
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
    private fun trustAllCertificates() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(TrustAllCertificates())
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}