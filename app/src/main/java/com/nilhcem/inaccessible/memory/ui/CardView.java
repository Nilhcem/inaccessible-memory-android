package com.nilhcem.inaccessible.memory.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.nilhcem.inaccessible.memory.BuildConfig;
import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Card;

import java.util.Locale;

public class CardView extends View implements View.OnClickListener {

    private Card mCard;
    private int mPosition;
    private OnCardFlippedListener mListener;

    public interface OnCardFlippedListener {
        void onCardFlipped(int position);
    }

    public CardView(Context context, Card card, int position, OnCardFlippedListener listener) {
        super(context);
        mCard = card;
        mPosition = position;
        mListener = listener;
        updateContentDescription();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onCardFlipped(mPosition);
        updateContentDescription();
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

            canvas.drawText(getContentDescription().toString().replaceAll(getResources().getString(R.string.card_content_description_prefix), "#"), 10, 25, paint);
        }
    }

    private void updateContentDescription() {
        String contentDescription;
        int readablePos = mPosition + 1;
        Resources res = getContext().getResources();

        if (mCard.isFaceDown()) {
            contentDescription = res.getString(R.string.card_content_description_face_down, readablePos);
        } else {
            contentDescription = res.getString(R.string.card_content_description_face_up, readablePos, mCard.getTitle().toLowerCase(Locale.getDefault()));
        }
        setContentDescription(contentDescription);
    }
}
