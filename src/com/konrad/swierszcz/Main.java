package com.konrad.swierszcz;

import com.konrad.swierszcz.blocks.worldElements.fields.Wall;
import com.konrad.swierszcz.world.WorldMapGraph;

public class Main {
    public static void main(String args[]) {
        WorldMapGraph world = new WorldMapGraph(5, 5);
        world.setPlayer(1, 1);
        world.setTarget(5, 5);

        for (int y = 1; y < 5; y++) {
            world.addWorldElementToMap(new Wall(2, y));
        }

        for (int y = 5; y > 1; y--) {
            world.addWorldElementToMap(new Wall(4, y));
        }

        System.out.println(world);

        world.findChildrenForAllGraphElementsExceptBorders();
        world.removeAllBordersFromChildrenListForAllElements();


        world.findAllPathsForGraph();

        world.countCrossingCostForAllPaths();

        System.out.println(world.getBestPath());
    }
}
