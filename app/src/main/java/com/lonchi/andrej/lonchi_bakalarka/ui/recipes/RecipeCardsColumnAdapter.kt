package com.lonchi.andrej.lonchi_bakalarka.ui.recipes

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
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import coil.load as load1

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class RecipeCardsColumnAdapter(
        val context: Context,
        val onItemClick: (RecipeItem) -> Unit
) : ListAdapter<RecipeItem, RecipeCardsColumnAdapter.ViewHolder>(object : DiffUtil.ItemCallback<RecipeItem>() {

    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.getId() == newItem.getId()
    }

    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.getId() == newItem.getId()
                && oldItem.getCookingTime() == newItem.getCookingTime()
                && oldItem.getRecipeIdType() == newItem.getRecipeIdType()
                && oldItem.getImageUrl() == newItem.getImageUrl()
                && oldItem.getName() == newItem.getName()
                && oldItem.getNumberOfIngredients() == newItem.getNumberOfIngredients()
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_card_column, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.textName.text = recipe.getName()

        //  TODO - parse time - e.g. 75 mins -> 1 hr 15 mins
        holder.textTime.text = context.resources.getQuantityString(
                R.plurals.recipe_time_minutes,
            recipe.getCookingTime(),
            recipe.getCookingTime()
        )

        holder.textIngredients.text = context.resources.getQuantityString(
                R.plurals.recipe_ingredients_pieces,
            recipe.getNumberOfIngredients(),
            recipe.getNumberOfIngredients()
        )

        holder.image?.load1(recipe.getImageUrl()) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }

        holder.root.setOnClickListener { onItemClick(recipe) }
        holder.backgroundClickable.setOnClickListener { onItemClick(recipe) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val root = view.findViewById<ConstraintLayout>(R.id.root)
        internal val backgroundClickable = view.findViewById<View>(R.id.backgroundClickable)
        internal val image = view.findViewById<ImageView>(R.id.image)
        internal val textName = view.findViewById<TextView>(R.id.textName)
        internal val textTime = view.findViewById<TextView>(R.id.textTime)
        internal val textIngredients = view.findViewById<TextView>(R.id.textIngredients)
    }
}