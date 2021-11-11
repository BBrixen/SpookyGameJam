package com.mygdx.game.Map;

import javafx.util.Pair;
import java.lang.Math;
import java.util.*;

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
                eachLineList.add('d');
            }
            SML.add(eachLineList);
        }
        virus(10, 1f,0.005f, 'g');
        virus(50, 1f,0.01f,'f');
        virus(100, 1f,0.1f,'c');
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
                float distX = Math.abs(startingX - x);
                float distY = Math.abs(startingY - y);
                float threshold = (float) (spreadRate - decayRate * (distX + distY));
                if (random.nextFloat() >= threshold) continue; // guard clause to not make it a forest

                SML.get(y).set(x, infectionType);
                if (infectionType == 'f') {
                    char type = infectionType;
                    if (random.nextFloat() > 0.5) {
                        type = 't'; // we want to have a 50/50 split of different trees
                        if (random.nextFloat() > 0.5) type = 'T';
                    } else if (random.nextFloat() > 0.5) {
                        type = 'F';
                    }
                    SML.get(y).set(x, type);
                }

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

}
