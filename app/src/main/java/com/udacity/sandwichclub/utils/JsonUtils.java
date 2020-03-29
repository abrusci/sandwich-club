package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        //Initialising sandwich and JSON objects
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJson = new JSONObject(json);

        //parsing JSON object
        String mainName = sandwichJson.getJSONObject("name").getString("mainName");

        JSONArray alsoKnownAsArray = sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<String>();
        for(int i = 0; i < alsoKnownAsArray.length(); i++){
            alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
        }

        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<String>();;
        for(int i = 0; i < ingredientsArray.length(); i++){
            ingredients.add(ingredientsArray.get(i).toString());
        }

        //populating sandwich object
        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}
