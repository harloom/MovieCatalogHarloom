package com.harloomDeveloper.moviecatalogharloom.data.local.provider

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {


    const val AUTHORITY = "com.harloomDeveloper.moviecatalogharloom"
    const val SCHEME = "content"
    class MovieColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
            const val pathPoser = "pathPoser"

            // untuk membuat URI content://com.harloomDeveloper.moviecatalogharloom/favorit_movie
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}