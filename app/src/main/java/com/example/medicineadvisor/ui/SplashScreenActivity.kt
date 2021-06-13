package com.example.medicineadvisor.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.medicineadvisor.R
import com.example.medicineadvisor.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var splashScreenBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        var topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        var botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        splashScreenBinding.ivSplashScreenLogo.setAnimation(topAnim)
        splashScreenBinding.tvSplashScreenText.setAnimation(botAnim)

        Handler().postDelayed(Runnable {
            var intent = Intent(this,UserRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        },5000)

    }
}