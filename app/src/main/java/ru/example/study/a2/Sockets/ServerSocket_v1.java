package ru.example.study.a2.Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ru.example.study.a2.Interfaces.onServerSocket_v1Listener;


public class ServerSocket_v1 {

    private int port;
    public int getPort(){return port;}

    public ArrayList <ServerThread> connections;
    private ArrayList <Socket> clientsToConnect;
    private ServerSocket SocketListener;
    private  Accepting Accept;
    EventsHandler eventhandler;

    // контрол обработчика событий
    private ArrayList <onServerSocket_v1Listener> eventsListener = new ArrayList<onServerSocket_v1Listener>();

    public void addListener(onServerSocket_v1Listener listener)
    {
        eventsListener.add(listener);
    }
    public void remove(onServerSocket_v1Listener listener)
    {
        eventsListener.remove(listener);
    }

    private void onRecive(ServerThread socket)
    {
        for(onServerSocket_v1Listener listener :eventsListener)
        {
            listener.onRecive(socket);
        }
    }

    private void onConnected(ServerThread socket)
    {
        for(onServerSocket_v1Listener listener :eventsListener)
        {
            listener.onConnected(socket);
        }
    }

    private void onConnect(SocketError cancel, ServerThread socket)
    {
        for(onServerSocket_v1Listener listener :eventsListener)
        {
            listener.onConnect(cancel,socket);
        }
    }

    private void onDisconnected(ServerThread socket)
    {
        for(onServerSocket_v1Listener listener :eventsListener)
        {
            listener.onDisconnected(socket);
        }
    }


    // конец контрола
    public ServerSocket_v1(int port)
    {
        this.port=port;

        connections= new ArrayList <ServerThread>();
        clientsToConnect= new ArrayList<Socket>();
    }


    //
    public void open()
    {
        try {
            SocketListener = new ServerSocket(port);

            Accept =new Accepting();
            Accept.start();

            eventhandler = new EventsHandler();
            eventhandler.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close(){
        Accept.stopThread=false;
        Accept.interrupt();
        eventhandler.stopThread=true;
        for(int i=0;i<connections.size();i++){
            connections.get(i).interrupt();
        }
        connections=null;
        eventhandler=null;
        Accept=null;
    }

    public void sendTextById(int id,String message){
        if(id < connections.size()){
            connections.get(id).sendText(message);
        }
    }

    public void sendTextAll(String message){
        for(int i=0;i<connections.size();i++){
            connections.get(i).sendText(message);
        }

    }

    public void sendTextAll(String []message){
        for(int i=0;i<connections.size();i++){
            connections.get(i).sendText(message[i]);
        }

    }

    protected class EventsHandler extends Thread
    {
        public boolean stopThread=false;
        @Override
        public void run() {
            super.run();

            while(!stopThread)
            {
                try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }

                if(clientsToConnect.size()>0)
                {
                    for(Socket soc:clientsToConnect){

                        ServerThread st =new ServerThread(soc);
                        SocketError cancel= new SocketError();

                        onConnect(cancel, st);
                        if(cancel.cancel){
                            st.sendText("SOCKET_REJECTED");
                            continue;
                        }
                        connections.add(st);
                        onConnected(connections.get(connections.size()-1));
                    }
                    clientsToConnect.clear();
                }

                int [] arrayOfDisconnected = new int[connections.size()];
                int countDisconnected=0;
                for(int i =0;i<connections.size();i++)
                {
                    boolean alive=connections.get(i).isAlive();
                    if(!alive)
                    {
                        arrayOfDisconnected[countDisconnected]=i;
                        countDisconnected++;
                    }
                }

                if(countDisconnected>0)
                {
                    for(int index=0;index<countDisconnected;index++)
                    {
                        onDisconnected(connections.get(arrayOfDisconnected[index]));
                        connections.remove(arrayOfDisconnected[index]);
                    }
                }

                for(ServerThread Socket:connections)
                {
                    if(Socket.isAvailableBytes())
                        onRecive(Socket);
                }

            }
        }
    }

    protected class Accepting extends Thread
    {
        public boolean stopThread=false;
        @Override
        public void run() {
            super.run();
            while(!stopThread)
            {
                try {

                    clientsToConnect.add(SocketListener.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

    }



}


