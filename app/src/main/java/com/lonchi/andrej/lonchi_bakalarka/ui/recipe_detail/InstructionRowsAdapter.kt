package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Instruction
import timber.log.Timber

/**
 *  @author Andrej Lončík <andrejloncik@gmail.com>
 * */
class InstructionRowsAdapter(
    val context: Context
) : ListAdapter<Instruction, InstructionRowsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Instruction>() {

    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.step == newItem.step
                && oldItem.number == newItem.number
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_instruction_row, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instruction = getItem(position)
        Timber.d("onBindViewHolder instruction: ${instruction}")
        Timber.d("onBindViewHolder position: ${position}")
        holder.instructionNumber.text = (position + 1).toString()
        holder.instructionStep.text = instruction.step
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val instructionNumber = view.findViewById<TextView>(R.id.textInstructionNumber)
        internal val instructionStep = view.findViewById<TextView>(R.id.textInstructionStep)
    }
}