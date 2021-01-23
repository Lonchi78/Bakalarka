package com.lonchi.andrej.lonchi_bakalarka.ui.recipes

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
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import coil.load as load1

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class RecipeCardsAdapter(
        val context: Context,
        val onItemClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeCardsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Recipe>() {

    //  TODO - add recipe interface, common id and params
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.idRestApi == newItem.idRestApi
    }

    //  TODO - add recipe interface, common id and params
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.title == newItem.title
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.textName.text = recipe.title

        holder.textTime.text = context.resources.getQuantityString(
                R.plurals.recipe_time_minutes,
            recipe.readyInMinutes ?: 0,
            recipe.readyInMinutes
        )

        holder.textIngredients.text = context.resources.getQuantityString(
                R.plurals.recipe_ingredients_pieces,
            recipe.ingredients?.size ?: 0,
            recipe.ingredients?.size ?: 0
        )

        /*holder.image?.load1(recipe.image) {
            crossfade(true)
            crossfade(R.integer.image_cross_fade_duration)
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }*/
        holder.image?.load1(recipe.image) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }

        holder.card.setOnClickListener { onItemClick(recipe) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val card = view.findViewById<CardView>(R.id.card)
        internal val image = view.findViewById<ImageView>(R.id.image)
        internal val textName = view.findViewById<TextView>(R.id.textName)
        internal val textTime = view.findViewById<TextView>(R.id.textTime)
        internal val textIngredients = view.findViewById<TextView>(R.id.textIngredients)
    }
}