package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphUtils {

    static List<Integer> dfs(List<List<Integer>> g,List<Integer> visits,List<Integer> comp, int v){
        visits.set(v, visits.get(v)+1);
        /*if(g.get(v).stream().distinct().collect(Collectors.toList()).size()==1){
            return new ArrayList<>(List.of(new Integer[]{v}));
        }*/
        comp.add(v);
        for (int i =0;i<g.size();i++){
            if(g.get(v).get(i)==1 && visits.get(i)==0){
                //System.out.println(v+" Visited: "+i);
                dfs(g,visits,comp,i);
            }
        }
        return comp;
    }

    static List<List<Integer>> findComps(List<List<Integer>> g) {
        List<List<Integer>> comps = new ArrayList<>();


        for (int i = 0; i < g.size(); i++) {
           /* System.out.println("-----------------------");
            System.out.println("Eval: " + i);
            System.out.println("-----------------------");*/
            List<Integer> visits = new ArrayList<>();
            for (int j = 0; j < g.size(); j++) {
                visits.add(0);
            }
            var tmp = dfs(g, visits, new ArrayList<>(), i).stream().sorted().toList();
            boolean flag = false;
            for (List<Integer> item : comps) {
                if (tmp.equals(item)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                comps.add(tmp.stream().sorted().collect(Collectors.toList()));
            }

        }
        //System.out.println(comps);
        return comps;
    }

    static List<List<Integer>> convertPointsToMatrix(double r, List<Node> nodes){
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i< nodes.size(); i++){
            List<Integer> tmp = new ArrayList<>();
            for(int j = 0; j< nodes.size(); j++){
                tmp.add(0);
            }
            res.add(tmp);
        }
        for (int i = 0; i< nodes.size()-1; i++){
            for (int j = i+1; j< nodes.size(); j++){
                //System.out.println("i="+i+";j="+j+":"+(Math.pow((nodes.get(i).x-nodes.get(j).x),2) + Math.pow((nodes.get(i).y-nodes.get(j).y),2)));
                if (Math.pow(r,2) > Math.pow((nodes.get(i).x- nodes.get(j).x),2) + Math.pow((nodes.get(i).y- nodes.get(j).y),2)){
                    res.get(i).set(j,1);
                    res.get(j).set(i,1);
                }
            }
        }
        return  res;
    }

    private static List<List<Integer>> prepareG(List<List<Integer>> g){
        for (int i = 0; i<g.size();i++){
            for(int j = 0;j<g.size();j++){
                if (g.get(i).get(j)==0){
                    g.get(i).set(j,Integer.MAX_VALUE);
                }
            }
        }
        return g;
    }

    private static List<List<Integer>> searchShortestWays(List<List<Integer>> g){
        g =  prepareG(g);
        for (int k = 0; k<g.size();k++){
            for(int i = 0;i<g.size();i++){
                for(int j = 0;j<g.size();j++){
                    if(g.get(i).get(k)<Integer.MAX_VALUE && g.get(k).get(j) < Integer.MAX_VALUE) {
                        if(i==j){
                            g.get(i).set(j,0);
                            continue;
                        }
                        if (g.get(i).get(k) + g.get(k).get(j) < g.get(i).get(j)) {
                            g.get(i).set(j, g.get(i).get(k) + g.get(k).get(j));
                        }
                    }
                }
            }
        }
        return g;
    }

    public static boolean isConnected(List<List<Integer>> g){
        g = searchShortestWays(g);
        for (int i = 0; i<g.size();i++){
            for(int j = 0;j<g.size();j++){
                if (g.get(i).get(j)==Integer.MAX_VALUE){
                    return false;
                }
            }
        }
        return true;
    }

    public static int searchDiameter(List<List<Integer>> g){
        g = searchShortestWays(g);
        int max = 0;
        for (int i = 0;i<g.size();i++){
            for(int j = i;j<g.size();j++){
                max = Math.max(max,g.get(i).get(j));
            }
        }
        //System.out.println("DIAMETER IS "+Integer.toString(max));
        return max;
    }

    public static int findElemCountInMainComp(List<List<Integer>> g){
        List<List<Integer>> comps = findComps(g);
        var tmp = comps.get(0);
        for(List<Integer> item:comps){
            if(item.size()>tmp.size()){
                tmp = item;
            }
        }
        return tmp.size();
    }

}
