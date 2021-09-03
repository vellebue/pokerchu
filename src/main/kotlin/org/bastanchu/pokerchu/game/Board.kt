package org.bastanchu.pokerchu.game

import org.bastanchu.pokerchu.core.Card

class Board {

    var pots: MutableList<Pot>;
    var commonCards: MutableList<Card>;
    var players: Array<Player>?;

    init {
        pots = ArrayList<Pot>();
        commonCards = ArrayList<Card>();
        players = null;
    }

    public fun initBoard(players: Array<Player>) {
        pots.add(Pot(players));
        this.players = players;
    }

    public fun resetBoard() {
        pots = ArrayList<Pot>();
        commonCards = ArrayList<Card>();
        players = null;
    }
}