package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Table {
    public static Table instance = null;
    protected Card[][] table;
    public Table() {
        final int NUMBER_ROW = 4;
        final int NUMBER_COLUMN = 5;
        this.table = new Card[NUMBER_ROW][NUMBER_COLUMN];

    }

    public final Card[][] getTable() {
        return table;
    }

    /**
     *
     * @param row
     * @param card
     */
    final void placeCard(final int row, final Card card) {
        // plaseaza cartea daca se poate pe randul dorit
        if (card.cardType == 2) {
            System.out.println("Cannot place environment card on table");
            return;
        }
        if (card.cardType == 1) {
            for (int i = 0; i < table[row].length; ++i) {
                if ((Minion) table[row][i] == null) {
                    putCardOnRow((Minion) card, row);
                    return;
                }
            }
            System.out.println("Cannot place card on table since row is full.");
        }

    }

    /**
     *
     * @param card
     * @return
     */
    public final int identifyTheRow(final Card card) {
        // identifica randul pe care se afla cartea
        for (int row = 0; row < 4; row++) {
            for (int i = 0; i < table[row].length; i++) {
                if (((Minion) card).equals((Minion) table[row][i])) {
                    return row;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param minion
     * @param row
     */
    public final void putCardOnRow(Minion minion, final int row) {
        for (int i = 0; i < table[row].length; ++i) {
            if ((Minion) table[row][i] == null) {
                minion = (Minion) table[row][i];
                break;
            }
        }
    }

    /**
     *
     * @param object
     * @return
     */
    public ArrayNode getFrozenCards(final ObjectMapper object) {
        ArrayNode frozenCards = object.createArrayNode();
        for (Card[] row : table) {
            for (Card card : row) {
                if (card != null && ((Minion) card).frozen) {
                    frozenCards.add(card.cardTransformToAnObjectNode(object));
                }
            }
        }
        return frozenCards;
    }


    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Card getCardAtPosition(final int x, final int y) {
        return table[3 - x][y];
    }

    /**
     *
     * @param card
     * @return
     */
    public final int findCardRow(final Card card) {
        // returneaza randul pe care se afla cartea
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (card == table[i][j]) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param object
     * @return
     */
    public ArrayNode getCards(final ObjectMapper object) {
        final int MAGIC_3 = 3;
        ArrayNode allCards = object.createArrayNode();
        for (int i = MAGIC_3; i >= 0; --i) {
            Card[] row = table[i];
            ArrayNode innerArrayNode = object.createArrayNode();
            for (Card card : row) {
                if (card != null) {
                    innerArrayNode.add(card.cardTransformToAnObjectNode(object));
                }
            }
            allCards.add(innerArrayNode);
        }
        return allCards;
    }

    /**
     *
     */
    public final void fillSpaces() {
        for (Card[] row : table) {
            int i = 0;
            for (Card card : row) {
                if (card != null && ((Minion) card).getHealth() > 0) {
                    row[i] = card;
                    i++;
                }
            }
            for (; i < row.length; ++i) {
                row[i] = null;
            }
        }
    }


}
