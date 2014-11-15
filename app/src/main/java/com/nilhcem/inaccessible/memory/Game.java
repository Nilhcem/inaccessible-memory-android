package com.nilhcem.inaccessible.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    final Card[] cards;
    Card previouslyFlippedCard;

    public Game(String... cards) {
        List<Card> deck = new ArrayList<>();
        for (String card : cards) {
            deck.add(new Card(card));
            deck.add(new Card(card));
        }
        Collections.shuffle(deck);
        this.cards = deck.toArray(new Card[deck.size()]);
    }

    public int getNbCards() {
        return cards.length;
    }

    public void flipCard(int position) {
        Card cardToFlip = cards[position];
        if (!cardToFlip.isFaceDown()) {
            throw new UnsupportedOperationException("Card is already face up");
        }

        cardToFlip.flip();
        if (previouslyFlippedCard == null) {
            previouslyFlippedCard = cardToFlip;
        } else {
            setCardsToFaceOffAgainIfNotSimilar(previouslyFlippedCard, cardToFlip);
            previouslyFlippedCard = null;
        }
    }

    private void setCardsToFaceOffAgainIfNotSimilar(Card card1, Card card2) {
        // If cards are not similar, then they must be face of again.
        if (!card1.equals(card2)) {
            card1.flip();
            card2.flip();
        }
    }

    public boolean isOver() {
        for (Card card : cards) {
            if (card.isFaceDown()) {
                return false;
            }
        }
        return true;
    }
}
