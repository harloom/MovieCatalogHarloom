package com.harloomDeveloper.moviecatalogharloom.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase

class FavoritProvider : ContentProvider() {

    private  var mDatabase: AppDatabase? =null
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    companion object{
        const val AUTHORITY = "com.harloomDeveloper.moviecatalogharloom"
        private val BASE_PATH = "favorit"
        private const val MovieFavorite = 1
        private const val TvFavorite = 2
    }


    init{


        uriMatcher.addURI(AUTHORITY, "$BASE_PATH/#", MovieFavorite)
        uriMatcher.addURI(AUTHORITY, "$BASE_PATH/#", TvFavorite)
    }




    override fun onCreate(): Boolean {
        context?.let{ mCOntext->
            mDatabase = AppDatabase.getDatabase(context = mCOntext)
        }

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        val code = uriMatcher.match(uri)
        cursor = when (code) {
            MovieFavorite -> mDatabase?.movieDao()?.provider()
            TvFavorite -> mDatabase?.movieDao()?.provider()
            else -> null
        }

        return  cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }



    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return null
    }

}
