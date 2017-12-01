package com.example.shafy.bakingpal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.view.View;
import android.widget.RemoteViews;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.model.Ingredient;
import com.example.shafy.bakingpal.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String c) {

        RemoteViews views = getIngredientListRemoteView(context,c);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetServices.startActionUpdateWidget(context);
    }

    public static void updatePlantWidget(Context context, AppWidgetManager appWidgetManager ,int[] appWidgetIds,String c){

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,appWidgetId,c);
        }

    }

    private static RemoteViews getIngredientListRemoteView(Context context,String c){
        RemoteViews views =new RemoteViews(context.getPackageName(),R.layout.widget_list_view);
        if(c==null||!c.equals("")){
            views.setTextViewText(R.id.tv_name,c);
            Intent i =new Intent(context,IngredientWidgetListService.class);
            views.setRemoteAdapter(R.id.lv_widget_ingredient_list,i);

            views.setViewVisibility(R.id.lv_widget_ingredient_list, View.VISIBLE);

            Intent launch = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,launch,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.rl_widget,pi);
        }
        else{
            views.setTextViewText(R.id.tv_name,"No Recipe");

            views.setViewVisibility(R.id.lv_widget_ingredient_list, View.INVISIBLE);

            Intent launch = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,launch,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.rl_widget,pi);
        }



        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

