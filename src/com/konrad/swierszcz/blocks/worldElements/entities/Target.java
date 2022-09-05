package com.konrad.swierszcz.blocks.worldElements.entities;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;

public class Target extends WorldElement {
    public Target(int posX, int posY) {
        super(posX, posY, 'X', 0);
    }

    @Override
    public String toString() {
        return "Target";
    }
}
