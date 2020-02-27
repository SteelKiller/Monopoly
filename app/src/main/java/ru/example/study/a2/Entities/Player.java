package ru.example.study.a2.Entities;

import java.util.Random;

public class Player extends Parts {
    private int image;
    public int position=0;
    public int x, y;
    public String name_player;
    public String ip;
    public String status;




    public Player(int id,String Tname)
    {
        name_player=Tname;
        this.id=id;
        status="Online";

    }

    public Square step()
    {
        Random random = new Random();
        square.square_one=random.nextInt(6);
        square.square_two=random.nextInt(6);
        stepCount=square.square_one + square.square_two;
        return square;
    }

    public String getData(){
        return money+":"+square.square_one+":"+square.square_two+":"+x+":"+y+":"+name_player+":"+id+":"+position+":";//id это и цвет
    }

    public String getInformation(){
        return id+":"+x+":"+y+":"+money+":"+name_player+":";//id это и цвет
    }







}

