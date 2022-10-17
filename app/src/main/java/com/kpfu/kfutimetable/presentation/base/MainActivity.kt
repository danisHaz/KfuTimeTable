package com.kpfu.kfutimetable.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kpfu.kfutimetable.databinding.ActivityMainBinding
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarFragment
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarScreen
import com.kpfu.kfutimetable.utils.routing.Router

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        router = Router(supportFragmentManager, binding.mainFragmentContainer.id)

        // initial navigation to calendar fragment (now by default)
        router?.navigate(CalendarScreen())
    }

    override fun onStop() {
        super.onStop()
        router = null
    }
    
    fun getGraphRouter(): Router? = router
}