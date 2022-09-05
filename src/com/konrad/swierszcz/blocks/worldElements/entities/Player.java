package com.konrad.swierszcz.blocks.worldElements.entities;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;

public class Player extends WorldElement {
    public Player(int posX, int posY) {
        super(posX, posY, '¥', 0);
    }

    @Override
    public String toString() {
        return "Player";
    }
}
