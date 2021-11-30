package com.mygdx.game.Server_Game;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.game.Entities.RenderingEntities.Textures;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;
import java.io.IOException;

public class ServerDisplay extends ApplicationAdapter {

    @Override
    public void create () {
        Textures.loadTextures();
        // this can also create a display, but we are not doing that yet
        int players = 2;
        try {
            new ServerNetworker(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render () {

    }

    @Override
    public void dispose () {

    }

}
