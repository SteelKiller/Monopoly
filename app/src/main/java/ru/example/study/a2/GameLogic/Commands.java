package ru.example.study.a2.GameLogic;

public class Commands {

    private String head;
    private String[] params;
    private boolean updated=false;

    public Commands(String cmd){
        String[] tmp = cmd.split(":");
        head=tmp[0];

        params= new String[tmp.length-1];

        for (int i=1;i<tmp.length;i++){
            params[i-1]=tmp[i];
        }
        updated=true;
    }

    public boolean isUpdated() {
        return updated;
    }

    public String getHead() {
        if(head!=null)
            return head;
        else
            return "END";
    }


    public String getFirst(){
        if(params!=null)
            return params[0];
        else
            return "END";
    }

    public String[] getParams() {
        updated=false;
        return params;
    }
}
