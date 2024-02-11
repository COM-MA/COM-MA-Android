package com.example.com_ma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.com_ma.ui.OnBoardingScreen
import com.example.com_ma.ui.SignInScreen

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen {
                println("버튼 클릭")
            }
            /*Surface(
                modifier = Modifier.fillMaxSize().padding(85.dp)) {
                OnBoardingScreen()
            }*/
        }
        //setContentView(R.layout.activity_login)
    }
}