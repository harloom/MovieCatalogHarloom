package com.harloomDeveloper.moviecatalogharloom.ui.televisi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.like.LikeButton
import com.like.OnLikeListener

import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {
    private var  vm : MainViewModel? =null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"
        vm = ViewModelProviders.of(this@DetailTvShowActivity)[MainViewModel::class.java]


        val data = intent.getParcelableExtra<ResultTv>(Utils.KEY_TvShow)
        data?.let { initUI(it) }

    }

    private fun initUI(item : ResultTv){
        dtl_title.text = item.originalName
        dtl_summary.text = item.overview
        dtl_popularity.text = String.format(getString(R.string.title_rating),item.voteAverage)
        dtl_release.text = item.firstAirDate
        favorit_button.isLiked = item.isFavorit
        favorit_button.setOnLikeListener(object  : OnLikeListener{
            override fun liked(likeButton: LikeButton?) {
                val data = ETv(
                    id = item.id,
                    voteCount = item.voteCount,
                    voteAverage = item.voteAverage,
                    posterPath = item.posterPath,
                    popularity = item.popularity,
                    overview = item.overview,
                    originalName = item.originalName,
                    originalLanguage = item.originalLanguage,
                    backdropPath = item.backdropPath,
                    title = item.name,
                    firstAirDate = item.firstAirDate


                )
                vm?.addToFavoritTv(data)
            }

            override fun unLiked(likeButton: LikeButton?) {
                item.id?.let {
                    vm?.deleteByIdMTv(item.id)
                }
            }

        })


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
