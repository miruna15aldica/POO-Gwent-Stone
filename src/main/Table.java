package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Table {
    public static Table instance = null;
    protected Card[][] table;
    public Table() {
        this.table = new Card[4][5];

    }

    public Card[][] getTable() {
        return table;
    }
    public void placeCard(int row, Card card) {
        if(card.cardType == 2) {
            System.out.println("Cannot place environment card on table");
            return;
        }
        if(card.cardType == 1) {
            for (int i = 0; i < table[row].length; ++i) {
                if ((Minion) table[row][i] == null) {
                    putCardOnRow((Minion) card, row);
                    return;
                }
            }
            System.out.println("Cannot place card on table since row is full.");
        }

    }
    public int identifyTheRow(Card card) {
        for(int row = 0; row < 4; row++) {
            for(int i = 0; i < table[row].length; i++) {
                if( ((Minion) card).equals((Minion) table[row][i]))
                    return row;
            }
        }
        return -1;
    }


    public void putCardOnRow(Minion minion, int row) {
        for(int i = 0; i < table[row].length; ++i) {
            if( (Minion) table[row][i] == null) {
                minion = (Minion) table[row][i];
                break;
            }
        }
    }

    public ArrayNode getFrozenCards(ObjectMapper object) {
        ArrayNode frozenCards = object.createArrayNode();
        for(Card[] row : table )
            for(Card card : row ) {
                if(card != null && ((Minion)card).frozen)
                    frozenCards.add(card.cardTransformToAnObjectNode(object));
            }
        return frozenCards;
    }



    public Card getCardAtPosition(int x, int y) {
        if(table[x][y] != null)
            return table[x][y];
        return null;
    }

    public int findCardRow(Card card) {
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table[i].length; j++ )
            {
                if(card == table[i][j])
                    return i;
            }
        }
        return -1;
    }
    public ArrayNode getCards(ObjectMapper object) {
        // TODO - de mutat in table si de apelat de acolo!!
        // e proprietatea lui TABLE!

        ArrayNode frozenCards = object.createArrayNode();
        for(Card[] row : table )
            for(Card card : row ) {
                if(card != null)
                    frozenCards.add(card.cardTransformToAnObjectNode(object));
            }
        return frozenCards;
    }




}
