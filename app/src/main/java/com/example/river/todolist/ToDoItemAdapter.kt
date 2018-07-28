package com.example.river.todolist

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.row_items.view.*


class ToDoItemAdapter(
        context: Context,
        toDoItemList: MutableList<ToDoItem>,
        clickListener: (id: Int, uniId: String, itemText: String, checked: Boolean, tv_itemText: TextView?) -> Unit)
    : RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {

    private val ctx = context
    private var list = toDoItemList
    private val itemClickListener = clickListener

    lateinit var myClickListener: IClickListener

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

    override fun getItemCount(): Int = list.size

    //*每次呼叫 notifyDataSetChanged 會觸發 onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //更改 Item 的屬性
        holder.cbDone.isChecked = list[position].done
        holder.tvText.text = list[position].itemText.toString()
        holder.uniqueId = list[position].uniqueId.toString()

        val checked = list[position].done
        val uniqueId = holder.uniqueId
        val itemText = holder.tvText.text.toString()


        //控制項加入事件
        holder.delBtn.setOnClickListener {
            itemClickListener.invoke(holder.delBtn.id, uniqueId, "", checked, null)
        }
        holder.tvText.setOnClickListener {
            itemClickListener.invoke(holder.tvText.id, uniqueId, itemText, checked, null)
        }
        holder.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
            itemClickListener.invoke(holder.cbDone.id, uniqueId, itemText, isChecked, holder.tvText)
        }

        //已勾選項目劃上刪除線
        if (checked) {
            holder.tvText.setTextColor(android.graphics.Color.GRAY)// = Color.parseColor("#DDDDDD")
            //textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            holder.tvText.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvText.setTextColor(android.graphics.Color.BLACK)// = Color.parseColor("#DDDDDD")
            //textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            holder.tvText.paint.flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cbDone = v.cb_item_is_done
        val tvText = v.tv_item_text
        val delBtn = v.iv_cross
        var uniqueId: String = ""
    }


    fun addItem(toDoItem: ToDoItem) {
        this.list.add(toDoItem)
        this.notifyDataSetChanged()
    }

    //更新畫面
    fun refreshView(newList: MutableList<ToDoItem>) {
        this.list = newList
        this.notifyDataSetChanged()
    }

    interface IClickListener {
        fun ClickListener(pos: Int)
    }


    var ccc: IClickListener? = null

    fun OuterCall(aaa: IClickListener) {
        ccc = aaa
    }


}