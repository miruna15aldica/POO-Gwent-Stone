package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Game {
    protected Player[] players;
    protected ArrayList<Deck> decks;
    protected int mana;
    protected int currentTurn;
    protected int firstPlayer;
    protected int currentRound;

    protected Table table;
    protected Boolean end;

    // protected Row row;

    public Game( Player[] players, int deckFirstIdx, int deckSecondIdx, Hero firstHeroCard, Hero secondHeroCard, int shuffleSeed, int firstPlayer) {
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Player currentPlayer() {
        return players[currentTurn];
    }

    public Player nextPlayer() {
        if (currentTurn == 0) {
            return players[1];
        } else {
            return players[0];
        }
    }

    public void endCurrentTurn() {
        // functie care sfarseste jocul;
        for(Card[] row : table.getTable() )
            for(Card card : row ) {
                if(card != null)
                    ((Minion)card).unfreeze();
            }
        currentTurn = (currentTurn == 0) ? 1 : 0;
        if (currentTurn == firstPlayer)
            this.beginNewRound();
    }

    public void beginNewRound() {
        currentRound++;
        final int addMana = (currentRound > 10) ? 10 : currentRound;
        for (int i = 0; i < players.length; ++i) {
            players[i].setMana(players[i].getMana() + addMana);
            players[i].drawCardFromDeck();
            players[i].getHero().setSpecial(false);
        }
        for (Card[] row : table.getTable()) {
            for (Card card : row) {
                if (card != null) {
                    ((Minion)card).setSpecial(false);
                    ((Minion)card).setAttacked(false);
                }
            }
        }
    }

    public boolean isPlayersRow(int row, Player player) {
        // TODO - modifici! - DONE
        return (player == players[0] && row <= 1) || (player == players[1] && row >= 2 );
    }
    public Boolean isPlayersRow(int row, int playerIdx) {
        return isPlayersRow(row, players[playerIdx]);
    }

    public Boolean placeCard(int row, Minion card) {
        // Aici vei avea buguri - succes!
        // TODO - Flow in ratiune
        // - De ce e int? Pentru ca ai doar 0 si 1, deci de ce e int?
        // - Ar fi logic sa iti returneze adevarat in momentul in care tu ai reusit sa faci actiunea
        //   <=> aka ai pus cartea jos <=> aka invers returnurile
        for(int i = 0; i < table.getTable()[row].length; i++) {
            if(table.getTable()[row][i] == null) {
                table.getTable()[row][i] = card;
                return true;
                // daca am reusit sa pozitionam cartea pe randul dorit, returnam true
            }
        }
        return false;
        // daca NU am reusit sa pozitionam cartea pe randul dorit returnam false
    }
    public Boolean hasTankTheEnemy() {
        for(int i = 0; i < table.getTable().length; ++i) {
            if(isPlayersRow(i, nextPlayer())) {
                for(Card card : table.getTable()[i]) {
                    if(((Minion)card).isTank()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean rowIsFull(int mirror_row) {
        for(int i = 0; i < table.getTable()[mirror_row].length; i++) {
            if(table.getTable()[mirror_row][i] != null) {
                return false;
            }
        }
        return true;
    }

public Boolean gameOver() {
    if (end == true) {
        // jocul se termina cand health-ul unei carti hero a unui jucator este 0
        if(players[0].getHero().getHealth() <= 0 || players[1].getHero().getHealth() <= 0) {
            if(players[0].getHero().getHealth() > 0) {
                players[0].raiseNumberVictory();
            }
            if(players[1].getHero().getHealth() > 0) {
                players[1].raiseNumberVictory();
            }
            return true;
        }

    }
    return false;
}

    public void rebuildTable() {
        int a = 0;
        ArrayList<Minion> cards = null;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                if(table.getTable()[i][j] != null && ((Minion) table.getTable()[i][j]).getHealth() <= 0) {
                    table.getTable()[i][j] = null;
                }
                if(table.getTable()[i][j] != null && ((Minion) table.getTable()[i][j]).getHealth() > 0) {
                    cards.set(a, ((Minion) table.getTable()[i][j]));
                    a++;
                    // ideea principala e ca am retinut intr-o alta lista pe moment ceea ce am eliminat
                }
                // in momentul in care o carte de genul se elimina, cartile se
                // shifteaza si ele, adica se duc mai in spate
                // TODO
            }
            for(int q = 0; q < a; q++) {
                table.getTable()[q/ 5][q % 5] = cards.get(q);
            }
            for(int q = a; q < 20; q++) {
                table.getTable()[q / 5][q % 5] = null;
            }

        }
    }

}


