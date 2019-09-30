package com.asow.astan.fragment


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager

import com.asow.astan.R
import com.asow.astan.adapter.RcJobListAdapter
import com.asow.astan.model.*
import com.asow.astan.util.AppPreference
import com.asow.astan.util.Common
import com.asow.astan.util.ConnectivityReceiver
import com.asow.astan.util.FirestoreSingleTon
import kotlinx.android.synthetic.main.fragment_post_job.*
import java.util.*
import kotlin.collections.ArrayList

class PostJobFragment(
    frgType: String,
    isedit: Boolean,
    jobModel: JobPostModel?,
    bookModel: BookModel?,
    BloodModel: BloodModel?
) : Fragment(),ConnectivityReceiver.ConnectivityReceiverListener {
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
      isConnect=isConnected
    }

    val mStrFrgType = frgType

    private var jobModel = jobModel
    private var bloodModel = BloodModel
    private var mBookModel = bookModel
    private var isedit = isedit
    private var myDocID: String? = null
private var isConnect:Boolean=false
    private lateinit var jobType: ArrayList<String>

    private var mMonthListShort =
        arrayListOf<String>("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec")
    private var mBloodList =
        arrayListOf<String>("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
var view:View=inflater.inflate(R.layout.fragment_post_job, container, false)





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc_book_req.layoutManager = LinearLayoutManager(activity)
        when {
            mStrFrgType.equals("book") -> {
                tv_form_lable.text = "Help to your socity student"
                rel_lay_book_req.visibility = View.VISIBLE
                sp_postjobtype.visibility = View.GONE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                fbut_book_req_post.visibility = View.VISIBLE
                edt_book_purpose.visibility = View.GONE
                btn_job_post.visibility = View.GONE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.GONE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.VISIBLE
                val f = FirestoreSingleTon.getinstancex()!!.collection("Book".trim()).get()
                f.addOnCompleteListener { t ->
                    if (t.isSuccessful) {

                        val l = ArrayList<BookModel>(t.result!!.toObjects(BookModel::class.java))
                        rc_book_req.adapter = activity?.let { RcJobListAdapter("book", it, null, l, null, null, null) }
                        pb_job.visibility = View.GONE
                    } else {
                        pb_job.visibility = View.GONE
                        Toast.makeText(activity!!.applicationContext, "fail", Toast.LENGTH_SHORT).show()
                    }

                }


                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        pb_job.visibility = View.GONE
                    }


            }
            mStrFrgType.equals("metting") -> {
                tv_form_lable.visibility = View.GONE
                rel_lay_book_req.visibility = View.VISIBLE
                sp_postjobtype.visibility = View.GONE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                fbut_book_req_post.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                btn_job_post.visibility = View.GONE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.GONE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.VISIBLE
                val f = FirestoreSingleTon.getinstancex()!!.collection("Meeting".trim()).get()
                f.addOnCompleteListener { t ->
                    if (t.isSuccessful) {

                        val l = ArrayList<MettingModel>(t.result!!.toObjects(MettingModel::class.java))
                        rc_book_req.adapter =
                            activity?.let { RcJobListAdapter("metting", it, null, null, l, null, null) }
                        pb_job.visibility = View.GONE
                    } else {
                        pb_job.visibility = View.GONE
                        Toast.makeText(activity!!.applicationContext, Common.retrievedatafailed, Toast.LENGTH_SHORT)
                            .show()
                    }

                }


                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        pb_job.visibility = View.GONE
                    }

            }
            mStrFrgType.equals("competition") -> {
                tv_form_lable.visibility = View.GONE
                rel_lay_book_req.visibility = View.VISIBLE
                sp_postjobtype.visibility = View.GONE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                fbut_book_req_post.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                btn_job_post.visibility = View.GONE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.GONE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.VISIBLE
                val f = FirestoreSingleTon.getinstancex()!!.collection("Competition".trim()).get()
                f.addOnCompleteListener { t ->
                    if (t.isSuccessful) {

                        val l = ArrayList<CompetitonModel>(t.result!!.toObjects(CompetitonModel::class.java))
                        rc_book_req.adapter =
                            activity?.let { RcJobListAdapter("competition", it, null, null, null, l, null) }
                        pb_job.visibility = View.GONE
                    } else {
                        pb_job.visibility = View.GONE
                        Toast.makeText(activity!!.applicationContext, Common.retrievedatafailed, Toast.LENGTH_SHORT)
                            .show()
                    }

                }


                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        pb_job.visibility = View.GONE
                    }


            }
            mStrFrgType.equals("blood") -> {
                tv_form_lable.text = "Save life"
                rel_lay_book_req.visibility = View.VISIBLE
                sp_postjobtype.visibility = View.GONE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                fbut_book_req_post.visibility = View.VISIBLE
                edt_book_purpose.visibility = View.GONE
                btn_job_post.visibility = View.GONE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.VISIBLE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.VISIBLE

                val f = FirestoreSingleTon.getinstancex()!!.collection("Blood".trim()).get()
                f.addOnCompleteListener { t ->
                    if (t.isSuccessful) {

                        val l = ArrayList<BloodModel>(t.result!!.toObjects(BloodModel::class.java))
                        rc_book_req.adapter = activity?.let { RcJobListAdapter("blood", it, null, null, null, null, l) }
                        pb_job.visibility = View.GONE
                    } else {
                        pb_job.visibility = View.GONE
                        Toast.makeText(activity!!.applicationContext, Common.retrievedatafailed, Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        pb_job.visibility = View.GONE
                    }

            }
            mStrFrgType.equals("blood_add") -> {
                tv_form_lable.text = "If you like to blood donate kindly register here"
                rel_lay_book_req.visibility = View.GONE
                sp_postjobtype.visibility = View.VISIBLE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                fbut_book_req_post.visibility = View.GONE
                edt_book_purpose.visibility = View.GONE
                btn_job_post.visibility = View.VISIBLE
                img_job_post_close.visibility = View.VISIBLE
                edt_blood_name.visibility = View.VISIBLE
                edt_blood_mob.visibility = View.VISIBLE
                pb_job.visibility = View.GONE
                jobType = arrayListOf<String>("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
                setSpinner()


            }
            mStrFrgType.equals("book_req_post") -> {
                tv_form_lable.text = "if you need to study materials kindly, post request here "
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                edt_book_purpose.visibility = View.VISIBLE
                rel_lay_book_req.visibility = View.GONE
                sp_postjobtype.visibility = View.VISIBLE
                tv_post_job_date.visibility = View.GONE
                et_postjobcompany_link!!.visibility = View.GONE
                et_postjob_company.visibility = View.GONE
                fbut_book_req_post.visibility = View.GONE
                btn_job_post.visibility = View.VISIBLE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.GONE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.GONE


                jobType = arrayListOf<String>(
                    "SSLC",
                    "HSC",
                    "Arts & science",
                    "Engineering",
                    "medical",
                    "Civil service",
                    "Agricultural",
                    "Others"
                )

                setSpinner()

            }
            mStrFrgType.equals("job_post") -> {
                tv_form_lable.text = "if you know about job vacancies, share here "
                tv_post_job_date.visibility = View.VISIBLE
                edt_book_purpose.visibility = View.VISIBLE
                rel_lay_book_req.visibility = View.GONE
                sp_postjobtype.visibility = View.VISIBLE
                et_postjobcompany_link!!.visibility = View.VISIBLE
                et_postjob_company.visibility = View.VISIBLE
                fbut_book_req_post.visibility = View.GONE
                btn_job_post.visibility = View.VISIBLE
                img_job_post_close.visibility = View.GONE
                edt_blood_name.visibility = View.GONE
                edt_blood_mob.visibility = View.GONE
                pb_job.visibility = View.GONE

                jobType =
                    arrayListOf<String>("Software", "Teaching", "Accounting", "Call center", "Part time", "Others")
                setSpinner()
            }
        }



        if (isedit) {
            img_job_post_close.visibility = View.VISIBLE
            btn_job_post.text = "Update"
            if (mStrFrgType.equals("job_post")) {
                sp_postjobtype.setSelection(jobType.indexOf(jobModel!!.jobtype))
                myDocID = jobModel!!.id

                tv_post_job_date.text = jobModel!!.lastdate
                et_postjobcompany_link.setText(jobModel!!.companylink)
                et_postjob_company.setText(jobModel!!.company)
                edt_book_purpose.setText(jobModel!!.desc)


            } else if (mStrFrgType.equals("book_req_post")) {
                sp_postjobtype.setSelection(jobType.indexOf(mBookModel!!.jobtype))
                myDocID = mBookModel!!.id
                edt_book_purpose.setText(mBookModel!!.bookPurpose)
            } else if (mStrFrgType.equals("blood_add")) {
                sp_postjobtype.setSelection(mBloodList.indexOf(bloodModel!!.blood))
                edt_blood_mob.setText(bloodModel!!.mob.toString())
                edt_blood_name.setText(bloodModel!!.name)
                myDocID = bloodModel!!.id

            }
        } else {
            btn_job_post.text = "Post"
            img_job_post_close.visibility = View.GONE
        }

        tv_post_job_date.setOnClickListener { setDate() }

        btn_job_post.setOnClickListener {
            setValuetoFirestore()
        }
        img_job_post_close.setOnClickListener {
            if (mStrFrgType.equals("book_req_post") && isedit) {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(com.asow.astan.R.id.framelayout_dashboard, PostJobFragment("book", false, null, null, null))
                    .commit()
            } else {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(com.asow.astan.R.id.framelayout_dashboard, JobFragment()).commit()
            }


        }
        fbut_book_req_post.setOnClickListener {
            if (mStrFrgType.equals("book")) {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        com.asow.astan.R.id.framelayout_dashboard,
                        PostJobFragment("book_req_post", false, null, null, null)
                    )
                    .commit()
            } else if (mStrFrgType.equals("blood")) {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        com.asow.astan.R.id.framelayout_dashboard,
                        PostJobFragment("blood_add", false, null, null, null)
                    )
                    .commit()
            }
        }
    }

    private fun setDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            tv_post_job_date.setText("" + dayOfMonth + mMonthListShort[monthOfYear] + "" + ", " + year)
            tv_post_job_date.setText(mMonthListShort[monthOfYear] + dayOfMonth + ", " + year)
        }, year, month, day)

        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

    private fun setSpinner() {
        var arryAdap = ArrayAdapter(activity, R.layout.spinner_item, jobType)
        arryAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_postjobtype.adapter = arryAdap

    }

    private fun setValuetoFirestore() {
        if (mStrFrgType.equals("book_req_post")) {

            if (isedit) {
                if (edt_book_purpose.equals("")) {
                    pb_job.visibility = View.GONE
                    Toast.makeText(activity, "Kindly please enter purpose field", Toast.LENGTH_SHORT).show()
                } else {
                    pb_job.visibility = View.VISIBLE
                    val docRef = FirestoreSingleTon.getinstancex()!!.collection("Book").document(myDocID.toString())
                    docRef.update("bookPurpose", edt_book_purpose.text.toString())
                    docRef.update("jobtype", sp_postjobtype.selectedItem.toString())
                        .addOnSuccessListener {
                            pb_job.visibility = View.GONE
                            activity!!.supportFragmentManager.beginTransaction()
                                .replace(
                                    com.asow.astan.R.id.framelayout_dashboard,
                                    PostJobFragment("book", false, null, null, null)
                                )
                                .commit()
                        }
                        .addOnFailureListener { e ->
                            pb_job.visibility = View.GONE
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()

                        }
                }
            } else {

                var docId = FirestoreSingleTon.getinstancex()!!.collection("Book").document().id
                val bok = BookModel(
                    sp_postjobtype.selectedItem.toString(), edt_book_purpose.text.toString(),
                    AppPreference.getInstance(activity!!.applicationContext)!!.getString((AppPreference.StringKeys.PD_USER_ID)),
                    docId
                )
                if (edt_book_purpose.text.toString().equals("")) {
                    pb_job.visibility = View.GONE
                    Toast.makeText(activity, "Kindly please enter purpose field", Toast.LENGTH_SHORT).show()
                } else {
                    pb_job.visibility = View.VISIBLE
                    FirestoreSingleTon.getinstancex()!!.collection("Book").document(docId)
                        .set(bok)
                        .addOnSuccessListener {
                            pb_job.visibility = View.GONE
                            sp_postjobtype.setSelection(0)
                            edt_book_purpose.setText("")

                            Toast.makeText(activity, "Thank you for your sending job...", Toast.LENGTH_SHORT).show()
                            activity!!.supportFragmentManager.beginTransaction()
                                .replace(
                                    com.asow.astan.R.id.framelayout_dashboard,
                                    PostJobFragment("book", false, null, null, null)
                                ).commit()
                        }
                        .addOnFailureListener { e ->
                            pb_job.visibility = View.GONE
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        }
                }


            }
        } else if (mStrFrgType.equals("job_post")) {

            if (isedit) {

                if (tv_post_job_date.text.toString().equals("") && et_postjob_company.text.toString().equals("") &&
                    et_postjobcompany_link.text.toString().equals("")
                ) {
                    pb_job.visibility = View.GONE
                    Toast.makeText(activity, "Kindly please enter the all fields", Toast.LENGTH_SHORT).show()
                } else {
                    pb_job.visibility = View.VISIBLE
                    val docRef = FirestoreSingleTon.getinstancex()!!.collection("PostJob").document(myDocID.toString())
                    docRef.update("jobtype", sp_postjobtype.selectedItem.toString());
                    docRef.update("lastdate", tv_post_job_date.text.toString());
                    docRef.update("company", et_postjob_company.text.toString());
                    docRef.update("desc", edt_book_purpose.text.toString())
                    docRef.update("companylink", et_postjobcompany_link.text.toString()).addOnSuccessListener {
                        pb_job.visibility = View.GONE
                        sp_postjobtype.setSelection(0)
                        tv_post_job_date.text = ""
                        et_postjob_company.setText("")
                        et_postjobcompany_link.setText("")
                        Toast.makeText(activity, "Data has been updated", Toast.LENGTH_SHORT).show()
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(
                                com.asow.astan.R.id.framelayout_dashboard,
                                JobListFragment()
                            ).commit()
                    }
                        .addOnFailureListener { e ->
                            pb_job.visibility = View.GONE
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        }

                }

            } else {
                var docId = FirestoreSingleTon.getinstancex()!!.collection("PostJob").document().id
                val postJob = JobPostModel(
                    sp_postjobtype.selectedItem.toString(), tv_post_job_date.text.toString(),
                    et_postjob_company.text.toString(), et_postjobcompany_link.text.toString(),

                    AppPreference.getInstance(activity!!.applicationContext)!!.getString((AppPreference.StringKeys.PD_USER_ID))
                    , edt_book_purpose.text.toString(), docId
                )
                val v = et_postjob_company.text.toString()
                val vf = et_postjobcompany_link.text!!.equals("")
                if (tv_post_job_date.text.toString().equals("") || et_postjob_company.text.toString().isEmpty() ||
                    edt_book_purpose.text.toString().isEmpty() ||
                    et_postjobcompany_link.text.toString().isEmpty()
                ) {
                    pb_job.visibility = View.GONE
                    Toast.makeText(activity, "Kindly please enter the all fields", Toast.LENGTH_SHORT).show()
                } else {
                    pb_job.visibility = View.VISIBLE
                    FirestoreSingleTon.getinstancex()!!.collection("PostJob").document(docId)
                        .set(postJob)
                        .addOnSuccessListener {
                            pb_job.visibility = View.GONE
                            sp_postjobtype.setSelection(0)
                            tv_post_job_date.text = ""
                            et_postjob_company.setText("")
                            et_postjobcompany_link.setText("")
                            edt_book_purpose.setText("")

                            Toast.makeText(activity, "Thank you for your sending job...", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            pb_job.visibility = View.GONE
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }


        } else if (mStrFrgType.equals("blood_add")) {
            if(isedit)
            {
                if (edt_blood_name.text.toString().equals("")) {
                    Toast.makeText(activity, "Kindly please enter donor name ", Toast.LENGTH_SHORT).show()
                } else if (edt_blood_mob.text.toString().length < 9) {
                    Toast.makeText(activity, "Kindly please enter 10 digit number ", Toast.LENGTH_SHORT).show()
                } else {
                    pb_job.visibility = View.VISIBLE
                    val docRef = FirestoreSingleTon.getinstancex()!!.collection("Blood").document(myDocID.toString())
                    docRef.update("mob", edt_blood_mob.text.toString().toLong())
                    docRef.update("name",edt_blood_name.text.toString())
                    docRef.update("blood", sp_postjobtype.selectedItem.toString())
                        .addOnSuccessListener {
                            pb_job.visibility = View.GONE
                            activity!!.supportFragmentManager.beginTransaction()
                                .replace(
                                    com.asow.astan.R.id.framelayout_dashboard,
                                    PostJobFragment("blood", false, null, null, null)
                                )
                                .commit()
                        }
                        .addOnFailureListener { e ->
                            pb_job.visibility = View.GONE
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()

                        }
                }
            }else{
            var docId = FirestoreSingleTon.getinstancex()!!.collection("Blood").document().id
            val blood = BloodModel(
                sp_postjobtype.selectedItem.toString(), edt_blood_name.text.toString(),
                edt_blood_mob.text.toString().toLong(),
                AppPreference.getInstance(activity!!.applicationContext)!!.getString((AppPreference.StringKeys.PD_USER_ID))
                ,
                docId

            )


            if (edt_blood_name.text.toString().equals("")) {
                Toast.makeText(activity, "Kindly please enter donor name ", Toast.LENGTH_SHORT).show()
            } else if (edt_blood_mob.text.toString().length < 9) {
                Toast.makeText(activity, "Kindly please enter 10 digit number ", Toast.LENGTH_SHORT).show()
            } else {
                pb_job.visibility = View.VISIBLE
                FirestoreSingleTon.getinstancex()!!.collection("Blood").document(docId)
                    .set(blood)
                    .addOnSuccessListener {
                        sp_postjobtype.setSelection(0)
                        edt_book_purpose.setText("")

                        Toast.makeText(
                            activity,
                            "Thank you so much for your sending post...",
                            Toast.LENGTH_SHORT
                        ).show()
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(
                                com.asow.astan.R.id.framelayout_dashboard,
                                PostJobFragment("blood", false, null, null, null)
                            ).commit()
                        pb_job.visibility = View.GONE
                    }
                    .addOnFailureListener { e ->
                        pb_job.visibility = View.GONE
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    }
            }

        }}
    }

}
