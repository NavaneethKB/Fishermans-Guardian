package com.example.firshermensguardian;

public class alerts {
    String sNo;
    String Name;
    String coOrdinates;

    public alerts(String sNo, String name, String coOrdinates) {
        this.sNo = sNo;
        this.Name = name;
        this.coOrdinates = coOrdinates;
    }

    public String getsNo() {
        return sNo;
    }

    public String getName() {
        return Name;
    }

    public String getCoOrdinates() {
        return coOrdinates;
    }
}
