package com.example.river.todolist

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.river.todolist.fragments.FirebaseFragment
import com.example.river.todolist.fragments.FirestoreFragment
import com.example.river.todolist.fragments.PreferenceFragment
import com.example.river.todolist.fragments.SQLiteFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var pageAdapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //新增項目事件
        fab_main.setOnClickListener {
            var fg = (vp_01.adapter as PageAdapter).getItem(vp_01.currentItem)

            when (fg) {
                is PreferenceFragment -> {
                    //TODO 為什麼可以這樣寫
//                    ItemDialog(this, null) { itemText -> fg.insertData(itemText) }.show()
//                    MyDialog(this).showInsertDlg{ itemText -> fg.insertData(itemText) }
                    MyDialog(this).showInsertDlg { itemText -> fg.insertData(itemText) }
                }
                is SQLiteFragment -> {
                    ItemDialog(this, null) { itemText -> fg.insertData(itemText) }.show()

                }
                is FirebaseFragment -> {
                    ItemDialog(this, null) { itemText -> fg.insertData(itemText) }.show()

                }
                is FirestoreFragment -> {
                    ItemDialog(this, null) { itemText -> fg.insertData(itemText) }.show()
                }
            }
        }


        var fragmentManager: FragmentManager = supportFragmentManager
        //addFragmentByTransaction()
        pageAdapter = PageAdapter(fragmentManager)

        setupViewPager(vp_01)
        vp_01.adapter = pageAdapter
        tabs.setupWithViewPager(vp_01)
    }

    fun setupViewPager(viewPager: ViewPager) {

        var fg = PreferenceFragment()
        var bb = fg.id
        var cc = fg.tag
        with(pageAdapter) {
            addFragment(PreferenceFragment(), "Preference")
            addFragment(SQLiteFragment(), "SQLite")
            addFragment(FirebaseFragment(), "Firebase")
            addFragment(FirestoreFragment(), "Firestore")
        }

//        pgeAdapter.addFragment(PreferenceFragment(), "Preferences")
//        pageAdapter.addFragment(SQLiteFragment(), "SQLite")
//        pageAdapter.addFragment(FirebaseFragment(), "Firebase")
//        pageAdapter.addFragment(FirestoreFragment(), "Firestore")
    }

    fun addFragmentByTransaction() {
        var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.vp_01, SQLiteFragment(), "SQLiteTag")
////        fragmentTransaction.add(R.id.vp_01, FirebaseFragment(), "FirebaseTag")
////        fragmentTransaction.add(R.id.vp_01, PreferenceFragment(), "PreferenceTag")
////        fragmentTransaction.add(R.id.vp_01, FirestoreFragment(), "FirestoreTag")
//        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}