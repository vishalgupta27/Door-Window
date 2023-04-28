package com.glidline.myglidelinss.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.glidline.myglidelinss.R
import com.google.android.material.navigation.NavigationView

class dashboard : AppCompatActivity() {

    var drawer: DrawerLayout? = null
    var toolbar: Toolbar? = null
    var navigationView: NavigationView? = null
    var sharedpreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


         sharedpreferences = getSharedPreferences("login", MODE_PRIVATE)
         sharedpreferences!!.getString("email", "avantika@augurs.in")
         sharedpreferences!!.getString("password","12345678")
         sharedpreferences!!.getBoolean("isLoggedIn",false)
        Log.d("TAG", "onCreate__32: "+sharedpreferences?.getBoolean("isLoggedIn",false))

        drawer = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.NavigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { toggleDrawer() }

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer)
        drawer!!.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        navigationView!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Click Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout -> {
                    // Clear login credentials from SharedPreferences
                    val sharedPref = getSharedPreferences("login", MODE_PRIVATE)
                    sharedPref.edit()
                        .remove("email")
                        .remove("password")
                        .remove("isLoggedIn")
                        .apply()
                      val intent = Intent(this, SignIn::class.java)
                        Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    
                    true
                }
                R.id.profileFragment ->{
                    val intent = Intent(this, CustomerDetailsList::class.java)
                    startActivity(intent)
                    finish()

                    true
                }
           

                else -> {
                    drawer!!.closeDrawer(GravityCompat.START)
                    false
                }
            }
        }


    }

    private fun toggleDrawer() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            // If drawer is open, close it
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            // If drawer is closed, open it
            drawer!!.openDrawer(GravityCompat.START)
        }
    }
    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed()
//            val intent = Intent(this, SignIn::class.java)
//            startActivity(intent)
        }
    }

    }
