package com.example.river.todolist

import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.row_items.view.*


//將資料轉換為介面
class ToDoItemAdapter(
        context: Context,
        toDoItemList: MutableList<ToDoItem>,
        clickListener: (id: Int, toDoItem: ToDoItem) -> Unit)
    : RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {

    private val ctx = context
    private var list = toDoItemList
    private val itemClickListener = clickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(ctx)
                .inflate(R.layout.row_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    //*每次刷新介面 會觸發 onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //更改 Item 的屬性
        holder.cbDone.isChecked = list[position].done
        holder.tvText.text = list[position].itemText
        holder.uniqueId = list[position].uniqueId

        //控制項加入事件
        holder.delBtn.setOnClickListener {
            itemClickListener.invoke(holder.delBtn.id, list[holder.adapterPosition])
        }
        holder.tvText.setOnClickListener {
            itemClickListener.invoke(holder.tvText.id, list[holder.adapterPosition])
        }
        holder.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
            list[holder.adapterPosition].done = isChecked
            itemClickListener.invoke(holder.cbDone.id, list[holder.adapterPosition])
        }

        //已勾選項目劃上刪除線
        Helper.doStrike(list[position].done, holder.tvText)
    }

    class ViewHolder : RecyclerView.ViewHolder {
        val cbDone: CheckBox = itemView.cb_item_is_done
        val tvText: TextView = itemView.tv_item_text
        val delBtn: ImageButton = itemView.iv_cross
        var uniqueId: String = ""

        constructor(v: View) : super(v) {
            cbDone.setOnCheckedChangeListener { compoundButton, b ->
                Helper.doStrike(b, tvText)
            }
        }
    }

    fun addNewItem(toDoItem: ToDoItem) {
        this.list.add(toDoItem)
        notifyItemInserted(list.size - 1)
    }


    fun deleteItem(toDoItem: ToDoItem) {
        val i = list.indexOf(toDoItem)
        this.list.remove(toDoItem)
        notifyItemRemoved(i)
    }

    fun updateItem(toDoItem: ToDoItem) {
        Handler().post {
            list.first { it.uniqueId == toDoItem.uniqueId }.itemText = toDoItem.itemText
            notifyItemChanged(list.indexOf(toDoItem))
        }
    }
}