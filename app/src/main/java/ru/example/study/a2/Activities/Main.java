package ru.example.study.a2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import ru.example.study.a2.Entities.Player;
import ru.example.study.a2.GameLogic.Commands;
import ru.example.study.a2.Interfaces.onClientSocket_v1Listener;
import ru.example.study.a2.Interfaces.onGameDraw;
import ru.example.study.a2.Interfaces.onServerSocket_v1Listener;
import ru.example.study.a2.GameLogic.Map;
import ru.example.study.a2.R;
import ru.example.study.a2.Sockets.ClientSocket_v1;
import ru.example.study.a2.Sockets.ServerSocket_v1;
import ru.example.study.a2.Sockets.ServerThread;
import ru.example.study.a2.Sockets.SocketError;

public class Main extends AppCompatActivity {
    TextView resultView ,myIP ,editIP;

    final String LOG_TAG = "myLogs";
    //public static MyThreadClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        resultView =findViewById(R.id.resultView);
        myIP=findViewById(R.id.myIP);
        editIP=findViewById(R.id.editIP);




    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(client!=null)
//            client.stop=true;
//        if(server!=null)
//            server.stop=true;
//        map=null;
//        System.exit(0);
//    }

//
//    public void onClick(View v){
//        //try{
//
//       /* }
//        catch(Exception e)
//        {
//            Log.d("myLogs",e.getMessage());
//        }*/
//
//    }
//    public void onClickMainMenu(View v){
//
//        //try{
//
//       /* }
//        catch(Exception e)
//        {
//            Log.d("myLogs",e.getMessage());
//        }*/
//
//    }

//    public void onClickAccept(View v){
//        client.setCommand("sendMessage","OFFER_ACCEPT:ACCEPT");
//
//    }
//
//    public void onClickOpenMap(View v){

//
//    }
//    public void onClickReject(View v){
//        client.setCommand("sendMessage","OFFER_ACCEPT:REJECT");
//
//    }


//    public void onClickConnect(View v) {
//
//        client = new MyThreadClient(editIP.getText().toString(),25565);
//        client.start();
//
//    }
//
//    public void onClickdisconnect(View v) {
//        client.setCommand("sendMessage","SOCKET_EXIT");
//    }
//    public void onClickServer(View v) {
//        server = new MyThreadServer(25565);
//        try {
//            while(!server.isOpened) {
//
//                Thread.sleep(50);
//            }
//        } catch (InterruptedException e) { e.printStackTrace(); }
//
//        myIP.setText("IP этой игры:"+getIpAddress());
//    }
//
//    public static String getIpAddress() {
//        try {
//            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//                NetworkInterface intf = (NetworkInterface)en.nextElement();
//                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
//                        String ipAddress=inetAddress.getHostAddress().toString();
//                        Log.e("IP address",""+ipAddress);
//                        return ipAddress;
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//            Log.e("Socket exception", ex.toString());
//        }
//        return null;
//    }
//
//
//    public void onClickStep(View v){
//        client.setCommand("sendMessage","STEP:ACCEPT");
//
//    }


//    // потоки для клиента и сервера-----------------------------------------------------------------
//    class MyThreadClient extends Thread implements onClientSocket_v1Listener {
//
//        private ArrayList<onGameDraw> eventsDraw = new ArrayList<>();
//        public void addLitener(onGameDraw ogd){eventsDraw.add(ogd);}
//        public void remove(onGameDraw ogd){
//            eventsDraw.remove(ogd);
//        }
//
//        ClientSocket_v1 client;
//        public boolean stop=false;
//        private String ip;
//        private int port;
//
//        private String command="";
//        private String parameters;
//        private Commands cmds;
//        private boolean commnadChanged;
//        private boolean myStep=false;
//        private String temporalyPlayername;
//        private int idPlayer;
//
//        private boolean getMyStep(){
//            if(myStep){
//                myStep=false;
//                return true;
//            }
//            else
//                return false;
//        }
//
//        private String getCommand(){
//            String cmd=command;
//            command="";
//            return cmd;
//        }
//
//        public void setCommand(String cmd,String param){
//            command=cmd;
//            parameters=param;
//            commnadChanged=true;
//        }
//        MyThreadClient(String Tip,int Tport){
//            ip=Tip;
//            port=Tport;
//            commnadChanged=false;
//            cmds= new Commands("");
//        }
//
//        public void setPlayerData(String name){
//            //player= new Player(0,"");
//            temporalyPlayername=name;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            client = new ClientSocket_v1(ip,port);
//            client.open();
//            client.addListener(this);
//
//            while(!stop){
//                try {
//                    Thread.sleep(100);
//                    if(commnadChanged){
//                        commnadChanged=false;
//                        switch(command){
//                            case "sendMessage":
//                                if(!parameters.equals("STEP"))
//                                    client.sendText(parameters);
//                                else if(getMyStep()){
//                                    client.sendText(parameters);
//                                }
//                                break;
//                            case "disconnect":
//
//                                client.close();
//                                break;
//                            case "data":
//                        }
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            client.close();
//        }
//
//        //Начало: события для рисования
//        private void stepPlayer(){
//            for(onGameDraw ogd :eventsDraw)
//            {
//                ogd.stepPlayer();
//            }
//        }
//
//        private void stepOtherPlayer(String[] message){
//            for(onGameDraw ogd :eventsDraw)
//            {
//                ogd.stepOtherPlayer(message);
//            }
//        }
//
//
//        //Конец: события для рисования
//
//        @Override
//        public void onRecive(String Text) {
//            Log.d(LOG_TAG,Text);
//            cmds= new Commands(Text);
//
//            switch (cmds.getHead()){
//                case "STEP":
//                    switch (cmds.getFirst()){
//                        case "CAN_STEP":
//                            myStep = true;
//                            stepPlayer();
//                            break;
//
//                        case "OTHER_PLAYER_MOVE":
//                            stepOtherPlayer(cmds.getParams());
//                            break;
//                    }
//                    break;
//
//                case "PLAYER_DATA_NAME":
//                    String[] param=cmds.getParams();
//                    client.sendText("PLAYER_DATA_NAME:"+param[0]+":"+temporalyPlayername);
//                    idPlayer=Integer.parseInt(param[0]);
//                    break;
//            }
//
//
//            // resultView.setText(Text);
//        }
//
//        @Override
//        public void onError(String Text) {
//            Log.d(LOG_TAG,Text);
//
//        }
//
//        //Начало Процедур для рисования
//
//
//
//
//        //Конец процедур для рисования
//    }
//
//    class MyThreadServer extends Thread implements onServerSocket_v1Listener {
//        ServerSocket_v1 server;
//        private int port;
//        public boolean stop=false;
//        public boolean isOpened=false;
//        private String command="";
//        MyThreadServer(int Tport){
//            port=Tport;
//            start();
//        }
//
//        private String getCommand(){
//            String cmd=command;
//            command="";
//            return cmd;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            server = new ServerSocket_v1(25565);
//            server.open();
//            server.addListener(this);
//            isOpened=true;
//            map = new Map(2);
//            while(!stop){
//                try {
//                    Thread.sleep(100);
//                    map.mainGameThread(server,getCommand());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            server.close();
//        }
//
//
//        @Override
//        public void onRecive(ServerThread socket) {
//            command=socket.getReciveText();
//            Log.d(LOG_TAG,"ReciveText:"+socket.getReciveText());
//        }
//
//        @Override
//        public void onDisconnected(ServerThread socket) {
//            Log.d(LOG_TAG,"Клиенте отключился:"+socket.getIP());
//            map.disconnectedPlayer(socket.getIP());
//        }
//
//        @Override
//        public void onConnected(ServerThread socket) {
//            Log.d(LOG_TAG,"Клиенте подключился:"+socket.getIP());
//            // map.connectedPlayer(socket.getIP());
//        }
//
//        @Override
//        public void onConnect(SocketError cancel, ServerThread socket) {
//            if(map.connectedPlayer(socket.getIP()))
//                cancel.cancel=true;
//        }
//    }
//    //----------------------------------------------------------------------------------------------
//


}

