package com.example.shafy.bakingpal.utils;

import com.example.shafy.bakingpal.model.Ingredient;
import com.example.shafy.bakingpal.model.Recipe;
import com.example.shafy.bakingpal.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shafy on 22/11/2017.
 */

public class JsonUtils {

    public static String ID ="id";
    public static String NAME ="name";
    public static String INGREDIENTS ="ingredients";
    public static String STEPS ="steps";
    public static String SERVINGS ="servings";
    public static String IMAGE ="image";
    public static String QUANTITY ="quantity";
    public static String MEASURE ="measure";
    public static String INGREDIENT ="ingredient";
    public static String SHORT_DESCRIPTION ="shortDescription";
    public static String DESCRIPTION ="description";
    public static String VIDEO_URL ="videoURL";
    public static String THUMBNAIL_URL ="thumbnailURL";


    public static Recipe [] getRecipes(String jsonResponse) throws JSONException{

        JSONArray array = new JSONArray(jsonResponse);
        Recipe[] recipes = new Recipe[array.length()];
        for (int i =0;i<array.length();i++){
            JSONObject jsonRecipe = array.getJSONObject(i);
            recipes[i]= new Recipe(
                    jsonRecipe.getInt(ID),
                    jsonRecipe.getString(NAME),
                    getIngredients(jsonRecipe.getJSONArray(INGREDIENTS)),
                    getSteps(jsonRecipe.getJSONArray(STEPS)),
                    jsonRecipe.getInt(SERVINGS),
                    jsonRecipe.getString(IMAGE)
            );
        }
        return recipes;
    }
    private static Ingredient[] getIngredients(JSONArray jsonArray) throws JSONException{
        Ingredient[] ingredients = new Ingredient [jsonArray.length()];
        for (int i =0;i<jsonArray.length();i++){
            JSONObject jsonIngredient = jsonArray.getJSONObject(i);
            ingredients[i]= new Ingredient(
                    jsonIngredient.getInt(QUANTITY),
                    jsonIngredient.getString(MEASURE),
                    jsonIngredient.getString(INGREDIENT)
            );
        }
        return ingredients;
    }

    private static Step[] getSteps(JSONArray jsonArray)throws JSONException{
        Step[] steps = new Step [jsonArray.length()];
        for (int i =0;i<jsonArray.length();i++){
            JSONObject jsonStep = jsonArray.getJSONObject(i);
            steps[i]= new Step(
                    jsonStep.getInt(ID),
                    jsonStep.getString(SHORT_DESCRIPTION),
                    jsonStep.getString(DESCRIPTION),
                    jsonStep.getString(VIDEO_URL),
                    jsonStep.getString(THUMBNAIL_URL)
            );
        }
        return steps;
    }
}
