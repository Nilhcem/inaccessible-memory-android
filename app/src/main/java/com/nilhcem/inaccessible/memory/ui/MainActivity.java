package com.nilhcem.inaccessible.memory.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nilhcem.inaccessible.memory.BuildConfig;
import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Game;

import java.util.Random;

import icepick.Icepick;
import icepick.Icicle;

import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.INVALID_PAIR;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.PAIR_FOUND;

public class MainActivity extends ActionBarActivity implements CardView.OnCardFlippedListener {

    @Icicle Game mGame;
    private ViewGroup mLayout;
    private String[] mCheersMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        if (mGame == null) {
            mGame = new Game(getResources().getStringArray(R.array.animals_array));
        }
        mCheersMessages = getResources().getStringArray(R.array.cheers_array);
        mLayout = createLayout(mGame);
        setContentView(mLayout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onCardFlipped(int position) {
        try {
            FlippedStatus flippedStatus = mGame.flipCard(position);
            if (flippedStatus.equals(PAIR_FOUND)) {
                announceMessage(mCheersMessages[new Random().nextInt(mCheersMessages.length)]);
            } else if (flippedStatus.equals(INVALID_PAIR)) {
                announceMessage(getString(R.string.pair_invalid));
            }
        } catch (UnsupportedOperationException e) {
            announceMessage(getString(R.string.card_already_flipped_error, position + 1));
        }

        updateCardsContentDescriptions();
        checkGameOver();
    }

    private ViewGroup createLayout(Game game) {
        GameLayout layout = new GameLayout(this);
        int nbCards = game.getNbCards();
        for (int i = 0; i < nbCards; i++) {
            layout.addView(new CardView(this, game.getCardAt(i), i, this));
        }
        return layout;
    }

    private void announceMessage(String error) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } else {
            mLayout.announceForAccessibility(error);
        }
    }

    private void updateCardsContentDescriptions() {
        final int count = mLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ((CardView) mLayout.getChildAt(i)).updateContentDescription();
        }
    }

    private void checkGameOver() {
        if (mGame.isOver()) {
            announceMessage(getString(R.string.game_over));
            finish();
        }
    }
}
