package ru.example.study.a2.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import ru.example.study.a2.Interfaces.onServerSocket_v1Listener;
import ru.example.study.a2.R;
import ru.example.study.a2.Sockets.MyThreadClient;
import ru.example.study.a2.Sockets.MyThreadServer;
import ru.example.study.a2.Sockets.ServerThread;
import ru.example.study.a2.Sockets.SocketError;


public class CreateServer extends Activity {

    Button bStartServer,bBeginGame;
    Switch auto_start;
    SeekBar sPlayersCount;
    TextView tPlayersCountText,ipAdress;
    MyThreadServer server;
    private int playersCountForStartGame=2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_activity);
        bStartServer= findViewById(R.id.startServer);
        bBeginGame= findViewById(R.id.beginGame);
        auto_start = findViewById(R.id.auto_start);
        sPlayersCount = findViewById(R.id.playersCount);
        tPlayersCountText= findViewById(R.id.playersCountText);
        ipAdress= findViewById(R.id.ipAdress);

        sPlayersCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                playersCountForStartGame= sPlayersCount.getProgress()+2;
                tPlayersCountText.setText(String.valueOf(playersCountForStartGame));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(server!=null)
            server.stop=true;
        System.exit(0);
    }

    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface)en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        String ipAddress=inetAddress.getHostAddress().toString();
                        Log.e("IP address",""+ipAddress);
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Socket exception", ex.toString());
        }
        return null;
    }



    public void onClickAutoLoad(View v){

    }

    public void onClickStartServer(View v){
        server = new MyThreadServer(25565,playersCountForStartGame);
        ipAdress.setText("IP сервера:"+getIpAddress());
        try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

        try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        Intent intent = new Intent(this,GameCanvas.class);
        startActivity(intent);
    }

    public void onClickBeginGame(View v){

    }

}
