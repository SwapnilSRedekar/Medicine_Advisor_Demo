package com.example.medicineadvisor.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.medicineadvisor.R

class SplashScreenActivity : AppCompatActivity() {

    lateinit var splashLogo : ImageView
    lateinit var splashText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashLogo = findViewById(R.id.iv_splashScreenLogo)
        splashText = findViewById(R.id.tv_splashScreenText)

        var topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        var botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        splashLogo.setAnimation(topAnim)
        splashText.setAnimation(botAnim)

        Handler().postDelayed(Runnable {
            var intent = Intent(this,UserRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        },5000)


    }
}