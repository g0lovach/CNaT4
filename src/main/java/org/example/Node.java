package org.example;

public class Node {
    double x;
    double y;



    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "Node: x="+x+"; y="+y+"\n";
    }
}
