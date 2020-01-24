package com.harloomDeveloper.moviecatalogharloom.widgets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie

class StackRemoteViewsFactory(private val mContext: Context): RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = mutableListOf<EMovie>()
    private  var mDatabase: AppDatabase? =null
    override fun onCreate() {
        val identityToken = Binder.clearCallingIdentity()
        mDatabase = AppDatabase.getDatabase(context = mContext)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return  p0.toLong();
    }

    override fun onDataSetChanged() {
            try {
                val mDao = mDatabase?.movieDao()
                val movie = mDao?.getForWidget()
                    mWidgetItems.clear()
                if (movie != null) {
                    mWidgetItems.addAll(movie)
                }



            }catch (e : Exception){
                e.printStackTrace()
            }
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.fate))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.drstone))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.fgo))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.poster_bumblebee))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.poster_robinhood))

    }

    override fun hasStableIds(): Boolean =false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)
        try {

            val posterPath = Constant.BASE_IMAGE+mWidgetItems[position].posterPath

            Log.d("debug" ,posterPath)
          val bitmap =   Glide.with(mContext).asBitmap().load(posterPath).apply {
                fitCenter()
            }.submit(800,500)
                .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap);
//

        }catch (e : Exception){

            Log.d("debug" ,e.toString());
            e.printStackTrace()
        }
        val extras = bundleOf(
            BannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int =mWidgetItems.size

    override fun getViewTypeCount(): Int =1

    override fun onDestroy() {
    }
}