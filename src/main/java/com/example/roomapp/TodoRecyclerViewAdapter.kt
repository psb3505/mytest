package com.example.roomapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.DB.TodoEntity
import com.example.roomapp.databinding.ItemTodoBinding

class TodoRecyclerViewAdapter(val todoList : ArrayList<TodoEntity>, val listener : OnItemLongClickListener) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvimportance
        val tv_title = binding.tvtitle
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]
        when(todoData.importance) {
            1 -> holder.tv_importance.setBackgroundResource(R.color.red)
            2 -> holder.tv_importance.setBackgroundResource(R.color.yellow)
            3 -> holder.tv_importance.setBackgroundResource(R.color.green)
        }
        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title

        // 삭제하기 위한 롱클릭 리스너 구현
        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
        }
    }
}