package com.posse.android.softjet.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.posse.android.softjet.MobileNavigationDirections
import com.posse.android.softjet.R
import com.posse.android.softjet.databinding.ActivityMainBinding
import com.posse.android.softjet.utils.NetworkStatus
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkStatus: NetworkStatus

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var isBackShown = false
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initOfflineStatus()
    }

    private fun initNavigation() {
        val navView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_fragment)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            val action = when (it.itemId) {
                R.id.mainFragment -> MobileNavigationDirections.actionGlobalToMainFragment()
                R.id.aboutFragment -> MobileNavigationDirections.actionGlobalToAboutFragment()
                else -> throw RuntimeException("Wrong navigation ID: ${it.itemId}")
            }
            navController.navigate(action)
            true
        }
    }

    private fun initOfflineStatus() {
        CoroutineScope(Dispatchers.Main).launch {
            networkStatus
                .isOnline()
                .collect { isOnline ->
                    val visibility = if (isOnline) View.GONE else View.VISIBLE
                    binding.offlineIndicator.visibility = visibility
                }
        }
    }

    override fun onBackPressed() = with(findNavController(R.id.nav_fragment)) {
        when (currentDestination?.id) {
            R.id.mainFragment -> {
                if (System.currentTimeMillis() - lastTimeBackPressed >= BACK_BUTTON_EXIT_DELAY) {
                    isBackShown = false
                }
                if (isBackShown) exitProcess(0)
                else {
                    Toast
                        .makeText(
                            context,
                            getString(R.string.back_again_to_exit),
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    isBackShown = true
                }
            }
            else -> super.onBackPressed()
        }
        lastTimeBackPressed = System.currentTimeMillis()
    }

    companion object {
        private const val BACK_BUTTON_EXIT_DELAY = 3000
    }
}