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
    private ArrayList<Card> shuffledCards; // cartile cand le amestec !!!

    public Deck(int deckIndex,int playerIndex,  ArrayList<Card> cards) {
        this.deckIndex = deckIndex;
        this.cards = cards;
        this.shuffledCards = null;
        this.playerIndex = playerIndex;
    }
    void shuffle(int shuffleSeed) {
        this.shuffledCards = new ArrayList<>();
        shuffledCards.addAll(cards);
        Collections.shuffle(shuffledCards, new Random(shuffleSeed));
    }

    public Card drawCard() {
        if (shuffledCards.size() > 0) {
            Card card = shuffledCards.get(0);
            shuffledCards.remove(0);
            return card;
        }
        return null;
    }

    public ArrayNode deckTransformToArrayNode(ObjectMapper objectMapper) {
        ArrayNode cardsInside = objectMapper.createArrayNode();
        for (Card shuffledCard : shuffledCards) {
            cardsInside.add(shuffledCard.cardTransformToAnObjectNode(objectMapper));
        }
        return cardsInside;
    }
}
