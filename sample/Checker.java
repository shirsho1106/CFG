/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

public class Checker {

    public boolean isIf(String statement){
        statement = statement.replaceAll("\\s","");
        if(statement.length()>=3){
            return statement.charAt(0) == 'i' && statement.charAt(1) == 'f' && statement.charAt(2) == '(';
        }
        return false;
    }
    
    public boolean isElseIf(String statement){
        statement = statement.replaceAll("\\s","");statement = statement.replaceAll("\\s","");
        if(statement.length()>=7){
            return statement.charAt(0) == 'e' && statement.charAt(1) == 'l' && statement.charAt(2) == 's'
                    && statement.charAt(3) == 'e' && statement.charAt(4) == 'i'
                    && statement.charAt(5) == 'f' && statement.charAt(6) == '(';
        }
        return false;
    }
    public boolean isElse(String statement){
        statement = statement.replaceAll("\\s","");
        if(statement.length()>=4){
            return statement.charAt(0) == 'e' && statement.charAt(1) == 'l' && statement.charAt(2) == 's'
                    && statement.charAt(3) == 'e';
        }
        return false;
    }
    public boolean isWhile(String statement){
        statement = statement.replaceAll("\\s","");
        if(statement.length()>=6){
            return statement.charAt(0) == 'w' && statement.charAt(1) == 'h' && statement.charAt(2) == 'i'
                    && statement.charAt(3) == 'l' && statement.charAt(4) == 'e'
                    && statement.charAt(5) == '(';
        }
        return false;
    }
    public boolean isFor(String statement){
        statement = statement.replaceAll("\\s","");
         if(statement.length()>=4){
             return statement.charAt(0) == 'f' && statement.charAt(1) == 'o' && statement.charAt(2) == 'r' && statement.charAt(3) == '(';
        }
        return false;
    }
    public boolean isLoop(String statement){                        //"for(" nowt working, so instead using for
        statement = statement.replaceAll("\\s","");
        return isFor(statement) || isWhile(statement);
    }
    public boolean foundEnd(String statement){
        statement = statement.replaceAll("\\s","");
        return statement.charAt(statement.length()-1)=='}';
    }
}