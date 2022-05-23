
package com.example.proiectfis;

public class tabele {
    private String echipa1,echipa2, data;

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    private int id_game;

    public tabele(String echipa1,String echipa2, String data,int id_game) {
        this.echipa1 = echipa1;
        this.echipa2=echipa2;
        this.data=data;
        this.id_game=id_game;

    }

    public String getEchipa1() {
        return echipa1;
    }

    public void setEchipa1(String echipa1) {
        this.echipa1 = echipa1;
    }

    public String getEchipa2() {
        return echipa2;
    }

    public void setEchipa2(String echipa2) {
        this.echipa2 = echipa2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}