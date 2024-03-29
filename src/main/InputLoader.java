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
    private ArrayList<GameInput> gameInputs;
    private Player[] players = new Player[2];
    private int currGame;
    private ObjectMapper objectMapper;
    private ArrayNode outputArray;

    public InputLoader(final Input input) {
        this.currGame = 0;
        this.gameInputs = input.getGames();
        objectMapper = new ObjectMapper();
        outputArray = objectMapper.createArrayNode();
        players[0] = new Player(0, input.getPlayerOneDecks().getDecks(0));
        players[1] = new Player(1, input.getPlayerTwoDecks().getDecks(1));
    }

    /**
     *
     * @param row1
     * @param row2
     * @return
     */
    public Boolean onTheSameTeam(final int row1, final int row2) {
        // verificam daca 2 randuri apartin aceluiasi jucator
        return ((row1 <= 1 && row2 <= 1) || (row1 >= 2 && row2 >= 2));
    }

    private Boolean debuggingCommand(final Game game, final ActionsInput actionsInput,
                                     final ObjectNode objectNode) {
        objectNode.put("command", actionsInput.getCommand());
        if (actionsInput.getCommand().equals("getPlayerOneWins")) {
            objectNode.put("output", players[0].numberOfWins());
        } else if (actionsInput.getCommand().equals("getPlayerTwoWins")) {
            objectNode.put("output", players[1].numberOfWins());
        } else if (actionsInput.getCommand().equals("getTotalGamesPlayed")) {
            objectNode.put("output", this.currGame);
        } else if (actionsInput.getCommand().equals("getFrozenCardsOnTable")) {
            objectNode.set("output", game.getTable().getFrozenCards(objectMapper));
        } else if (actionsInput.getCommand().equals("getEnvironmentCardsInHand")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.set("output",
                    players[actionsInput.getPlayerIdx() - 1].currentCardsEnvironment(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerMana")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.put("output", players[actionsInput.getPlayerIdx() - 1].getMana());
        } else if (actionsInput.getCommand().equals("getCardAtPosition")) {
            objectNode.put("x", actionsInput.getX());
            objectNode.put("y", actionsInput.getY());
            Minion minion =
                    (Minion) game.getTable().getCardAtPosition(actionsInput.getX(), actionsInput.getY());
            if (minion == null) {
                objectNode.put("output", "No card available at that position.");
            } else {
                objectNode.set("output", minion.cardTransformToAnObjectNode(objectMapper));
            }
        } else if (actionsInput.getCommand().equals("getPlayerHero")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.set("output",
                    players[actionsInput.getPlayerIdx() - 1].getHero().cardTransformToAnObjectNode(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerTurn")) {
            if (game.currentPlayer() == players[0]) {
                objectNode.put("output", 1);
            }
            if (game.currentPlayer() == players[1]) {
                objectNode.put("output", 2);
            }
        } else if (actionsInput.getCommand().equals("getCardsOnTable")) {
            objectNode.set("output", game.getTable().getCards(objectMapper));
        } else if (actionsInput.getCommand().equals("getPlayerDeck")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.set("output",
                    players[actionsInput.getPlayerIdx() - 1].getPlayerDeck().deckTransformToArrayNode(objectMapper));
        } else if (actionsInput.getCommand().equals("getCardsInHand")) {
            objectNode.put("playerIdx", actionsInput.getPlayerIdx());
            objectNode.set("output",
                    players[actionsInput.getPlayerIdx() - 1].getCardsInHand(objectMapper));

        } else {
            return false;
        }
        return true;
    }

    /**
     *
     */
    public final void run() {
        for (int i = 0; i < gameInputs.size(); i++) {
            this.currGame = this.currGame + 1;
            StartGameInput startGameInput = gameInputs.get(i).getStartGame();
            Game newGame = startGameInput.getGame(players);
            for (ActionsInput actionsInput : gameInputs.get(i).getActions()) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                if (!this.debuggingCommand(newGame, actionsInput, objectNode)) {
                    if (!this.actionCommand(newGame, actionsInput, objectNode)) {
                        System.out.println("Nu a intrat pe niciun fel de comanda: "
                                + actionsInput.getCommand());
                    }
                }
                if (objectNode.has("output")
                        || objectNode.has("error") || objectNode.has("gameEnded")) {
                    outputArray.add(objectNode);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public ArrayNode output() {
        return outputArray;
    }

    private Boolean actionCommand(final Game game, final ActionsInput actionsInput,
                                  final ObjectNode objectNode) {
        int magic3 = 3;
        objectNode.put("command", actionsInput.getCommand());
        if (actionsInput.getCommand().equals("useEnvironmentCard")) {
            objectNode.put("handIdx", actionsInput.getHandIdx());
            objectNode.put("affectedRow", actionsInput.getAffectedRow());
            int a = actionsInput.getAffectedRow();
            Card card = game.currentPlayer().cardOnHand(actionsInput.getHandIdx());
            if (card.cardType != 2) {
                objectNode.put("error", "Chosen card is not of type environment.");
            } else if (card.getMana() > game.currentPlayer().getMana()) {
                objectNode.put("error", "Not enough mana to use environment card.");
            } else if (card instanceof Heart) {
                int mirror_row = magic3 - a;
                if (game.rowIsFull(mirror_row)) {
                    objectNode.put("error",
                            "Cannot steal enemy card since the player's row is full.");
                }
            } else if (game.currentPlayer() == players[0]) {
                if (((a == 2 || a == magic3) && ((Environment) card).abilityOnEnemy)
                        || ((a == 0 || a == 1) && ((Environment) card).abilityOnSelf)) {
                    objectNode.put("error", "Chosen row does not belong to the enemy.");
                }

            } else if (game.currentPlayer() == players[1]) {
                if (((a == 0 || a == 1) && ((Environment) card).abilityOnEnemy)
                        || ((a == 2 || a == magic3) && ((Environment) card).abilityOnSelf)) {
                    objectNode.put("error", "Chosen row does not belong to the enemy.");
                }
            }
            if (!objectNode.has("error")) {
                ((Environment) card).specialAbility(game.getTable(), magic3 - a);
                game.currentPlayer().dropCardFromHand(actionsInput.getHandIdx());
            }
            game.getTable().fillSpaces();
        } else if (actionsInput.getCommand().equals("useHeroAbility")) {
            objectNode.put("affectedRow", actionsInput.getAffectedRow());
            int row = actionsInput.getAffectedRow();
            Hero card = game.currentPlayer().getHero();
            Player player = game.currentPlayer();
            if (card.mana > player.mana) {
                objectNode.put("error", "Not enough mana to use hero's ability.");
            } else if (player.getHero().getSpecial()) {
                objectNode.put("error", "Hero has already attacked this turn.");
            } else if (!player.getHero().abilityOnEnemy
                    && onTheSameTeam(game.currentTurn + 1, actionsInput.getAffectedRow())) {
                objectNode.put("error", "Selected row does not belong to the current player.");
            } else if (!player.getHero().abilityOnSelf
                    && !onTheSameTeam(game.currentTurn + 1, actionsInput.getAffectedRow())) {
                objectNode.put("error", "Selected row does not belong to the enemy.");
            } else {
                card.specialAbility(game.getTable(), magic3 - row);
                player.setMana(player.getMana() - card.getMana());
            }
            game.getTable().fillSpaces();
        } else if (actionsInput.getCommand().equals("useAttackHero")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            ObjectNode cardAttackerNode = objectMapper.createObjectNode();
            cardAttackerNode.put("x", attackerX);
            cardAttackerNode.put("y", attackerY);
            objectNode.set("cardAttacker", cardAttackerNode);
            Hero attacked = game.nextPlayer().getHero();
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            // gasim cartea care e la pozitia (x, y)
            Hero herocard = game.nextPlayer().getHero();
            // ne trebuie o functie care gaseste urmatorul player, dar care identifica si
            // hero ul fiecarui jucator
            if (attacker.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
            } else if (attacker.getSpecial()) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (game.hasTankTheEnemy()) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            } else {
                attacker.cardUsesAttack(attacked, game);
                if (game.gameOver()) {
                    objectNode.removeAll();
                    if (players[0].getHero().health > 0) {
                        objectNode.put("gameEnded", "Player one killed the enemy hero.");
                    } else {
                        objectNode.put("gameEnded", "Player two killed the enemy hero.");
                    }
                }
            }
            game.getTable().fillSpaces();
        } else if (actionsInput.getCommand().equals("cardUsesAbility")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            int attackedX = actionsInput.getCardAttacked().getX();
            int attackedY = actionsInput.getCardAttacked().getY();
            Minion attacked = (Minion) game.getTable().getCardAtPosition(attackedX, attackedY);
            ObjectNode cardAttackedNode = objectMapper.createObjectNode();
            cardAttackedNode.put("x", attackedX);
            cardAttackedNode.put("y", attackedY);
            ObjectNode cardAttackerNode = objectMapper.createObjectNode();
            cardAttackerNode.put("x", attackerX);
            cardAttackerNode.put("y", attackerY);
            objectNode.set("cardAttacked", cardAttackedNode);
            objectNode.set("cardAttacker", cardAttackerNode);
            if (attacker.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
            } else if (attacker.getSpecial()) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (!((Minion) attacker).abilityOnEnemy
                    && !onTheSameTeam(attackerX, attackedX)) {
                objectNode.put("error", "Attacked card does not belong to the current player.");
            } else if (!((Minion) attacker).abilityOnSelf
                    && onTheSameTeam(attackerX, attackedX)) {
                objectNode.put("error", "Attacked card does not belong to the enemy.");
            } else if (game.hasTankTheEnemy() && !attacked.isTank()
                    && !(attacker instanceof Disciple)) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            }
            if (!objectNode.has("error")) {
                attacker.specialAbility(attacked);
                game.getTable().fillSpaces();
            }

        } else if (actionsInput.getCommand().equals("cardUsesAttack")) {
            int attackerX = actionsInput.getCardAttacker().getX();
            int attackerY = actionsInput.getCardAttacker().getY();
            Minion attacker = (Minion) game.getTable().getCardAtPosition(attackerX, attackerY);
            int attackedX = actionsInput.getCardAttacked().getX();
            int attackedY = actionsInput.getCardAttacked().getY();
            Minion attacked = (Minion) game.getTable().getCardAtPosition(attackedX, attackedY);
            ObjectNode cardAttackedNode = objectMapper.createObjectNode();
            cardAttackedNode.put("x", attackedX);
            cardAttackedNode.put("y", attackedY);
            ObjectNode cardAttackerNode = objectMapper.createObjectNode();
            cardAttackerNode.put("x", attackerX);
            cardAttackerNode.put("y", attackerY);
            objectNode.set("cardAttacked", cardAttackedNode);
            objectNode.set("cardAttacker", cardAttackerNode);
            if (onTheSameTeam(attackerX, attackedX)) {
                objectNode.put("error", "Attacked card does not belong to the enemy.");
            } else if (attacker.attacked) {
                objectNode.put("error", "Attacker card has already attacked this turn.");
            } else if (attacker.frozen) {
                objectNode.put("error", "Attacker card is frozen.");
            } else if (game.hasTankTheEnemy() && !attacked.isTank()) {
                objectNode.put("error", "Attacked card is not of type 'Tank'.");
            } else {
                attacker.cardUsesAttack(attacked, game);
            }
            game.getTable().fillSpaces();
        } else if (actionsInput.getCommand().equals("placeCard")) {
            Card card = game.currentPlayer().cardOnHand(actionsInput.getHandIdx());
            if (card.cardType == 2) {
                objectNode.put("error", "Cannot place environment card on table.");
                objectNode.put("handIdx", actionsInput.getHandIdx());
            } else if (card.getMana() > game.currentPlayer().mana) {
                objectNode.put("error", "Not enough mana to place card on table.");
                objectNode.put("handIdx", actionsInput.getHandIdx());
            } else if (((Minion) (card)).placeOnLastRow) {
                if (game.isPlayersRow(0, game.currentPlayer())) {

                    Boolean a = game.placeCard(0, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                        objectNode.put("handIdx", actionsInput.getHandIdx());
                    } else {
                        game.currentPlayer().dropCardFromHand(actionsInput.getHandIdx());
                    }
                }
                if (game.isPlayersRow(magic3, game.currentPlayer())) {
                    Boolean a = game.placeCard(magic3, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                        objectNode.put("handIdx", actionsInput.getHandIdx());
                    } else {
                        game.currentPlayer().dropCardFromHand(actionsInput.getHandIdx());
                    }
                }
            } else if (((Minion) (card)).placeOnFirstRow) {
                if (game.isPlayersRow(1, game.currentPlayer())) {

                    Boolean a = game.placeCard(1, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                        objectNode.put("handIdx", actionsInput.getHandIdx());
                    } else {
                        game.currentPlayer().dropCardFromHand(actionsInput.getHandIdx());
                    }
                }
                if (game.isPlayersRow(2, game.currentPlayer())) {

                    Boolean a = game.placeCard(2, (Minion) card);
                    if (!a) {
                        objectNode.put("error", "Cannot place card on table since row is full.");
                        objectNode.put("handIdx", actionsInput.getHandIdx());
                    } else {
                        game.currentPlayer().dropCardFromHand(actionsInput.getHandIdx());
                    }
                }
            } else {
                objectNode.put("PlayerIdx", actionsInput.getPlayerIdx());
                game.rebuildTable();
            }
        } else if (actionsInput.getCommand().equals("endPlayerTurn")) {
            game.endCurrentTurn();
        } else {
            return false;
        }
        if (!objectNode.has("error") && !objectNode.has("gameEnded")) {
            objectNode.removeAll();
        }
        return true;
    }
}







