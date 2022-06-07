package com.company;

public class MessegeCallback implements GUI{
    private String Messege;

    public MessegeCallback(String str){
        Messege = str;
    }

    @Override
    public void print() {
        System.out.println(Messege);
    }
}
