/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    String path = "F:\\semester6\\CFG_&_AutoTestCase\\sample\\";
    ArrayList<String>Lines;
    boolean[] vis;
    int[][] adj = new int[50][50];
    Checker checker = new Checker();
    int cur = 0;

    public Graph(ArrayList<String> lines){
        this.Lines = lines;
        vis = new boolean[Lines.size()];
    }
    
    public void make() throws IOException{
        cur=0;
        
        while(Lines.get(cur).contains("intmain(){") || Lines.get(cur).charAt(0)=='#' || Lines.get(cur).charAt(0)=='/'){
            cur++;
        }
        
        Node root = new Node(cur,Lines.get(cur));
        cur++;
        
        //System.out.println("\n\n\nroot node no: " + root.nodeNumber +"\n"+ "root node statement: "+root.Statement);
        
        makeRelations(root, false);
        
        dfs(root, -1);
        bfs(root);
        printGraph();
        //saveGraph();
    }
    
    
    public Node makeRelations(Node branchRoot, boolean inLoop){
        Node par = branchRoot;
        //System.out.println(cur);
        ArrayList<Node> branchingsOfThisBranch = new ArrayList<>();
        //System.out.println(branchRoot.nodeNumber+" "+inLoop);
        
        while(cur<Lines.size()) {
            //System.out.println("finished " + cur);
            Node curNode = new Node(cur,Lines.get(cur));

            if(checker.isElse(curNode.Statement)){
                //System.out.println("Else - "+ curNode.Statement);

                par.childs.add(curNode);
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
            else if(checker.isElseIf(curNode.Statement)){
                par.childs.add(curNode);
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
            else if(checker.isIf(curNode.Statement)){
                if(branchingsOfThisBranch.size()>0){
                    for(int i=0; i<branchingsOfThisBranch.size(); i++){
                        branchingsOfThisBranch.get(i).childs.add(curNode);
                        branchingsOfThisBranch.clear();
                    }
                }
                else{
                    par.childs.add(curNode);
                }
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
            else if(checker.isLoop(curNode.Statement)){
                //System.out.println("loop - "+ curNode.Statement);

                if(branchingsOfThisBranch.size()>0){
                    for(int i=0; i<branchingsOfThisBranch.size(); i++){
                        branchingsOfThisBranch.get(i).childs.add(curNode);
                        branchingsOfThisBranch.clear();
                    }
                }
                else{
                    par.childs.add(curNode);
                }
                branchingsOfThisBranch.add(curNode);
                cur++;
                makeRelations(curNode, true);
            }
            else{
                //System.out.println("Statement - "+ curNode.Statement+branchingsOfThisBranch.size());
                 if(branchingsOfThisBranch.size()>0){
                     for (Node ofThisBranch : branchingsOfThisBranch) {
                         ofThisBranch.childs.add(curNode);
                     }
                    branchingsOfThisBranch.clear();
                }
                else{
                    par.childs.add(curNode);
                }
                //branchingsOfThisBranch.add(curNode);
                cur++;
                if(checker.foundEnd(curNode.Statement)){
                    if(inLoop) {
                        curNode.childs.add(branchRoot);
                    }
                    return curNode;
                }
                par = curNode;
            }
        }
        return null;
    }
    
    
    
    public void dfs(Node cur, int prev){
        //System.out.println(prev + " " + cur.nodeNumber+" "+cur.Statement);
        vis[cur.nodeNumber] = true;
        
        for(int i=0; i<cur.childs.size(); i++){
            int nodeNo = cur.childs.get(i).nodeNumber;
            if(!vis[nodeNo]){
                dfs(cur.childs.get(i), cur.nodeNumber);
            }
        }
        
        for(int i=0; i<cur.childs.size(); i++){
            adj[cur.nodeNumber][cur.childs.get(i).nodeNumber] = 1;
        }
    }
    
    public void bfs(Node root) throws IOException{
        int[] level = new int[50];
        for(int i=0; i<50; i++) level[i] = 100000000;
        
        level[root.nodeNumber] = 1;
        Queue<Integer>q = new LinkedList<>();
        q.add(root.nodeNumber);
        while(!q.isEmpty()){
            int cur = q.peek();
            q.poll();
            for(int i=0; i<50; i++){
                if(adj[cur][i]==1 && level[i]>level[cur]+1){
                    level[i] = level[cur]+1;
                    //System.out.println(i + " " + level[i]);
                    q.add(i);
                }
            }
        }
//        try (FileWriter myWriter = new FileWriter("/home/shirsho/Desktop/testing/cfgjava/CFG/cfg/LeveledNodes.txt")) {
//        try (FileWriter myWriter = new FileWriter(path+"LeveledNodes.txt")) {
//            for(int i=0; i<Lines.size(); i++){
//                myWriter.write(i + " " + level[i]+"\n");
//            }
//        }
        
    }
    public void printGraph (){
//        System.out.println("\nAdjacency List:");
//        for(int i=0; i<Lines.size(); i++){
//            System.out.print("\t"+i+"  ->   ");
//            for(int j=0; j<Lines.size(); j++){
//                if(adj[i][j]==1){
//                    System.out.print(j+" ");
//                }
//            }
//            System.out.println();
//        }
        int nodes = 0; int edges = 0;
        nodes = Lines.size()-1;
        System.out.println("\nAdjacency Matrix:"+nodes);
        for(int i=1; i<Lines.size(); i++){
            System.out.print("\t"+i+"\t");
            for(int j=1; j<Lines.size(); j++){
                if(adj[i][j]==1) edges++;
                System.out.print(adj[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("");
        System.out.println("Cyclomatic complexity (edges-nodes+2) :"+(edges-nodes+2));
        System.out.println("");
    }
//    public void saveGraph() throws IOException{
////        try (FileWriter myWriter = new FileWriter("/home/shirsho/Desktop/testing/cfgjava/CFG/cfg/Edges.txt")) {
//        try (FileWriter myWriter = new FileWriter(path+"Edges.txt")) {
//            //myWriter.write((Lines.size())+"\n");
//            for(int i=0; i<Lines.size(); i++){
//                for(int j=0; j<Lines.size(); j++){
//                    if(adj[i][j]==1){
//                        myWriter.write(i+" "+j+"\n");
//                    }
//                }
//            }
//        }
//    }
    
}
