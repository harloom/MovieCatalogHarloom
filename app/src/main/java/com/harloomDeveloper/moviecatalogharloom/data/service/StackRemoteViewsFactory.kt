package com.harloomDeveloper.moviecatalogharloom.data.service

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.ui.widget.MyMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = mutableListOf<EMovie>()
    private  var mDao : MovieDao? =null

    init {
       val  mDatabase : AppDatabase? = AppDatabase.getDatabase(mContext)
        mDao = mDatabase?.movieDao()
    }

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? =null

    override fun getItemId(p0: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
         val   job = CoroutineScope(IO).launch {
             val list = mDao?.getForWidget()
             list?.let { listMovie->
                 mWidgetItems.addAll(listMovie)

             }
        }

        job.invokeOnCompletion {
            Binder.restoreCallingIdentity(identityToken)
        }


    }
    override fun hasStableIds(): Boolean  = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)

        try {

            val posterPath = Constant.BASE_IMAGE+mWidgetItems[position].posterPath
            Glide.with(mContext).asBitmap().load(posterPath).into(object : CustomTarget<Bitmap>()
            {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    rv.setImageViewBitmap(R.id.imageView, resource)

                }
            })

        }catch (e : Exception){
            e.printStackTrace()
        }

        val extras = bundleOf(
            MyMovie.EXTRA_ITEM to position
        )


        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int  =mWidgetItems.size
    override fun getViewTypeCount(): Int  =1

    override fun onDestroy() {
    }
}