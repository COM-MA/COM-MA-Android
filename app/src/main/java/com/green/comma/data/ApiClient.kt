package com.green.comma.data

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object ApiClient {
    private const val baseUrl = "https://com-ma.store:8081"
    private const val contentType = "application/json"
    private const val sampleToken = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzEzMzIyNTQyLCJleHAiOjE3MTU5MTQ1NDJ9.k4UqxxAWf_DBO0YpIZXg-UMqNaI_pe0Fda1X4oivXTg"

    fun getApiClient(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(sslOkHttpClient(context, AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    private fun sslOkHttpClient(context: Context, interceptor: AppInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        try {
            val cf = CertificateFactory.getInstance("X.509")
            val rawFileId = context.resources.getIdentifier("songhayeon", "raw", context.packageName)
            val caInput = context.resources.openRawResource(rawFileId)
            var ca: Certificate? = null
            try {
                ca = cf.generateCertificate(caInput)
                Log.d("[HTTP]", "ca=" + (ca as X509Certificate?)!!.subjectDN)
            } catch (e: CertificateException) {
                e.printStackTrace()
            } finally {
                caInput.close()
            }

            if (ca != null) {
                val keyStoreType = KeyStore.getDefaultType()
                val keyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                keyStore.setCertificateEntry("ca", ca)

                val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
                val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
                tmf.init(keyStore)

                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, tmf.trustManagers, null)

                if (tmf.trustManagers.isNotEmpty() && tmf.trustManagers.first() is X509TrustManager) {
                    okHttpClient.sslSocketFactory(sslContext.socketFactory, tmf.trustManagers.first() as X509TrustManager)
                    okHttpClient.hostnameVerifier { _, _ -> true }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        okHttpClient.addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        return okHttpClient.build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Content-Type", contentType)
                .addHeader("Authorization", sampleToken)
                .build()
            proceed(newRequest)
        }
    }
}