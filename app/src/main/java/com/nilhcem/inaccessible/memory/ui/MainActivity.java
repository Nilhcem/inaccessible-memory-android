package com.nilhcem.inaccessible.memory.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Game;

public class MainActivity extends ActionBarActivity {

    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = new Game(getResources().getStringArray(R.array.animals_array));
        setContentView(createLayout());
    }

    private View createLayout() {
        GameLayout layout = new GameLayout(this);
        int nbCards = mGame.getNbCards();
        for (int i = 0; i < nbCards; i++) {
            layout.addView(new CardView(this, mGame.getCardAt(i), i));
        }
        return layout;
    }
}
