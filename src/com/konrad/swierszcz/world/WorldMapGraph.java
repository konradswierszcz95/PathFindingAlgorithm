package com.konrad.swierszcz.world;

import com.konrad.swierszcz.blocks.worldElements.WorldElement;
import com.konrad.swierszcz.blocks.worldElements.entities.Player;
import com.konrad.swierszcz.blocks.worldElements.entities.Target;
import com.konrad.swierszcz.blocks.worldElements.fields.Border;
import com.konrad.swierszcz.blocks.worldElements.fields.BorderType;
import com.konrad.swierszcz.blocks.worldElements.fields.Empty;

import java.util.ArrayList;
import java.util.List;

public class WorldMapGraph {
    //Class fields
    private int sizeX, sizeY;
    private GraphMapElement[][] elementsOfGraph;
    private List<GraphPath> paths;
    private GraphMapElement target, player;
    //============================================

    //Constructors
    public WorldMapGraph(int sizeX, int sizeY) {
        //Size is increased to insert borders on the edge
        this.sizeX = sizeX + 2;
        this.sizeY = sizeY + 2;

        elementsOfGraph = new GraphMapElement[this.sizeY][this.sizeX];

        this.paths = new ArrayList<>();

        fillElementsOfGraphArrayWithEmptyElements();
        addBordersOnTheEdge();
    }
    //============================================

    //Overrided
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                sb.append(getElement(x, y).getRepresentationOfType());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    //============================================

    //Getters
    public GraphMapElement getElement(int posX, int posY) {
        return elementsOfGraph[posY][posX];
    }

    public List<String> getPaths() {
        List<String> stringPaths = new ArrayList<>();

        for (GraphPath path : this.paths) {
            stringPaths.add(path.toString());
        }

        return stringPaths;
    }

    public String getBestPath() {
        GraphPath winner = paths.get(0);

        for (GraphPath path: paths) {
            if (path.getSummaryCost() < winner.getSummaryCost()) {
                winner = path;
            }
        }

        return winner.toString();
    }
    //============================================

    //Graph content modification
    private void addElementToArray(WorldElement element) {
        elementsOfGraph[element.getPosY()][element.getPosX()] = new GraphMapElement(element);
    }

    public void fillElementsOfGraphArrayWithEmptyElements() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                addElementToArray(new Empty(x, y));
            }
        }
    }

    public void addBordersOnTheEdge() {
        for (int x = 1; x < sizeX - 1; x++) {
            addElementToArray(new Border(x, 0, BorderType.HORIZONTAL));
            addElementToArray(new Border(x, sizeY - 1, BorderType.HORIZONTAL));
        }

        for (int y = 1; y < sizeY - 1; y++) {
            addElementToArray(new Border(0, y, BorderType.VERTICAL));
            addElementToArray(new Border(sizeX - 1, y, BorderType.VERTICAL));
        }

        addElementToArray(new Border(0, 0, BorderType.CORNER_LEFT_UP));
        addElementToArray(new Border(0, sizeY - 1, BorderType.CORNER_LEFT_DOWN));
        addElementToArray(new Border(sizeX - 1, 0, BorderType.CORNER_RIGHT_UP));
        addElementToArray(new Border(sizeX - 1, sizeY - 1, BorderType.CORNER_RIGHT_DOWN));
    }

    public void setTarget(int posX, int posY) {
        addElementToArray(new Target(posX, posY));
        this.target = this.getElement(posX, posY);
    }

    public void setPlayer(int posX, int posY) {
        addElementToArray(new Player(posX, posY));
        this.player = this.getElement(posX, posY);
    }

    public void addWorldElementToMap(WorldElement element) {
        addElementToArray(element);
    }
    //============================================

    //Graph elements modification
    public void findChildrenForAllGraphElementsExceptBorders() {
        for (int y = 1; y < sizeY - 1; y++) {
            for (int x = 1; x < sizeX - 1; x++) {
                GraphMapElement e = this.getElement(x, y);
                e.addChild(this.getElement(x - 1, y));
                e.addChild(this.getElement(x + 1, y));
                e.addChild(this.getElement(x, y - 1));
                e.addChild(this.getElement(x, y + 1));
            }
        }
    }

    public void removeAllBordersFromChildrenListForAllElements() {
        for (int y = 1; y < sizeY - 1; y++) {
            for (int x = 1; x < sizeX - 1; x++) {
                GraphMapElement e = this.getElement(x, y);
                e.removeBordersFromChildrenList();
            }
        }
    }
    //============================================

    //Path choosing related methods
    public void findAllPathsForGraph() {
        this.findPathForNextLevel();
        this.removePathsWithoutTargetReached();
    }

    public void findPathForNextLevel() {
        List<GraphMapElement> newPath = new ArrayList<>();
        newPath.add(this.player);
        this.paths.add(new GraphPath(newPath));

        findPathForNextLevel(this.paths.get(0));
    }

    public void findPathForNextLevel(GraphPath previousPath) {
        GraphMapElement parent = previousPath.getLastStep();

        for (GraphMapElement child : parent.getChildren()) {
            if (!previousPath.pathContains(child) && !previousPath.pathContains(this.target)) {
                GraphPath nextLevelPath = new GraphPath(previousPath.getSteps());
                nextLevelPath.addStep(child);
                this.paths.add(nextLevelPath);
                findPathForNextLevel(nextLevelPath);
            }
        }
    }

    public void removePathsWithoutTargetReached() {
        List<GraphPath> toRemove = new ArrayList<>();

        for (GraphPath path : this.paths) {
            if (!path.pathContains(target)) {
                toRemove.add(path);
            }
        }

        for (GraphPath path : toRemove) {
            this.paths.remove(path);
        }
    }

    public void countCrossingCostForAllPaths() {
        for (GraphPath path : this.paths) {
            path.countSummaryCostOfPath();
        }
    }
    //============================================


}

