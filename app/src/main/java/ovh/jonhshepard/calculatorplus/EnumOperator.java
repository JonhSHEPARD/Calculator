package ovh.jonhshepard.calculatorplus;

import android.content.Context;

public enum EnumOperator {

    ADD(R.string.buttonPlus, 2, true),
    SUBSTRACT(R.string.buttonMinus, 2, true),
    DIVIDE(R.string.buttonDivide, 1, true),
    MULTIPLY(R.string.buttonMultiply, 1, true),
    PARLEFT(R.string.buttonParLeft, 0, false),
    PARRIGHT(R.string.buttonParRight, 0, false),
    DOT(R.string.buttonDot, -1, false);

    private int stringVal;
    private int priority;
    private boolean calcOp;

    EnumOperator(int stringVal, int priority, boolean calcOp) {
        this.stringVal = stringVal;
        this.priority = priority;
        this.calcOp = calcOp;
    }

    public int getString() {
        return stringVal;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCalcOp() {
        return calcOp;
    }

    public double calculate(double first, double second) {
        if(!calcOp)
            return 0;
        switch (this) {
            case ADD:
                return first + second;
            case SUBSTRACT:
                return first - second;
            case DIVIDE:
                return first / second;
            case MULTIPLY:
                return first * second;
        }
        return 0;
    }

    public static boolean isOperator(Context c, String s) {
        for (EnumOperator op : values()) {
            if (s.equals(c.getString(op.getString())))
                return true;
        }
        return false;
    }

    public static EnumOperator fromString(Context c, String s) {
        for (EnumOperator op : values()) {
            if (s.equals(c.getString(op.getString())))
                return op;
        }
        return null;
    }
}
