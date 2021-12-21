package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Map.Tiles.*;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;

public class MapGeneration {

    // currently we are storing everything in memory
    // if things get big we might have to make functions to look them up instead of memory
    private static HashMap<String, Texture> typeToTexture;
    private static HashMap<Character, String> charToType;
    private static List<String> itemTypes, classTypes;

    public static void generateMap(Map map) {
        typeToTexture = new HashMap<>();
        charToType = new HashMap<>();
        itemTypes = new ArrayList<>();
        classTypes = new ArrayList<>();
        initMaps(typeToTexture, charToType, itemTypes, classTypes);

        // filling with empty tiles
        for (int i = 0; i < Map.size; i++) {
            ArrayList<Tile> eachLineList = new ArrayList<>();
            for (int j = 0; j < Map.size; j++) {
                eachLineList.add(new DefaultTile("grass", i, j, map.isServer, typeToTexture));
            }
            map.SML.add(eachLineList);
        }

        // generating the map
        virus(map, 10, 1f,0.01f, "dirt");
        virus(map, 50, 1f,0.01f,"forest");
        virus(map, 100, 1f,0.1f,"cobble");
        virus(map, 50, 1f, 0.2f, "boulder");

        generatePremade(map, "../src/com/mygdx/game/Map/PremadeStructures/maze.txt");
        generatePremade(map, "../src/com/mygdx/game/Map/PremadeStructures/town.txt");
    }

