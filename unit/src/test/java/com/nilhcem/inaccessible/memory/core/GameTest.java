package com.nilhcem.inaccessible.memory.core;

import com.nilhcem.inaccessible.memory.RobolectricGradleTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.INVALID_PAIR;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.PAIR_FOUND;
import static com.nilhcem.inaccessible.memory.core.Game.FlippedStatus.WAITING_FOR_SECOND_CARD_TO_BE_FLIPPED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class GameTest {

    private com.nilhcem.inaccessible.memory.core.Game game = new com.nilhcem.inaccessible.memory.core.Game("Fox");

    @Test
    public void should_initialize_a_game_with_two_similar_cards_for_each_entry() {
        assertThat(game.getNbCards()).isEqualTo(2);
    }

    @Test
    public void should_flip_a_card_from_the_game() {
        FlippedStatus flippedStatus = game.flipCard(0);
        assertThat(flippedStatus).isEqualTo(WAITING_FOR_SECOND_CARD_TO_BE_FLIPPED);
        assertThat(game.mCards[0].isFaceDown()).isFalse();
        assertThat(game.mCards[1].isFaceDown()).isTrue();
    }

    @Test
    public void should_be_null_when_getting_previously_flipped_card_when_never_having_flipped_a_card() {
        assertThat(game.mPreviouslyFlippedCard).isNull();
    }

    @Test
    public void should_save_previously_flipped_card() {
        game.flipCard(1);
        assertThat(game.mPreviouslyFlippedCard).isSameAs(game.mCards[1]);
    }

    @Test
    public void should_reset_previously_flipped_card_after_two_cards_flipped() {
        game.flipCard(0);
        game.flipCard(1);
        assertThat(game.mPreviouslyFlippedCard).isNull();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throw_exception_when_flipping_an_already_face_up_card() {
        // Given
        com.nilhcem.inaccessible.memory.core.Card card = mock(com.nilhcem.inaccessible.memory.core.Card.class);
        when(card.isFaceDown()).thenReturn(false);
        game.mCards[0] = card;

        // When
        game.flipCard(0);
    }

    @Test
    public void should_return_false_when_some_cards_havent_still_be_found() {
        assertThat(game.isOver()).isFalse();
    }

    @Test
    public void should_return_true_when_all_cards_have_been_found() {
        // Given
        com.nilhcem.inaccessible.memory.core.Card card1 = mock(com.nilhcem.inaccessible.memory.core.Card.class);
        com.nilhcem.inaccessible.memory.core.Card card2 = mock(com.nilhcem.inaccessible.memory.core.Card.class);
        when(card1.isFaceDown()).thenReturn(false);
        when(card2.isFaceDown()).thenReturn(false);
        game.mCards[0] = card1;
        game.mCards[1] = card2;

        // When
        assertThat(game.isOver()).isTrue();
    }

    @Test
    public void should_keep_cards_face_up_when_pair_is_found() {
        // Given
        game = new com.nilhcem.inaccessible.memory.core.Game("fox", "fennec");
        com.nilhcem.inaccessible.memory.core.Card card1 = new com.nilhcem.inaccessible.memory.core.Card("fox");
        com.nilhcem.inaccessible.memory.core.Card card2 = new com.nilhcem.inaccessible.memory.core.Card("fennec");
        com.nilhcem.inaccessible.memory.core.Card card3 = new com.nilhcem.inaccessible.memory.core.Card("fox");
        com.nilhcem.inaccessible.memory.core.Card card4 = new com.nilhcem.inaccessible.memory.core.Card("fennec");
        game.mCards[0] = card1;
        game.mCards[1] = card2;
        game.mCards[2] = card3;
        game.mCards[3] = card4;

        // When
        game.flipCard(0);
        FlippedStatus flippedStatus = game.flipCard(2);

        // Then
        assertThat(flippedStatus).isEqualTo(PAIR_FOUND);
        assertThat(game.mCards[0].isFaceDown()).isFalse();
        assertThat(game.mCards[1].isFaceDown()).isTrue();
        assertThat(game.mCards[2].isFaceDown()).isFalse();
        assertThat(game.mCards[3].isFaceDown()).isTrue();
    }

    @Test
    public void should_set_back_cards_to_face_down_when_pair_is_invalid() {
        // Given
        game = new Game("fox", "cat");
        com.nilhcem.inaccessible.memory.core.Card card1 = new com.nilhcem.inaccessible.memory.core.Card("fox");
        com.nilhcem.inaccessible.memory.core.Card card2 = new com.nilhcem.inaccessible.memory.core.Card("cat");
        com.nilhcem.inaccessible.memory.core.Card card3 = new com.nilhcem.inaccessible.memory.core.Card("fox");
        com.nilhcem.inaccessible.memory.core.Card card4 = new com.nilhcem.inaccessible.memory.core.Card("cat");
        game.mCards[0] = card1;
        game.mCards[1] = card2;
        game.mCards[2] = card3;
        game.mCards[3] = card4;

        // When
        game.flipCard(0);
        FlippedStatus flippedStatus = game.flipCard(1);

        // Then
        assertThat(flippedStatus).isEqualTo(INVALID_PAIR);
        assertThat(game.mCards[0].isFaceDown()).isTrue();
        assertThat(game.mCards[1].isFaceDown()).isTrue();
        assertThat(game.mCards[2].isFaceDown()).isTrue();
        assertThat(game.mCards[3].isFaceDown()).isTrue();
    }
}
