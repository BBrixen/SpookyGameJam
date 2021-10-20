package com.mygdx.game.Networking.Server_Data;


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

public class ServerNetworker {

    private ServerSocket serverSocket;
    private HashMap<Socket, Player> sockets;
    private Queue<NetworkData> queue;
    private boolean isSending;
    private ServerGame game;
    private int pID = 0;
    
    public ServerNetworker(int number_of_clients) throws IOException{
        sockets = new HashMap<>();
        queue = new LinkedList<>();
        isSending = false;
        this.game = new ServerGame(this, number_of_clients);

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

//        sending connected message to socket
//        sendData(new NetworkData(null, null), socket);

        // add player to game after everything is established
        Player p = new Player(pID);
        sockets.put(socket, p);
        pID ++;
        this.game.playerJoins(p);
    }

    public void sendData(NetworkData data, Socket recipient) throws IOException {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObjectOutputStream out = new ObjectOutputStream(recipient.getOutputStream());
        out.reset(); // cleaning before

        // sending data
        out.writeObject(data);

        // cleaning afterwards
        out.flush();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println(finalClientSocket + " disconnected");
                        sockets.remove(finalClientSocket);

                        // leave message
                        // remove the player from the gamedata
                        NetworkData message = new NetworkData(null);
                        addToQueueAndSend(message);

                        return;
                    }
                }
            }
        });
        thread.start();
    }

    public void startSendingQueuedData() {
        this.isSending = true;

        while (!this.queue.isEmpty()) {
            try {
                NetworkData data = this.queue.remove();
                sendDataToOtherClients(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.isSending = false;
    }

    public void handleIncomingData(NetworkData data) {
        System.out.println(data);
        System.out.println("the number of clients is " + this.sockets.size());
        // this will just call game functions, and the game will call
        // addToQueueAndSend when it is finished and ready to update the players
        addToQueueAndSend(data);
    }

    public void addToQueueAndSend(NetworkData data) {
        queue.add(data);

        if (! isSending){
            startSendingQueuedData();
        }
    }
}