    private static void initMaps(HashMap<String, Texture> typeToTexture, HashMap<Character, String> charToType,
                          List<String> itemTypes, List<String> classTypes) {
        try {
            // getting static class as class object
            Class<?> textureClass = Class.forName("com.mygdx.game.Entities.RenderingEntities.Textures");
            File textureLookupFile = new File("../src/com/mygdx/game/Map/texture_lookup");
            Scanner textureLookupScanner = new Scanner(textureLookupFile);

            while (textureLookupScanner.hasNextLine()) {
                // on each line, get the name
                String textureName = textureLookupScanner.nextLine();
                Field f = textureClass.getDeclaredField(textureName); // get the static variable
                Texture value = (Texture) f.get(textureClass); // get the value from the Texures class
                typeToTexture.put(textureName, value); // add to hashmap

                if (textureName.startsWith("item")) {
                    itemTypes.add(textureName); // all the item types are stored in this file already,
                    // so parse them into their own list
                } else if (textureName.startsWith("Item")) {
                    classTypes.add(textureName);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            File typeLookupFile = new File("../src/com/mygdx/game/Map/type_lookup");
            Scanner typeLookupScanner = new Scanner(typeLookupFile);
            while (typeLookupScanner.hasNextLine()) {
                String[] line = typeLookupScanner.nextLine().split(" "); // split by spaces
                char c = line[0].toCharArray()[0]; // get the character
                charToType.put(c, line[1]); // map character to type
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void virus(Map map, int quantity, float spreadRate, float decayRate, String infectionType) {
        for (int i = 0; i < quantity; i++) {
            LinkedList<Pair<Integer, Integer>> theCurrentQueue = new LinkedList<>();
            HashSet<Pair<Integer, Integer>> usedSpaces = new HashSet<>();

            //gets map.random start point
            int startingX = Math.round(map.random.nextFloat() * (Map.size-2));
            int startingY = Math.round(map.random.nextFloat() * (Map.size-2));
            theCurrentQueue.add(new Pair<>(startingX, startingY));

            while (!theCurrentQueue.isEmpty()) {
                Pair<Integer, Integer> currentCoords = theCurrentQueue.remove();
                int x = currentCoords.getKey();
                int y = currentCoords.getValue();

                //checks queue item to see if it becomes a forest, then adds its neighbours
                float distX = Math.abs(startingX - x);
                float distY = Math.abs(startingY - y);
                float threshold = spreadRate - decayRate * (distX + distY);
                if (map.random.nextFloat() >= threshold) continue; // guard clause to not make it a forest

                map.SML.get(y).set(x, determineTile(map, infectionType, y, x));

                // the way this is currently set up, the forests cannot move in if they are on the edges
                // i think its fine this was
                if (0 >= x || x > Map.size-2 || 0 >= y || y > Map.size-2) continue;
                Pair<Integer, Integer> p1 = new Pair<>(x + 1, y);
                Pair<Integer, Integer> p2 = new Pair<>(x, y + 1);
                Pair<Integer, Integer> p3 = new Pair<>(x - 1, y);
                Pair<Integer, Integer> p4 = new Pair<>(x, y - 1);

                if (!usedSpaces.contains(p1)) {
                    theCurrentQueue.add(p1);
                    usedSpaces.add(p1);
                }

                if (!usedSpaces.contains(p2)) {
                    theCurrentQueue.add(p2);
                    usedSpaces.add(p2);
                }

                if (!usedSpaces.contains(p3)) {
                    theCurrentQueue.add(p3);
                    usedSpaces.add(p3);
                }

                if (!usedSpaces.contains(p4)) {
                    theCurrentQueue.add(p4);
                    usedSpaces.add(p4);
                }
            }
        }
    }

    private static Tile determineTile(Map map, String type, int row, int col) {
        // takes type and returns a new tile object with needed type
        if (type.equals("boulder") || type.equals("B")) return new BoulderTile(type, row, col, map.isServer);

        if (type.startsWith("item")) {
            int index = (int) (map.random.nextFloat() * itemTypes.size());
            String itemType = itemTypes.get(index);
            return new ItemTile(itemType, row, col, map.isServer, typeToTexture);
        }

        if (type.startsWith("Item")) {
            int index = (int) (map.random.nextFloat() * classTypes.size());
            String itemType = classTypes.get(index);
            return new ItemTile(itemType, row, col, map.isServer, typeToTexture);
        }

        if (type.equals("forest")) {
            float treeTypeRandomizer = map.random.nextFloat();
            if (treeTypeRandomizer > 0.5) type = "tree2";
            else if (treeTypeRandomizer > 0.25) type = "tree1";
            else type = "tree3";

            boolean treeFlipped = map.random.nextFloat() > 0.5;
            return new TreeTile(type, row, col, treeFlipped, map.isServer, typeToTexture);
        }

        return new DefaultTile(type, row, col, map.isServer, typeToTexture);
    }

    private static void generatePremade (Map map, String path) {
        ArrayList<char[]> premadeList = new ArrayList<>(); // MAKES THE PREMADE MEGA LIST

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                premadeList.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        //gets some map.random coords to start the maze at
        int startingX = Math.round(map.random.nextFloat() * (Map.size));
        int startingY = Math.round(map.random.nextFloat() * (Map.size));

        //loops until it finds a valid spot for the premade
        while (startingX-premadeList.get(1).length < 0 || premadeList.get(1).length+startingX > Map.size
                || startingY-premadeList.size() < 0 || premadeList.size()+startingY > Map.size) {
            startingX = Math.round(map.random.nextFloat() * (Map.size));
            startingY = Math.round(map.random.nextFloat() * (Map.size));
        }

        int yMult = 1;
        if (map.random.nextFloat() > 0.5) yMult = -1;
        int xMult = 1;
        if (map.random.nextFloat() > 0.5) xMult = -1;


        //decides how to flip the maze to give it a more map.random feel
        for (int i = 0; i < premadeList.size(); i++) {
            int y = (i * yMult) + startingY;
            for (int j = 0; j < premadeList.get(i).length; j++) {
                int x = (j * xMult) + startingX;
                char m = premadeList.get(i)[j];
                Tile tile;
                tile = determineTile(map, charToType.get(m), y, x);
                map.SML.get((i * yMult) + startingY).set((j * xMult) + startingX, tile);
            }
        }
    }

}
