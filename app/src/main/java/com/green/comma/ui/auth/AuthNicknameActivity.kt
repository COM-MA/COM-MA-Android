package com.green.comma.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.viewModels
import com.green.comma.CommaApplication
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.ActivityAuthNicknameBinding

class AuthNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthNicknameBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(applicationContext, false) }

    private lateinit var nickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthNicknameBinding.inflate(layoutInflater)

        val randomNickname = intent.getStringExtra("randomNickname").toString()
        nickname = randomNickname

        authViewModel.nicknameResult.observe(this){
            if(it){
                CommaApplication.preferences.setString(getString(R.string.nickname), this.nickname)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "닉네임 등록에 실패했어요.", Toast.LENGTH_SHORT).show()
            }
        }

        setNicknameHint()
        setDeleteTextBtn()
        setCompleteBtn()

        setContentView(binding.root)
    }

    private fun setNicknameHint(){
        val editableNickname = Editable.Factory.getInstance().newEditable(nickname)
        binding.editTextNickname.text = editableNickname
    }

    private fun setDeleteTextBtn(){
        binding.imgBtnDelete.setOnClickListener {
            binding.editTextNickname.text = null
        }
    }

    private fun setCompleteBtn() {
        binding.btnComplete.setOnClickListener {
            val nickname = binding.editTextNickname.text.toString()
            this.nickname = nickname
            authViewModel.postNickname(this.nickname)
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}