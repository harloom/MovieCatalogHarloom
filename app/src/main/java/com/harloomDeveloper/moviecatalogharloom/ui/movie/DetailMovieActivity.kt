package com.harloomDeveloper.moviecatalogharloom.ui.movie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepositoryImp
import com.like.LikeButton
import com.like.OnLikeListener

import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {
    private var data : ResultMovie?=null
    private var  vm : MainViewModel? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"

        vm = ViewModelProviders.of(this@DetailMovieActivity)[MainViewModel::class.java]


        data = intent.getParcelableExtra<ResultMovie>(Utils.KEY_MOVIE)
        data?.let {
            initUI(it)
        }

    }


    private fun initUI(item : ResultMovie){

        dtl_title.text = item.title
        dtl_summary.text = item.overview
        dtl_tahun.text = item.releaseDate
        dtl_vote.text = String.format(getString(R.string.title_rating),item.voteAverage)

        favorit_button.isLiked = item.isFavorit
        favorit_button.setOnLikeListener(object  : OnLikeListener{
            override fun liked(likeButton: LikeButton?) {
                val movie  = EMovie(id = item.id,title = item.title,adult = item.adult,backdropPath = item.backdropPath,
                    originalLanguage = item.originalLanguage,originalTitle = item.originalTitle,overview = item.overview,
                    popularity = item.popularity,posterPath = item.posterPath,releaseDate = item.releaseDate,
                    video = item.video,
                    voteAverage = item.voteAverage,
                    voteCount = item.voteCount)
                vm?.addToFavoritMovie(movie)
            }

            override fun unLiked(likeButton: LikeButton?) {
                item.id?.let {
                    vm?.deleteByIdMovie(item.id)
                }
            }

        })
        try {
            Glide.with(this@DetailMovieActivity)
                .load(Constant.BASE_IMAGE+item.posterPath)
                .into(dtl_thumbail)


        }catch (e : Exception){

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
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

    override fun onDestroy() {
        super.onDestroy()

    }
}
