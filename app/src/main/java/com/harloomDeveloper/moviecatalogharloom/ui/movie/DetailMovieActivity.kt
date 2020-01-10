package com.harloomDeveloper.moviecatalogharloom.ui.movie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.Utils

import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {


    private var data : ResultMovie?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"





        data = intent.getParcelableExtra<ResultMovie>(Utils.KEY_MOVIE)
        data?.let {
            initUI(it)
        }

    }


    private fun initUI(item : ResultMovie){
//        dtl_title.text =  String.format(getString(R.string.title_detail),item.id,item.title)
//        dtl_genre.text =getGenre(item)
        dtl_title.text = item.title
        dtl_summary.text = item.overview
        dtl_tahun.text = item.releaseDate
        dtl_vote.text = String.format(getString(R.string.title_rating),item.voteAverage)
//        dtl_thumbail.setImageResource(item.thumbails!!)

        try {
            Glide.with(this@DetailMovieActivity)
                .load(Constant.BASE_IMAGE+item.posterPath)
                .into(dtl_thumbail)
        }catch (e : Exception){

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        data?.let {
//            outState.putParcelable(Utils.KEY_STATE_ITEM,data)
//        }
        super.onSaveInstanceState(outState)
    }


    private fun  getGenre (item: ResultMovie): String {
        var genre : String= ""
            item.genreIds?.forEach {
                genre += " $it,"
            }
        return genre.dropLast(1)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
