package com.harloomDeveloper.moviecatalogharloom.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.harloomDeveloper.moviecatalogharloom.R
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movies(
    val id : String,
     val title: String,
     val desc: String,
     val status: String,
     val genre: List<String>,
     val tahunRilis: String,
     val thumbails: Int?
) : Parcelable


class DataSourceMovie{
    private  val  movies : MutableList<Movies> = mutableListOf()

    fun getListMovide() : List<Movies>{
        //1
        movies.add(Movies(
            id = "#1",
            title = "Venom ",
            desc = "Investigative journalist Eddie Brock attempts a comeback following a scandal, but accidentally becomes the host of Venom, a violent, super powerful alien symbiote. Soon, he must rely on his newfound powers to protect the world from a shadowy organization looking for a symbiote of their own.",
            status = "Released", genre = mutableListOf<String>(
           "Action","SCIENCE FICTION"
           ), tahunRilis = "2018"
        , thumbails = R.drawable.poster_venom
         )
        )
        //2
        movies.add(Movies(
            id = "#2",
            title = "Avengers: Infinity War",
            desc = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            status = "Released",
            genre = mutableListOf<String>(
            "Adventrue","Action","Science Fiction"
        ), tahunRilis = "2018"
        , thumbails = R.drawable.poster_avengerinfinity
             )
        )

        //3
        movies.add(Movies(
            id = "#3",
            title = "Aquaman",
            desc = "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
            status = "",genre = mutableListOf<String>(
            "Action","Fantasy","Adeventure"
        ),tahunRilis = "2018"
        ,thumbails = R.drawable.poster_aquaman
        )
        )

        //4
        movies.add(Movies(
            id = "#4",
            title = "The Girl in the Spider's Web",
            desc = "In Stockholm, Sweden, hacker Lisbeth Salander is hired by Frans Balder, a computer engineer, to retrieve a program that he believes it is too dangerous to exist.",
            status = "Released",genre = mutableListOf<String>(
            "Action","Crime"
        ),tahunRilis = "2018",
            thumbails = R.drawable.poster_thegirl
        )
        )

        //5
        movies.add(Movies(
            id = "#5",
            title = "Creed II",
            desc = "Between personal obligations and training for his next big fight against an opponent with ties to his family's past, Adonis Creed is up against the challenge of his life.",
            status = "Released",genre = mutableListOf<String>(
            "Drama"
        ),tahunRilis = "2018"
        ,thumbails = R.drawable.poster_creed        )
        )

        //6
        movies.add(Movies(
            id = "#6",
            title = "Dragon Ball Super: Broly",
            desc = "Earth is peaceful following the Tournament of Power. Realizing that the universes still hold many more strong people yet to see, Goku spends all his days training to reach even greater heights. Then one day, Goku and Vegeta are faced by a Saiyan called 'Broly' who they've never seen before. The Saiyans were supposed to have been almost completely wiped out in the destruction of Planet Vegeta, so what's this one doing on Earth? This encounter between the three Saiyans who have followed completely different destinies turns into a stupendous battle, with even Frieza (back from Hell) getting caught up in the mix.",
            status = "Released",genre = mutableListOf<String>(
            "Action","Adventure","Fantasy"
        ),tahunRilis = "2018",
            thumbails = R.drawable.poster_dragonball
        )
        )

        //7
        movies.add(Movies(
            id = "#7",
            title = "Spider-Man: Into the Spider-Verse",
            desc = "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension.",
            status = "Released",genre = mutableListOf<String>(
            "Action","Adventure","Comedy"
        ),tahunRilis = "2018",

            thumbails = R.drawable.poster_spiderman
        )
        )

        //8
        movies.add(Movies(
            id = "#8",
            title = "Robin Hood",
            desc = "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
            status = "Released",genre = mutableListOf<String>(
            "Action","Adventure"
        ),tahunRilis = "2018",
            thumbails = R.drawable.poster_robinhood
        )
        )

        //9

        movies.add(Movies(
            id = "#9",
            title = "Bumblebee",
            desc = "On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken. When Charlie revives him, she quickly learns this is no ordinary yellow VW bug.",
            status = "Released",genre = mutableListOf<String>(
            "Action","Science Fiction","Adventure"
        ),tahunRilis = "2018",
            thumbails = R.drawable.poster_bumblebee
        )
        )

        //10
        movies.add(Movies(
            id = "#10",
            title = "Fate/Stay Night: Heaven's Feel II. Lost Butterfly",
            desc = "Theatrical-release adaptation of the visual novel \"Fate/stay night\", following the third and final route. (Part 2 of a trilogy.)",
            status = "Released",genre = mutableListOf<String>(
            "Action","Drama","Fantasy"
        ),tahunRilis = "2019",
            thumbails = R.drawable.fate
        )
        )
        return  movies
    }

}