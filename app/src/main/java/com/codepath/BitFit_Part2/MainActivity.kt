package com.codepath.BitFit_Part2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.BitFit_Part2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

//fun createJson() = Json {
//    isLenient = true
//    ignoreUnknownKeys = true
//    useAlternativeNames = false
//}

private const val TAG = "MainActivity/"
//private const val SEARCH_API_KEY = BuildConfig.API_KEY
//private const val ARTICLE_SEARCH_URL =
//    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

const val DRINKS_EXTRA = "DRINKS_EXTRA"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val drinksFragment: Fragment = DrinkListFragment()
        val dashbaordFragment: Fragment = DashboardFragment()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.drinks_log -> fragment = drinksFragment
                R.id.drinks_dashboard -> fragment = dashbaordFragment
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.drinks_log

        // send ur drinks array to the adding screen (DetailsActivity)
        findViewById<Button>(R.id.addNewDrinkBtn).setOnClickListener {
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(this, AddDrinkActivity::class.java)
            startActivity(intent)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.drink_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}