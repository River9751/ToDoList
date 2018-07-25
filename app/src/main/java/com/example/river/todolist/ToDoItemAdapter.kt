package com.example.river.todolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_items.view.*


interface delAction {
    fun deleteItem(uniqueId: String)
}

class ToDoItemAdapter(context: Context, toDoItemList: MutableList<ToDoItem>, clickListener: (String) -> Unit)
    : RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {

    private val ctx = context
    var list = toDoItemList
    private val itemClickListener = clickListener

//    //如果沒有主要建構子，次要建構子需要加上 super 關鍵字
//    constructor(toDoItemList: MutableList<ToDoItem>) : super() {
//        list = toDoItemList
//        var action: delAction
//
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(ctx)
                .inflate(R.layout.row_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //更改 Item 的屬性
        holder.cbDone.isChecked = list[position].done
        holder.tvText.text = list[position].itemText.toString()
        holder.uniqueId = list[position].uniqueId.toString()
        holder.delBtn.setOnClickListener {
            itemClickListener.invoke(holder.uniqueId)
        }
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cbDone = v.cb_item_is_done
        val tvText = v.tv_item_text
        val delBtn = v.iv_cross
        var uniqueId: String = ""

        init {

//            if (v.cb_item_is_done.isChecked) {
//                v.tv_item_text.setTextColor(android.graphics.Color.GRAY)// = Color.parseColor("#DDDDDD")
//                //textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
//                v.tv_item_text.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
//            }
        }
    }

    //更新畫面
    fun refreshView(newList: MutableList<ToDoItem>) {
        this.list = newList
        this.notifyDataSetChanged()
    }

}