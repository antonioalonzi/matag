package com.aa.mtg.game.player;

import com.aa.mtg.cards.Card;
import com.aa.mtg.cards.CardInstance;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<CardInstance> cards;

    public Library() {
        this.cards = new ArrayList<>();
    }

    CardInstance draw() {
        return this.cards.remove(0);
    }

    public List<CardInstance> getCards() {
        return cards;
    }

    public List<CardInstance> maskedLibrary() {
        return CardInstance.mask(this.cards);
    }
}
