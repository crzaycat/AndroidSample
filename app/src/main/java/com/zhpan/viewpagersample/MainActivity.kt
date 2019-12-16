package com.zhpan.viewpagersample

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer

import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2 = findViewById(R.id.view_pager)
        val myAdapter = MyAdapter()
        myAdapter.setList(data)
        viewPager2.adapter = myAdapter
        viewPager2.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_10) +
                        resources.getDimensionPixelOffset(R.dimen.dp_10)
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Toast.makeText(this@MainActivity, "page selected $position", Toast.LENGTH_SHORT).show()
            }
        })
//        viewPager2.isUserInputEnabled = false
//        viewPager2.orientation=ViewPager2.ORIENTATION_VERTICAL

    }

    private val data: List<Int>
        get() {
            val list = ArrayList<Int>()
            for (i in 0..3) {
                list.add(i)
            }
            return list
        }

    fun fakeDragBy(view: View) {
        viewPager2.beginFakeDrag()
        if (viewPager2.fakeDragBy(-310f))
            viewPager2.endFakeDrag()
    }
}
