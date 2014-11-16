package com.nilhcem.inaccessible.memory.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nilhcem.inaccessible.memory.BuildConfig;
import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Game;

public class MainActivity extends ActionBarActivity implements CardView.OnCardFlippedListener {

    private Game mGame;
    private ViewGroup mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = new Game(getResources().getStringArray(R.array.animals_array));
        mLayout = createLayout(mGame);
        setContentView(mLayout);
    }

    private ViewGroup createLayout(Game game) {
        GameLayout layout = new GameLayout(this);
        int nbCards = game.getNbCards();
        for (int i = 0; i < nbCards; i++) {
            layout.addView(new CardView(this, game.getCardAt(i), i, this));
        }
        return layout;
    }

    @Override
    public void onCardFlipped(int position) {
        try {
            mGame.flipCard(position);
        } catch (UnsupportedOperationException e) {
            displayError(getString(R.string.card_already_flipped_error, position + 1));
        }

        final int count = mLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ((CardView) mLayout.getChildAt(i)).updateContentDescription();
        }
    }

    private void displayError(String error) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } else {
            mLayout.announceForAccessibility(error);
        }
    }
}
