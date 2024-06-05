package org.example;

public class PursuerNode extends Node{

    LeadNode lead;

    public PursuerNode(double x, double y, LeadNode lead) {
        super(x, y);
        this.lead = lead;
    }

}
