package com.example.tripperandtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_pois.*

class PoisActivity : AppCompatActivity() {
    private lateinit var viewPagerFragmentAdapter: ViewPagerFragmentAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var viewPager2PageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pois)

        //final variable (cant be assigned twice)
        val tabTitles = arrayOf(getString(R.string.my_pois_tab_label), getString(R.string.cloud_pois_tab_label))

        //alternative way getting view object reference
        //toolbar = findViewById(R.id.poisToolbar)
        toolbar = poisToolbar
        fab = floatingActionButton
        viewPager = view_pager
        tabLayout = tab_layout

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(this, tabTitles)
        viewPager.adapter = viewPagerFragmentAdapter
        viewPager2PageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            //override method(s) what you need it
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> fab.show()
                    else -> fab.hide()
                }
            }
        }
        viewPager.registerOnPageChangeCallback(viewPager2PageChangeCallback)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(viewPager2PageChangeCallback)
    }
}

class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity, private val tabTitles : Array<String>) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> CloudPoisFragment()
            else -> MyPoisFragment()
        }
    }

    override fun getItemCount(): Int {
        return tabTitles.size
    }


}
