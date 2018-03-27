package com.example.pk.docsappnitt;

import android.location.Address;

public class Profile {
    public String MobileNumber;
    public String RollNumber;
    public String Address;
    public String Name;
    public String isDoctor;
    public String flagDoctor;

    public Profile(String Name,String RollNumber,String MobileNumber,String Address,String isDoctor,String flagDoctor){
        this.Name=Name;
        this.Address=Address;
        this.MobileNumber=MobileNumber;
        this.RollNumber=RollNumber;
        this.isDoctor=isDoctor;
        this.flagDoctor=flagDoctor;
    }
}
