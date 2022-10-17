package sample;

public class ParsedExpression {
    String variable, operator;
    double number;
    public ParsedExpression(String variable, double number, String operator){
        this.variable = variable;
        this.number = number;
        this.operator = operator;
    }

    public String getVariable() {
        return variable;
    }

    public double getNumber() {
        return number;
    }

    public String getOperator() {
        return operator;
    }
}
