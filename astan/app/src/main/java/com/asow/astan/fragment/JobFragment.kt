package com.asow.astan.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


import com.asow.astan.adapter.JobTabAdapter
import kotlinx.android.synthetic.main.fragment_job_post2.*

import com.asow.astan.R


class JobFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var mJobTabLay: TabLayout
    private lateinit var mJobViewPager: ViewPager
    private var mMonthListShort =
        arrayListOf<String>("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec")
    private var imgSel = arrayListOf<Int>( R.drawable.ic_joblist,R.drawable.ic_postjob)
    private var imgunSel = arrayListOf<Int>(R.drawable.ic_joblistunsel,R.drawable.ic_postjobunsel)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_post2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mJobTabLay = view.findViewById<TabLayout>(com.asow.astan.R.id.tab_lay_job)
        mJobViewPager = view.findViewById<ViewPager>(com.asow.astan.R.id.vp_job)
        setJobAdapter()
        mJobTabLay.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0!!.position) {
                    0 -> {
                        tab_lay_job.getTabAt(0)!!.setIcon(imgSel[0])
                        tab_lay_job.getTabAt(1)!!.setIcon(imgunSel[1])
                    }
                    1 -> {
                        tab_lay_job.getTabAt(0)!!.setIcon(imgunSel[0])
                        tab_lay_job.getTabAt(1)!!.setIcon(imgSel[1])
                    }
                }
            }

        })


    }

    private fun setJobAdapter() {
        mJobViewPager.adapter = JobTabAdapter(childFragmentManager, false)
        mJobTabLay.setupWithViewPager(mJobViewPager, true)
    }


}
