package ru.example.study.a2.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.example.study.a2.R;
import ru.example.study.a2.Sockets.MyThreadClient;

public class SearchServers extends Activity {

    Button connectToServer;
    EditText serverAdressIp;
    TextView playersList;
    SwitchCompat connectionStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);
        connectToServer= findViewById(R.id.connectToServer);
        serverAdressIp= findViewById(R.id.serverAdressIp);
        playersList= findViewById(R.id.playersList);
        connectionStatus= findViewById(R.id.connectionStatus);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
       // if(client!=null)
           // client.stop=true;
        System.exit(0);
    }


    public void onClickConnect(View v){
        connectionStatus.setChecked(true);
        GameCanvas.serverAdressIp=serverAdressIp.getText().toString();
        Intent intent = new Intent(this,GameCanvas.class);
        startActivity(intent);

    }
    public void onClickDisconnect(View v){
//        client.setCommand("sendMessage","SOCKET_EXIT");
//        client.stop=true;
    }
}
