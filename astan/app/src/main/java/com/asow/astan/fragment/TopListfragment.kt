package com.asow.astan.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asow.astan.R
import com.asow.astan.model.TopModel
import com.asow.astan.util.FirestoreSingleTon
import kotlinx.android.synthetic.main.fragment_top_listfragment.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopListfragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TopListfragment(isMain: Boolean, img: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isMain = isMain
    private var img = img
    private var arryTop = ArrayList<TopModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {
        val view = inflater.inflate(R.layout.fragment_top_listfragment, container, false)
        if (isMain) {
            view.img_vp_main.visibility = View.VISIBLE
            view.tv_top_1.visibility = View.GONE
            view.tv_top_2.visibility = View.GONE
            view.tv_top_3.visibility = View.GONE
            view.tv_top_4.visibility = View.GONE
            view.tv_top_5.visibility = View.GONE
            view.tv_lab_10.visibility = View.GONE
            view.tv_lab_12.visibility = View.GONE
            view.tv_top_6.visibility = View.GONE
            view.tv_top_7.visibility = View.GONE
            view.tv_top_8.visibility = View.GONE
            view.tv_top_9.visibility = View.GONE
            view.tv_top_10.visibility = View.GONE

            view.img_vp_main.setImageResource(img)
        } else {

            view.img_vp_main.visibility = View.GONE
            view.tv_top_1.visibility = View.VISIBLE
            view.tv_top_2.visibility = View.VISIBLE
            view.tv_top_3.visibility = View.VISIBLE
            view.tv_top_4.visibility = View.VISIBLE
            view.tv_top_5.visibility = View.VISIBLE
            view.tv_lab_10.visibility = View.VISIBLE
            view.tv_lab_12.visibility = View.VISIBLE
            view.tv_top_6.visibility = View.VISIBLE
            view.tv_top_7.visibility = View.VISIBLE
            view.tv_top_8.visibility = View.VISIBLE
            view.tv_top_9.visibility = View.VISIBLE
            view.tv_top_10.visibility = View.VISIBLE
            val arryTop = ArrayList<TopModel>()
            val x = FirestoreSingleTon.getinstancex()!!.collection("Top").document("top")
                .get()
            x.addOnSuccessListener { s ->
                val l = s.toObject(TopModel::class.java)
                if (l != null) {
                    arryTop.addAll(listOf(l))
                    view.tv_top_1.text = arryTop.get(0).HSC!!.get(0)
                    view.tv_top_2.text = arryTop.get(0).HSC!!.get(1)
                    view.tv_top_3.text = arryTop.get(0).HSC!!.get(0)
                    view.tv_top_4.text = arryTop.get(0).HSC!!.get(0)
                    view.tv_top_5.text = arryTop.get(0).HSC!!.get(0)

                    view.tv_top_6.text = arryTop.get(0).sslc!!.get(0)
                    view.tv_top_7.text = arryTop.get(0).sslc!!.get(1)
                    view.tv_top_8.text = arryTop.get(0).sslc!!.get(2)
                    view.tv_top_9.text = arryTop.get(0).sslc!!.get(3)
                    view.tv_top_10.text = arryTop.get(0).sslc!!.get(4)

                    //  setValues(arryTop, view)
                }

            }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }

        }
        return view
    }

}
//companion object {
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TopListfragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    @JvmStatic
//    fun newInstance(param1: String, param2: String) =
//        TopListfragment().apply {
//            arguments = Bundle().apply {
//                putString(ARG_PARAM1, param1)
//                putString(ARG_PARAM2, param2)
//            }
//        }
//}






