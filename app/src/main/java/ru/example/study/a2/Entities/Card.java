package ru.example.study.a2.Entities;

public class Card {
    public int idImage;
    public int id;
    public int cost=1000;
    public int renta=500;
    public String group;
    public String name;
    public int countInGroup;
    public double factor;
    public int hotelCost;
    public int cardType;
    public String cardName;
    public int idPlayer=-1;
    public boolean isDeposit=false;

    public Card(int TId){
        id=TId;
    }

    public int getCost(){
        if(!isDeposit)
            return idPlayer==-1?cost: renta+(int)(renta * factor);
        else
            return cost/2;
    }

    public int getHotelCost(){
        return hotelCost;
    }



}
