package main;

import java.util.ArrayList;

public class Game {
    protected Player[] players;
    protected int mana;
    protected int currentTurn;
    protected final int firstPlayer;
    protected int currentRound;

    protected Table table;
    protected Boolean end;

    // protected Row row;

    public Game(final Player[] players, final int deckFirstIdx, final int deckSecondIdx,
                 final Hero firstHeroCard, final Hero secondHeroCard, final int shuffleSeed,
                final int firstPlayer) {
        this.players = players;
        this.players[0].remake(deckFirstIdx, shuffleSeed, firstHeroCard);
        this.players[1].remake(deckSecondIdx, shuffleSeed, secondHeroCard);
        this.firstPlayer = firstPlayer;
        this.currentTurn = firstPlayer;
        this.table = new Table();
        this.mana = 1;
        this.currentRound = 0;
        this.beginNewRound();
        this.end = false;
    }

    public final Table getTable() {
        return table;
    }

    public final void setTable(final Table table) {
        this.table = table;
    }

    /**
     *
     * @return
     */
    public Player currentPlayer() {
        return players[currentTurn];
    }

    /**
     *
     * @return
     */
    public Player nextPlayer() {
        if (currentTurn == 0) {
            return players[1];
        } else {
            return players[0];
        }
    }

    /**
     *
     */
    public void endCurrentTurn() {
        // functie care sfarseste jocul nostru
        for (int i = 0; i < table.getTable().length; ++i) {
            if (isPlayersRow(i, currentTurn)) {
                for (Card card : table.getTable()[i]) {
                    if (card != null) {
                        ((Minion) card).unfreeze();
                    }
                }
            }
        }
        currentTurn = (currentTurn == 0) ? 1 : 0;
        if (currentTurn == firstPlayer) {
            this.beginNewRound();
        }
    }

    /**
     *
     */
    public final void beginNewRound() {
        // functie pentru setarea proprietatilor cartilor
        // la inceput de runda
        currentRound++;
        final int maxmanaadd = 10;
        final int addMana = (currentRound > maxmanaadd) ? maxmanaadd : currentRound;
        for (int i = 0; i < players.length; ++i) {
            players[i].setMana(players[i].getMana() + addMana);
            players[i].drawCardFromDeck();
            players[i].getHero().setSpecial(false);
        }
        for (Card[] row : table.getTable()) {
            for (Card card : row) {
                if (card != null) {
                    ((Minion) card).setSpecial(false);
                    ((Minion) card).setAttacked(false);
                }
            }
        }
    }

    /**
     *
     * @param row
     * @param player
     * @return
     */
    public boolean isPlayersRow(final int row, final Player player) {
        return (player == players[0] && row <= 1) || (player == players[1] && row >= 2);
    }

    /**
     *
     * @param row
     * @param playerIdx
     * @return
     */
    public final Boolean isPlayersRow(final int row, final int playerIdx) {
        return isPlayersRow(row, players[playerIdx]);
    }

    /**
     *
     * @param row
     * @param card
     * @return
     */
    public Boolean placeCard(final int row, final Minion card) {
        // plaseaza cartea de tip minion pe randul
        // dorit daca mai exista spatiul necesar
        for (int i = 0; i < table.getTable()[row].length; i++) {
            if (table.getTable()[row][i] == null) {
                table.getTable()[row][i] = card;
                return true;
                // daca am reusit sa pozitionam cartea pe randul dorit, returnam true
            }
        }
        return false;
        // daca NU am reusit sa pozitionam cartea pe randul dorit returnam false
    }

    /**
     *
     * @return
     */
    public Boolean hasTankTheEnemy() {
        for (int i = 0; i < table.getTable().length; ++i) {
            if (isPlayersRow(i, nextPlayer())) {
                for (Card card : table.getTable()[i]) {
                    if (card != null && ((Minion) card).isTank()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param mirror_row
     * @return
     */
    public Boolean rowIsFull(final int mirrorrow) {
        // verificam daca un rand e plin
        for (int i = 0; i < table.getTable()[mirrorrow].length; i++) {
            if (table.getTable()[mirrorrow][i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public final Boolean gameOver() {
    if (!end) {
        // jocul se termina cand health-ul unei carti hero a unui jucator este 0
        if (players[0].getHero().getHealth() <= 0 || players[1].getHero().getHealth() <= 0) {
            end = true;
            if (players[0].getHero().getHealth() > 0) {
                players[0].raiseNumberVictory();
            }
            if (players[1].getHero().getHealth() > 0) {
                players[1].raiseNumberVictory();
            }
        }
    }
    return end;
}

    /**
     *
     */
    public final void rebuildTable() {
        int a = 0;
        final int magic1 = 4;
        final int magic2 = 5;
        ArrayList<Minion> cards = null;
        for (int i = 0; i < magic1; i++) {
            for (int j = 0; j < magic2; j++) {
                if (table.getTable()[i][j] != null
                       && ((Minion) table.getTable()[i][j]).getHealth() <= 0) {
                    table.getTable()[i][j] = null;
                }
                if (table.getTable()[i][j] != null
                       && ((Minion) table.getTable()[i][j]).getHealth() > 0) {
                    cards.set(a, ((Minion) table.getTable()[i][j]));
                    a++;
                }
            }
            final int maxrow = 5;
            final int maxnumbercards = 20;
            for (int q = 0; q < a; q++) {
                table.getTable()[q / maxrow][q % maxrow] = cards.get(q);
            }
            for (int q = a; q < maxnumbercards; q++) {
                table.getTable()[q / maxrow][q % maxrow] = null;
            }

        }
    }

}


