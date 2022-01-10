package com.solvro.topwr.ui.fragments.splashscreen

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ActivitySplashBinding
import com.solvro.topwr.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        animateLogo()
        fakeLongLastingBackgroundTask {
            navigateToMain()
        }
    }

    private fun animateLogo() {
        ValueAnimator.ofFloat(0.8f, 1f).apply {
            duration = 1200
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                binding.logoImageView.scaleX = animatedValue as Float
                binding.logoImageView.scaleY = animatedValue as Float
            }
            start()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //TODO: To refactor
    private fun fakeLongLastingBackgroundTask(onComplete: () -> Unit) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) { delay(2000) }
            onComplete.invoke()
        }
    }


}