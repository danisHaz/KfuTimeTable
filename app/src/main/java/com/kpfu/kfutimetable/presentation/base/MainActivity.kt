package com.kpfu.kfutimetable.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ActivityMainBinding
import com.kpfu.kfutimetable.utils.routing.RouteManager
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isActivityRestarted: Boolean = false

    @Inject
    lateinit var screenProvider: ScreenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.BaseStyle)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RouteManager.initializeRouter(supportFragmentManager, R.id.mainFragmentContainer)
    }

    override fun onStart() {
        super.onStart()

        // initial navigation to calendar fragment (now by default)
        if (!isActivityRestarted) {
            RouteManager.router?.navigate(
                screenProvider.get(ScreenProvider.ScreenType.SignInFragment)
            )
        }
    }

    override fun onRestart() {
        super.onRestart()
        isActivityRestarted = true
    }

    override fun onDestroy() {
        super.onDestroy()
        RouteManager.removeRouter()
    }
}