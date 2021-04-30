package com.cafrecode.citadel.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cafrecode.citadel.databinding.ActivitySplashBinding
import com.cafrecode.citadel.utils.SharedPrefsUtil

private const val SPLASH_DELAY: Long = 1700 // 1.x seconds

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    private var mDelayHandler: Handler? = null

    private val mRunnable: Runnable = Runnable {

        if (!isFinishing) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // No wallet is registered in, so wait for click
        if (SharedPrefsUtil.getDefaultAddress(this).isNullOrEmpty()) {
            binding.proceedButton.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } else { // Show as self-dismissing splash
            binding.proceedButton.visibility = View.GONE
            complete()
        }
    }

    private fun complete() {

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }
}