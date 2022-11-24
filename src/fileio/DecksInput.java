package fileio;

import main.Card;
import main.Deck;

import java.util.ArrayList;

public final class DecksInput {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInput>> decks;

    public DecksInput() {
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<CardInput>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<CardInput>> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }
    public ArrayList<Deck> getDecks(int playerIdx) {
        ArrayList<Deck> deckPlayer = new ArrayList<>();
        int deck = 0;
        while(deck < decks.size())
         {
            ArrayList<Card> cards = new ArrayList<>();
            for(CardInput card : decks.get(deck))
                cards.add(card.toCard());

            deckPlayer.add(new Deck(deck, playerIdx, cards));
             deck++;

        }
        return deckPlayer;
    }

}
