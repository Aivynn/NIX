package com.model;

public class OperationSystem {

    private int version;
    private String designation;

    public OperationSystem(int version, String designation) {
        this.designation = designation;
        this.version = version;
    }

    @Override
    public String toString() {
        return "OperationSystem{" +
                "version=" + version +
                ", designation='" + designation + '\'' +
                '}';
    }
}
