package com.example.optionmenucalculator;

public class Results {
    private float num1,num2,result;
    private char operator;

    public Results(float num1, float num2, char operator, float result) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = result;
    }

    public float getNum1() {
        return num1;
    }

    public float getNum2() {
        return num2;
    }

    public char getOperator() {
        return operator;
    }

    public float getResult() {
        return result;
    }
}
