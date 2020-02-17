package ovh.jonhshepard.calculatorplus;

public class Operation {

    private Object left;
    private EnumOperator operator;
    private Object right;

    public Operation(Object left, EnumOperator operator, Object right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public EnumOperator getOperator() {
        return operator;
    }

    public void setOperator(EnumOperator operator) {
        this.operator = operator;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public double computeValue() {
        double leftV = (left instanceof Operation) ? ((Operation) left).computeValue() : (double) left;
        double rightV = (right instanceof Operation) ? ((Operation) right).computeValue() : (double) right;

        return operator.calculate(leftV, rightV);
    }
}
