package com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter

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

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class DietRowsAdapter(
    val context: Context,
    val removeDiet: (AddDietsObject) -> Unit,
    val addDiet: (AddDietsObject) -> Unit
) : ListAdapter<AddDietsObject, DietRowsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<AddDietsObject>() {

    override fun areItemsTheSame(oldItem: AddDietsObject, newItem: AddDietsObject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AddDietsObject, newItem: AddDietsObject): Boolean {
        return oldItem.name == newItem.name
                && oldItem.isChecked == newItem.isChecked
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_diets, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addDietsObject = getItem(position)
        holder.name.text = addDietsObject.name
        handleIcon(holder, addDietsObject.isChecked)

        holder.root.setOnClickListener {
            if (addDietsObject.isChecked) {
                //  Diet is selected yet -> remove diet
                handleIcon(holder, false)
                removeDiet.invoke(addDietsObject)
            } else {
                //  Diet is not selected yet -> add diet
                handleIcon(holder, true)
                addDiet.invoke(addDietsObject)
            }
        }
    }

    private fun handleIcon(holder: ViewHolder, isChecked: Boolean) {
        holder.icon.setImageResource(
            if (isChecked) R.drawable.ic_check_active
            else R.drawable.ic_check_inactive
        )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val root = view.findViewById<ConstraintLayout>(R.id.itemDietRoot)
        internal val name = view.findViewById<TextView>(R.id.textName)
        internal val icon = view.findViewById<ImageView>(R.id.iconCheck)
    }
}