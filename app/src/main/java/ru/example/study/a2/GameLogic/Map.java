package ru.example.study.a2.GameLogic;

import android.content.Intent;

import ru.example.study.a2.Entities.Bank;
import ru.example.study.a2.Entities.Card;
import ru.example.study.a2.Entities.Fields;
import ru.example.study.a2.Entities.Player;
import ru.example.study.a2.Interfaces.onTickCounterListener;
import ru.example.study.a2.Sockets.ServerSocket_v1;

public class Map {


    Fields fields;
    public Player[] players;
    private Bank bank;
    private Commands cmds;
    Messages msg;
    TickCounter tick;
    private boolean playerIsGoing=false;
    public int idOfPlayerProcessingStep=0;

    public boolean isFull=false;
    public boolean isBeginedGame=false;
    public boolean isPlaying=false;

    int countPlayers;

    public Map(int TcountPlayers)
    {
        countPlayers=TcountPlayers;
        fields = new Fields();

        players= new Player[countPlayers];
        bank = new Bank();
        msg= new Messages();
        tick= new TickCounter();
        tick.addListener(new onTickCounterListener() {
            @Override
            public void call(String label,int id) {
                msg.setMsg(label,id);

            }
        });
        countPlayers=0;
    }




    public void mainGameThread(ServerSocket_v1 server, String command){

        tick.Tick();

        cmds =new Commands(command);


        if(!isBeginedGame){
            switch (cmds.getHead()){
                case "PLAYER_DATA_NAME":
                    String [] param = cmds.getParams();
                    int id = Integer.parseInt(param[0]);
                    players[id].name_player=param[1];
                    break;
            }
        }

        if(isBeginedGame){
            server.sendTextAll("GAME:GAME_WAS_BEGAN");
            server.sendTextAll("PLAYER_LIST:"+getPlayerlist());
            //tick.addTask("GAME:UPDATE",-1,100,true);
            server.sendTextAll(getPlayersData());
            server.sendTextById(idOfPlayerProcessingStep,"STEP:CAN_STEP");
            playerIsGoing=true;
            isBeginedGame=false;
            isPlaying=true;
        }


        if(isPlaying){

             switch(cmds.getHead()){
                 case "STEP":
                    if(playerIsGoing && cmds.getFirst().equals("ACCEPT")){
                        step();
                        server.sendTextAll(getInformatiobAboutPlayers());
                    }
                    break;
                 case "OFFER_ACCEPT":

                     if(players[idOfPlayerProcessingStep].isOpenedOffer){
                         switch (players[idOfPlayerProcessingStep].offerStatus(fields.fieldCoords, cmds)) {
                             case 0:
                                 playerIsGoing = false;
                                 msg.setMsg("MESSAGE:Игрок покупает поле "+players[idOfPlayerProcessingStep].offer.byOffer.card[0].cardName,-1);
                                 break;
                             case 1:
                                 server.sendTextById(idOfPlayerProcessingStep,"ALERT:NOT_ENOUGH_MONEY");
                                 break;
                             case 2:
                                 msg.setMsg("MESSAGE:Игрок отказался от покупки поля "+players[idOfPlayerProcessingStep].offer.byOffer.card[0].cardName,-1);
                                 //msg.setMsg();
                                 break;

                         }
                     }


                     break;
                 case "BUILD":

                     break;

                 case "DEPOSIT":

                     break;

                 case "OFFER_SUGGEST":

                     break;

            }

            if(players[idOfPlayerProcessingStep].isOpenedOffer){
                if(!players[idOfPlayerProcessingStep].offer.isSendedOffer){
                    server.sendTextById(idOfPlayerProcessingStep,players[idOfPlayerProcessingStep].offer.getDate());
                    players[idOfPlayerProcessingStep].offer.isSendedOffer=true;
                }
             }

//             if(playerIsGoing){
//                 tick.addTask("STEP:CAN_STEP",idOfPlayerProcessingStep,20,false);
//             }

                if(!playerIsGoing){
                    playerIsGoing=true;
                    idOfPlayerProcessingStep++;
                    if(idOfPlayerProcessingStep>=players.length)
                        idOfPlayerProcessingStep=0;

                    server.sendTextById(idOfPlayerProcessingStep,"STEP:CAN_STEP");
                    server.sendTextAll(getPlayersData());
                    server.sendTextAll(getInformatiobAboutPlayers());
                    msg.setMsg("MAP_FIELDS:"+fields.getCardDate(),-1);
                }

        }


            if(msg.size()!=0){
                Message [] trace=msg.getMsg();
                for (int i=0;i<msg.size();i++){
                    if(trace[i].id==-1)
                        server.sendTextAll(trace[i].message);
                    else
                        server.sendTextById(trace[i].id,trace[i].message);
                }
            msg.clear();
            }



    }




