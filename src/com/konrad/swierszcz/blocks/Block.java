package com.konrad.swierszcz.blocks;

public class Block {
    private int posX, posY;
    private char representation;

    public Block(int posX, int posY, char representation) {
        this.posX = posX;
        this.posY = posY;
        this.representation = representation;
    }

    protected void setRepresentation(char c) {
        this.representation = c;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public char getRepresentation() {
        return representation;
    }
}
