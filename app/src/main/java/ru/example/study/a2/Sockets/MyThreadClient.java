package ru.example.study.a2.Sockets;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;

import ru.example.study.a2.Activities.GameCanvas;
import ru.example.study.a2.Activities.Main;
import ru.example.study.a2.Activities.MainMenu;
import ru.example.study.a2.Activities.Settings;
import ru.example.study.a2.GameLogic.Commands;
import ru.example.study.a2.Interfaces.onClientSocket_v1Listener;
import ru.example.study.a2.Interfaces.onGameDraw;

public class MyThreadClient extends Thread implements onClientSocket_v1Listener {

    final String LOG_TAG = "myLogs";

    private ArrayList<onGameDraw> eventsDraw = new ArrayList<>();
    private ArrayList<onClientSocket_v1Listener> ClientSocket_v1Listener= new ArrayList<>();
    public void addLitener(onGameDraw ogd){eventsDraw.add(ogd);}
    public void remove(onGameDraw ogd){
        eventsDraw.remove(ogd);
    }

    public static ClientSocket_v1 client;
    public boolean stop=false;
    private String ip;
    private int port;

    private String command="";
    private String parameters;
    private Commands cmds;
    private boolean commnadChanged;
    private boolean myStep=false;
    private String temporalyPlayername;
    private int idPlayer;

    private boolean getMyStep(){
        if(myStep){
            myStep=false;
            return true;
        }
        else
            return false;
    }

    private String getCommand(){
        String cmd=command;
        command="";
        return cmd;
    }

    public void setCommand(String cmd,String param){
        command=cmd;
        parameters=param;
        commnadChanged=true;
    }
    public MyThreadClient(String Tip,int Tport){
        ip=Tip;
        port=Tport;
        commnadChanged=false;
        cmds= new Commands("");temporalyPlayername=MainMenu.PlayerName;
    }
    public void addClientSocket_v1Listener(onClientSocket_v1Listener onck){
        ClientSocket_v1Listener.add(onck);
    }

    public void setPlayerData(String name){
        //player= new Player(0,"");
        temporalyPlayername=name;
    }

    @Override
    public void run() {
        super.run();
        client = new ClientSocket_v1(ip,port);
        client.open();
        client.addListener(this);
        for (onClientSocket_v1Listener onck:ClientSocket_v1Listener){
            client.addListener(onck);
        }

        while(!stop){
            try {
                Thread.sleep(100);
                if(commnadChanged){
                    commnadChanged=false;
                    switch(command){
                        case "sendMessage":
                            if(!parameters.equals("STEP"))
                                client.sendText(parameters);
                            else if(getMyStep()){
                                client.sendText(parameters);
                            }
                            break;
                        case "disconnect":

                            client.close();
                            break;
                        case "data":
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        client.close();
        Log.d(LOG_TAG,"Сервер отключен");
    }

    //Начало: события для рисования
    private void stepPlayer(){
        for(onGameDraw ogd :eventsDraw)
        {
            ogd.stepPlayer();
        }
    }

    private void stepOtherPlayer(String[] message){
        for(onGameDraw ogd :eventsDraw)
        {
            ogd.stepOtherPlayer(message);
        }
    }



    //Конец: события для рисования

    @Override
    public void onRecive(String Text) {
        Log.d(LOG_TAG,Text);
        cmds= new Commands(Text);

        switch (cmds.getHead()){
            case "STEP":
                switch (cmds.getFirst()){
                    case "CAN_STEP":
                        myStep = true;
                        stepPlayer();
                        break;

                    case "OTHER_PLAYER_MOVE":
                        stepOtherPlayer(cmds.getParams());
                        break;
                }
                break;

            case "PLAYER_DATA_NAME":

                String[] param=cmds.getParams();
                client.sendText("PLAYER_DATA_NAME:"+param[0]+":"+temporalyPlayername+":");
                idPlayer=Integer.parseInt(param[0]);
                break;

            case "GAME":
                switch(cmds.getFirst()){
                    case "GAME_WAS_BEGAN":

                        break;

                    case "UPDATE":
                        //canvasUpdate();
                        break;

                }
                break;
        }


        // resultView.setText(Text);
    }

    @Override
    public void onError(String Text) {
        Log.d(LOG_TAG,Text);

    }

    //Начало Процедур для рисования




    //Конец процедур для рисования
}