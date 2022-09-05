package com.konrad.swierszcz.blocks.worldElements.fields;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;

public class Wall extends WorldElement {
    public Wall(int posX, int posY) {
        super(posX, posY, 'W', 9999);
    }

    @Override
    public String toString() {
        return "Wall";
    }
}
