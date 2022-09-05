package com.konrad.swierszcz.blocks.worldElements;

import com.konrad.swierszcz.blocks.Block;

public class WorldElement extends Block {
    private int crossingCost;

    public WorldElement(int posX, int posY, char representation, int crossingCost) {
        super(posX, posY, representation);
        this.crossingCost = crossingCost;
    }

    public int getCrossingCost() {
        return crossingCost;
    }
}
