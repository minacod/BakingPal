package com.example.shafy.bakingpal.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by shafy on 28/11/2017.
 */

public class IngredientContract {

    public interface IngredientEntry{
        @DataType(DataType.Type.INTEGER)
        String RECIPE_ID = "recipe_id";
        @DataType(DataType.Type.TEXT)
        String RECIPE_NAME = "recipe_name";
        @DataType(DataType.Type.INTEGER)
        String QUANTITY = "quantity";
        @DataType(DataType.Type.TEXT)
        String MEASURE = "measure";
        @DataType(DataType.Type.TEXT)
        String INGREDIENT = "ingredient";
    }
    @Database(version = IngredientDatebase.VERSION)
    public final class IngredientDatebase {

        public static final int VERSION = 1;

        @Table(IngredientEntry.class) public static final String INGREDIENTS = "ingredients";
    }
}
