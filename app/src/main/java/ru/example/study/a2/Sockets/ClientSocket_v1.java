package ru.example.study.a2.Sockets;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ru.example.study.a2.Interfaces.onClientSocket_v1Listener;

public class ClientSocket_v1 {

    final String LOG_TAG = "myLogs";
    private String ip;
    private int port;
    private BufferedReader in;
    private BufferedWriter out;
    private String message="";
    private Socket client;
    private boolean availableBytes;
    private EventsHandler eventhandler;


    private ArrayList <onClientSocket_v1Listener> eventsListener = new ArrayList<>();
    //обработчик
    public void addListener(onClientSocket_v1Listener listener){
        eventsListener.add(listener);
    }
    public void remove(onClientSocket_v1Listener listener){
        eventsListener.remove(listener);
    }
    private void onRecive(String text)
    {
        for(onClientSocket_v1Listener listener :eventsListener)
        {
            listener.onRecive(text);
        }
    }

    private void onError(String text)
    {
        for(onClientSocket_v1Listener listener :eventsListener)
        {
            listener.onError(text);
        }
    }

    // обработчик


    public ClientSocket_v1(String Tip,int Tport){
        ip= Tip;
        port=Tport;
    }

    public void open(){
        try {
            client=new Socket(ip,port);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (UnknownHostException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}

        eventhandler = new EventsHandler();
        eventhandler.start();
    }



    public void sendText(String Tmessage){
        try {
            out.write(Tmessage+"\n");
            out.flush();
        } catch (IOException e) {e.printStackTrace();}
    }

    public String getReciveText(){
        return message;
    }

    public boolean isAvailableBytes(){
        if(availableBytes)
        {
            availableBytes=false;
            return true;
        }
        else
            return false;
    }

    public void close(){
        try {
            if (!client.isClosed()){
                out.write("SOCKET_EXIT\n");
                out.flush();
                client.close();
                eventhandler.interrupt();
                eventhandler=null;
            }
        } catch (IOException e) {e.printStackTrace();}
    }


    protected class EventsHandler extends Thread
    {
        @Override
        public void run() {
            super.run();
            String tmp;
            while(true)
            {
                try {
                    tmp= in.readLine();
                    if(tmp!=null)
                    {
                        if(tmp.equals("SOCKET_EXIT"))
                            break;
                        if(tmp.equals("SOCKET_REJECTED")) {
                            onError("Connection was rejected");
                            break;
                        }
                        message=tmp;
                        availableBytes=true;
                    }
                    if(isAvailableBytes())
                        onRecive(message);
                } catch (IOException e) {


                    Log.d(LOG_TAG,"blablabla:"+e.toString());
                    break;

                }

            }
        }
    }


}



