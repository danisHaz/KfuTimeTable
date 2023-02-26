package com.kpfu.kfutimetable.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.TopSheetDialog.TopSheetDialog
import com.kpfu.kfutimetable.databinding.ActivityMainBinding
import com.kpfu.kfutimetable.utils.UserSession
import com.kpfu.kfutimetable.utils.routing.RouteManager
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menuDialog: TopSheetDialog? = null
    private var isActivityRestarted: Boolean = false

    @Inject
    lateinit var screenProvider: ScreenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.BaseStyle)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuDialog = TopSheetDialog(this, R.style.TopSheet).apply {
            window?.attributes?.windowAnimations = -1
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setContentView(R.layout.layout_top_slidable_menu)
        }
        setListeners()

        RouteManager.initializeRouter(supportFragmentManager, R.id.mainFragmentContainer)
    }

    override fun onStart() {
        super.onStart()

        if (!isActivityRestarted) {
            UserSession.executeOnInitCompletion { user ->
                val initialFragment = if (user != null) {
                    ScreenProvider.ScreenType.CalendarFragment
                } else {
                    ScreenProvider.ScreenType.SignInFragment
                }
                Log.w("kek","$user")
                RouteManager.router?.navigate(
                    screenProvider.get(initialFragment),
                    addToBackStack = false
                )
            }
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

    fun disableToolbar() {
        binding.toolbar.root.visibility = View.GONE
    }

    fun enableToolbar() {
        binding.toolbar.root.visibility = View.VISIBLE
    }

    private fun setListeners() = with(binding) {
        toolbar.menu.setOnClickListener {
            if (menuDialog?.isShowing == true) {
                menuDialog?.hide()
            } else {
                menuDialog?.show()
            }
        }
        toolbar.avatar.setOnClickListener {

            Log.e("kek", "${UserSession.user}")
            RouteManager.router?.navigate(
                screenProvider.get(ScreenProvider.ScreenType.AccountFragment)
            )
        }

        menuDialog?.layout?.let<FrameLayout, Unit> { frameLayout ->
            val buttonAccount = frameLayout.findViewById<Button>(R.id.buttonAccount)
            val sendReport = frameLayout.findViewById<Button>(R.id.sendReport)
            val faq = frameLayout.findViewById<Button>(R.id.faq)
            val exit = frameLayout.findViewById<Button>(R.id.exit)
            val timetable = frameLayout.findViewById<Button>(R.id.timetable)

            listOf(
                Pair(buttonAccount, ScreenProvider.ScreenType.AccountFragment),
                Pair(timetable, ScreenProvider.ScreenType.CalendarFragment),
                Pair(faq, ScreenProvider.ScreenType.FqqFragment),
                Pair(sendReport, ScreenProvider.ScreenType.FeedbackFragment),
            ).forEach { pair ->
                pair.first?.setOnClickListener { view ->
                    menuDialog?.cancelWithAction {
                        RouteManager.router?.navigate(screenProvider.get(pair.second))
                    }
                }
            }

            exit?.setOnClickListener {
                menuDialog?.cancelWithAction {
                    RouteManager.router?.clearBackStack()
                    RouteManager.router?.navigate(
                        screenProvider.get(ScreenProvider.ScreenType.SignInFragment),
                        addToBackStack = false
                    )
                    UserSession.update(newUser = null, context = applicationContext)
                }
            }
        }
    }
}