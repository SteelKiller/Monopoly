package ru.example.study.a2.GameLogic;

import java.util.ArrayList;
import ru.example.study.a2.Interfaces.onTickCounterListener;

public class TickCounter {
    private ArrayList<onTickCounterListener> TickCounterHandler= new ArrayList<>();
    private ArrayList<Task>task = new ArrayList<>();

    public void addListener(onTickCounterListener onTickCounter){TickCounterHandler.add(onTickCounter);}
    public void removeListener(onTickCounterListener onTickCounter){TickCounterHandler.remove(onTickCounter);}
    private int tickCount=0;
    private int [] arrayToDel;

    public TickCounter(){


    }
    public void addTask(String label,int id,int tickCount,boolean isRepeat){
        boolean existTask =false;
        for(int i= 0;i<task.size();i++) {
            if(task.get(i).label.equals(label)){
                existTask=true;
                break;
            }
        }
        if(!existTask)
        task.add(new Task(label,id,tickCount,this.tickCount,isRepeat));
    }
    public void removetask(){
        for(int i:arrayToDel){
            if(i!=-1)
                task.remove(i);
        }

    }
    public void stopTickCounter(){
        task.clear();
        TickCounterHandler.clear();
    }

    private void Tick(String label,int id){
        for(onTickCounterListener tch :TickCounterHandler){
            tch.call(label,id);
        }
    }

    public void Tick(){
        tickCount++;
        int countTodel=0;
        arrayToDel= new int[task.size()];
        for(int i=0;i<arrayToDel.length;i++)
            arrayToDel[i]=-1;

        for(int i=0;i<task.size();i++){
            if(task.get(i).finished(tickCount)){
                Tick(task.get(i).label,task.get(i).id);
                if(!task.get(i).isRepeat())
                {
                    arrayToDel[countTodel]=i;
                    countTodel++;
                }
                else
                    task.get(i).beginTickPosition=tickCount;


            }
        }
        removetask();

    }
}
