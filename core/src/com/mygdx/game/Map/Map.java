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

        for (int i = 0; i < 10; i++) {
            Queue<Pair<Integer,Integer>> theCurrentQueue= new LinkedList<>();
            Set<Pair<Integer, Integer>> usedSpaces = new HashSet<>();

            //gets random start point
            int x = (int)Math.round(Math.random() * 1500);
            int y = (int)Math.round(Math.random() * 1500);
            theCurrentQueue.add(new Pair<>(x, y));

            while (! theCurrentQueue.isEmpty()) {
                Pair<Integer, Integer> currentCoords = theCurrentQueue.remove();
                x = currentCoords.getKey();
                y = currentCoords.getValue();

                //checks queue item to see if it becomes a forest, then adds its neighbours
                if (Math.random() < spreadRate) {
                    SML.get(x).set(y,infectionType);

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
        int count = 0;
        for (int i = 0; i < 1500; i++) {
            boolean printed_line = false;
            for (int j = 0; j < 1500; j++) {
                char current = SML.get(i).get(j);
                if (current == 'f') {
                    count++;
                    if (printed_line != true) {
                        System.out.println(SML.get(i));
                        printed_line = true;
                    }
                }
            }
        }
        System.out.println("there are " + count + " forest tiles spawned");
    }

    public void rockSummon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            //Determines coords and boulder size
            int rockSize = (int)Math.round(Math.random() * 25);
            int x = (int)Math.round(Math.random() * 1500);
            int y = (int)Math.round(Math.random() * 1500);
            int initialX = x;
            int initialY = y;
            int innerSize = (int)rockSize/2;

            //Checks bounds and if good
            if (x > 25 && x < 1475 && y > 25 && y < 1475) {
                //adds a solid middle of boulder
                for (int j = 0; j < innerSize; j++) {
                    for (int k = 0; k < innerSize; k++) {
                        SML.get(x+j).set(y+k, 'c');
                    }
                }
                //adds some lingering small rocks around the big boulder
                for (int j = -innerSize; j < rockSize; j++) {
                    for (int k = -innerSize; k < rockSize; k++) {
                        if (Math.random() > 0.25) {
                            SML.get(x+j).set(y+k, 'c');
                        }
                    }
                }
            }
        }

        //Quick rock count:
        int count = 0;
        for (int i = 0; i < 1500; i++) {
            for (int j = 0; j < 1500; j++) {
                char current = SML.get(i).get(j);
                if (current == 'c') {
                    count++;
                }
            }
        }
        System.out.println("there are " + count + " rock tiles");
    }
}

