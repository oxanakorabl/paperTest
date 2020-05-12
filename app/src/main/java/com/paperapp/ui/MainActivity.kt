package com.paperapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paperapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_subscriptions,
                R.id.navigation_map,
                R.id.navigation_lists,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /* ToDo: Auth Repository. sample code. Please move into a correct place */
        /* val authRepository = ComponentHolder.appComponent.getAuthRepository() */
        /* authRepository.doGuestAuth() */

        /* ToDo: Auth Repository. Sample code of email request */
        /* ToDo: Please move into a correct place */
//        ComponentHolder.appComponent.getAuthRepository().requestLoginEmail(
//            email = "serbudnik@gmail.com"
//        )

        /* ToDo: Branch Repository. Sample code of Branch registration initialization */
        /* ToDo: Please move into a correct place */
//        intent?.data?.let { branchData ->
//            val result = ComponentHolder.appComponent.getBranchRepository().init(
//                activity = this,
//                branchData = branchData,
//                onRegistrationSuccess = {
//                    /* ToDo: go to home screen */
//                },
//                onRegistrationFailure = {
//                    /* ToDo: go to login screen */
//                }
//            )
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)

        // ComponentHolder.appComponent.getBranchRepository().reInit(activity = this)
    }
}
