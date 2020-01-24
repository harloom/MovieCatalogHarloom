package com.harloomDeveloper.moviecatalogharloom.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.harloomDeveloper.moviecatalogharloom.R

/**
 * Implementation of App Widget functionality.
 */
class BannerWidget : AppWidgetProvider() {
    companion object{
        const val TOAST_ACTION = "com.harloomDeveloper.moviecatalogharloom.TOAST_ACTION"
         val REFRESS_ACTION = "com.harloomDeveloper.moviecatalogharloom.REFRESS"
        const val EXTRA_ITEM = "com.harloomDeveloper.moviecatalogharloom.EXTRA_ITEM"
            fun refress(mcontext: Context){
                val intentWidget = Intent(mcontext, BannerWidget::class.java)
                intentWidget.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                val sendPending = PendingIntent.getBroadcast(mcontext, 0, intentWidget, PendingIntent.FLAG_UPDATE_CURRENT)
                sendPending.send()
            }

         fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
             //intent untuk ke service
             val intent = Intent(context, StackWidgetService::class.java)
             intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
             intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()


             //view widget
             val views = RemoteViews(context.packageName, R.layout.banner_widget)

             //get id stackview
             views.setRemoteAdapter(R.id.stack_view, intent)
             views.setEmptyView(R.id.stack_view, R.id.empty_view)

             //intent saat ditap
             val toastIntent = Intent(context, BannerWidget::class.java)
             toastIntent.action = TOAST_ACTION
             toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
             intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            //pendingIntent
             val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
             views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

             // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        //get pending intent
        if (intent.action != null) {
            Log.d("debug" ,intent.action.toString())
            if (intent.action == TOAST_ACTION) {
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }

            if(intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE){
                val appWidget = AppWidgetManager.getInstance(context)
                val thisWidget = ComponentName(context,BannerWidget::class.java)
                val id = appWidget.getAppWidgetIds(thisWidget)
                appWidget.notifyAppWidgetViewDataChanged(id,R.id.stack_view)

            }
        }


    }


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

