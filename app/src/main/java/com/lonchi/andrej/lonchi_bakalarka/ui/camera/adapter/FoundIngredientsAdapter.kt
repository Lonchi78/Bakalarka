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
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.logic.util.reverseVisibility
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class FoundIngredientsAdapter(
    val context: Context,
    val onSelectedIngredientsChange: (List<ImageLabelingItem>) -> Unit
) : ListAdapter<ImageLabelingItem, FoundIngredientsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<ImageLabelingItem>() {

    override fun areItemsTheSame(oldItem: ImageLabelingItem, newItem: ImageLabelingItem): Boolean {
        return oldItem.entityId == newItem.entityId
    }

    override fun areContentsTheSame(oldItem: ImageLabelingItem, newItem: ImageLabelingItem): Boolean {
        return oldItem.entityId == newItem.entityId
    }

}) {
    private var selectedIngredients: MutableList<ImageLabelingItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_found_ingredient, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.name.text = ingredient.item

        val isSelected = selectedIngredients.firstOrNull { it.entityId == ingredient.entityId } != null
        if (isSelected) holder.isSelected.setVisible(true)
        else holder.isSelected.setVisible(false)

        holder.itemView.setOnClickListener {
            handleIngredientSelection(holder, ingredient)
        }
    }

    private fun handleIngredientSelection(holder: ViewHolder, ingredient: ImageLabelingItem) {
        holder.isSelected.reverseVisibility()
        val alreadySelected = selectedIngredients.firstOrNull {
            it.entityId == ingredient.entityId
        } != null

        if (alreadySelected) selectedIngredients.remove(ingredient)
        else selectedIngredients.add(ingredient)
        onSelectedIngredientsChange(selectedIngredients)
    }

    fun getSelectedIngredients(): List<ImageLabelingItem> = selectedIngredients

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val name = view.findViewById<TextView>(R.id.textIngredient)
        internal val isSelected = view.findViewById<ImageView>(R.id.iconSelected)
    }
}