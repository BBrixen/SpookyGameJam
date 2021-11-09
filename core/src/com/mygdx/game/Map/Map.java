package com.mygdx.game.Map;

import javafx.util.Pair;
import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class Map {

    public List<List<Character>> SML;
    private final Random random;
    public final int size = 500;

    public Map(long seed) {
        //makes the map size by size char wide
        SML = new ArrayList<>();
        random = new Random(seed);

        for (int i = 0; i < size; i++) {
            List<Character> eachLineList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                eachLineList.add('g');
            }
            SML.add(eachLineList);
        }
        virus(20, 1f,0.01f,'f');
        rockSummon(50);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(SML.get(i).get(j));
            }
            System.out.println();
        }
    }

    public void virus(int quantity, float spreadRate, float decayRate, char infectionType) {
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
                float distXSquared = (startingX - x) * (startingX - x);
                float distYSquared = (startingY - y) * (startingY - y);
                float threshold = (float) (spreadRate - decayRate * Math.sqrt(distXSquared + distYSquared));
                if (random.nextFloat() >= threshold) continue; // guard clause to not make it a forest

                SML.get(y).set(x, infectionType);

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

    public void rockSummon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            //Determines coords and boulder size
            int rockSize = Math.round(random.nextFloat() * 25);
            int x = Math.round(random.nextFloat() * size);
            int y = Math.round(random.nextFloat() * size);

            int innerSize = rockSize /2;

            //Checks bounds and if good
            if (x > 25 && x < size - 25 && y > 25 && y < size - 25) {
                //adds a solid middle of boulder
                for (int j = 0; j < innerSize; j++) {
                    for (int k = 0; k < innerSize; k++) {
                        SML.get(y+j).set(x+k, 'c');
                    }
                }
                //adds some lingering small rocks around the big boulder
                for (int j = -innerSize; j < rockSize; j++) {
                    for (int k = -innerSize; k < rockSize; k++) {
                        if (random.nextFloat() > 0.25) {
                            SML.get(y+j).set(x+k, 'c');
                        }
                    }
                }
            }
        }
    }
}

