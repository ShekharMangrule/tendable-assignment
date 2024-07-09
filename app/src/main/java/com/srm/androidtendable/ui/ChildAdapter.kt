package com.srm.androidtendable.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srm.androidtendable.R
import com.srm.androidtendable.databinding.ChildListItemBinding
import com.srm.androidtendable.model.Question

class ChildAdapter(
    private val question: List<Question>,
    private val catName: String,
    private val callback: OnAnswerSelection
) : RecyclerView.Adapter<ChildAdapter.AnswersViewHolder>() {

    interface OnAnswerSelection {
        fun onAnswerClick(questions: List<Question>)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersViewHolder {
        val binding = ChildListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnswersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswersViewHolder, position: Int) {
        holder.radioButton4.visibility = View.GONE
        val answers = question[position].answerChoices
        holder.questionText.text = question[position].name

        holder.radioButton1.text = answers[0].name
        holder.radioButton2.text = answers[1].name
        holder.radioButton3.text = answers[2].name
        if (catName == "Overall Impressions") {
            holder.radioButton3.text = answers[3].name
        }

        holder.radioGroup.setOnCheckedChangeListener(null)
        holder.radioGroup.clearCheck()

        /*when (answers[position].selectedAnswer) {
            0 -> holder.radioButton1.isChecked = true
            1 -> holder.radioButton2.isChecked = true
            2 -> holder.radioButton3.isChecked = true
        }*/

        // Handle new selections
        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Log.i("SRM", "radioButton1 click ${answers[position].name}")
            var selectedID: Int = -1
            when (checkedId) {

                R.id.radioButton1 -> {
                    selectedID = 0
                }
                R.id.radioButton2 -> {
                    selectedID = 1
                }
                R.id.radioButton3 -> {
                    selectedID = 2
                }
                R.id.radioButton4 -> {
                    selectedID = 3
                }
            }
            question[position].selectedAnswerChoiceId = answers[selectedID].id
            callback.onAnswerClick(question)
        }
    }

    inner class AnswersViewHolder(private val itemView: ChildListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
        val radioButton1: RadioButton = itemView.findViewById(R.id.radioButton1)
        val radioButton2: RadioButton = itemView.findViewById(R.id.radioButton2)
        val radioButton3: RadioButton = itemView.findViewById(R.id.radioButton3)
        val radioButton4: RadioButton = itemView.findViewById(R.id.radioButton4)
    }

    override fun getItemCount(): Int {
        return question.size
    }
}