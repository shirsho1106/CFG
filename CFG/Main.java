/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
       System.out.println("Control Flow Graph\n\n");
       String fileName = "F:\\semester6\\CFG\\CFG\\cfg\\test.txt";
       makeCFG(fileName);
    }
    private static void makeCFG(String s) throws IOException{
        File file = new File(s);
        Scanner input = new Scanner(file);
        ArrayList<String> Lines = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            //currentLine = currentLine.replaceAll("\\s","");
            Lines.add(line);
        }
        Graph Graph = new Graph(Lines);
        Graph.make();
    }
}
