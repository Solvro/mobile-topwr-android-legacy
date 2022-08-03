package com.solvro.topwr.ui.fragments.splashscreen

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
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
        startAnim()
    }

    private fun startAnim() {
        binding.splashAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(animator: Animator) {
                lifecycleScope.launch {
                    navigateToMain()
                    withContext(Dispatchers.Main) {
                        binding.splashAnim.pauseAnimation()
                        withContext(Dispatchers.Default) {
                            delay(500)
                        }
                        binding.splashAnim.playAnimation()
                    }
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}