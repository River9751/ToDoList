package com.example.river.todolist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.dialog.*


class MyDialog(private var ctx: Context) : AlertDialog(ctx) {

    private lateinit var cb: (itemText: String) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.dialog)

        //移除不允許 Focus 的 flag
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        dlgAdd.setOnClickListener {
            this.cb.invoke(dlgEditText.text.toString())
            this.dismiss()
        }
    }

    override fun onStart() {
        this.setOnShowListener {
            showSoftInput()
        }

        super.onStart()
    }

    fun showInsertDlg(cb: (itemText: String) -> Unit) {
        this.cb = cb
        this.show()
        dlgTitle.text = context.getString(R.string.insertTitle)
        dlgAdd.setText(R.string.dlgBtnAdd)
    }

    fun showUpdateDlg(itemText: String, cb: (itemText: String) -> Unit) {
        this.cb = cb
        this.show()
        dlgTitle.text = context.getString(R.string.updateTitle)
        dlgEditText.setText(itemText)
        dlgAdd.setText(R.string.dlgBtnUpdate)
    }

    private fun hideSoftInput() {
        val imm: InputMethodManager =
                ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
                dlgEditText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun showSoftInput() {
        dlgEditText.requestFocus()
        val imm: InputMethodManager =
                ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(dlgEditText, InputMethodManager.SHOW_IMPLICIT)
    }
}