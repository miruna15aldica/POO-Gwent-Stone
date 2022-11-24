package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;

import java.util.ArrayList;

public class InputLoader {
    ArrayList<GameInput> gameInputs;
    Player[] players = new Player[2];
    int currGame;
    private ObjectMapper objectMapper;
    private ArrayNode outputArray;

    public InputLoader(Input input) {
        this.currGame = 0;
        this.gameInputs = input.getGames();
        objectMapper = new ObjectMapper();
        outputArray = objectMapper.createArrayNode();

        // TODO
        players[0] = new Player(0, input.getPlayerOneDecks().getDeck(0));
        players[1] = new Player(1, input.getPlayerTwoDecks().getDeck(1));
    }

    // pentru 2 carti cu coordonatele randurilor date
    // verifaicam daca sunt in aceeasi echipa
    public Boolean onTheSameTeam(int row1, int row2) {
        return ((row1 <= 1 && row2 <= 1) || (row1 >= 2 && row2 >= 2));
    }
//        // facem o functie de start joc
//        // facem o functie de selectat players
//        // facem o functie care ne ia indexul jocului
//
//    }

    private Boolean debuggingCommand(Game game, ActionsInput actionsInput, ObjectNode objectNode) {
        objectNode.put("command", actionsInput.getCommand());
        if (actionsInput.getCommand().equals("getPlayerOneWins")) {
            objectNode.put("output", players[0].numberOfWins());
        } else if (actionsInput.getCommand().equals("getPlayerTwoWins")) {
            objectNode.put("output", players[1].numberOfWins());
        } else if (actionsInput.getCommand().equals("getTotalGamesPlayed")) {
            objectNode.put("output", this.currGame + 1);
        } else if (actionsInput.getCommand().equals("getFrozenCardsOnTable")) {
            objectNode.set("output", game.getTable().getFrozenCards(objectMapper));
        } else if (actionsInput.getCommand().equals("getEnvironmentCardsInHand")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.put("output", players[actionsInput.getPlayerIdx() - 1].currentCardsEnvironment(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerMana")) {
            objectNode.put("output", players[actionsInput.getPlayerIdx() - 1].getMana());
        } else if (actionsInput.getCommand().equals("getCardAtPosition")) {
            objectNode.put("x", actionsInput.getX());
            objectNode.put("y", actionsInput.getY());
            Minion minion = (Minion) game.getTable().getCardAtPosition(actionsInput.getX(), actionsInput.getY());
            if (minion == null)
                objectNode.put("output", "No card at that position.");
            else
                objectNode.set("output", minion.cardTransformToAnObjectNode(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerHero")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.set("output", players[actionsInput.getPlayerIdx() - 1].getHero().cardTransformToAnObjectNode(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerTurn")) {
            if (game.currentPlayer() == players[0])
                objectNode.put("output", 1);
            if (game.currentPlayer() == players[1])
                objectNode.put("output", 2);
        } else if (actionsInput.getCommand().equals("getCardsOnTable")) {
            objectNode.set("output", game.getTable().getCards(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerDeck")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            // TODO - DONE
            objectNode.set("output", players[actionsInput.getPlayerIdx() - 1].getPlayerDeck().deckTransformToArrayNode(objectMapper));
        } else if (actionsInput.getCommand().equals("getCardsInHand")) {
            objectNode.put("output", actionsInput.getPlayerIdx());
            objectNode.set("output", players[actionsInput.getPlayerIdx() - 1].getCardsInHand(objectMapper));
            // TODO -

        } else {
            return false;
        }
        return true;
    }

    public void run() {
        for (int i = 0; i < gameInputs.size(); i++) {
            this.currGame = this.currGame + 1;
            StartGameInput startGameInput = gameInputs.get(i).getStartGame();
            Game newGame = startGameInput.getGame(players);
            // get game e o functie care trebuie facuta mai tarziu :)
            for (ActionsInput actionsInput : gameInputs.get(i).getActions()) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                if (!this.debuggingCommand(newGame, actionsInput, objectNode)) {
                    if (!this.actionCommand(newGame, actionsInput, objectNode)) {
                        System.out.println("Nu a intrat pe niciun fel de comanda: " + actionsInput.getCommand());
                    }
                }
                outputArray.add(objectNode);
            }
        }
    }

    public ArrayNode output() {
        return outputArray;
    }

    private Boolean actionCommand(Game game, ActionsInput actionsInput, ObjectNode objectNode) {
        objectNode.put("command", actionsInput.getCommand());
        if (actionsInput.getCommand().equals("useEnvironmentCard")) {
            objectNode.put("handIdx", actionsInput.getHandIdx());
            objectNode.put("affectedRow", actionsInput.getAffectedRow());
            // functie pentru jucatorul curent si cartea din mana sa
            int a = actionsInput.getAffectedRow();
            Card card = game.currentPlayer().cardOnHand(actionsInput.getHandIdx());
            if (card.cardType != 2) {
                objectNode.put("error", "Chosen card is not of type environment.");
                output().add(objectNode);
            } else if (card.getMana() < players[actionsInput.getHandIdx()].getMana()) {
                objectNode.put("error", "Not enough mana to use environment card.");
            } else if (card instanceof Heart) {
                int x = game.getTable().findCardRow(card);
                int mirror_row = 3 - x;
                if (game.rowIsFull(mirror_row)) {
                    objectNode.put("error", "Cannot steal enemy card since the player's row is full.");
                }
            } else if (game.currentPlayer() == players[0]) {
                objectNode.put("error", "Chosen row does not belong to the enemy.");
                if (a == 2 || a == 3) {
                    objectNode.put("error", "Chosen row does not belong to the enemy.");
                }

            } else if (game.currentPlayer() == players[1]) {
                objectNode.put("error", "Chosen row does not belong to the enemy.");
                if (a == 0 || a == 1) {
                    objectNode.put("error", "Chosen row does not belong to the enemy.");
                }

            }
            game.currentPlayer().setMana(game.currentPlayer().getMana() - game.mana);
        } else if (actionsInput.getCommand().equals("useHeroAbility")) {
            objectNode.put("affectdRow", actionsInput.getAffectedRow());
            int row = actionsInput.getAffectedRow();
            Card card = game.currentPlayer().cardOnHand(actionsInput.getHandIdx());
            Player player = players[actionsInput.getPlayerIdx()];
            if (card.mana < player.mana) {
                objectNode.put("error", "Not enough mana to use environment card.");
            } else if (player.getHero().getSpecial()) {
                objectNode.put("error", "Hero has already attacked this turn.");
            } else if (player.getHero() instanceof Lord) {
                if (onTheSameTeam(actionsInput.getPlayerIdx() + 1, actionsInput.getAffectedRow())) {
                    objectNode.put("error", "Selected row does not belong to the enemy.");
                }
            } else if (player.getHero() instanceof General) {
                if (!onTheSameTeam(actionsInput.getPlayerIdx() + 1, actionsInput.getAffectedRow())) {
                    objectNode.put("error", "Selected row does not belong to the current player.");
                }
            } else if (player.getHero() instanceof King) {
                if (!onTheSameTeam(actionsInput.getPlayerIdx() + 1, actionsInput.getAffectedRow())) {
                    objectNode.put("error", "Selected row does not belong to the current player.");
                }
            } else {
                ((Hero) card).specialAbility(game.getTable(), row);
            }
        } else if (actionsInput.getCommand().equals("useAttackHero")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            int attackedX = actionsInput.getCardAttacked().getX();
            int attackedY = actionsInput.getCardAttacked().getY();
            Minion attacked = (Minion) game.getTable().getCardAtPosition(attackedX, attackedY);
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            // gasim cartea care e la pozitia (x, y)
            Hero herocard = game.nextPlayer().getHero();
            // ne trebuie o functie care gaseste urmatorul player, dar care identifica si
            // hero ul fiecarui jucator
            if (attacked.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
            } else if (attacker.getSpecial() == true) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacked.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (game.hasTankTheEnemy()) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            } else if (game.gameOver()) {
                attacker.cardUsesAttack(attacked, game);
                if (players[0].getHero().health > 0) {
                    objectNode.put("gameEnded", "Player one killed the enemy hero.");
                } else {
                    objectNode.put("gameEnded", "Player two killed the enemy hero.");
                }
            }


            // aici e greut, ne gandim si altadata

        }
        else if (actionsInput.getCommand().equals("cardUsesAbility")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            int attackedX = actionsInput.getCardAttacked().getX();
            int attackedY = actionsInput.getCardAttacked().getY();
            Minion attacked = (Minion) game.getTable().getCardAtPosition(attackedX, attackedY);
            if (!attacker.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
            } else if (attacker.getSpecial() == true) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker instanceof Disciple) {
                if (!onTheSameTeam(attackerX, attackedX))
                    objectNode.put("error", "Attacked card does not belong to the current player.");
                // facem o functie care verifica in functie de rand pe unde sunt
            } else if (attacker instanceof Ripper) {
                if (onTheSameTeam(attackerX, attackedX))
                    objectNode.put("error", "Attacked card does not belong to the enemy.");
//                // facem o functie care verifica daca exista o carte tank
//                // facem o functie care verifica in functie de rand pe unde sunt
            } else if (attacker instanceof Miraj) {
                if (onTheSameTeam(attackerX, attackedX))
                    objectNode.put("error", "Attacked card does not belong to the enemy.");
                // facem o functie care verifica in functie de rand pe unde sunt
                // facem o functie care verifica daca exista o carte tank
            } else if (attacker instanceof Cursed) {
                if (onTheSameTeam(attackerX, attackedX))
                    objectNode.put("error", "Attacked card does not belong to the enemy.");
                // facem o functie care verifica in functie de rand pe unde sunt
            } else if (game.hasTankTheEnemy()) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            } else {
                attacker.specialAbility(attacked);
            }
            // la fel, verifcam pentru celelalte clase si randuri

        }
        else if (actionsInput.getCommand().equals("cardUsesAttack")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            int attackedX = actionsInput.getCardAttacked().getX();
            int attackedY = actionsInput.getCardAttacked().getY();
            Minion attacked = (Minion) game.getTable().getCardAtPosition(attackedX, attackedY);
            if (onTheSameTeam(attackerX, attackerY)) {
                objectNode.put("error", "Attacked card does not belong to the enemy.");
            } else if (attacker.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
//            } else if(attackedX == 0 || attackerX == 1) {
//              // face o fucntie care gaseste cartile de tip tank ale unui jucator
//                if(players[0].getTank()) {
//                    if(!(game.players[0].getTank()).equals(attacked))
//                        objectNode.put("error", "Attacked card is not of type 'Tank'.");
//                    return  true;
//                }
            } else if (game.hasTankTheEnemy()) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            } else {
                attacker.cardUsesAttack(attacked, game);
            }
        }
        else if (actionsInput.getCommand().equals("placeCard")) {
            //game.currentPlayer().cardOnBoard(actionsInput.getHandIdx());

            // functie pentru jucatorul curent, apoi functie pentru cartea din
            // mana dupa indexul ei
            Card card = game.currentPlayer().cardOnHand(actionsInput.getHandIdx());
            if (card.cardType == 2) {
                objectNode.put("error", "Cannot place environment card on table.");
            } else if (card.getMana() > players[actionsInput.getPlayerIdx()].mana) {
                objectNode.put("error", "Not enough mana to place card on table.");
            }
            else if (((Minion) (card)).isOnLastRow) {
                // TODO - probabil nu merge!
                if (game.isPlayersRow(actionsInput.getPlayerIdx(), 0)) {

                    Boolean a = game.placeCard(0, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                    }
                }
                if (game.isPlayersRow(actionsInput.getPlayerIdx(), 3)) {

                    Boolean a = game.placeCard(3, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                    }
                }


            } else if (((Minion) (card)).isOnFirstRow) {
                // TODO - probabil nu merge!
                if (game.isPlayersRow(actionsInput.getPlayerIdx(), 0)) {

                    Boolean a = game.placeCard(1, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                    }
                }
                if (game.isPlayersRow(actionsInput.getPlayerIdx(), 3)) {

                    Boolean a = game.placeCard(2, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                    }
                }
            } else {
                objectNode.put("PlayerIdx", actionsInput.getPlayerIdx());
                game.rebuildTable();
            }

        } else
        if(actionsInput.getCommand().equals("endPlayerTurn"))
            game.endCurrentTurn();
        else {
            return true;
        }

        return false;
    }
}







