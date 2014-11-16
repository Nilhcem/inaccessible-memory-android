package com.nilhcem.inaccessible.memory.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

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
}
