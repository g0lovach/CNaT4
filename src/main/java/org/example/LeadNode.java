package org.example;

public class LeadNode extends Node{

    Node target;
    public LeadNode(double x, double y, Node target) {
        super(x, y);
        this.target = target;
    }
}
