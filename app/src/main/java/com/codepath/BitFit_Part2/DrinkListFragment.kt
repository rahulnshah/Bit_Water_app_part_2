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

private const val TAG = "DrinkListFragment"

class DrinkListFragment : Fragment() {

    // Add these properties
    private val drinks = mutableListOf<DisplayDrink>()
    private lateinit var drinksRecyclerView: RecyclerView
    private lateinit var drinksAdapter: DisplayDrinkAdapter
    private lateinit var waterImg : ImageView
    private lateinit var textMsg : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_drink_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        drinksAdapter = DisplayDrinkAdapter(view.context, drinks)
        drinksRecyclerView = view.findViewById(R.id.drinksRv)

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(): DrinkListFragment {
            return DrinkListFragment()
        }
    }

    private fun fetchDrinks() {
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
                        // Show message layout and hide RecyclerView
                        waterImg.visibility = View.VISIBLE
                        textMsg.visibility = View.VISIBLE
                        drinksRecyclerView.visibility = View.GONE
                    }
                    else {
                        // Hide message layout and show RecyclerView
                        val layoutManager = LinearLayoutManager(context)
                        waterImg.visibility = View.GONE
                        textMsg.visibility = View.GONE
                        drinksRecyclerView.visibility = View.VISIBLE

                        // TODO: Set up DisplayArticleAdapter with articles
                        drinksRecyclerView.adapter = drinksAdapter

                        drinksRecyclerView.layoutManager = layoutManager.also {
                            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
                            drinksRecyclerView.addItemDecoration(dividerItemDecoration)
                        }
                    }
                }
            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        waterImg = view.findViewById(R.id.imageView)
        textMsg = view.findViewById(R.id.waterMsg)
        fetchDrinks()
    }
}