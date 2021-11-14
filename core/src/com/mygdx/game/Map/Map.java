package com.mygdx.game.Map;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.*;

public class Map {

    public List<List<Tile>> SML;
    public List<char[]> mazeList;
    private final Random random;
    public final int size = 500;

    public Map(long seed) {
        //makes the map size by size char wide
        SML = new ArrayList<>();
        random = new Random(seed);

        for (int i = 0; i < size; i++) {
            List<Tile> eachLineList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                eachLineList.add(new DefaultTile("grass"));
            }
            SML.add(eachLineList);
        }
        virus(10, 1f,0.01f, "dirt");
        virus(50, 1f,0.01f,"forest");
        virus(100, 1f,0.1f,"cobble");
        maze();
    }

    public int playerPositionToMapIndex(float pos) {
        return (int) pos  / 64 + size / 2;
    }

    public float mapIndexToPlayerPosition(int index) {
        return (index - size/2f) * 64;
    }

    public void virus(int quantity, float spreadRate, float decayRate, String infectionType) {
        for (int i = 0; i < quantity; i++) {
            Queue<Pair<Integer, Integer>> theCurrentQueue = new LinkedList<>();
            Set<Pair<Integer, Integer>> usedSpaces = new HashSet<>();

            //gets random start point
            int startingX = Math.round(random.nextFloat() * (size-2));
            int startingY = Math.round(random.nextFloat() * (size-2));
            theCurrentQueue.add(new Pair<>(startingX, startingY));

            while (!theCurrentQueue.isEmpty()) {
                Pair<Integer, Integer> currentCoords = theCurrentQueue.remove();
                int x = currentCoords.getKey();
                int y = currentCoords.getValue();

                //checks queue item to see if it becomes a forest, then adds its neighbours
                float distX = Math.abs(startingX - x);
                float distY = Math.abs(startingY - y);
                float threshold = spreadRate - decayRate * (distX + distY);
                if (random.nextFloat() >= threshold) continue; // guard clause to not make it a forest

                String type = infectionType;
                if (infectionType.equals("forest")) {
                    // picking the type of tree
                    float treeType = random.nextFloat();
                    boolean treeFlipped = random.nextFloat() > 0.5;
                    if (treeType > 0.5) {
                        type = "tree2";
                    } else if (treeType > 0.25) {
                        type = "tree1";
                    } else {
                        type = "tree3";
                    }
                    SML.get(y).set(x, new TreeTile(type, treeFlipped));
                }
                SML.get(y).get(x).updateType(type);

                // the way this is currently set up, the forests cannot move in if they are on the edges
                // i think its fine this was
                if (0 >= x || x > size-2 || 0 >= y || y > size-2) continue;
                Pair<Integer, Integer> p1 = new Pair<>(x + 1, y);
                Pair<Integer, Integer> p2 = new Pair<>(x, y + 1);
                Pair<Integer, Integer> p3 = new Pair<>(x - 1, y);
                Pair<Integer, Integer> p4 = new Pair<>(x, y - 1);

                if (!usedSpaces.contains(p1)) theCurrentQueue.add(p1);
                if (!usedSpaces.contains(p2)) theCurrentQueue.add(p2);
                if (!usedSpaces.contains(p3)) theCurrentQueue.add(p3);
                if (!usedSpaces.contains(p4)) theCurrentQueue.add(p4);

                usedSpaces.add(p1);
                usedSpaces.add(p2);
                usedSpaces.add(p3);
                usedSpaces.add(p4);
            }
        }
    }

    public void maze () {
        //BENNETT
        //IF YOU ARE READING THIS
        //YOU SHOULDNT BE
        //THIS IS IMPECCABLE
        //AND A PERFECT SYSTEm

        mazeList = new ArrayList<>(); // MAKES THE MAP MEGA LIST

        try {
            File file = new File("/home/bbrixen/IdeaProjects/GameJam/core/src/com/mygdx/game/Map/maze.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                mazeList.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        //gets some random coords to start the maze at
        int startingX = Math.round(random.nextFloat() * (size-45));
        int startingY = Math.round(random.nextFloat() * (size-45));
        // this is for testing the maze
        startingY = 250;
        startingX = 250;
        int yMult = 1;
        if (random.nextFloat() > 0.5) yMult = -1;
        int xMult = 1;
        if (random.nextFloat() > 0.5) xMult = -1;


        //decides how to flip the maze to give it a more random feel
        for (int i = 0; i < mazeList.size(); i++) {
            for (int j = 0; j < mazeList.get(i).length; j++) {
                char m = mazeList.get(i)[j];
                Tile tile;
                if (m == 'B') tile = new BoulderTile("boulder");
                else tile = new DefaultTile("manmadeCobble");

                SML.get((i * yMult) + startingY).set((j * xMult) + startingX, tile);
            }
        }
    }
}
