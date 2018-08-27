package com.example.river.todolist

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class ItemDialog : AlertDialog.Builder {

    private var ctx: Context
    private var oriText: String?
    private var callback: (itemText: String) -> Unit

    private var itemEditText: EditText
    private var messageText: String
    private var positiveButtonText: String

    /**
     * @param ctx 依附的 Context
     * @param oriText 欲更新項目原本的文字
     * @param callback insert 或 update 完成後須執行的 callback
     */
    constructor(
            ctx: Context,
            oriText: String?,
            callback: (itemText: String) -> Unit
    ) : super(ctx) {
        this.ctx = ctx
        this.itemEditText = EditText(ctx)
        this.oriText = oriText
        this.callback = callback
        this.messageText = if (oriText == null) "Add New Item" else "Update Item"
        this.positiveButtonText = if (oriText == null) "Add" else "Update"
    }

    override fun create(): AlertDialog {

        //為什麼順序交換 Dlg 介面就不會出現?

        this.setMessage(messageText)
                .setTitle("Enter To Do Item Text")
                .setView(itemEditText)
                .setPositiveButton(positiveButtonText) { dialog, positiveButton ->
                    itemEditText.requestFocus()



                    val imm: InputMethodManager =
                            ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(
                            itemEditText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)

                    this.callback.invoke(itemEditText.text.toString())
                }

        val dlg: AlertDialog = super.create()
        dlg.setOnShowListener {
            itemEditText.requestFocus()
            val imm: InputMethodManager =
                    ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(itemEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        //更新文字的話，先將原本的文字顯示在 EditText
        if (this.oriText != null) {
            itemEditText.setText(oriText)
        }
        return dlg
    }
}