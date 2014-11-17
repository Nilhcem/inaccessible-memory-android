package com.nilhcem.inaccessible.memory.ui.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

import com.nilhcem.inaccessible.memory.BuildConfig;
import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.core.Card;

import java.util.Locale;

public class CardView extends View implements View.OnClickListener {

    private Card mCard;
    private int mPosition;
    private OnCardFlippedListener mListener;
    private Paint mPaint = new Paint();

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
        if (mCard.isFaceDown()) {
            updateContentDescription(false);
            announceForAccessibility(getContentDescription());
        }
        mListener.onCardFlipped(mPosition);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Simplify manual testing by painting the card's content description
        if (BuildConfig.DEBUG) {
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(20);
            canvas.drawText(getContentDescription().toString().replaceAll(getResources().getString(R.string.card_content_description_prefix), "#"), 10, 25, mPaint);
        }
    }

    public void updateContentDescription() {
        updateContentDescription(mCard.isFaceDown());
    }

    public void updateContentDescription(boolean cardIsFacingDown) {
        String contentDescription;
        int readablePos = mPosition + 1;
        Resources res = getContext().getResources();

        if (cardIsFacingDown) {
            contentDescription = res.getString(R.string.card_content_description_face_down, readablePos);
        } else {
            contentDescription = res.getString(R.string.card_content_description_face_up, readablePos, mCard.getTitle().toLowerCase(Locale.getDefault()));
        }
        setContentDescription(contentDescription);

        if (BuildConfig.DEBUG) {
            invalidate();
        }
    }
}