    public boolean connectedPlayer(String Tip) {

        boolean wasConnected = false;
        boolean fillNow = false;

        for (int i = 0; i < players.length; i++) {
            if (players[i] != null)
                if (players[i].ip.equals(Tip)) {
                    if(isPlaying)
                    players[i].status = "Online";
                    wasConnected = true;

                }
        }

        if(countPlayers < players.length && !wasConnected){
            int Tid = countPlayers;
            players[Tid] = new Player(Tid,"Игрок"+countPlayers);
            players[Tid].ip = Tip;
            msg.setMsg("GAME:COUNT_PLAYERS:"+players.length+":",Tid);
            msg.setMsg("PLAYER_DATA_NAME:"+Tid,Tid);
            msg.setMsg(getInformatiobAboutPlayers(),-1);

            fillNow = true;
            countPlayers++;
        }

        if(countPlayers == players.length&&!isFull){
            isFull = true;
            isBeginedGame = true;
        }
        msg.setMsg("PLAYER_LIST:"+getPlayerlist(),-1);
        return !wasConnected && isFull&& !fillNow;
    }

    public void disconnectedPlayer(String TName){
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null)
                if (players[i].name_player.equals(TName)) {
                    players[i].status = "Offline";
                    break;
                }
        }
    }

    public String getPlayerlist(){
        String list="";
        for(int i=0;i<countPlayers;i++){
            if (players[i] != null&&!players[i].status.equals("Offline"))
                list+=players[i].name_player+":";
        }
        return list;
    }

    private String[] getPlayersData(){
        String[] res= new String[players.length];
        for (int i=0;i<players.length;i++){
            if(players[i]!=null)
            res[i]="PLAYER_DATA:"+players[i].getData();
        }
        return res;
    }

    private String getInformatiobAboutPlayers(){
        String res="PLAYERS_INFORMATION:";
        for (int i=0;i<players.length;i++){
            if(players[i]!=null)
                res+=players[i].getData();
        }
        return res;
    }

    public void step( )
    {
            players[idOfPlayerProcessingStep].step();
            Move(players[idOfPlayerProcessingStep]);

            if(!players[idOfPlayerProcessingStep].isOpenedOffer){
                playerIsGoing=false;
            }
            else {
                //что то хотел написать забыл((
            }

    }



    private void playerStayToField(Player player, Card card){
        if(card.idPlayer==-1){
            Card[] cardArrayby ={};
            Card[] cardArrayto ={card};

            player.setOffer(new Offers(
                    bank,
                    player,
                    new OfferTerms(0,cardArrayto),
                    new OfferTerms(card.getCost(),cardArrayby)),

                    true);

        }else if(card.idPlayer!=player.id&&!card.isDeposit){
            //Log.d("myLogs","Игрок "+player.id+" заплатил ренту игроку "+card.idPlayer);
            msg.setMsg("MESSAGE:Игрок "+player.name_player+" заплатил ренту игроку "+players[card.idPlayer].name_player+" "+card.renta,-1);
            msg.setRequireUpdate();
            player.money-=card.renta;
            players[card.idPlayer].money+=card.renta;
        }else if(card.isDeposit){
            msg.setMsg("MESSAGE:Игрок "+player.name_player+" не платит ренту т.к. собственность заложена",-1);
            msg.setRequireUpdate();
        }
    }

    public void Move(Player player)
    {
        player.position+=player.stepCount;

        msg.setMsg("STEP:OTHER_PLAYER_MOVE:"+player.name_player+":"+player.square.square_one+":"+player.square.square_two,-1);

        if(player.position>39){
            player.position-=39;
            player.money+=2000;
            msg.setMsg("MESSAGE:Игрок"+player.name_player+" получает 2000 за прохождение круга",-1);
            if(player.position==0){
                msg.setMsg("MESSAGE:Игрок"+player.name_player+" получает 1000 за остановку на первом поле",-1);
                player.money+=1000;
            }
        }

        player.x=fields.fieldCoords[player.position].x;
        player.y=fields.fieldCoords[player.position].y;

        playerStayToField(player,fields.fieldCoords[player.position].card);


    }

}
