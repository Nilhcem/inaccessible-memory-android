package com.nilhcem.inaccessible.memory.core;

import android.os.Parcel;

import com.nilhcem.inaccessible.memory.RobolectricGradleTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class CardTest {

    private Card card = new Card("title");

    @Test
    public void should_be_face_down_when_created() {
        assertThat(card.isFaceDown()).isTrue();
    }

    @Test
    public void should_be_face_up_when_flipped_while_face_down() {
        card.flip();
        assertThat(card.isFaceDown()).isFalse();
    }

    @Test
    public void should_be_face_down_when_flipped_while_face_up() {
        card.flip();
        card.flip();
        assertThat(card.isFaceDown()).isTrue();
    }

    @Test
    public void should_return_true_when_checking_two_cards_with_similar_names_equality() {
        Card card1 = new Card("same_name");
        card1.flip();
        Card card2 = new Card("same_name");

        assertThat(card1).isNotSameAs(card2);
        assertThat(card1).isEqualTo(card2);
    }

    @Test
    public void should_return_false_when_checking_different_cards_names_equality() {
        Card card1 = new Card("name_1");
        Card card2 = new Card("name_2");
        assertThat(card1).isNotEqualTo(card2);
    }


    @Test
    public void should_restore_from_parcelables() {
        // Given
        Card card = new Card("TITLE");
        card.flip();

        // When
        Parcel parcel = Parcel.obtain();
        card.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Then
        Card fromParcel = Card.CREATOR.createFromParcel(parcel);
        assertThat(fromParcel.getTitle()).isEqualTo("TITLE");
        assertThat(fromParcel.isFaceDown()).isFalse();
    }
}
