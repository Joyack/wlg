package com.wlg.Test;

/**
 * Created by Administrator on 2016/7/14.
 */
public class Person {
    private Card card;

    public Person(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    public boolean playCard(Card card){
//        if (card.getCardSize())
        return true;
    }
}
