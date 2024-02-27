package com.codepath.BitFit_Part2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

const val DRINK_EXTRA = "DRINK_EXTRA"
private const val TAG = "DisplayDrinkAdapter"

class DisplayDrinkAdapter(private val context: Context, private val drinks: List<DisplayDrink>) :
    RecyclerView.Adapter<DisplayDrinkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_drink, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val drink = drinks[position]
        holder.bind(drink)
    }

    override fun getItemCount() = drinks.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val titleTextView = itemView.findViewById<TextView>(R.id.drinkTitle)
        private val volumeTextView = itemView.findViewById<TextView>(R.id.volumeAmt)
        private val sugarTextView = itemView.findViewById<TextView>(R.id.sugarAmt)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(drink: DisplayDrink) {
            titleTextView.text = drink.drinkTitle
            volumeTextView.text = drink.volumeAmt.toString()
            sugarTextView.text = drink.sugarAmt.toString()
        }

        override fun onClick(v: View?) {
            // Get selected article
            val drink = drinks[absoluteAdapterPosition]

            // TODO: Navigate to Details screen and pass selected article
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(DRINK_EXTRA, drink)
//            context.startActivity(intent)
        }
    }
}