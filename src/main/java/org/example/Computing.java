package org.example;

import org.uncommons.maths.random.GaussianGenerator;

import java.util.*;

public class Computing {

    double r;
    double n;
    double countOfStarts;
    GaussianGenerator generator;
    long statCountOfElemsInMainComp;
    long statDiam;
    int countConnected;

    double maxLeadSpeed;
    double pursuerAcceleration;

    double dT = 1;

    double tMax = 10;

    Map<Double,Double> periods;


    public Computing(double r, double n, double countOfStarts, GaussianGenerator generator, double maxLeadSpeed, double pursuerAcceleration) {
        this.r = r;
        this.n = n;
        this.countOfStarts = countOfStarts;
        this.generator = generator;
        this.statDiam=0;
        this.statCountOfElemsInMainComp=0;
        this.countConnected=0;
        this.maxLeadSpeed = maxLeadSpeed;
        this.pursuerAcceleration = pursuerAcceleration;
        periods = new TreeMap<>();
        for (double i = 1;i<=tMax/2;i++){
            periods.put(i,0d);
        }
    }

    private void generateTargetForLead(LeadNode lead){
        Random random = new Random();
        while(true){
            Node tmpTarget = new Node(random.nextDouble(), random.nextDouble());
            if((Math.pow((tmpTarget.x-lead.x),2)+Math.pow((tmpTarget.y-lead.y),2))/dT>Math.pow(maxLeadSpeed,2)){
                continue;
            }
            lead.target=tmpTarget;
            break;
        }
    }

    private void moveNodes(List<Node> nodes){
        for(Node node:nodes){
            if (node instanceof LeadNode){
                node.x = ((LeadNode) node).target.x;
                node.y = ((LeadNode) node).target.y;
               generateTargetForLead((LeadNode) node);
            }
            else{
                node.x = node.x + pursuerAcceleration*(((PursuerNode) node).lead.x - node.x) + generator.nextValue();
                node.y = node.y + pursuerAcceleration*(((PursuerNode) node).lead.y - node.y) + generator.nextValue();
            }
        }
    }

    public List<Double> doComputing(){


        for(int i = 0;i<countOfStarts;i++){
            double t = 0;
            double connectedCounter = 0;
            //if(i%1000==0) System.out.println(i);

            List<Node> nodes = new ArrayList<>();
            Random random = new Random();
            LeadNode lead = new LeadNode(generator.nextValue(), generator.nextValue(), null);
            generateTargetForLead(lead);
            nodes.add(lead);
            for(int j=0;j<n-1;j++){
                nodes.add(new PursuerNode(generator.nextValue(), generator.nextValue(), lead));
            }

            while(t<tMax){
                var g = GraphUtils.convertPointsToMatrix(r, nodes);
                int diam = -1;
                int countElemsInMainComp = -1;
                if(GraphUtils.isConnected(g)){
                    diam = GraphUtils.searchDiameter(g);
                    statDiam+=diam;
                    countConnected++;
                    if(t>=tMax/2){
                        connectedCounter++;
                        if(t==tMax-dT){
                            periods.put(connectedCounter,periods.get(connectedCounter)+1);
                        }
                    }
                }
                else{
                    if(t>=tMax/2){
                        if (connectedCounter>0) periods.put(connectedCounter,periods.get(connectedCounter)+1);
                        connectedCounter=0;
                    }

                    countElemsInMainComp = GraphUtils.findElemCountInMainComp(g);
                    statCountOfElemsInMainComp+=countElemsInMainComp;
                }
                moveNodes(nodes);
                t+=dT;
            }


        }
        return this.getStatistics();
    }

    private List<Double> getStatistics(){
        List<Double> res = new ArrayList<>();
        res.add((double)statDiam/countConnected);
       // res.add((double)statCountOfElemsInMainComp/(countOfStarts-countConnected));
        res.add(((double)statCountOfElemsInMainComp/(((tMax/dT)*countOfStarts)-countConnected))*100/n);
        for(Map.Entry<Double,Double> e:periods.entrySet()){
            if(e.getKey()!=0) {
                e.setValue(e.getValue() * e.getKey());
            }
        }
        System.out.println(periods);
        double sum = periods.values().stream()
                .mapToDouble(a -> a)
                .sum();
        System.out.println(sum);
        System.out.println("Probs:");

        for(Map.Entry<Double,Double> e: periods.entrySet()){
            System.out.println(e.getKey()+": "+e.getValue()/sum);
        }
        return res;
    }
}
