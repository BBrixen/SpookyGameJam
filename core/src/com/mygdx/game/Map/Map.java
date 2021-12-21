package com.mygdx.game.Map;

import com.mygdx.game.Entities.RenderingEntities.Textures;
import com.mygdx.game.Map.Tiles.*;
import java.util.*;

public class Map {

    public ArrayList<ArrayList<Tile>> SML;
    public final Random random;
    public static final int size = 500;
    public final boolean isServer;

    public Map(long seed, boolean server) {
        // before we create the map we need to make hashmaps of types to textures
        isServer = server;
        if (server) Textures.loadTextures();

        //makes the map size by size char wide
        SML = new ArrayList<>();
        random = new Random(seed);

        MapGeneration.generateMap(this);
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

}
