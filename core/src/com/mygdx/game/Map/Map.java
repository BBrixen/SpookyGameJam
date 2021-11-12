package com.mygdx.game.Map;

import javafx.util.Pair;
import java.lang.Math;
import java.util.*;

public class Map {

    public List<List<Tile>> SML;
    public List<List<Character>> mazeList;
    private final Random random;
    public final int size = 500;

    public Map(long seed) {
        //makes the map size by size char wide
        SML = new ArrayList<>();
        random = new Random(seed);

        for (int i = 0; i < size; i++) {
            List<Tile> eachLineList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                eachLineList.add(new Tile("grass", false));
            }
            SML.add(eachLineList);
        }
        virus(10, 1f,0.01f, "dirt");
        virus(30, 1f,0.02f,"forest");
        virus(100, 1f,0.1f,"cobble");
//        maze();
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
                    SML.get(y).get(x).setFlipped(treeFlipped);
                    SML.get(y).get(x).setyOffset(32);

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
        List<Character> mazeLine1  = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine2  = Arrays.asList('B', 'C', 'C', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'C', 'C', 'B', 'B', 'B', 'B', 'C', 'C', 'C', 'B', 'C', 'C', 'C', 'C', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine3  = Arrays.asList('B', 'C', 'B', 'B', 'B', 'B', 'C', 'B', 'C', 'C', 'C', 'C', 'B', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine4  = Arrays.asList('B', 'C', 'B', 'C', 'C', 'B', 'C', 'C', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine5  = Arrays.asList('B', 'C', 'B', 'B', 'C', 'B', 'C', 'B', 'C', 'C', 'B', 'C', 'C', 'C', 'B', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'C', 'C', 'C', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine6  = Arrays.asList('B', 'C', 'B', 'B', 'C', 'B', 'C', 'C', 'B', 'C', 'C', 'C', 'B', 'B', 'B', 'C', 'C', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine7  = Arrays.asList('B', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine8  = Arrays.asList('B', 'C', 'B', 'B', 'B', 'C', 'C', 'C', 'B', 'C', 'C', 'B', 'C', 'C', 'C', 'C', 'B', 'B', 'C', 'C', 'C', 'B', 'C', 'B', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine9  = Arrays.asList('B', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'C', 'C', 'C', 'B', 'B', 'C', 'C', 'C', 'B', 'B', 'C', 'C', 'C', 'B', 'B', 'B', 'C', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine10 = Arrays.asList('B', 'C', 'B', 'C', 'B', 'C', 'C', 'C', 'C', 'C', 'C', 'B', 'C', 'C', 'C', 'B', 'B', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine11 = Arrays.asList('B', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'B', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine12 = Arrays.asList('B', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'C', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine13 = Arrays.asList('B', 'C', 'B', 'C', 'B', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine14 = Arrays.asList('B', 'C', 'B', 'C', 'B', 'C', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine15 = Arrays.asList('B', 'C', 'C', 'C', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine16 = Arrays.asList('B', 'C', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine17 = Arrays.asList('B', 'C', 'C', 'C', 'C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine18 = Arrays.asList('B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine19 = Arrays.asList('B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine20 = Arrays.asList('B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine21 = Arrays.asList('C', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C');
        List<Character> mazeLine22 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine23 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine24 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine25 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine26 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine27 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine28 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine29 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine30 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine31 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine32 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine33 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine34 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine35 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine36 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine37 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine38 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine39 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine40 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');
        List<Character> mazeLine41 = Arrays.asList('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B');

        //Adds it all to the map mega list
        mazeList.add(mazeLine1);
        mazeList.add(mazeLine2);
        mazeList.add(mazeLine3);
        mazeList.add(mazeLine4);
        mazeList.add(mazeLine5);
        mazeList.add(mazeLine6);
        mazeList.add(mazeLine7);
        mazeList.add(mazeLine8);
        mazeList.add(mazeLine9);
        mazeList.add(mazeLine10);
        mazeList.add(mazeLine11);
        mazeList.add(mazeLine12);
        mazeList.add(mazeLine13);
        mazeList.add(mazeLine14);
        mazeList.add(mazeLine15);
        mazeList.add(mazeLine16);
        mazeList.add(mazeLine17);
        mazeList.add(mazeLine18);
        mazeList.add(mazeLine19);
        mazeList.add(mazeLine20);
        mazeList.add(mazeLine21);
        mazeList.add(mazeLine22);
        mazeList.add(mazeLine23);
        mazeList.add(mazeLine24);
        mazeList.add(mazeLine25);
        mazeList.add(mazeLine26);
        mazeList.add(mazeLine27);
        mazeList.add(mazeLine28);
        mazeList.add(mazeLine29);
        mazeList.add(mazeLine30);
        mazeList.add(mazeLine31);
        mazeList.add(mazeLine32);
        mazeList.add(mazeLine33);
        mazeList.add(mazeLine34);
        mazeList.add(mazeLine35);
        mazeList.add(mazeLine36);
        mazeList.add(mazeLine37);
        mazeList.add(mazeLine38);
        mazeList.add(mazeLine39);
        mazeList.add(mazeLine40);
        mazeList.add(mazeLine41);

        //gets some random coords to start the maze at
        int startingX = Math.round(random.nextFloat() * (size-45));
        int startingY = Math.round(random.nextFloat() * (size-45));
        // this is for testing the maze
        startingY = 250;
        startingX = 250;
        float randomDirection = random.nextFloat();

        //decides how to flip the maze to give it a more random feel
        if (randomDirection <= 0.25) {
            for (int i = 0; i < 41; i++) {
                for (int j = 0; j < 41; j++) {
//                    SML.get(startingY + j).set(startingX + i, mazeList.get(j).get(i));
                }
            }
        }
        else if (randomDirection > 0.25 && randomDirection <=.5) {
            for (int i = 0; i < 41; i++) {
                for (int j = 0; j < 41; j++) {
//                    SML.get(startingY + j).set(startingX + i, mazeList.get(40-j).get(i));
                }
            }
        }
        else if (randomDirection > 0.5 && randomDirection <=0.75) {
            for (int i = 0; i < 41; i++) {
                for (int j = 0; j < 41; j++) {
//                    SML.get(startingY + j).set(startingX + i, mazeList.get(j).get(40-i));
                }
            }
        }
        else if (randomDirection > 0.75) {
            for (int i = 0; i < 41; i++) {
                for (int j = 0; j < 41; j++) {
//                    SML.get(startingY + j).set(startingX + i, mazeList.get(40-j).get(40-i));
                }
            }
        }
    }
}
