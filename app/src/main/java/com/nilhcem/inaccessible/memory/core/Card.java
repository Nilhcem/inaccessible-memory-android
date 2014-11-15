package com.nilhcem.inaccessible.memory.core;

public class Card {

    final String mTitle;
    boolean mFaceDown = true;

    public Card(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isFaceDown() {
        return mFaceDown;
    }

    public void flip() {
        mFaceDown = !mFaceDown;
    }

    @Override
    public String toString() {
        return "Card{title='" + mTitle + "', faceDown=" + mFaceDown + "}";
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
        if (mTitle != null ? !mTitle.equals(card.mTitle) : card.mTitle != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return mTitle != null ? mTitle.hashCode() : 0;
    }
}
