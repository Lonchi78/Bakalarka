package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

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
import coil.load as load1

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class IngredientRowsAdapter(
    val context: Context,
    val isCustom: Boolean? = false
) : ListAdapter<Ingredient, IngredientRowsAdapter.ViewHolder>(object :
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

    companion object {
        const val INGREDIENT_IMAGE_BASE_URL_100 = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val INGREDIENT_IMAGE_BASE_URL_250 = "https://spoonacular.com/cdn/ingredients_250x250/"
        const val INGREDIENT_IMAGE_BASE_URL_500 = "https://spoonacular.com/cdn/ingredients_500x500/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient_row, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.ingredientName.text = ingredient.name

        if (isCustom == true) {
            holder.ingredientAmount.text = ingredient.customMeasure
        } else {
            holder.ingredientAmount.text =
                ingredient.measures?.metric?.amount?.toString() + " " + ingredient.measures?.metric?.unitShort
        }

        holder.image?.load1(INGREDIENT_IMAGE_BASE_URL_100 + ingredient.image) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val card = view.findViewById<CardView>(R.id.card)
        internal val image = view.findViewById<ImageView>(R.id.imageIngredient)
        internal val ingredientName = view.findViewById<TextView>(R.id.textIngredientName)
        internal val ingredientAmount = view.findViewById<TextView>(R.id.textIngredientAmount)
    }
}