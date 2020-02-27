package ru.example.study.a2.Sockets;

import android.util.Log;

import ru.example.study.a2.GameLogic.Map;
import ru.example.study.a2.Interfaces.onServerSocket_v1Listener;

public class MyThreadServer extends Thread implements onServerSocket_v1Listener {

    Map map;
    final String LOG_TAG = "myLogs";
    ServerSocket_v1 server;
    private int port;
    public boolean stop=false;
    public boolean isOpened=false;
    private String command="";
    private boolean commandchanged=false;
    public int playersCount;
    public MyThreadServer(int Tport,int PlayersCount){
        port=Tport;
        start();
        playersCount=PlayersCount;
    }

    private String getCommand(){
        String cmd=command;
        commandchanged=false;
        command="";
        return cmd;
    }
    private boolean isCommandChanged(){
        return commandchanged;
    }
    private void setCommand(String cm){
        command=cm;
        commandchanged=true;
    }


    @Override
    public void run() {
        super.run();
        server = new ServerSocket_v1(25565);
        server.open();
        server.addListener(this);
        isOpened=true;
        map = new Map(playersCount);
        while(!stop){
            try {
                Thread.sleep(100);

                    map.mainGameThread(server,getCommand());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        map=null;
        server.close();
    }


    @Override
    public void onRecive(ServerThread socket) {
        setCommand(socket.getReciveText());
        Log.d(LOG_TAG,"Текст принятый сервером:"+socket.getReciveText());
    }

    @Override
    public void onDisconnected(ServerThread socket) {
        Log.d(LOG_TAG,"Клиенте отключился:"+socket.getIP());
        map.disconnectedPlayer(socket.getIP());
    }

    @Override
    public void onConnected(ServerThread socket) {
        Log.d(LOG_TAG,"Клиенте подключился:"+socket.getIP());
        // map.connectedPlayer(socket.getIP());
    }

    @Override
    public void onConnect(SocketError cancel, ServerThread socket) {
        if(map.connectedPlayer(socket.getIP()))
            cancel.cancel=true;

    }
}
