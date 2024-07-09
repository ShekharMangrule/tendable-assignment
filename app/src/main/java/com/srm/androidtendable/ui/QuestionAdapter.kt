package com.srm.androidtendable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srm.androidtendable.R
import com.srm.androidtendable.databinding.ListItemBinding
import com.srm.androidtendable.model.Category
import com.srm.androidtendable.model.Question

class QuestionAdapter(
    private val categoryList: List<Category>,
    private val callback: OnItemSelection
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    interface OnItemSelection {
        fun onItemClick(question: List<Question>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val category = categoryList[position]
        val que = category.questions[position].name
        val queList = category.questions

        val childAdapter = ChildAdapter(queList, category.name, object : ChildAdapter.OnAnswerSelection{
            override fun onAnswerClick(questions: List<Question>) {
                callback.onItemClick(questions)
            }
        })
        holder.childList.layoutManager = LinearLayoutManager(holder.childList.context)
        holder.childList.adapter = childAdapter
    }

    inner class QuestionViewHolder(private val itemView: ListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val childList: RecyclerView = itemView.findViewById(R.id.childRecyclerView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}