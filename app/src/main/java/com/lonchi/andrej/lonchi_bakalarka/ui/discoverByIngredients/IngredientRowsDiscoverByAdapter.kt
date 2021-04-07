package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.lonchi_bakalarka.R

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class IngredientRowsDiscoverByAdapter(
    val context: Context,
    val deleteIngredient: (String) -> Unit
) : ListAdapter<String, IngredientRowsDiscoverByAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient_discover_by_ingredients_row, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.ingredientName.text = ingredient
        holder.iconDelete?.setOnClickListener { deleteIngredient.invoke(ingredient) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val ingredientName = view.findViewById<TextView>(R.id.textIngredientName)
        internal val iconDelete = view.findViewById<ImageView>(R.id.iconIngredientDelete)
    }
}