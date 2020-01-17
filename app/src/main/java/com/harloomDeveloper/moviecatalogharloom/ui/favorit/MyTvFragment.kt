package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvFavoritTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import kotlinx.android.synthetic.main.fragment_favorit.*

/**
 * A placeholder fragment containing a simple view.
 */
class MyTvFragment : Fragment(), RcvFavoritTvAdapter.Interaction {
    override fun onItemSelected(position: Int, item: ETv) {

    }

    override fun onDeleteTap(position: Int, item: ETv) {
        MaterialDialog(context!!).show {
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

    private lateinit var pageViewModel: PageViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var movieAdapter: RcvFavoritTvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorit, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        initVm()
        initRcv()
    }

    private fun initVm() {
        pageViewModel = ViewModelProviders.of(activity!!).get(PageViewModel::class.java)

    }
    private fun initRcv() {
        movieAdapter = RcvFavoritTvAdapter(this@MyTvFragment)
        mRecyclerView =  view!!.findViewById(R.id.rcv_f_movies)
        mRecyclerView.apply {
            adapter = movieAdapter
        }
        pageViewModel.getTvs()?.observe(this@MyTvFragment, Observer {tv->
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