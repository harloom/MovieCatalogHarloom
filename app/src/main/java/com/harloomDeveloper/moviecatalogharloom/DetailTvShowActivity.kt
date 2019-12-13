package com.harloomDeveloper.moviecatalogharloom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harloomDeveloper.moviecatalogharloom.data.TvShow

import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"

        val data = intent.getParcelableExtra<TvShow>(utils.KEY_TvShow)
        data.let {
            initUI(it)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initUI(item : TvShow){
        dtl_title.text =  String.format(getString(R.string.title_detail),item.id,item.title)
        dtl_genre.text =getGenre(item)
        dtl_summary.text = item.desc
        dtl_tahun.text = item.tahunRilis
        dtl_release.text = String.format(getString(R.string.title_status),item.status)
        dtl_thumbail.setImageResource(item.thumbails!!)
    }

    private fun  getGenre (item: TvShow): String {
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
