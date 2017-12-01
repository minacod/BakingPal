package com.example.shafy.bakingpal.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by shafy on 28/11/2017.
 */

@ContentProvider(authority = IngredientContentProvider.AUTHORITY, database = IngredientContract.IngredientDatebase.class)
public class IngredientContentProvider {
    public static final String AUTHORITY = "com.example.shafy.bakingpal";

    @TableEndpoint(table = IngredientContract.IngredientDatebase.INGREDIENTS) public static class Ingredients {
        @ContentUri(
                path = IngredientContract.IngredientDatebase.INGREDIENTS,
                type = "vnd.android.cursor.dir/list")
        public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY ).buildUpon()
                .appendPath(IngredientContract.IngredientDatebase.INGREDIENTS).build();
    }
}
