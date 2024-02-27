package com.codepath.BitFit_Part2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    // Add these properties
    private val drinks = mutableListOf<DisplayDrink>()
    private lateinit var drinksAdapter: DisplayArticleAdapter
    private lateinit var totalDrinksTextView: TextView
    private lateinit var totalSugarTextView: TextView
    private lateinit var maxLitersTextView : TextView
    private lateinit var minLitersTextView: TextView
    private lateinit var favoriteDrinkTextView: TextView
    private lateinit var totalWaterTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // Add these configurations for the recyclerView and to configure the adapter
        drinksAdapter = DisplayArticleAdapter(view.context, drinks)
        return view
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    private fun setDrinksData() {
        lifecycleScope.launch {
            // activity? returns the activity this fragment is associated with, which is MainActivity
            (activity?.application as DrinkApplication).db.drinkDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayDrink(
                        entity.drinkTitle,
                        entity.volumeAmt,
                        entity.sugarAmt
                    )
                }.also { mappedList ->
                    drinks.clear()
                    drinks.addAll(mappedList)
                    drinksAdapter.notifyDataSetChanged()

                    // Check if drinks array is empty
                    if (drinks.isEmpty()) {
                        // set all data values to 0 initially
                        totalDrinksTextView.text = "0"
                        totalSugarTextView.text = "0"
                        minLitersTextView.text = "0"
                        maxLitersTextView.text = "0"
                    }
                    else {

                        // loop the drinks array to find the min, max, total sugars and total liters consumed

                        // ?: is the Elvis operator
                        val maxVolumeDrink : DisplayDrink? = drinks.maxByOrNull { it.volumeAmt ?: Double.MIN_VALUE }
                        val minVolumeDrink : DisplayDrink? = drinks.minByOrNull { it.volumeAmt ?: Double.MAX_VALUE }
                        val totalSugar = drinks.sumOf { it.sugarAmt ?: 0.0 }
                        val mostFrequentDrink : String? = drinks.groupingBy { it.drinkTitle }
                            .eachCount()
                            .maxByOrNull { it.value }
                            ?.key
                        val totalWaterVolume = drinks.filter { it.drinkTitle == "Water" }
                            .sumOf { it.volumeAmt ?: 0.0 }

                        // Fill up the text boxes with the values
                        totalDrinksTextView.text = drinks.size.toString()
                        minLitersTextView.text = minVolumeDrink?.volumeAmt.toString()
                        maxLitersTextView.text = maxVolumeDrink?.volumeAmt.toString()
                        totalSugarTextView.text = totalSugar.toString()
                        favoriteDrinkTextView.text = mostFrequentDrink
                        totalWaterTextView.text = totalWaterVolume.toString()
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        totalDrinksTextView = view.findViewById(R.id.totalDrinks)
        totalSugarTextView = view.findViewById(R.id.totalSugar)
        minLitersTextView = view.findViewById(R.id.minLiters)
        maxLitersTextView = view.findViewById(R.id.maxLiters)
        favoriteDrinkTextView = view.findViewById(R.id.favoriteDrink)
        totalWaterTextView = view.findViewById(R.id.totalLitersOfWater)
        setDrinksData()
    }
}