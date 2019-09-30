package com.asow.astan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.asow.astan.R
import com.asow.astan.fragment.JobListFragment
import com.asow.astan.fragment.PostJobFragment
import com.asow.astan.fragment.TopListfragment

class JobTabAdapter(val fm:FragmentManager,isMain:Boolean): FragmentStatePagerAdapter(fm) {
    val isMain=isMain
    override fun getItem(position: Int): Fragment {
        if(isMain)
        {
            when(position)
            {


                0->
                {
                    return TopListfragment(true, R.drawable.a1)
                }
                1->
                {

                    return TopListfragment(true, R.drawable.a2)
                }
                2->
                {

                    return TopListfragment(true, R.drawable.a3)
                }
                3->
                {

                    return TopListfragment(true, R.drawable.a4)
                }
                4->
                {

                    return TopListfragment(true, R.drawable.a5)
                }
                5->
                {

                    return TopListfragment(true, R.drawable.a5)
                }
                else->
                {
                    return TopListfragment(true,  R.drawable.a4)
                }
            }
        }else
        {
            when(position)
            {
                0->
                {
                    return JobListFragment()
                }
                1->
                {

                    return PostJobFragment("job_post",false,null,null,null)
                }
                else->
                {
                    return PostJobFragment("job_post",false,null,null,null)
                }
            }
        }

    }

    override fun getCount(): Int {
        if(isMain)
            return 6
        else
        return  2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(!isMain)
        {
            when(position)
            {
                0->
                {
                    return "Job"
                }
                1->
                {
                    return "Post Job"
                }
                else->
                {
                    return "Post Job"
                }
            }
        }
        return "Post Job"

}
}