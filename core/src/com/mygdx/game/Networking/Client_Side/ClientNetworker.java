package com.mygdx.game.Networking.Client_Side;

import com.mygdx.game.Client_Display.ClientGame;
import com.mygdx.game.Networking.Server_Data.NetworkData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientNetworker {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean connected;
    private ClientGame game;

    public ClientNetworker(ClientGame game) throws IOException, ClassNotFoundException {
        System.out.println("Connecting...");
        this.socket = new Socket("localhost", 7777);
        System.out.println("Connected");

        this.in = new ObjectInputStream(socket.getInputStream());
        System.out.println("InputStream Established");

        this.connected = true;
        this.game = game;

        this.continuallyRecieveData();
    }

    public void sendData(NetworkData data) throws IOException {
        //this creates a new output stream for each new time data is being sent
        this.out = new ObjectOutputStream(this.socket.getOutputStream());

        this.out.reset(); // cleaning before

        //this sends the data
        this.out.writeObject(data);
    }

    public NetworkData receiveData() throws IOException, ClassNotFoundException {
        this.in = new ObjectInputStream(this.socket.getInputStream());
        return (NetworkData) this.in.readObject();
    }

    public void continuallyRecieveData() {
        final ClientNetworker self = this;
        //thread the receiving data code so that it can run at the same time as the send data
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        NetworkData data = receiveData();
                        game.updateGameData(data.getGameData());
                        // depending on the data recieved, we may need to update the display, or bring up a pop up menu for the user to interact with
//                        display.update(data);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Disconnected from server. Press enter to quit the program");
                        self.setConnected(false);
                        self.setOut(null);
                        self.setIn(null);
                        return;
                    }
                }
            }
        });
        thread.start();
    }

    // obj stuff
    public Socket getSocket() {
        return this.socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public ObjectInputStream getIn() {
        return this.in;
    }
    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
    public ObjectOutputStream getOut() {
        return this.out;
    }
    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}


