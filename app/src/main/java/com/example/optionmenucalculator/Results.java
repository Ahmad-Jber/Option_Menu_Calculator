package com.example.optionmenucalculator;

public class Results {
    private String num1,num2,result;
    private char operator;

    public Results(String num1, String num2, char operator, String result) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = result;
    }

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public char getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }
}
