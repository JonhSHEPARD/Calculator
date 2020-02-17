package ovh.jonhshepard.calculatorplus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        value = "";

        Map<Integer, Button> numberButtons = new HashMap<>();
        numberButtons.put(1, (Button) findViewById(R.id.button1));
        numberButtons.put(2, (Button) findViewById(R.id.button2));
        numberButtons.put(3, (Button) findViewById(R.id.button3));
        numberButtons.put(4, (Button) findViewById(R.id.button4));
        numberButtons.put(5, (Button) findViewById(R.id.button5));
        numberButtons.put(6, (Button) findViewById(R.id.button6));
        numberButtons.put(7, (Button) findViewById(R.id.button7));
        numberButtons.put(8, (Button) findViewById(R.id.button8));
        numberButtons.put(9, (Button) findViewById(R.id.button9));
        numberButtons.put(0, (Button) findViewById(R.id.button0));

        for (final int val : numberButtons.keySet()) {
            Objects.requireNonNull(numberButtons.get(val)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertNumber(val);
                }
            });
        }

        Map<EnumOperator, Button> operatorButtons = new HashMap<>();
        operatorButtons.put(EnumOperator.ADD, (Button) findViewById(R.id.buttonPlus));
        operatorButtons.put(EnumOperator.SUBSTRACT, (Button) findViewById(R.id.buttonMinus));
        operatorButtons.put(EnumOperator.DIVIDE, (Button) findViewById(R.id.buttonDivide));
        operatorButtons.put(EnumOperator.MULTIPLY, (Button) findViewById(R.id.buttonMultiply));
        operatorButtons.put(EnumOperator.DOT, (Button) findViewById(R.id.buttonDot));
        operatorButtons.put(EnumOperator.PARLEFT, (Button) findViewById(R.id.buttonParLeft));
        operatorButtons.put(EnumOperator.PARRIGHT, (Button) findViewById(R.id.buttonParRight));

        for (final EnumOperator val : operatorButtons.keySet()) {
            Objects.requireNonNull(operatorButtons.get(val)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOperator(val);
                }
            });
        }

        Button equalButton = findViewById(R.id.buttonEqual);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "= " + computeCalc();
                textView.setText(s);
                value = "";
            }
        });
    }

    private double computeCalc() {
        Stack<Double> valStack = new Stack<>();
        Stack<EnumOperator> opStack = new Stack<>();

        int index = 0;
        while (index < value.length()) {
            char c = value.charAt(index);

            if (c >= 48 && c <= 57) {
                int endIndex = getEndOfNumber(index);
                double d = Double.parseDouble(value.substring(index, endIndex));
                valStack.push(d);
                index = endIndex;
                continue;
            }

            if (!EnumOperator.isOperator(this, c + ""))
                return 0;

            EnumOperator operator = EnumOperator.fromString(this, c + "");

            if (operator == null)
                return 0;

            switch (operator) {
                case PARLEFT:
                    opStack.push(operator);
                    break;
                case PARRIGHT:
                    while (opStack.peek() != EnumOperator.PARLEFT)
                        valStack.push(opStack.pop().calculate(valStack.pop(), valStack.pop()));
                    opStack.pop();
                    break;
                case ADD:
                case SUBSTRACT:
                case MULTIPLY:
                case DIVIDE:
                    while (!opStack.empty() && hasPrecedence(operator, opStack.peek()))
                        valStack.push(opStack.pop().calculate(valStack.pop(), valStack.pop()));
                    opStack.push(operator);
                    break;
            }
            index++;
        }

        while (!opStack.empty())
            valStack.push(opStack.pop().calculate(valStack.pop(), valStack.pop()));

        return -valStack.pop();
    }

    public static boolean hasPrecedence(EnumOperator op1, EnumOperator op2) {
        if (op2 == EnumOperator.PARLEFT || op2 == EnumOperator.PARRIGHT)
            return false;
        return (op1 != EnumOperator.MULTIPLY && op1 != EnumOperator.DIVIDE)
                || (op2 != EnumOperator.ADD && op2 != EnumOperator.SUBSTRACT);
    }

    private int getEndOfNumber(int index) {
        char val = value.charAt(index);

        while (!EnumOperator.isOperator(this, val + "")
                || EnumOperator.fromString(this, val + "") == EnumOperator.DOT) {
            index++;
            if (index >= value.length())
                val = getString(EnumOperator.ADD.getString()).charAt(0);
            else
                val = value.charAt(index);
        }

        return index;
    }

    private void insertNumber(int number) {
        if (value.length() == 0
                || !value.substring(value.length() - 1).equals(getString(EnumOperator.PARRIGHT.getString())))
            value += number;
        printValue();
    }

    private void setOperator(EnumOperator operator) {
        String val = getString(operator.getString());
        if (value.length() == 0) {
            if (operator == EnumOperator.PARLEFT)
                value += val;
        } else {
            if (operator == EnumOperator.PARLEFT || operator == EnumOperator.PARRIGHT || !EnumOperator.isOperator(this, value.substring(value.length() - 1)))
                value += val;
        }
        printValue();
    }

    private void printValue() {
        textView.setText(value);
    }
}
