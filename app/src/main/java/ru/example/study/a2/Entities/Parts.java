package ru.example.study.a2.Entities;

import ru.example.study.a2.GameLogic.Commands;
import ru.example.study.a2.GameLogic.Offers;

public class Parts {

    public int id;
    public int money=15000;
    public Square square;
    public int stepCount;
    public Card[] card;
    public Offers offer;
    public boolean isOpenedOffer;
    public boolean confirm;


    public Parts(){
        square=new Square();
    }

    public void setOffer(Offers TOffer,boolean TConfirm ){
        offer=TOffer;
        isOpenedOffer=true;
        confirm=TConfirm;
    }


    public int offerStatus(FieldCoords[] mapFieldCoords, Commands cmd){
        String answer =cmd.getFirst();
        if(!confirm)
            answer="ACCEPT";

        switch(answer){
            case"ACCEPT":

                if(offer.to.money-offer.toOffer.money>=0) {
                    offer.to.money -= offer.toOffer.money;
                    offer.by.money += offer.toOffer.money;

                    offer.by.money -= offer.byOffer.money;
                    offer.to.money += offer.byOffer.money;

                    for (int i=0;i<offer.toOffer.card.length;i++)
                        mapFieldCoords[offer.toOffer.card[i].id].card.idPlayer = offer.by.id;

                    for (int i=0;i<offer.byOffer.card.length;i++)
                        mapFieldCoords[offer.byOffer.card[i].id].card.idPlayer = offer.to.id;

                    isOpenedOffer=false;
                    return 0;

                }
                else
                    return 1;
            case"REJECT":
                isOpenedOffer=false;
                return 2;

        }

        return -1;
    }


}
