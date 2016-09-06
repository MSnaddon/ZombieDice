package com.example.user.zombiedice;

/**
 * Created by user on 03/09/2016.
 */
public enum Side {
    BRAIN("Brain"),
    FOOTSTEP("Footsteps"),
    SHOTGUN("Shotgun"),
    DOUBLEBRAIN("Double Brain"),
    DOUBLESHOTGUN("Double Shotgun");

    private String value;
    Side(String v){
        value = v;
    }
    public String valueOf(){
        return this.value;
    }
}
