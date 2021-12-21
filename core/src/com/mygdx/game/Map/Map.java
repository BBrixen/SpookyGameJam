package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.RenderingEntities.Textures;
import com.mygdx.game.Map.Tiles.*;
import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.lang.reflect.Field;
import java.util.*;

public class Map {

    public ArrayList<ArrayList<Tile>> SML;
    private final Random random;
    public static final int size = 500;
    private final boolean isServer;

    // currently we are storing everything in memory
    // if things get big we might have to make functions to look them up instead of memory
    private final HashMap<String, Texture> typeToTexture;
    private final HashMap<Character, String> charToType;
    private final List<String> itemTypes, classTypes;

    public Map(long seed, boolean server) {
        // before we create the map we need to make hashmaps of types to textures
        isServer = server;
        if (server) Textures.loadTextures();

        typeToTexture = new HashMap<>();
        charToType = new HashMap<>();
        itemTypes = new ArrayList<>();
        classTypes = new ArrayList<>();
        initMaps(typeToTexture, charToType, itemTypes, classTypes);

        //makes the map size by size char wide
        SML = new ArrayList<>();
        random = new Random(seed);

        for (int i = 0; i < size; i++) {
            ArrayList<Tile> eachLineList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                eachLineList.add(new DefaultTile("grass", i, j, server, typeToTexture));
            }
            SML.add(eachLineList);
        }
        virus(10, 1f,0.01f, "dirt");
        virus(50, 1f,0.01f,"forest");
        virus(100, 1f,0.1f,"cobble");
        virus(50, 1f, 0.2f, "boulder");

        generatePremade("../src/com/mygdx/game/Map/PremadeStructures/maze.txt");
        generatePremade("../src/com/mygdx/game/Map/PremadeStructures/town.txt");
    }

    /**
     * This function modifies the two hashmaps given so we can use constant lookup time to get the correct map texture
     * this reads the lookup files and creates mappings between the following:
     * letter - type
     * type - texture
     *
     * for type - texture we need to use fields to access the static declared fields of Texutures
     * essentially, if we pass "wood" as a line in the file, we should get the value of Textures.wood
     *
     * examples of text file inputs:
     * letter - type:
     * G grass
     * D dirt
     * i item
     * I item
     *
     * type - texture:
     * grass
     * dirt
     * cobble
     * manmadeCobble
     */
    private void initMaps(HashMap<String, Texture> typeToTexture, HashMap<Character, String> charToType,
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

    public static int playerYToMapRow(float pos) {
        return playerPositionToMapIndex(pos);
    }
    public static int playerXToMapCol(float pos) {
        return playerPositionToMapIndex(pos+24);
    }

    public static int playerPositionToMapIndex(float pos) {
        return (int) ( (pos / 64) + size / 2 );
    }

    public static float mapIndexToPlayerPosition(int index) {
        return (index - size/2f) * 64;
    }

    public void virus(int quantity, float spreadRate, float decayRate, String infectionType) {
        for (int i = 0; i < quantity; i++) {
            LinkedList<Pair<Integer, Integer>> theCurrentQueue = new LinkedList<>();
            HashSet<Pair<Integer, Integer>> usedSpaces = new HashSet<>();

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

                SML.get(y).set(x, determineTile(infectionType, y, x));

                // the way this is currently set up, the forests cannot move in if they are on the edges
                // i think its fine this was
                if (0 >= x || x > size-2 || 0 >= y || y > size-2) continue;
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

    private Tile determineTile(String type, int row, int col) {
        // takes type and returns a new tile object with needed type
        if (type.equals("boulder") || type.equals("B")) return new BoulderTile(type, row, col, isServer);

        if (type.startsWith("item")) {
            int index = (int) (random.nextFloat() * itemTypes.size());
            String itemType = itemTypes.get(index);
            return new ItemTile(itemType, row, col, isServer, typeToTexture);
        }

        if (type.startsWith("Item")) {
            int index = (int) (random.nextFloat() * classTypes.size());
            String itemType = classTypes.get(index);
            return new ItemTile(itemType, row, col, isServer, typeToTexture);
        }

        if (type.equals("forest")) {
            float treeTypeRandomizer = random.nextFloat();
            if (treeTypeRandomizer > 0.5) type = "tree2";
            else if (treeTypeRandomizer > 0.25) type = "tree1";
            else type = "tree3";

            boolean treeFlipped = random.nextFloat() > 0.5;
            return new TreeTile(type, row, col, treeFlipped, isServer, typeToTexture);
        }

        return new DefaultTile(type, row, col, isServer, typeToTexture);
    }

    private void generatePremade (String path) {
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

        //gets some random coords to start the maze at
        int startingX = Math.round(random.nextFloat() * (size));
        int startingY = Math.round(random.nextFloat() * (size));
        startingY = 250;
        startingX = 250;

        //loops until it finds a valid spot for the premade
        while (startingX-premadeList.get(1).length < 0 || premadeList.get(1).length+startingX > size
                || startingY-premadeList.size() < 0 || premadeList.size()+startingY > size) {
            startingX = Math.round(random.nextFloat() * (size));
            startingY = Math.round(random.nextFloat() * (size));
        }

        int yMult = 1;
        if (random.nextFloat() > 0.5) yMult = -1;
        int xMult = 1;
        if (random.nextFloat() > 0.5) xMult = -1;


        //decides how to flip the maze to give it a more random feel
        for (int i = 0; i < premadeList.size(); i++) {
            int y = (i * yMult) + startingY;
            for (int j = 0; j < premadeList.get(i).length; j++) {
                int x = (j * xMult) + startingX;
                char m = premadeList.get(i)[j];
                Tile tile;
                tile = determineTile(charToType.get(m), y, x);
                SML.get((i * yMult) + startingY).set((j * xMult) + startingX, tile);
            }
        }
    }
}
