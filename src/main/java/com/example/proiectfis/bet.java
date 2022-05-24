package com.example.proiectfis;

public class bet {
    private String echipa1,echipa2,amount,status,data,pariu;

    public bet(String echipa1,String echipa2,String data,String pariu, String amount,String status) {
        this.echipa1 = echipa1;
        this.echipa2=echipa2;
        this.amount=amount;
        this.status=status;
        this.data=data;
        this.pariu=pariu;
    }

    public String getPariu() {
        return pariu;
    }

    public void setPariu(String pariu) {
        this.pariu = pariu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
