package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvFavoritTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import kotlinx.android.synthetic.main.fragment_favorit.*

/**
 * A placeholder fragment containing a simple view.
 */
class MyTvFragment : Fragment(R.layout.fragment_favorit), RcvFavoritTvAdapter.Interaction {
    override fun onItemSelected(position: Int, item: ETv) {

    }

    override fun onDeleteTap(position: Int, item: ETv) {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.title_alert)
                message(R.string.your_message)
                cornerRadius(8F)
                positiveButton(R.string.agree) { dialog ->
                    pageViewModel.deleteFromFavoritTv(item)
                    dialog.dismiss()
                }
                negativeButton(R.string.disagree) { dialog ->
                    // Do something
                    dialog.dismiss()
                }
            }
        }
    }

    private lateinit var pageViewModel: PageViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var movieAdapter: RcvFavoritTvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        initRcv()
    }

    private fun initVm() {
        activity?.let {activity->
            pageViewModel = ViewModelProvider(activity).get(PageViewModel::class.java)
        }


    }
    private fun initRcv() {
        movieAdapter = RcvFavoritTvAdapter(this@MyTvFragment)
        mRecyclerView =  view!!.findViewById(R.id.rcv_f_movies)
        mRecyclerView.apply {
            adapter = movieAdapter
        }
        pageViewModel.getTvs()?.observe(viewLifecycleOwner, Observer {tv->
                showLoading(false)
                showIndicatorDataNull(tv.isEmpty())
                movieAdapter.submitList(tv)
        })



    }

    private  fun showIndicatorDataNull(bool : Boolean){
        if(bool){
            bacground_indicator.visibility = View.VISIBLE
        }else{
            bacground_indicator.visibility = View.GONE
        }
    }
    private fun showLoading(st : Boolean){
        if(st){
            loading_indicator.visibility = View.VISIBLE
        }else{
            loading_indicator.visibility = View.GONE
        }
    }


}