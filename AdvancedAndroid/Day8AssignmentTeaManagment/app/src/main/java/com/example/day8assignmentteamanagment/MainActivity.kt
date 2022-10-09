package com.example.day8assignmentteamanagment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.day8assignmentteamanagment.databinding.ActivityMainBinding
import com.example.day8assignmentteamanagment.fragment.HomeFragmentDirections
import com.example.day8assignmentteamanagment.vm.WorkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //@Inject
    //private lateinit var sharedPreference: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: WorkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setup action bar and fragment host
        setSupportActionBar(binding.toolBar)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController
        setupActionBarWithNavController(navController)
        //reminder tea
        viewModel.enqueuePeriodicReminder()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort -> {
                val action = HomeFragmentDirections.actionHomeFragmentToUserFragment()
                navController.navigate(action)
            }
        }
        return false
    }

    //back
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}