package com.harloomDeveloper.moviecatalogharloom.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase

class FavoritProvider : ContentProvider() {
    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    private  var mDatabase: AppDatabase? =null
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private  val MovieFavorit = 1;
    private val TvFavorit = 2


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
        val code = sUriMatcher.match(uri)
        cursor = when (code) {
            MovieFavorit -> mDatabase?.movieDao()?.provider()
            TvFavorit -> mDatabase?.movieDao()?.provider()
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
}
