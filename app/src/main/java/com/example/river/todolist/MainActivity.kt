package com.example.river.todolist

import android.os.Bundle
import android.support.v4.app.FragmentManager
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
//        fab_01.setOnClickListener {
//            itemDialog(toDoItem = ToDoItem("", "", false))
//        }

        var fragmentManager: FragmentManager = supportFragmentManager
//        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.add(R.id.vp_01, FirebaseFragment())
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()

        pageAdapter = PageAdapter(fragmentManager)

        setupViewPager(vp_01)
        vp_01.adapter = pageAdapter

        tabs.setupWithViewPager(vp_01)
    }

    fun setupViewPager(viewPager: ViewPager) {

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
}