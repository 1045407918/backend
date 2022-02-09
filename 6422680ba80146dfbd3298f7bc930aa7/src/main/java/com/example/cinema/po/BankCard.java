package com.example.cinema.po;

public class BankCard {
    private int cardNumber;
    private int password;
    private double cardBalance;

    public int getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(int cardNumber){
        this.cardNumber=cardNumber;
    }

    public int getPassword(){
        return password;
    }

    public void setPassword(int password){
        this.password=password;
    }

    public double getcardBalance(){
        return cardBalance;
    }

    public void setcardBalance(double cardBalance){
        this.cardBalance=cardBalance;
    }
}
