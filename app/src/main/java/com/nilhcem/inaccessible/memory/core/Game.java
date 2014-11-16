package com.nilhcem.inaccessible.memory.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Parcelable {

    final Card[] mCards;
    Card mPreviouslyFlippedCard;

    public enum FlippedStatus {
        WAITING_FOR_SECOND_CARD_TO_BE_FLIPPED,
        PAIR_FOUND,
        INVALID_PAIR
    }

    public Game(String... cards) {
        List<Card> deck = new ArrayList<>();
        for (String card : cards) {
            deck.add(new Card(card));
            deck.add(new Card(card));
        }
        Collections.shuffle(deck);
        mCards = deck.toArray(new Card[deck.size()]);
    }

    public Card getCardAt(int position) {
        return mCards[position];
    }

    public int getNbCards() {
        return mCards.length;
    }

    public FlippedStatus flipCard(int position) {
        Card cardToFlip = mCards[position];
        if (!cardToFlip.isFaceDown()) {
            throw new UnsupportedOperationException("Card is already facing up");
        }

        cardToFlip.flip();
        if (mPreviouslyFlippedCard == null) {
            mPreviouslyFlippedCard = cardToFlip;
            return FlippedStatus.WAITING_FOR_SECOND_CARD_TO_BE_FLIPPED;
        } else {
            boolean success = setCardsToFaceOffAgainIfNotSimilar(mPreviouslyFlippedCard, cardToFlip);
            mPreviouslyFlippedCard = null;
            return (success) ? FlippedStatus.PAIR_FOUND : FlippedStatus.INVALID_PAIR;
        }
    }

    private boolean setCardsToFaceOffAgainIfNotSimilar(Card card1, Card card2) {
        // If cards are not similar, then they must be face of again.
        if (!card1.equals(card2)) {
            card1.flip();
            card2.flip();
            return false;
        }
        return true;
    }

    public boolean isOver() {
        for (Card card : mCards) {
            if (card.isFaceDown()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCards.length);
        dest.writeTypedArray(mCards, flags);
        dest.writeParcelable(this.mPreviouslyFlippedCard, 0);
    }

    private Game(Parcel in) {
        mCards = new Card[in.readInt()];
        in.readTypedArray(mCards, Card.CREATOR);
        this.mPreviouslyFlippedCard = in.readParcelable(Card.class.getClassLoader());
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
