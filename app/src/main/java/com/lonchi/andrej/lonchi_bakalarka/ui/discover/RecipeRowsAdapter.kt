package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import coil.load as load1

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class RecipeRowsAdapter(
    val context: Context,
    val onRecipeClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeRowsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.idRestApi == newItem.idRestApi
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.idRestApi == newItem.idRestApi
                && oldItem.image == newItem.image
                && oldItem.title == newItem.title
    }

}) {

    companion object {
        const val RECIPE_IMAGE_BASE_URL = "https://spoonacular.com/recipeImages/"
        const val RECIPE_HTTPS_PREFIX = "https:"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_row, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.layoutRoot.setOnClickListener { onRecipeClick.invoke(recipe) }

        holder.textName.text = recipe.title
        holder.textCookingTime.text = context.getString(
            R.string.discover_search_recipe_ready_in_minutes,
            recipe.readyInMinutes
        )

        if (recipe.image?.startsWith(RECIPE_HTTPS_PREFIX) == true) {
            holder.image?.load1(recipe.image) {
                placeholder(R.color.gray50)
                error(R.color.gray50)
            }
        } else {
            holder.image?.load1(RECIPE_IMAGE_BASE_URL + recipe.image) {
                placeholder(R.color.gray50)
                error(R.color.gray50)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val layoutRoot = view.findViewById<ConstraintLayout>(R.id.rootLayout)
        internal val image = view.findViewById<ImageView>(R.id.imageRecipe)
        internal val textName = view.findViewById<TextView>(R.id.textRecipeName)
        internal val textCookingTime = view.findViewById<TextView>(R.id.textRecipeCookingTime)
    }
}