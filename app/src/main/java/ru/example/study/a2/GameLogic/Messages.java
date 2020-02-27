package ru.example.study.a2.GameLogic;

public class Messages {
    private Message[] msg;
    private int id=-1;
    private boolean requireUpdate=false;

    public boolean isRequireUpdate() {
        if(requireUpdate){
            requireUpdate=false;
            return true;
        }
        else {
            return false;
        }
    }

    public Message[] getMsg() {

        return msg;
    }

    public int size(){
        if(msg!=null)
            return msg.length;
        else
            return 0;
    }

    public void clear(){
        msg=null;
    }

    public void setMsg(String str,int id) {
        Message[] temp;

        if(msg!=null){
            temp=new Message[msg.length+1];

            for (int i=0;i<msg.length;i++){
                temp[i]= new Message();
                temp[i]=msg[i];
                temp[i].id=msg[i].id;
            }
            temp[temp.length-1]= new Message();
            temp[temp.length-1].message=str;
            temp[temp.length-1].id=id;
        }
        else{
            temp=new Message[1];
            temp[0] = new Message();
            temp[0].message=str;
            temp[0].id=id;
        }
        this.msg = temp;
        requireUpdate=true;
    }


    public int getId() {
        return id;
    }

    public void setRequireUpdate() {
        this.requireUpdate = true;
    }
}
