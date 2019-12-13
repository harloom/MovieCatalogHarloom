package com.harloomDeveloper.moviecatalogharloom.ui.televisi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.DetailTvShowActivity
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.RcvTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.DataSourceTV
import com.harloomDeveloper.moviecatalogharloom.data.TvShow
import com.harloomDeveloper.moviecatalogharloom.utils

class TelevisiFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListTvShow: List<TvShow>
    private lateinit var mTvadapter: RcvTvAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRcv()
    }

    private fun initRcv() {
        mTvadapter.submitList(mListTvShow)
        mRecyclerView.apply {
            adapter = mTvadapter
        }
    }


    private fun init(){
        mRecyclerView = view!!.findViewById(R.id.rcv_tv)
        mListTvShow = DataSourceTV().getListTv(context!!)
        mTvadapter = RcvTvAdapter(callbackAdaptet)
    }

    private  val   callbackAdaptet : RcvTvAdapter.Interaction = object :
        RcvTvAdapter.Interaction {
        override fun onItemSelected(position: Int, item: TvShow) {
            startActivity(getIntentToDetail(context,item))
        }
    }

    private  fun getIntentToDetail(context: Context?, item: TvShow): Intent {
        val intent = Intent(context, DetailTvShowActivity::class.java)
        intent.putExtra(utils.KEY_TvShow,item)
        return  intent

    }
}