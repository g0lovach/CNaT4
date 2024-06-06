package org.example;

import org.uncommons.maths.random.GaussianGenerator;

import java.util.Random;

public class Main {



    public static void main(String[] args) {

        int n = 100;
        double r = 0.5;
        GaussianGenerator gaussianGenerator = new GaussianGenerator(0,0.5,new Random());
       /* for(double i = 0.6;i<1.6;i+=0.1){
            System.out.println("r="+i+":");
            for(int j = 10;j<101;j+=10){*/
                Computing computing = new Computing(0.5,10,1000,gaussianGenerator, 16, 0.95);
                var tmp = computing.doComputing();

                System.out.println(tmp.get(0).toString().replace(".",",") + "\t"
                        + tmp.get(1).toString().replace(".",",")+"\t"
                        + Double.toString(tmp.get(1)*5/100).replace(".",","));/*
            }
        }
*/


        /*Computing computing = new Computing(r,n,10000,gaussianGenerator);
        var tmp = computing.doComputing();
        System.out.println(tmp.get(0).toString().replace(".",",") + "\t"
                + tmp.get(1).toString().replace(".",",")+"\t"
                + Double.toString(tmp.get(1)*n/100).replace(".",","));*/

    }
}