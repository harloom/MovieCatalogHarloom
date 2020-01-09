package com.harloomDeveloper.moviecatalogharloom.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.harloomDeveloper.moviecatalogharloom.R
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TvShowExample(
    val id : String,
     val title: String,
     val desc: String,
     val status: String,
     val genre: List<String>,
     val tahunRilis: String,
     val jamTayang : String,
     val thumbails: Int?
) : Parcelable


class DataSourceTV{
    private  val  tv : MutableList<TvShowExample> = mutableListOf()

    fun getListTv(context: Context) : List<TvShowExample>{
        //1
        tv.add(TvShowExample(
            id = "#1",
            title = "Batwoman",
            desc = context.getString(R.string.bat_woman),
            status = context.getString(R.string.status1), genre = mutableListOf<String>(
                "Action","Sci-fi" , "Fantasy"
           ), tahunRilis = "2019"
        , thumbails = R.drawable.bat_woman
        ,jamTayang = ""
         )
        )
        //2
        tv.add(TvShowExample(
            id = "#2",
            title = "Final Space",
            desc = context.getString(R.string.final_space),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Action","Adventure" , "Fantasy"
            ), tahunRilis = "2018"
            , thumbails = R.drawable.fiinal_space
            ,jamTayang = ""
        )
        )

        //3
        tv.add(TvShowExample(
            id = "#3",
            title = "Assassins Pride ",
            desc = context.getString(R.string.assasin_pride),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Action","Adventure" , "Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.assasin_pride
            ,jamTayang = ""
        )
        )

        //4
        tv.add(TvShowExample(
            id = "#4",
            title = "Euphoria",
            desc = context.getString(R.string.euphoria),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
            "Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.eroupia
            ,jamTayang = ""
        )
        )

        //5
        tv.add(TvShowExample(
            id = "#5",
            title = "Servant",
            desc = context.getString(R.string.servant),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.servant
            ,jamTayang = ""
        )
        )

        //6
        tv.add(TvShowExample(
            id = "#6",
            title = "The L Word: Generation Q",
            desc = context.getString(R.string.thel),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.thel
            ,jamTayang = ""
        )
        )

        //7
        tv.add(TvShowExample(
            id = "#7",
            title = "Dr. Stone",
            desc = context.getString(R.string.dr_stone),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Animation" ,"Adventure"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.drstone
            ,jamTayang = ""
        )
        )

        //8
        tv.add(TvShowExample(
            id = "#8",
            title = "Fate/Grand Order Absolute Demonic Front: Babylonia",
            desc = context.getString(R.string.fgo),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Animation" ,"Adventure", "Drama","Sci-fi" , "Fantasy"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.fgo
            ,jamTayang = ""
        )
        )

        //9

        tv.add(TvShowExample(
            id = "#9",
            title = "Fam",
            desc = context.getString(R.string.farm),
            status =  context.getString(R.string.status1), genre = mutableListOf<String>(
                "Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.fam
            ,jamTayang = ""
        )
        )

        //10
        tv.add(TvShowExample(
            id = "#10",
            title = "Good Omens",
            desc = context.getString(R.string.goodOmens),
            status =  context.getString(R.string.status2), genre = mutableListOf<String>(
               "Comedy" ,"Drama"
            ), tahunRilis = "2019"
            , thumbails = R.drawable.goodome
            ,jamTayang = ""
        )
        )
        return  tv
    }

}