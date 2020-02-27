package ru.example.study.a2.GameLogic;

import ru.example.study.a2.Entities.Parts;

public class Offers {
    public Parts by;
    public Parts to;
    public OfferTerms byOffer;
    public OfferTerms toOffer;


    public  boolean isSendedOffer=false;

    public Offers(Parts TBy,Parts TTo,OfferTerms TbyOffer,OfferTerms TtoOffer){
        by=TBy;
        to=TTo;
        byOffer= TbyOffer;
        toOffer=TtoOffer;
    }


    public String getDate(){
        String textO1="";
        String textO2="";

        for(int i=0;i<byOffer.card.length;i++){
            textO1+=byOffer.card[i].id+":";
        }

        for(int i=0;i<toOffer.card.length;i++){
            textO2+=toOffer.card[i].id+":";
        }

        return by.id+":"+to.id+":"+byOffer.money+":"+toOffer.money+":"+byOffer.card.length+":"+toOffer.card.length
                +":"+textO1+textO2;
    }
}
