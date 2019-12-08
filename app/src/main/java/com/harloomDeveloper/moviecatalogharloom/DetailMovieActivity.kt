package com.harloomDeveloper.moviecatalogharloom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harloomDeveloper.moviecatalogharloom.data.Movies
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"

        val data = intent.getParcelableExtra<Movies>(utils.KEY_MOVIE)
        data.let {
            initUI(it)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initUI(item : Movies){
        dtl_title.text = "${item.id} ${item.title}"
        dtl_genre.text =getGenre(item)
        dtl_summary.text = item.desc
        dtl_tahun.text = item.tahunRilis
        dtl_release.text = "Status : "+item.status
        dtl_thumbail.setImageResource(item.thumbails!!)
    }

    private fun  getGenre (item: Movies): String {
        var genre : String= ""
            item.genre.forEach {
                genre += " $it,"
            }
        return genre.dropLast(1)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
