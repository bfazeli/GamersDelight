package edu.orangecoastcollege.cs273.mpaulding.gamersdelight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

import static edu.orangecoastcollege.cs273.mpaulding.gamersdelight.R.id.gameListView;
import static edu.orangecoastcollege.cs273.mpaulding.gamersdelight.R.id.gameRatingBar;
import static edu.orangecoastcollege.cs273.mpaulding.gamersdelight.R.id.nameEditText;

public class GameListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Game> gamesList;
    private GameListAdapter gamesListAdapter;
    private ListView gameListView;
    private EditText nameEditText, descriptionEditText;
    private RatingBar gameRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        db.addGame(new Game("League of Legends", "Multiplayer online battle arena", 4.5f, "lol.png"));
        db.addGame(new Game("Dark Souls III", "Action role-playing", 4.0f, "ds3.png"));
        db.addGame(new Game("The Division", "Single player experience", 3.5f, "division.png"));
        db.addGame(new Game("Doom FLH", "First person shooter", 2.5f, "doomflh.png"));
        db.addGame(new Game("Battlefield 1", "Single player campaign", 5.0f, "battlefield1.png"));

        // TODO:  Populate all games from the database into the list
        gamesList = db.getAllGames();
        // TODO:  Create a new ListAdapter connected to the correct layout file and list
        gamesListAdapter = new GameListAdapter(this, R.layout.game_list_item, gamesList);
        // TODO:  Connect the ListView with the ListAdapter
        gameListView = (ListView) findViewById(R.id.gameListView);
        gameListView.setAdapter(gamesListAdapter);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        gameRatingBar = (RatingBar) findViewById(R.id.gameRatingBar);
    }

    public void viewGameDetails(View view) {

        // TODO: Use an Intent to start the GameDetailsActivity with the data it needs to correctly inflate its views.
        if (view instanceof LinearLayout)
        {
            LinearLayout selectedGameLinearLayout = (LinearLayout) view;
            Game selectedGame = (Game) selectedGameLinearLayout.getTag();

            Intent detailIntent = new Intent(this, GameDetailsActivity.class);
            detailIntent.putExtra("Name", selectedGame.getName());
            detailIntent.putExtra("Description", selectedGame.getDescription());
            detailIntent.putExtra("Rating", selectedGame.getRating());
            detailIntent.putExtra("ImageName", selectedGame.getImageName());
            startActivity(detailIntent);
        }
    }

    public void addGame(View view)
    {
        // TODO:  Add a game to the database, list, list adapter
        String nameText = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        float rating = gameRatingBar.getRating();

        if (nameText.isEmpty())
        {
            Toast.makeText(this, "Game name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Game newGame = new Game(nameText, description, rating);
            // TODO:  Make sure the list adapter is updated
            gamesListAdapter.add(newGame);
            db.addGame(newGame);

            // TODO:  Clear all entries the user made (edit text and rating bar)
            nameEditText.setText("");
            descriptionEditText.setText("");
            gameRatingBar.setRating(0);
        }
    }

    public void clearAllGames(View view)
    {
        // TODO:  Delete all games from the database and lists
        gamesList.clear();
        db.deleteAllGames();
        gamesListAdapter.notifyDataSetChanged();

    }

}
