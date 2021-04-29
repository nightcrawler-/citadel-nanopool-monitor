package com.cafrecode.citadel.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cafrecode.citadel.R
import com.cafrecode.citadel.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        requestPermissions()
    }

    ///PERMISSION THINGS
    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun requestPermissions() {

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_ALL_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_ALL_PERMISSIONS && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                var permission = permissions[i]
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    var showRationale: Boolean =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            shouldShowRequestPermissionRationale(permission)
                        } else {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }
                    if (!showRationale) {
                        Toast.makeText(this, "PLEASE GRANT ALL PERMISSIONS", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, REQUEST_ALL_PERMISSIONS)
                    }
                    Toast.makeText(this, "ALL REQUESTED PERMISSIONS REQUIRED", Toast.LENGTH_LONG)
                        .show()
                    this.finish()
                }
            }
            try {
                requestPermissions()
                Log.i(TAG, "permission granted")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private const val REQUEST_ALL_PERMISSIONS = 204

        private val PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}