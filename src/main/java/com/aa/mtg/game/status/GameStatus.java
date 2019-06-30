package com.aa.mtg.game.status;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.game.player.Player;
import com.aa.mtg.game.stack.SpellStack;
import com.aa.mtg.game.turn.Turn;

import java.util.concurrent.atomic.AtomicInteger;

public class GameStatus {

    private AtomicInteger nextCardId = new AtomicInteger();
    private String gameId;
    private Player player1;
    private Player player2;
    private Turn turn;
    private SpellStack stack;

    public GameStatus(String gameId) {
        this.gameId = gameId;
        this.turn = new Turn();
        this.stack = new SpellStack();
    }

    public String getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Turn getTurn() {
        return turn;
    }

    public SpellStack getStack() {
        return stack;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
    public Player getPlayerBySessionId(String sessionId) {
        if (this.getPlayer1().getSessionId().equals(sessionId)) {
            return this.getPlayer1();
        } else if (this.getPlayer2().getSessionId().equals(sessionId)) {
            return this.getPlayer2();
        }
        throw new RuntimeException("SessionId " + sessionId + " is not linked to game " + gameId + " .");
    }

    public Player getPlayerByName(String name) {
        if (this.getPlayer1().getName().equals(name)) {
            return this.getPlayer1();
        } else {
            return this.getPlayer2();
        }
    }

    public Player getCurrentPlayer() {
        if (turn.getCurrentTurnPlayer().equals(this.getPlayer1().getName())) {
            return this.getPlayer1();
        } else {
            return this.getPlayer2();
        }
    }

    public boolean isPlayerCurrent(Player player) {
        return getCurrentPlayer().getName().equals(player.getName());
    }

    public Player getNonCurrentPlayer() {
        if (turn.getCurrentTurnPlayer().equals(this.getPlayer1().getName())) {
            return this.getPlayer2();
        } else {
            return this.getPlayer1();
        }
    }

    public void putIntoGraveyard(CardInstance cardInstance) {
        Player owner = getPlayerByName(cardInstance.getOwner());
        cardInstance.clearModifiers();
        owner.getGraveyard().addCard(cardInstance);
    }

    public int nextCardId() {
        return nextCardId.incrementAndGet();
    }

    public CardInstance extractCardByIdFromAnyBattlefield(int id) {
        if (getNonCurrentPlayer().getBattlefield().hasCardById(id)) {
            return getNonCurrentPlayer().getBattlefield().extractCardById(id);

        } else if (getCurrentPlayer().getBattlefield().hasCardById(id)) {
            return getCurrentPlayer().getBattlefield().extractCardById(id);
        }

        return null;
    }
}
