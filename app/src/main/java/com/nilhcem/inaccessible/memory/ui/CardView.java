package com.nilhcem.inaccessible.memory.ui;

import android.content.Context;
import android.view.View;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Card;

public class CardView extends View {

    private Card mCard;
    private int mPosition;

    public CardView(Context context, Card card, int position) {
        super(context);
        mCard = card;
        mPosition = position;
        setContentDescription();
    }

    private void setContentDescription() {
        if (mCard.isFaceDown()) {
            setContentDescription(getContext().getResources().getString(R.string.card_content_description_face_down, mPosition + 1));
        } else {
            setContentDescription(getContext().getResources().getString(R.string.card_content_description_face_up, mPosition + 1, mCard.getTitle()));
        }
    }
}
