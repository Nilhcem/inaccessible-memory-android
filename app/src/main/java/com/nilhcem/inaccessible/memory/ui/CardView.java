package com.nilhcem.inaccessible.memory.ui;

import android.content.Context;
import android.view.View;

import com.nilhcem.inaccessible.memory.core.Card;

public class CardView extends View {

    private Card mCard;
    private int mPosition;

    public CardView(Context context, Card card, int position) {
        super(context);
        mCard = card;
        mPosition = position;
    }
}
