package ru.example.study.a2.Entities;



public class Fields {
    public FieldCoords[] fieldCoords;


    public Fields(){
        fieldCoords = new FieldCoords[40];
        for(int i=0;i<40;i++)
            fieldCoords[i] = new FieldCoords(i);

        int count=0;
        for(int x=0;x<11;x++){
            fieldCoords[count].x=x;
            fieldCoords[count].y=0;
            count++;
        }

        for(int y=1;y<10;y++){
            fieldCoords[count].x=10;
            fieldCoords[count].y=y;
            count++;
        }

        for(int x=10;x>=0;x--){
            fieldCoords[count].x=x;
            fieldCoords[count].y=10;
            count++;
        }

        for(int y=9;y>0;y--){
            fieldCoords[count].x=0;
            fieldCoords[count].y=y;
            count++;
        }


    }


    public String getCardDate(){
        String res="";
        for (int i=0;i<40;i++){
            res+=fieldCoords[i].card.idPlayer+":";
        }
        return res;
    }
}
