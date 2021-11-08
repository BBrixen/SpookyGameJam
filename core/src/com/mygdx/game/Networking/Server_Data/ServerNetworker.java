package com.mygdx.game.Networking.Server_Data;


import com.mygdx.game.Server_Game.GameData;
import com.mygdx.game.Server_Game.Player;
import com.mygdx.game.Server_Game.ServerGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerNetworker {

    private ServerSocket serverSocket;
    private HashMap<Socket, Player> sockets;
    public boolean isSending, alreadySending;
    private ServerGame game;
    private int pID = 0;
    
    public ServerNetworker(int number_of_clients) throws IOException{
        sockets = new HashMap<>();
        isSending = false;
        this.game = new ServerGame(this, number_of_clients, true);

        System.out.println("Starting Server");
        serverSocket = new ServerSocket(7777);
        System.out.println("Server Started");

        for (int i = 0; i < number_of_clients; i++) {
            clientJoins();
        }
    }

    //obj stuff
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public HashMap<Socket, Player> getSockets() {
        return sockets;
    }
    public void setSockets(HashMap<Socket, Player> sockets) {
        this.sockets = sockets;
    }

    public void clientJoins() throws IOException {
        Socket socket;

        socket = serverSocket.accept();
        System.out.println("Client Connected, address: " + socket.getInetAddress());

        //this is neccessary to establish the ObjectInputStream on client side
        //it does nothing but without it everything breaks, idk why
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        // add player to game after everything is established
        Player p = new Player(pID);
        sockets.put(socket, p);
        pID ++;
        this.game.playerJoins(p);
    }

    public void sendData(NetworkData data, Socket recipient) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(recipient.getOutputStream());
        out.reset(); // cleaning before

        // sending data
        out.writeObject(data);

        // cleaning afterwards
        out.flush();
    }

    public NetworkData receiveData(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (NetworkData) in.readObject();
    }

    //sends the data to all the clients except the one that send the data
    public void sendDataToOtherClients(NetworkData data) throws IOException {
        for (Socket s: this.sockets.keySet()) {
            this.sendData(data, s);
        }
    }

    public void continuallyRecieveData() {
        for (Socket client : sockets.keySet()) {
            continuallyRecieveDataFromClient(client);
        }
    }

    public void continuallySendData() {
        if (alreadySending) return;
        alreadySending = true;
        final ServerGame game = this.game;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                isSending = true;
                try {
                    sendDataToOtherClients(new NetworkData(game.gameData));
                } catch (IOException e) { e.printStackTrace(); }
                isSending = false;
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void continuallyRecieveDataFromClient(Socket client_socket) {
        final Socket finalClientSocket = client_socket;
        //thread the receiving data code so that it can run at the same time as the send data
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("server waiting for data");
                        NetworkData data = receiveData(finalClientSocket);
                        handleIncomingData(data);

                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        System.out.println(finalClientSocket + " disconnected");
                        sockets.remove(finalClientSocket);

                        // leave message
                        // remove the player from the gamedata
                        NetworkData message = new NetworkData(new GameData(0));

                        return;
                    }
                }
            }
        });
        thread.start();
    }

    public void handleIncomingData(NetworkData data) throws InterruptedException {
        if (isSending) {
            Thread.sleep(20);
        }
        this.game.gameData = data.getGameData();
        System.out.println("GAME UPDATED WITH NEW DATA: " + game.gameData.players.get(0).getSpeedX());
        // this will just call game functions, and the game will call
        // addToQueueAndSend when it is finished and ready to update the players
    }
}


