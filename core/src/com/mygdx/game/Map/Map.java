package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.RenderingEntities.Textures;
import com.mygdx.game.Map.Tiles.*;
import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.*;

public class Map {

    public ArrayList<ArrayList<Tile>> SML;
    private final Random random;
    public static final int size = 500;
    private final boolean isServer;
    private HashMap<String, Texture> typeToTexture;
    private HashMap<Character, String> charToType;

    public Map(long seed, boolean server) {
        // before we create the map we need to make hashmaps of types to textures
        isServer = server;
        if (server) Textures.loadTextures();
        typeToTexture = new HashMap<>();
        charToType = new HashMap<>();
        initMaps(typeToTexture, charToType);

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

        generatePremade("../src/com/mygdx/game/Map/maze.txt");
        generatePremade("../src/com/mygdx/game/Map/town.txt");
    }

    private void initMaps(HashMap<String, Texture> typeToTexture, HashMap<Character, String> charToType) {
        typeToTexture.put("grass", Textures.grass);
        typeToTexture.put("dirt", Textures.dirt);
        typeToTexture.put("cobble", Textures.cobble);
        typeToTexture.put("manmadeCobble", Textures.manmadeCobble);
        typeToTexture.put("wood", Textures.wood);
        typeToTexture.put("tree1", Textures.tree);
        typeToTexture.put("tree2", Textures.tree2);
        typeToTexture.put("tree3", Textures.tree3);

        charToType.put('B', "boulder");
        charToType.put('D', "dirt");
        charToType.put('C', "manmadeCobble");
        charToType.put('G', "grass");
        charToType.put('W', "wood");
        charToType.put('i', "item");
        charToType.put('I', "item");
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
        if (type.equals("item") || type.equals("i")) return new ItemTile(type, row, col);

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
        System.out.println("starting premade on " + path);
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
