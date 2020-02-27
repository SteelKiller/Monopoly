package ru.example.study.a2.Sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;



public class ServerThread extends Thread{

    private BufferedReader in;
    private BufferedWriter out;
    private String message="";
    private Socket client;
    private boolean availableBytes;
    private boolean brockenPie=false;

    public String getIP(){
        return client.getInetAddress().toString();
    }

    public String getReciveText(){
        return message;
    }
    public void sendText(String outMessage){
        try {
            out.write(outMessage+"\n");
            out.flush();
        } catch (IOException e) {
            if(e.getMessage().equals("Broken pipe"))
                brockenPie=true;
        }
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

    public ServerThread(Socket Tclient){
        client=Tclient;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {e.printStackTrace();}
        availableBytes=false;
        start();
    }

    @Override
    public void run() {

        String tmp=null;
        while(!brockenPie){
            try {
                tmp= in.readLine();
                if(tmp!=null)
                {
                    if(tmp.equals("SOCKET_EXIT")){
                        break;
                    }
                    message=tmp;
                    availableBytes=true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }
}


