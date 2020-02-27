package ru.example.study.a2.GameLogic;

public class Task {

    private int tickCount;
    public String label;
    public int id;
    public int beginTickPosition;
    private boolean isRepeat;

    public Task(String label,int id,int tickCount,int beginTickPosition, boolean isRepeat){
        this.label=label;
        this.id=id;
        this.tickCount=tickCount;
        this.beginTickPosition=beginTickPosition;
        this.isRepeat=isRepeat;
    }

    public boolean finished(int currentTick){
        if(currentTick>=beginTickPosition+tickCount)
            return true;
        else
            return false;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

}
