package com.example.shafy.bakingpal.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.data.IngredientContentProvider;
import com.example.shafy.bakingpal.data.IngredientContract;
import com.example.shafy.bakingpal.utils.ImgResUtils;

/**
 * Created by shafy on 29/11/2017.
 */

public class IngredientWidgetListService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListRemoteViewsFactory(getApplicationContext());
    }
}
class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    Context mContext;
    Cursor mCursor;

    public IngredientListRemoteViewsFactory(Context applicationContext) {
        this.mContext=applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri uri = IngredientContentProvider.Ingredients.INGREDIENTS;
        if(mCursor!=null)mCursor.close();
        mCursor = mContext.getContentResolver().query(uri,null,
                null,null,null);
    }

    @Override
    public void onDestroy() {
        if(mCursor!=null)
            mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        int ingIndex =mCursor.getColumnIndex(IngredientContract.IngredientEntry.INGREDIENT);
        int measureIndex =mCursor.getColumnIndex(IngredientContract.IngredientEntry.MEASURE);
        int qIndex =mCursor.getColumnIndex(IngredientContract.IngredientEntry.QUANTITY);

        mCursor.moveToPosition(position);
        int quantity =mCursor.getInt(qIndex);
        String ingredient =mCursor.getString(ingIndex);
        String measure =mCursor.getString(measureIndex);

        RemoteViews views =new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.tv_w_ingredient,ingredient);
        views.setTextViewText(R.id.tv_w_quantity,String.valueOf(quantity));
        views.setImageViewResource(R.id.iv_w_unit, ImgResUtils.getImgRes(measure));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
