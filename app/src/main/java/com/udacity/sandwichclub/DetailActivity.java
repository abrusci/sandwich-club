package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownAsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            closeOnError();
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        //ensuring correct formatting of ingredients list
        for(int i=0; i<sandwich.getIngredients().size(); i++)
            if(i==sandwich.getIngredients().size()-1)
                mIngredientsTextView.append(sandwich.getIngredients().get(i));
            else
                mIngredientsTextView.append(sandwich.getIngredients().get(i)+", ");

        //Configuring visibility to avoid blank space if there is no value for "also known as"
        //ensuring correct formatting of also known as list
        if(!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            for (int j = 0; j < sandwich.getAlsoKnownAs().size(); j++)
                if (j == sandwich.getAlsoKnownAs().size() - 1)
                    mAlsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(j));
                else
                    mAlsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(j) + ", ");
        }

    }
}
