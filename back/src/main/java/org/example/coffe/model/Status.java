package org.example.coffe.model;

public enum Status {

    READY,
    MAKE,
    CLEAN,
    STOP;

    public static Status getEnum(byte index){
        return Status.values()[index];
    }
}
