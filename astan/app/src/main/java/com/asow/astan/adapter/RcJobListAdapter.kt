package com.asow.astan.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import com.asow.astan.util.FirestoreSingleTon
import kotlinx.android.synthetic.main.row_view_job_list.view.*


import com.asow.astan.fragment.PostJobFragment
import com.asow.astan.model.*
import com.asow.astan.util.AppPreference

import com.asow.astan.R


class RcJobListAdapter(
    strFrgType: String,
    con: FragmentActivity,
    jobLit: ArrayList<JobPostModel>?,
    bookList: ArrayList<BookModel>?,

    mettingmodelList: ArrayList<MettingModel>?,
    competitionmodelList: ArrayList<CompetitonModel>?,
    bloodModelList: ArrayList<BloodModel>?
) :
    RecyclerView.Adapter<RcJobListAdapter.ViewHolder>() {
    val contex = con
    var emailAddress: String = ""
    var currentDocId: String = ""
    var companyurl: String = ""
    var number: Long = 0
    val strFrgType = strFrgType
    val jobList = jobLit
    val bloodModelList = bloodModelList
    val bookList = bookList
    val mCompetitonlist = competitionmodelList
    val mettingList = mettingmodelList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcJobListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(contex).inflate(R.layout.row_view_job_list, parent, false))
    }

    override fun getItemCount(): Int {
        var size = 0
        when {
            strFrgType.equals("book") -> {
                size = bookList!!.size
            }
            strFrgType.equals("metting") -> {
                size = mettingList!!.size
            }
            strFrgType.equals("competition") -> {
                size = mCompetitonlist!!.size
            }
            strFrgType.equals("book_req_post") -> {
                size = bookList!!.size
            }
            strFrgType.equals("job_post") -> {
                size = jobList!!.size
            }
            strFrgType.equals("blood") -> {
                size = bloodModelList!!.size
            }
        }
        return size

    }

    override fun onBindViewHolder(holder: RcJobListAdapter.ViewHolder, position: Int) {

        when {
            strFrgType.equals("book") -> {
                holder.tv_purpose.text = "Purpose: " + bookList!!.get(position).bookPurpose
                holder.tvJobCompName.text = "Request by " + bookList!!.get(position).email
                holder.tvJobType.text = "I need book of  " + bookList.get(position).jobtype
                holder.tvJobCompLink.visibility = View.GONE
                emailAddress = bookList.get(position).email.toString()
                currentDocId = bookList.get(position).id.toString()

                holder.tvJobTypeDate.visibility = View.INVISIBLE
                if (bookList.get(position).email!!.equals(AppPreference.getInstance(contex)!!.getString(AppPreference.StringKeys.PD_USER_ID))) {
                    holder.imgEdit.visibility = View.VISIBLE
                    holder.imgDelete.visibility = View.VISIBLE
                } else {
                    holder.imgEdit.visibility = View.GONE
                    holder.imgDelete.visibility = View.GONE
                }


            }
            strFrgType.equals("metting") -> {
                holder.tvJobType.visibility = View.GONE
                holder.tvJobTypeDate.text = mettingList!!.get(position).date
                holder.tv_purpose.text = "Description: " + mettingList.get(position).purpose
                holder.tvJobCompName.text = "Area: " + mettingList!!.get(position).place
                holder.tvJobPostBy.visibility = View.GONE
                holder.tvJobCompLink.visibility = View.GONE
                holder.tvjobCmd.visibility = View.GONE
                holder.imgEdit.visibility = View.GONE
                holder.imgDelete.visibility = View.GONE
            }
            strFrgType.equals("blood") -> {
                holder.tvJobType.text = bloodModelList!!.get(position).blood
                holder.tvJobTypeDate.visibility = View.INVISIBLE

                holder.tv_purpose.setTextColor(contex.resources.getColor(R.color.Kelly_green))
//                holder.tv_purpose.text = mettingList.get(position).purpose
                holder.tvJobCompName.text = bloodModelList.get(position).name
                holder.tv_purpose.text = "Cell: " + bloodModelList.get(position).mob.toString()
                holder.tvJobPostBy.visibility = View.GONE
                holder.tvJobCompLink.visibility = View.GONE
                holder.tvjobCmd.visibility = View.GONE
                holder.imgEdit.visibility = View.GONE
                holder.imgDelete.visibility = View.GONE
                if (bloodModelList.get(position).email!!.equals(
                        AppPreference.getInstance(contex)!!.getString(
                            AppPreference.StringKeys.PD_USER_ID
                        )
                    )
                ) {
                    holder.imgEdit.visibility = View.VISIBLE
                    holder.imgDelete.visibility = View.VISIBLE
                } else {
                    holder.imgEdit.visibility = View.GONE
                    holder.imgDelete.visibility = View.GONE
                }
            }
            strFrgType.equals("competition") -> {
                holder.tvJobType.text = mCompetitonlist!!.get(position).type
                holder.tvJobTypeDate.text = mCompetitonlist.get(position).date
                holder.tvJobCompName.text = mCompetitonlist.get(position).place
                holder.tvJobCompLink.text = mCompetitonlist.get(position).link
                holder.tvjobCmd.visibility = View.GONE

                holder.tvJobPostBy.visibility = View.GONE
                holder.tv_purpose.visibility = View.GONE
                holder.tvjobCmd.visibility = View.GONE
                holder.imgEdit.visibility = View.GONE
                holder.imgDelete.visibility = View.GONE
            }

            strFrgType.equals("book_req_post") -> {

            }
            strFrgType.equals("job_post") -> {
                companyurl = jobList!!.get(position).companylink + "".trim()
                holder.tvJobType.text = "Job type: " + jobList!!.get(position).jobtype
                holder.tvJobTypeDate.text = jobList.get(position).lastdate
                holder.tvJobCompName.text = jobList.get(position).company
                holder.tvJobCompLink.text = jobList.get(position).companylink
                holder.tvJobPostBy.visibility = View.GONE
                companyurl = jobList!!.get(position).companylink + "".trim()
                holder.tv_purpose.text = "post by " + jobList.get(position).email + "\n Description: \n" +
                        jobList.get(position).desc
                emailAddress = jobList.get(position).email.toString()
                currentDocId = jobList.get(position).id.toString()
                if (jobList.get(position).email!!.equals(AppPreference.getInstance(contex)!!.getString(AppPreference.StringKeys.PD_USER_ID))) {
                    holder.imgEdit.visibility = View.VISIBLE
                    holder.imgDelete.visibility = View.VISIBLE
                } else {
                    holder.imgEdit.visibility = View.GONE
                    holder.imgDelete.visibility = View.GONE
                }
            }

        }
        holder.tvJobCompLink.setOnClickListener {
            try {
                when {
                    strFrgType.equals("job_post") -> {
                        companyurl = jobList!!.get(holder.adapterPosition).companylink + "".trim()
                    }
                    strFrgType.equals("competition") -> {
                        companyurl = mCompetitonlist!!.get(holder.adapterPosition).link + "".trim()
                    }
                    }

                if (!companyurl.isEmpty()) {
                    var uri = Uri.parse(companyurl)
                    if (!companyurl.startsWith("http://") && !companyurl.startsWith("https://")) {
                        uri = Uri.parse("http://" + uri)
                    }
                    val launchBrowser = Intent(Intent.ACTION_VIEW, uri)
                    contex.startActivity(launchBrowser)
                }
            } catch (e: Exception) {
                Toast.makeText(contex, "Web link error", Toast.LENGTH_SHORT).show()
            }
        }
        holder.tvjobCmd.setOnClickListener {
            val inte = Intent(Intent.ACTION_SENDTO)
            inte.data = Uri.parse("mailto:" + emailAddress)
            inte.putExtra(Intent.EXTRA_SUBJECT, "From astan app")
            if (inte.resolveActivity(contex.packageManager) != null) {
                contex.startActivity(inte)
            }
        }
        holder.imgDelete.setOnClickListener {

            if (strFrgType.equals("job_post")) {
                FirestoreSingleTon.getinstancex()!!.collection("PostJob").document(currentDocId).delete()
                    .addOnSuccessListener {
                        jobList!!.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(contex, e.message, Toast.LENGTH_SHORT).show()

                    }
            } else if (strFrgType.equals("book")) {
                FirestoreSingleTon.getinstancex()!!.collection("Book").document(currentDocId).delete()
                    .addOnSuccessListener {
                        bookList!!.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(contex, e.message, Toast.LENGTH_SHORT).show()

                    }
            } else if (strFrgType.equals("blood")) {
                FirestoreSingleTon.getinstancex()!!.collection("Blood")
                    .document(bloodModelList!!.get(holder.adapterPosition).id.toString()).delete()
                    .addOnSuccessListener {
                        bloodModelList!!.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(contex, e.message, Toast.LENGTH_SHORT).show()

                    }
            }

        }
        holder.tv_purpose.setOnClickListener {
            number = bloodModelList!!.get(holder.adapterPosition).mob!!.toLong()

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + number)
            contex.startActivity(intent)
        }
        holder.imgEdit.setOnClickListener {
            when {


                strFrgType.equals("metting") -> {

                }
                strFrgType.equals("blood") -> {
                    contex.supportFragmentManager.beginTransaction()
                        .replace(
                            com.asow.astan.R.id.framelayout_dashboard, PostJobFragment(
                                "blood_add", true, null,
                                null, bloodModelList!!.get(holder.adapterPosition)
                            )
                        )
                        .commit()
                }
                strFrgType.equals("competition") -> {

                }
                strFrgType.equals("book") -> {
                    contex.supportFragmentManager.beginTransaction()
                        .replace(
                            com.asow.astan.R.id.framelayout_dashboard, PostJobFragment(
                                "book_req_post",
                                true, null, bookList!!.get(position), null
                            )
                        )
                        .commit()


                }
                strFrgType.equals("job_post") -> {
                    contex.supportFragmentManager.beginTransaction()
                        .replace(
                            com.asow.astan.R.id.framelayout_dashboard, PostJobFragment(
                                strFrgType,
                                true, jobList!!.get(position), null, null
                            )
                        )
                        .commit()
                }

            }
        }
    }

    class ViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvJobType = vi.tv_rv_job_type
        val tvJobTypeDate = vi.tv_rv_job_date
        val tvJobCompName = vi.tv_rv_job_comp_name
        val tvJobCompLink = vi.tv_rv_job_link
        val tvJobPostBy = vi.tv_rv_job_ref
        val tvjobCmd = vi.tv_rv_job_cmd
        val imgDelete = vi.img_rv_del
        val imgEdit = vi.img_rv_edit
        val tv_purpose = vi.tv_rv_purpose

    }

}