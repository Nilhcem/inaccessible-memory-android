package com.nilhcem.inaccessible.memory.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.nilhcem.inaccessible.memory.BuildConfig;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Simplify manual testing by painting the card's content description
        if (BuildConfig.DEBUG) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(getContentDescription().toString(), 10, 25, paint);
        }
    }

    private void setContentDescription() {
        if (mCard.isFaceDown()) {
            setContentDescription(getContext().getResources().getString(R.string.card_content_description_face_down, mPosition + 1));
        } else {
            setContentDescription(getContext().getResources().getString(R.string.card_content_description_face_up, mPosition + 1, mCard.getTitle()));
        }
    }
}
