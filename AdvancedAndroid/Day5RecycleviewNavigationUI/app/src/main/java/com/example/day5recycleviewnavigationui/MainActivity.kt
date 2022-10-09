package com.example.day5recycleviewnavigationui

import android.R
import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.day5recycleviewnavigationui.databinding.ActivityMainBinding
import com.example.day5recycleviewnavigationui.fragment.LessonFragment
import com.example.day5recycleviewnavigationui.fragment.MessageFragment
import com.example.day5recycleviewnavigationui.fragment.StudentInforFragment
import com.example.day5recycleviewnavigationui.model.Lesson
import com.example.day5recycleviewnavigationui.vm.LessonViewModel
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG: String = "DHP"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LessonViewModel by viewModels()
    private lateinit var lessons: ArrayList<Lesson>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //get list lesson
        viewModel.listLesson.observe(this) {
            lessons = it
        }

        //toolbar
        setSupportActionBar(binding.toolbar)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.ok,
            R.string.cancel
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setNavigationViewListener()

        supportFragmentManager.commit {
            add<StudentInforFragment>(com.example.day5recycleviewnavigationui.R.id.fragment)
        }
    }

    private fun setNavigationViewListener() {
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.day5recycleviewnavigationui.R.menu.menu, menu)
        //search
        val item = menu?.findItem(com.example.day5recycleviewnavigationui.R.id.search)
        val searchView = item?.actionView as SearchView?
        searchView?.queryHint = "Search"
        //auto complete textview
        searchView?.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)?.threshold =
            1
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(com.example.day5recycleviewnavigationui.R.id.item_label)
        val cursorAdapter = android.widget.SimpleCursorAdapter(
            this,
            com.example.day5recycleviewnavigationui.R.layout.search_item, null, from, to,
            android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        searchView?.suggestionsAdapter = cursorAdapter
        //search view query text listener
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //search lesson
                    searchLesson(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //create view suggest text
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    lessons.forEachIndexed { index, suggestion ->
                        if (suggestion.name.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion.name))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })
        //searchview suggest listener
        searchView?.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {

                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)
                //search lesson
                searchLesson(selection.toString())
                return true
            }
        })
        return true
    }

    private fun searchLesson(selection: String) {
        val selectLesson: Lesson? = findLessonByName(selection.toString())
        if (selectLesson != null) {
            viewModel.setLesson(selectLesson)
            supportFragmentManager.commit {
                replace<LessonFragment>(com.example.day5recycleviewnavigationui.R.id.fragment)
            }
        } else {
            Toast.makeText(this@MainActivity, "Not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun findLessonByName(name: String): Lesson? {
        var lesson: Lesson? = null
        lessons.forEach {
            if (it.name.equals(name)) {
                lesson = it
            }
        }
        return lesson
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        com.example.day5recycleviewnavigationui.R.id.like -> {
            Toast.makeText(this, "like", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.day5recycleviewnavigationui.R.id.message ->
                supportFragmentManager.commit {
                    replace<MessageFragment>(com.example.day5recycleviewnavigationui.R.id.fragment)
                }
            com.example.day5recycleviewnavigationui.R.id.profile ->
                supportFragmentManager.commit {
                    replace<StudentInforFragment>(com.example.day5recycleviewnavigationui.R.id.fragment)
                }
            else ->
                Toast.makeText(this, "other", Toast.LENGTH_SHORT).show()
        }
        val drawer =
            findViewById<DrawerLayout>(com.example.day5recycleviewnavigationui.R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}