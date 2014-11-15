package com.nilhcem.inaccessible.memory.core;

import com.nilhcem.inaccessible.memory.RobolectricGradleTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class CardTest {

    private com.nilhcem.inaccessible.memory.core.Card card = new com.nilhcem.inaccessible.memory.core.Card("title");

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
        com.nilhcem.inaccessible.memory.core.Card card1 = new com.nilhcem.inaccessible.memory.core.Card("same_name");
        card1.flip();
        com.nilhcem.inaccessible.memory.core.Card card2 = new com.nilhcem.inaccessible.memory.core.Card("same_name");

        assertThat(card1).isNotSameAs(card2);
        assertThat(card1).isEqualTo(card2);
    }

    @Test
    public void should_return_false_when_checking_different_cards_names_equality() {
        com.nilhcem.inaccessible.memory.core.Card card1 = new com.nilhcem.inaccessible.memory.core.Card("name_1");
        com.nilhcem.inaccessible.memory.core.Card card2 = new com.nilhcem.inaccessible.memory.core.Card("name_2");
        assertThat(card1).isNotEqualTo(card2);
    }
}
