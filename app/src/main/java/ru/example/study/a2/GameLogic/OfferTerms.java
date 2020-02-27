package ru.example.study.a2.GameLogic;

import ru.example.study.a2.Entities.Card;

public class OfferTerms {
    public Card[] card;
    public int money;
    public boolean deposit;
    public int totalCost;

    public OfferTerms(int TMoney,Card[] Tcard,boolean TDeposit){
        money = TMoney;
        card = Tcard;
        deposit =TDeposit;
        getTotalCost();
    }

    public OfferTerms(int TMoney,Card[] Tcard){
        money = TMoney;
        card = Tcard;
        deposit =false;
        getTotalCost();
    }

    private void getTotalCost(){
        totalCost=money;
        for(int i=0;i<card.length;i++){
            totalCost+=card[i].cost;
        }
    }
}
