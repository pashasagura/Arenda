package com.example.arenda;

public  class Instanse {
    private int GG = 1;
    private static Instanse instanse;

    public static Instanse getInstance() {
        if(instanse==null) {
            instanse= new Instanse();
        }
     return instanse;
    }

    public int getGG() {
        return GG;
    }

    public void setGG(int GG) {
        this.GG = GG;
    }
}
