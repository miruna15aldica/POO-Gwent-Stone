package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Player {
    protected int playerNumber;
    protected int win;
    protected int lost;
    protected ArrayList<Deck> decks;
    protected Deck currentDeck;
    protected ArrayList<Card> currentCards;

    protected int mana;

    Hero hero;

    public Player(final int playerNumber, final ArrayList<Deck> decks) {
        this.playerNumber = playerNumber;
        this.decks = decks;
        this.currentCards = new ArrayList<>();
        this.win = 0;
        this.lost = 0;
    }

    /**
     *
     * @param deckIdx
     * @param seed
     * @param hero
     */
    public void remake(final int deckIdx, final int seed, final Hero hero) {

        this.currentDeck = decks.get(deckIdx);
        this.currentDeck.shuffle(seed);
        this.currentCards = new ArrayList<>();
        this.hero = hero;
        this.mana = 0;
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     *
     * @return
     */
    public Hero getHero() {
        return this.hero;
    }

    /**
     *
     * @return
     */
    public int numberOfWins() {
        return this.win;
    }

    /**
     *
     * @param objectMapper
     * @return
     */
    public ArrayNode currentCardsEnvironment(final ObjectMapper objectMapper) {
        ArrayNode cardsInHand = objectMapper.createArrayNode();
        for (Card card : currentCards) {
            if (card.getCardType() == 2) {
                cardsInHand.add(card.cardTransformToAnObjectNode(objectMapper));
            }
        }
        return cardsInHand;
    }

    /**
     *
     */
    public void raiseNumberVictory() {
        this.win += 1;
    }

    /**
     *
     * @param objectMapper
     * @return
     */
    public ArrayNode getCardsInHand(final ObjectMapper objectMapper) {
        ArrayNode cardsInHand = objectMapper.createArrayNode();
        for (Card card : currentCards) {
            cardsInHand.add(card.cardTransformToAnObjectNode(objectMapper));
        }
        return cardsInHand;
    }

    public final Deck getPlayerDeck() {
        return currentDeck;
    }

    /**
     *
     * @param handIdx
     * @return
     */
    public Card cardOnHand(final int handIdx) {
        return currentCards.get(handIdx);
    }

    /**
     *
     * @param handIdx
     */
    public void dropCardFromHand(final int handIdx) {
        mana -= currentCards.get(handIdx).getMana();
        currentCards.remove(handIdx);
    }

    /**
     *
     */
    public void drawCardFromDeck() {
        Card drawnCard = currentDeck.drawCard();
        if (drawnCard != null) {
            currentCards.add(drawnCard);
        }
    }

    public final Object getPlayerCards() {
        return currentCards;
    }

}
