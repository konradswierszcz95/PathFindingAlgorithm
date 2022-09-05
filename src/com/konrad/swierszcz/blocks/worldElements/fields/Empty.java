package com.konrad.swierszcz.blocks.worldElements.fields;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;

public class Empty extends WorldElement {
    public Empty(int posX, int posY) {
        super(posX, posY, ' ', 20);
    }

    @Override
    public String toString() {
        return "Empty";
    }
}
