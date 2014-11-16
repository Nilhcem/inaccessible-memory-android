package com.nilhcem.inaccessible.memory.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Game;

public class MainActivity extends ActionBarActivity implements CardView.OnCardFlippedListener {

    private Game mGame;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = new Game(getResources().getStringArray(R.array.animals_array));
        mLayout = createLayout(mGame);
        setContentView(mLayout);
    }

    private View createLayout(Game game) {
        GameLayout layout = new GameLayout(this);
        int nbCards = game.getNbCards();
        for (int i = 0; i < nbCards; i++) {
            layout.addView(new CardView(this, game.getCardAt(i), i, this));
        }
        return layout;
    }

    @Override
    public void onCardFlipped(int position) {
        mGame.flipCard(position);
        mLayout.invalidate();
    }
}
