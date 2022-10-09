package com.example.finalbasicandroid

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalbasicandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listApp: ArrayList<Apps> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //open Home Activity
        binding.btnStartHomeActivity.setOnClickListener() {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        // get all application installed
        val packages: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        packages.forEach { packageInfo: ApplicationInfo ->
            //add infor app to listApp
            listApp.add(
                Apps(
                    packageManager.getApplicationLabel(packageInfo).toString(),
                    packageInfo.packageName.toString(),
                    packageManager.getPackageInfo(
                        packageInfo.packageName,
                        0
                    ).firstInstallTime.toString(),
                    packageManager.getApplicationIcon(packageInfo.packageName)
                )
            )
        }
        setData()

        binding.btnScrollLeft.setOnClickListener() {
            binding.rvApps.smoothScrollToPosition(0)
        }
        binding.btnScrollRight.setOnClickListener() {
            binding.rvApps.smoothScrollToPosition(listApp.size - 1)
        }
        binding.btnSortByName.setOnClickListener() {
            listApp.sortBy {
                it.appName
            }
        }
        binding.btnSortByInstalledTime.setOnClickListener() {
            listApp.sortBy {
                it.installTime
            }
            setData()
        }
    }

    //set data
    private fun setData() {
        val adapter = GridAppAdapter(listApp, this)
        adapter.setOnItemClickListener(object : GridAppAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val app = listApp.get(position)
                val i: Intent? = packageManager.getLaunchIntentForPackage(app.packageName)
                val resolver =
                    i?.let { packageManager.resolveActivity(it, PackageManager.MATCH_DEFAULT_ONLY) }
                //open app installed
                if (resolver != null)
                    startActivity(i)
                else
                    Toast.makeText(this@MainActivity, "App system not open", Toast.LENGTH_SHORT)
                        .show()
            }
        })
        //set GridLayoutManager with 1 column and HORIZONTAL
        binding.rvApps.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.rvApps.adapter = adapter
    }
}