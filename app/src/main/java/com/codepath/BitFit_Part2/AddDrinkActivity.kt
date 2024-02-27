package com.codepath.BitFit_Part2


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codepath.BitFit_Part2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AddDrinkActivity"

class AddDrinkActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var volumeAmtTextView: TextView
    private lateinit var sugarAmtTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        titleTextView = findViewById(R.id.drinkTitle)
        volumeAmtTextView = findViewById(R.id.volumeAmt)
        sugarAmtTextView = findViewById(R.id.sugarAmt)


        // TODO: Insert the new Drink into drinks_table, make a DisplayDrink object, add it to the drinks list, and redirct to MainActivity
        findViewById<Button>(R.id.button).setOnClickListener{
            val drinkEntity = DrinkEntity(
                drinkTitle = titleTextView.text.toString(),
                volumeAmt = volumeAmtTextView.text.toString().toDouble(),
                sugarAmt = sugarAmtTextView.text.toString().toDouble()
            )

            lifecycleScope.launch(Dispatchers.IO) {
                (application as DrinkApplication).db.drinkDao().insertOne(drinkEntity)
            }
            // Finish this activity
            finish()
        }

    }
}