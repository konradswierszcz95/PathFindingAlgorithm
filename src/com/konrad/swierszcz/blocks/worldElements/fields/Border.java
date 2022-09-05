package com.konrad.swierszcz.blocks.worldElements.fields;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;

public class Border extends WorldElement {
    private char representation;

    public Border(int posX, int posY, BorderType borderType) {
        super(posX, posY, '║', 99999);
        switch (borderType) {
            case VERTICAL:
                representation = '║';
                break;
            case HORIZONTAL:
                representation = '=';
                break;
            case CORNER_LEFT_DOWN:
                representation = '╚';
                break;
            case CORNER_LEFT_UP:
                representation = '╔';
                break;
            case CORNER_RIGHT_UP:
                representation = '╗';
                break;
            case CORNER_RIGHT_DOWN:
                representation = '╝';
                break;
        }

        setRepresentation(this.representation);

    }

    @Override
    public String toString() {
        return "Border";
    }
}
