package com.harloomDeveloper.moviecatalogharloom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"



        val data = intent.getParcelableExtra<ResultTv>(utils.KEY_TvShow)
        data?.let { initUI(it) }

    }

    private fun initUI(item : ResultTv){
        dtl_title.text =  String.format(getString(R.string.title_detail),item.id,item.name)
        dtl_summary.text = item.overview
        dtl_popularity.text = item.popularity.toString()
        dtl_release.text = item.firstAirDate

        try {
            dtl_thumbail.clipToOutline =true
            Glide.with(this@DetailTvShowActivity)
                .load(Constant.BASE_IMAGE+item.posterPath)
                .into(dtl_thumbail)
        }catch (e : Exception){

        }
    }

//    private fun  getGenre (item: ResultTv): String {
//        var genre : String= ""
//            item.genreIds?.forEach {
//                genre += " $it,"
//            }
//        return genre.dropLast(1)
//    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
