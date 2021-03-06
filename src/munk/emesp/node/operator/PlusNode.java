package munk.emesp.node.operator;

import munk.emesp.Expression;
import munk.emesp.VariableValues;
import munk.emesp.visitor.ExpressionVisitor;

public class PlusNode extends AbstractOperatorNode {

	public PlusNode(Expression leftChild, Expression rightChild) {
		super(leftChild, rightChild);
	}

	@Override
	public double eval(VariableValues varVal) {
		return getLeftChild().eval(varVal) + getRightChild().eval(varVal);
	}

	@Override
	public Expression getDerivative(String variable) {
		Expression left = getLeftChild().getDerivative(variable);
		Expression right = getRightChild().getDerivative(variable);
		
		return new PlusNode(left, right);
	}

	@Override
	public String getSymbol() {
		return "+";
	}
	
	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
