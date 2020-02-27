package ru.example.study.a2.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.example.study.a2.R;

public class MainMenu extends Activity {

    final String FILENAME = "Settings.xml";
    public static String PlayerName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        readFile();
    }



    void readFile() {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));

            while ((PlayerName = br.readLine()) != null) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickSearchGame(View v){
        Intent intent = new Intent(this,SearchServers.class);
        startActivity(intent);
    }

    public void onClickCreateServer(View v){
        Intent intent = new Intent(this,CreateServer.class);
        startActivity(intent);
    }

    public void onClickSettings(View v){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }
}
