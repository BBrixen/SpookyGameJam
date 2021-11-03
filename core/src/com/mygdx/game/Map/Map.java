package com.mygdx.game.Map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public static void SMLMaker(String[] args) {
        List<List<Character>> SML=new ArrayList<>();
        for (int i = 0; i <1050 ; i++) {
            List<Character> eachLineList= new ArrayList<Character>();
            for (int j = 0; j < 1050; j++) {
                eachLineList.add('a');
            }
            SML.add(eachLineList);
        }
    }
}
