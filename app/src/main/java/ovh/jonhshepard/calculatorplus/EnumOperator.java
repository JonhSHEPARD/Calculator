package ovh.jonhshepard.calculatorplus;

import android.content.Context;

public enum EnumOperator {

    ADD(R.string.buttonPlus, true),
    SUBSTRACT(R.string.buttonMinus, true),
    DIVIDE(R.string.buttonDivide, true),
    MULTIPLY(R.string.buttonMultiply, true),
    PARLEFT(R.string.buttonParLeft, false),
    PARRIGHT(R.string.buttonParRight, false),
    DOT(R.string.buttonDot, false);

    private int stringVal;
    private boolean calcOp;

    EnumOperator(int stringVal, boolean calcOp) {
        this.stringVal = stringVal;
        this.calcOp = calcOp;
    }

    public int getString() {
        return stringVal;
    }

    public double calculate(double first, double second) {
        if (!calcOp)
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
