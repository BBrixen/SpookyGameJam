package com.mygdx.game.Map;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class Map {
    public List<List<Character>> SML;
    public Map() {
        SML = new ArrayList<>();
        for (int i = 0; i < 1050; i++) {
            List<Character> eachLineList = new ArrayList<Character>();
            for (int j = 0; j < 1050; j++) {
                eachLineList.add('a');
            }
            SML.add(eachLineList);
        }
        System.out.println(((Math.random() * 1049)));
    }
    public void virus(float spreadRate, float decayRate, char infectionType) {
        Queue<Pair<Integer,Integer>> theCurrentQueue= new LinkedList<>();
        System.out.println(((Math.random() * (1049 - 0)) + 1049));
    }
}

