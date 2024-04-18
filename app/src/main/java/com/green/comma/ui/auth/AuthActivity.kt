package com.green.comma.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(applicationContext) }
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)

            val serverAuth = account.serverAuthCode
            println(serverAuth)
            if(serverAuth != null){
                val urlServerAuth = serverAuth.substring(0, 1) + "%2F" + serverAuth.substring(2)
                authViewModel.loadGoogleLogin(serverAuth)
            }
            //moveSignUpActivity()

        } catch (e: ApiException) {
            Log.e(AuthActivity::class.java.simpleName, e.stackTraceToString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)

        addListener()

        authViewModel.googleLoginResult.observe(this, Observer { item ->
            println(item)
        })

        setContentView(binding.root)
    }

    private fun addListener() {
        binding.btnGoogleLogin.setOnClickListener {
            requestGoogleLogin()
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.client_id_web)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun moveSignUpActivity() {
        this.run {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}