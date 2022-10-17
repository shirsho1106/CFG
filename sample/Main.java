/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
       System.out.println("Control Flow Graph\n\n");
       String fileName = "F:\\semester6\\CFG_&_AutoTestCase\\sample\\test.txt";

       File file = new File(fileName);
       Scanner input = new Scanner(file);
       ArrayList<String> Lines = new ArrayList<>();

       while (input.hasNextLine()) {
           String line = input.nextLine();
           //currentLine = currentLine.replaceAll("\\s","");
           Lines.add(line);
       }

       makeCFG(Lines);
       makeTestCases(Lines);
    }
    private static void makeCFG(ArrayList<String> lines) throws IOException{
        Graph graph = new Graph(lines);
        graph.make();
    }
    private static void makeTestCases(ArrayList<String> lines) throws IOException{
        TestCases testCases = new TestCases(lines);
        testCases.make();
    }
}
