package com.green.comma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.green.comma.ui.SignInScreen

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