class GraphMapElement {
    private int posX, posY;
    private List<GraphMapElement> children = new ArrayList<>();
    private WorldElement typeOfElement;

    public GraphMapElement(WorldElement typeOfElement) {
        this.typeOfElement = typeOfElement;
        this.posX = typeOfElement.getPosX();
        this.posY = typeOfElement.getPosY();
    }

    //Overrided
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        }

        GraphMapElement element = (GraphMapElement) obj;


        if (this.posX == element.posX && this.posY == element.posY) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "X = " + this.posX + " | Y = " + this.posY + " | TYPE = " + typeOfElement.toString();
    }
    //============================================

    //Children list modification
    public void addChild(GraphMapElement child) {
        this.children.add(child);
    }

    public void removeChild(GraphMapElement child) {
        this.children.remove(child);
    }

    public void removeBordersFromChildrenList() {
        List<GraphMapElement> toRemove = new ArrayList<>();
        for (GraphMapElement child : children) {
            if (child.typeOfElement instanceof Border) {
                toRemove.add(child);
            }
        }

        for (GraphMapElement child : toRemove) {
            this.removeChild(child);
        }
    }
    //=============================================

    //Getters
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public List<GraphMapElement> getChildren() {
        return children;
    }

    public WorldElement getTypeOfElement() {
        return typeOfElement;
    }

    public char getRepresentationOfType() {
        return typeOfElement.getRepresentation();
    }

    public int getCrossingCost() {
        return this.typeOfElement.getCrossingCost();
    }
    //=============================================
}

class GraphPath {
    private List<GraphMapElement> steps = new ArrayList<>();
    private int summaryCost;

    public GraphPath(List<GraphMapElement> childElements) {
        this.summaryCost = 0;
        for (GraphMapElement element : childElements) {
            this.steps.add(element);
        }
    }

    //Getters
    public List<GraphMapElement> getSteps() {
        return this.steps;
    }

    public GraphMapElement getLastStep() {
        GraphMapElement lastStep = steps.get(steps.size() - 1);
        return lastStep;
    }

    public int getSummaryCost() {
        return summaryCost;
    }
    //============================================

    //Overrided
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GraphMapElement element : this.steps) {
            sb.append(element.toString() + " ->\n");
        }

        return sb.toString();
    }
    //============================================

    //Steps list operation
    public void addStep(GraphMapElement element) {
        this.steps.add(element);
    }

    public boolean pathContains(GraphMapElement element) {
        if (steps.contains(element)) {
            return true;
        } else {
            return false;
        }
    }
    //============================================

    //Cost calculation
    public void countSummaryCostOfPath() {
        for (GraphMapElement element : steps) {
            summaryCost += element.getCrossingCost();
        }
    }
    //============================================
}