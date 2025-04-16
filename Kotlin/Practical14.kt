package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical14 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical14)
        val listItems : Array<String> = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8","Item 9")
        val gridItems : Array<String> = arrayOf("Grid 1", "Grid 2", "Grid 3", "Grid 4", "Grid 5", "Grid 6", "Grid 7", "Grid 8","Grid 9")
        setContents(listItems, gridItems)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main13)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } }
    private fun setContents(listItems : Array<String>, gridItems : Array<String>){
        val listView : ListView = findViewById(R.id.myListView)
        val listAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = listAdapter
        val gridView : GridView = findViewById(R.id.myGridView)
        val gridAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, gridItems)
        gridView.adapter = gridAdapter
    }
}