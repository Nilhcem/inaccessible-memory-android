package com.nilhcem.inaccessible.memory;

public class Card {

    final String title;
    boolean faceDown = true;

    public Card(String title) {
        this.title = title;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void flip() {
        faceDown = !faceDown;
    }

    @Override
    public String toString() {
        return "Card{title='" + title + "', faceDown=" + faceDown + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;
        if (title != null ? !title.equals(card.title) : card.title != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
