package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Deck {
    private final int deckIndex;
    private final ArrayList<Card> cards;

    private final int playerIndex;
    private ArrayList<Card> shuffledCards; // cartile cand le amestecam

    public Deck(final int deckIndex, final int playerIndex, final ArrayList<Card> cards) {
        this.deckIndex = deckIndex;
        this.cards = cards;
        this.shuffledCards = null;
        this.playerIndex = playerIndex;
    }
    void shuffle(final int shuffleSeed) {
        // amestecam pachetul de carti
        this.shuffledCards = new ArrayList<>();
        shuffledCards.addAll(cards);
        Collections.shuffle(shuffledCards, new Random(shuffleSeed));
    }

    /**
     *
     * @return
     */
    public Card drawCard() {
        if (shuffledCards.size() > 0) {
            Card card = shuffledCards.get(0).copy();
            shuffledCards.remove(0);
            return card;
        }
        return null;
    }

    /**
     *
     * @param objectMapper
     * @return
     */
    public ArrayNode deckTransformToArrayNode(final ObjectMapper objectMapper) {
        // transformam pachetul intr-un array
        ArrayNode cardsInside = objectMapper.createArrayNode();
        for (Card shuffledCard : shuffledCards) {
            cardsInside.add(shuffledCard.cardTransformToAnObjectNode(objectMapper));
        }
        return cardsInside;
    }
}
