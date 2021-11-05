package com.mygdx.game.Map;

import javafx.util.Pair;

import java.util.*;
import java.lang.Math;

public class Map {
    public List<List<Character>> SML;
    public Map() {
        //makes the map 1500 by 1500 char wide
        SML = new ArrayList<>();
        for (int i = 0; i < 1500; i++) {
            List<Character> eachLineList = new ArrayList<Character>();
            for (int j = 0; j < 1500; j++) {
                eachLineList.add('g');
            }
            SML.add(eachLineList);
        }
    }
    public void virus(float spreadRate, float decayRate, char infectionType) {
        //runs the infection i times

        for (int i = 0; i < 5; i++) {
            Queue<Pair<Integer,Integer>> theCurrentQueue= new LinkedList<>();
            Set<Pair<Integer, Integer>> usedSpaces = new HashSet<>();

            int x = (int)Math.round(Math.random() * 1500);
            int y = (int)Math.round(Math.random() * 1500);
            theCurrentQueue.add(new Pair<>(x, y));

            while (! theCurrentQueue.isEmpty()) {
                Pair<Integer, Integer> currentCoords = theCurrentQueue.remove();
                x = currentCoords.getKey();
                y = currentCoords.getValue();
                // take top item from queue
                // does it turn into a forest

                if (Math.random() < spreadRate) {
                    SML.get(x).set(y,infectionType);
                    System.out.println(SML.get(x));
                    if (0 < x && x < 1499 && 0 < y && y < 1499) {
                        Pair<Integer, Integer> p1 = new Pair<>(x+1, y);
                        Pair<Integer, Integer> p2 = new Pair<>(x, y+1);
                        Pair<Integer, Integer> p3 = new Pair<>(x-1, y);
                        Pair<Integer, Integer> p4 = new Pair<>(x, y-1);

                        if (! usedSpaces.contains(p1)) {
                            theCurrentQueue.add(p1);
                            usedSpaces.add(p1);
                        }
                        if (! usedSpaces.contains(p2)) {
                            theCurrentQueue.add(p2);
                            usedSpaces.add(p2);
                        }
                        if (! usedSpaces.contains(p3)) {
                            theCurrentQueue.add(p3);
                            usedSpaces.add(p3);
                        }
                        if (! usedSpaces.contains(p4)) {
                            theCurrentQueue.add(p4);
                            usedSpaces.add(p4);
                        }

                    }
                }
                spreadRate -= decayRate;
            }
        }
        for (int i = 0; i < 1500; i++) {
            for (int j = 0; j < 1500; j++) {
                System.out.print(SML.get(i).get(j));
            }
            System.out.println();
        }

    }

}

