package com.lonchi.andrej.lonchi_bakalarka.ui.camera.adapter

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
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.logic.util.reverseVisibility
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class FoundIngredientsAdapter(
    val context: Context
) : ListAdapter<Ingredient, FoundIngredientsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Ingredient>() {

    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.name == newItem.name
    }

}) {
    private var selectedIngredients: MutableList<Ingredient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_found_ingredient, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.name.text = ingredient.name

        val isSelected = selectedIngredients.firstOrNull { it.id == ingredient.id } != null
        if (isSelected) holder.isSelected.setVisible(true)
        else holder.isSelected.setVisible(false)

        holder.itemView.setOnClickListener {
            handleIngredientSelection(holder, ingredient)
        }
    }

    private fun handleIngredientSelection(holder: ViewHolder, ingredient: Ingredient) {
        holder.isSelected.reverseVisibility()
        val alreadySelected = selectedIngredients.firstOrNull {
            it.id == ingredient.id
        } != null

        if (alreadySelected) selectedIngredients.remove(ingredient)
        else selectedIngredients.add(ingredient)
    }

    fun getSelectedIngredients(): List<Ingredient> = selectedIngredients

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val name = view.findViewById<TextView>(R.id.textIngredient)
        internal val isSelected = view.findViewById<ImageView>(R.id.iconSelected)
    }
}