package com.nilhcem.inaccessible.memory;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class GameTest {

    private Game game = new Game("Fox");

    @Test
    public void should_initialize_a_game_with_two_similar_cards_for_each_entry() {
        assertThat(game.getNbCards()).isEqualTo(2);
    }

    @Test
    public void should_flip_a_card_from_the_game() {
        game.flipCard(0);
        assertThat(game.cards[0].isFaceDown()).isFalse();
        assertThat(game.cards[1].isFaceDown()).isTrue();
    }

    @Test
    public void should_be_null_when_getting_previously_flipped_card_when_never_having_flipped_a_card() {
        assertThat(game.previouslyFlippedCard).isNull();
    }

    @Test
    public void should_save_previously_flipped_card() {
        game.flipCard(1);
        assertThat(game.previouslyFlippedCard).isSameAs(game.cards[1]);
    }

    @Test
    public void should_reset_previously_flipped_card_after_two_cards_flipped() {
        game.flipCard(0);
        game.flipCard(1);
        assertThat(game.previouslyFlippedCard).isNull();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throw_exception_when_flipping_an_already_face_up_card() {
        // Given
        Card card = mock(Card.class);
        when(card.isFaceDown()).thenReturn(false);
        game.cards[0] = card;

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
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);
        when(card1.isFaceDown()).thenReturn(false);
        when(card2.isFaceDown()).thenReturn(false);
        game.cards[0] = card1;
        game.cards[1] = card2;

        // When
        assertThat(game.isOver()).isTrue();
    }

    @Test
    public void should_keep_cards_face_up_when_pair_is_found() {
        // Given
        game = new Game("fox", "fennec");
        Card card1 = new Card("fox");
        Card card2 = new Card("fennec");
        Card card3 = new Card("fox");
        Card card4 = new Card("fennec");
        game.cards[0] = card1;
        game.cards[1] = card2;
        game.cards[2] = card3;
        game.cards[3] = card4;

        // When
        game.flipCard(0);
        game.flipCard(2);

        // Then
        assertThat(game.cards[0].isFaceDown()).isFalse();
        assertThat(game.cards[1].isFaceDown()).isTrue();
        assertThat(game.cards[2].isFaceDown()).isFalse();
        assertThat(game.cards[3].isFaceDown()).isTrue();
    }

    @Test
    public void should_set_back_cards_to_face_down_when_pair_is_invalid() {
        // Given
        game = new Game("fox", "cat");
        Card card1 = new Card("fox");
        Card card2 = new Card("cat");
        Card card3 = new Card("fox");
        Card card4 = new Card("cat");
        game.cards[0] = card1;
        game.cards[1] = card2;
        game.cards[2] = card3;
        game.cards[3] = card4;

        // When
        game.flipCard(0);
        game.flipCard(1);

        // Then
        assertThat(game.cards[0].isFaceDown()).isTrue();
        assertThat(game.cards[1].isFaceDown()).isTrue();
        assertThat(game.cards[2].isFaceDown()).isTrue();
        assertThat(game.cards[3].isFaceDown()).isTrue();
    }
}
