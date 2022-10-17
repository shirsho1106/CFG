package sample;

import java.util.ArrayList;

public class TestCases {
    ArrayList<String>Lines;
    ArrayList<ParsedExpression>testCases;
    ArrayList<String>expressions;

    int cur = 0;
    Checker checker = new Checker();

    public TestCases(ArrayList<String> lines){
        this.Lines = lines;
    }
    public void make() {
        while(Lines.get(cur).contains("intmain(){") || Lines.get(cur).charAt(0)=='#' || Lines.get(cur).charAt(0)=='/'){
            cur++;
        }

        expressions = makeCases();

        for (String expression:expressions) {
//            System.out.println(expression);
        }
        testCases = manageOperators(expressions);
        printCases(testCases);
    }

    private void printCases(ArrayList<ParsedExpression> testCases) {
        int serial = 1;
        for (ParsedExpression testCase: testCases) {
            System.out.println("Testcase "+ (serial++) + ": " + testCase.getVariable() + " = " + (testCase.getNumber()-1));
            System.out.println("Testcase "+ (serial++) + ": " + testCase.getVariable() + " = " + (testCase.getNumber()));
            System.out.println("Testcase "+ (serial++) + ": " + testCase.getVariable() + " = " + (testCase.getNumber()+1));
        }
    }

    private String expressionCatcher(String statement, int startIndex){
        String l = "";
        while(statement.charAt(startIndex)!=')'){
            l += statement.charAt(startIndex++);
        }
        return l;
    }
    private ArrayList<String> makeCases(){

        ArrayList<String> expressions = new ArrayList<>();
        String statement = "";

        while(cur<Lines.size()) {
            Node curNode = new Node(cur,Lines.get(cur));
            if(checker.isElseIf(curNode.Statement)){
                cur++;
                statement = curNode.Statement.replaceAll("\\s","");
                expressions.add(expressionCatcher(statement,7));
            }
            else if(checker.isIf(curNode.Statement)){
                cur++;
                statement = curNode.Statement.replaceAll("\\s","");
                expressions.add(expressionCatcher(statement,3));
            }
            else if(checker.isWhile(curNode.Statement)){
                cur++;
                statement = curNode.Statement.replaceAll("\\s","");
                expressions.add(expressionCatcher(statement,6));
            }
//            else cur++;
            else if(checker.isFor(curNode.Statement)){
                cur++;
                statement = curNode.Statement.replaceAll("\\s","");
                String forstatement = "";
                int startIndex = -1;
                for (int i = 0 ; i < statement.length() ; i++){
                    if(statement.charAt(i)==';'){
                        if(startIndex == -1) startIndex = i+1;
                        else {
                            forstatement += ')';
                            break;
                        }
                    }
                    forstatement += statement.charAt(i);
                }
                expressions.add(expressionCatcher(forstatement,startIndex));
            }
            else cur++;
        }
        return expressions;
    }

    private ArrayList<ParsedExpression> manageOperators(ArrayList<String> expressions){
        ArrayList<ParsedExpression> parsedExpressions = new ArrayList<>();
        int it = 0;

        for (String expression: expressions) {
            it++;
            boolean operatorFlag = false;
            String left = "", right = "";
            double number = 0.0;
            String variable = "";
            String operator = "";
            for(int i = 0 ; i < expression.length() ; i++){
                if(expression.charAt(i) == '|' || expression.charAt(i) == '&' ){
                    parsedExpressions.add(addExpression(left,right,operator));
                    i++;
                    operatorFlag = false;
                    left = ""; right = "";
                    number = 0.0;
                    variable = "";
                    operator = "";
                    continue;
                }
                if(expression.charAt(i) == '=' || expression.charAt(i) == '>' ||
                   expression.charAt(i) == '<' || expression.charAt(i) == '!' )
                {
                    operator+=expression.charAt(i);
                    operatorFlag = true;
                    continue;
                }
                if(operatorFlag) right += expression.charAt(i);
                else left += expression.charAt(i);
            }
            ParsedExpression ex = addExpression(left,right,operator);
            if(ex != null)
                parsedExpressions.add(ex);
            else
                System.out.println("Could not build a case");
        }
        return parsedExpressions;
    }

    private ParsedExpression addExpression(String left, String right, String operator){
        double number;
        String variable = "";

        try {
            number = Double.parseDouble(right);
            variable = left;
        } catch (Exception e){
            try {
                number = Double.parseDouble(left);
                variable = right;
            } catch (Exception e2){
                System.out.println("Could not build a case");
                return null;
            }
        }
        //System.out.println(variable+' '+operator+' '+number);
        return new ParsedExpression(variable,number,operator);
    }
}