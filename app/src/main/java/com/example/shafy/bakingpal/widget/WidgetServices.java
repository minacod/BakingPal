package com.example.shafy.bakingpal.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.data.IngredientContentProvider;
import com.example.shafy.bakingpal.data.IngredientContract;

/**
 * Created by shafy on 29/11/2017.
 */

public class WidgetServices extends IntentService {


    public static final String ACTION_UPDATE_WIDGET =
            "com.example.shafy.bakingpal.action.water_update_widget";

    public WidgetServices() {
        super("WidgetService");
    }

    public static void startActionUpdateWidget(Context context){
        Intent intent = new Intent(context,WidgetServices.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        Uri uri = IngredientContentProvider.Ingredients.INGREDIENTS;
        Cursor c =getContentResolver().query(uri,null,null,null,null);
        String recipeName="";
        if(c!=null&&c.getCount()!=0){
            c.moveToFirst();
            recipeName =c.getString(c.getColumnIndex(IngredientContract.IngredientEntry.RECIPE_NAME));
            c.close();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appwidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,IngredientWidget.class));
        IngredientWidget.updatePlantWidget(this,appWidgetManager,appwidgetIds, recipeName);
        appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.lv_widget_ingredient_list);
    }
}
