package ru.example.study.a2.Entities;

public class FieldCoords {
    public int x;
    public int y;
    public Card card;
    public String type;

    FieldCoords(int id){
        card= new Card(id);
    }
}
