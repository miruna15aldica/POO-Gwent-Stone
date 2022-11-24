package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;

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

    public Player(int playerNumber, ArrayList<Deck> decks) {
        this.playerNumber = playerNumber;
        this.decks = decks;
        this.currentCards = new ArrayList<>();
        this.win = 0;
        this.lost = 0;
    }

    public void remake(int deckIdx, int seed, Hero hero) {

        this.currentDeck = decks.get(deckIdx);
        this.currentDeck.shuffle(seed);
        this.currentCards = new ArrayList<>();
        this.hero = hero;
        this.mana = 0;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return this.hero;
    }
    public int numberOfWins() {
        return this.win;
    }

    public ArrayNode currentCardsEnvironment(ObjectMapper objectMapper) { // ??????????
        // Aici trebuie facuta afisare - TODO
        ArrayNode cardsInHand = objectMapper.createArrayNode();
        for (Card card : currentCards)
            if (card.getCardType() == 2)
                cardsInHand.add(card.cardTransformToAnObjectNode(objectMapper));
        return cardsInHand;
    }
    public void raiseNumberVictory() {
        this.win += 1;
    }


    public ArrayNode getCardsInHand(@NonNull ObjectMapper objectMapper) {
        ArrayNode cardsInHand = objectMapper.createArrayNode();
        for (Card card : currentCards)
            cardsInHand.add(card.cardTransformToAnObjectNode(objectMapper));
        return cardsInHand;
    }

    public Deck getPlayerDeck() {
        return currentDeck;
    }

    public Card cardOnHand(int handIdx) {
        return currentCards.get(handIdx);
    }

    public void dropCardFromHand(int handIdx) {
        mana -= currentCards.get(handIdx).getMana();
        currentCards.remove(handIdx);
    }

    public void drawCardFromDeck() {
        Card drawnCard = currentDeck.drawCard();
        if (drawnCard != null) {
            currentCards.add(drawnCard);
        }
    }


    // noi trebuie sa resetam ce am avut inainte, saa nu ne afecteze jocurile
    // trecute


    public Object getPlayerCards() {
        return currentCards;
    }

//    public void cardOnBoard(int handIdx) {
//        Card card = cardOnHand(handIdx);
//        if(card.cardType == 1) {
//            return "Cannot place environment card on table.";
//        } else if()
//    }
}
