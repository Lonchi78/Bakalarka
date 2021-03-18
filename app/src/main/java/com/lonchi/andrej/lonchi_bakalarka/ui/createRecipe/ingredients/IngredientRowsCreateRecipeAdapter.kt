package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class IngredientRowsCreateRecipeAdapter(
    val context: Context,
    val editIngredient: (Ingredient) -> Unit,
    val deleteIngredient: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientRowsCreateRecipeAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Ingredient>() {

    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.id == newItem.id
                && oldItem.image == newItem.image
                && oldItem.measures == newItem.measures
                && oldItem.name == newItem.name
                && oldItem.originalName == newItem.originalName
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient_row_create_recipe, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.ingredientName.text = ingredient.name
        holder.ingredientAmount.text = ingredient.customMeasure
        holder.card.setOnClickListener { editIngredient.invoke(ingredient) }
        holder.iconDelete?.setOnClickListener { deleteIngredient.invoke(ingredient) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val card = view.findViewById<CardView>(R.id.card)
        internal val ingredientName = view.findViewById<TextView>(R.id.textIngredientName)
        internal val ingredientAmount = view.findViewById<TextView>(R.id.textIngredientAmount)
        internal val iconDelete = view.findViewById<ImageView>(R.id.iconIngredientDelete)
    }
}