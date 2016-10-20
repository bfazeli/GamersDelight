package edu.orangecoastcollege.cs273.mpaulding.gamersdelight;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class GameDetailsActivity extends AppCompatActivity {

    private ImageView gameDetailsImageView;
    private TextView gameDetailsNameTextView, gameDetailsDescriptionTextView;
    private RatingBar gameDetailsRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        gameDetailsImageView = (ImageView) findViewById(R.id.gameDetailsImageView);
        gameDetailsNameTextView = (TextView) findViewById(R.id.gameDetailsNameTextView);
        gameDetailsDescriptionTextView = (TextView) findViewById(R.id.gameDetailsDescriptionTextView);
        gameDetailsRatingBar = (RatingBar) findViewById(R.id.gameDetailsRatingBar);

        // TODO:  Use the Intent object sent from GameListActivity to populate the Views on
        Intent intent = getIntent();

        // TODO:  this layout, including the TextViews, RatingBar and ImageView with the Game details.
        AssetManager am = this.getAssets();

        try {
            InputStream stream = am.open(intent.getStringExtra("ImageName"));
            Drawable image = Drawable.createFromStream(stream, intent.getStringExtra("Name"));
            gameDetailsImageView.setImageDrawable(image);
        } catch (IOException ex)
        {
            Log.e("Gamers Delight", "Cannot load image: " + intent.getStringExtra("ImageName") + ex);
        }

        gameDetailsNameTextView.setText(intent.getStringExtra("Name"));
        gameDetailsDescriptionTextView.setText(intent.getStringExtra("Description"));
        gameDetailsRatingBar.setRating(intent.getFloatExtra("Rating", 0));

    }
}
