package com.nilhcem.inaccessible.memory.core;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeByte(mFaceDown ? (byte) 1 : (byte) 0);
    }

    private Card(Parcel in) {
        mTitle = in.readString();
        mFaceDown = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
