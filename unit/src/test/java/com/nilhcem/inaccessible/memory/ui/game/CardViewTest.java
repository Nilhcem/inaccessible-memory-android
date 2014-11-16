package com.nilhcem.inaccessible.memory.ui.game;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.RobolectricGradleTestRunner;
import com.nilhcem.inaccessible.memory.core.Card;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class CardViewTest {

    @Mock Card card;
    private CardView view;
    private CardView.OnCardFlippedListener listener = new CardView.OnCardFlippedListener() {
        @Override
        public void onCardFlipped(int position) {
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(card.isFaceDown()).thenReturn(true);
        view = new CardView(Robolectric.application, card, 0, listener);
    }

    @Test
    public void should_have_face_off_content_description_when_created() {
        Assertions.assertThat(view.getContentDescription()).isEqualTo(view.getContext().getString(R.string.card_content_description_face_down, 1));
    }
}
