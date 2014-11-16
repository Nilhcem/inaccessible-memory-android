package com.nilhcem.inaccessible.memory.ui.game;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nilhcem.inaccessible.memory.BuildConfig;
import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Game;
import com.nilhcem.inaccessible.memory.ui.base.BaseActivity;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.Random;

import icepick.Icicle;

import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.INVALID_PAIR;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.PAIR_FOUND;

public class GameActivity extends BaseActivity implements CardView.OnCardFlippedListener {

    public static final String EXTRA_NB_CARDS = "nb_cards";
    private static final int ONE_SECOND = 1000;

    @Icicle Game mGame;
    @Icicle String[] mCheersMessages;
    @Icicle DateTime mStarted;

    private int mNbCards;
    private ViewGroup mLayout;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNbCards = getIntent().getIntExtra(EXTRA_NB_CARDS, 0);

        if (savedInstanceState == null) {
            mGame = new Game(Arrays.copyOfRange(getResources().getStringArray(R.array.animals_array), 0, mNbCards));
            mCheersMessages = getResources().getStringArray(R.array.cheers_array);
            mStarted = new DateTime();
        }
        mLayout = createLayout(mGame);
        setContentView(mLayout);
    }

    @Override
    public void onCardFlipped(int position) {
        try {
            FlippedStatus flippedStatus = mGame.flipCard(position);
            if (flippedStatus.equals(PAIR_FOUND)) {
                announceMessage(mCheersMessages[new Random().nextInt(mCheersMessages.length)], true);
            } else if (flippedStatus.equals(INVALID_PAIR)) {
                announceMessage(getString(R.string.pair_invalid), true);
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

    private void announceMessage(String message) {
        announceMessage(message, false);
    }

    private void announceMessage(final String message, boolean withDelay) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    mLayout.announceForAccessibility(message);
                }
            }, withDelay ? ONE_SECOND : 0);
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
            announceMessage(getString(R.string.game_over, mNbCards * 2, new Duration(mStarted, new DateTime()).getStandardSeconds()));
            finish();
        }
    }
}
