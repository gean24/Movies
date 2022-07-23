package com.mauro.reto.core.ui.base

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.mauro.reto.MainActivity
import com.mauro.reto.core.event.UnauthorizedEvent
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


// Easy to switch base activity in future
typealias BaseActivity = DaggerActivity

/**
 * Base activity providing Dagger support and [ViewModel] support
 */
@AndroidEntryPoint
abstract class DaggerActivity : AppCompatActivity(){

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    fun onUnauthorizedEvent(e: UnauthorizedEvent?) {
        handleUnauthorizedEvent()
    }

    protected open fun handleUnauthorizedEvent() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "La sesión ha expirado");
        startActivity(intent)
        finish()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}

