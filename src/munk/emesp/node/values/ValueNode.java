package munk.emesp.node.values;

import munk.emesp.*;
import munk.emesp.visitor.ExpressionVisitor;

public class ValueNode extends AbstractExpression {
	
	private static final double TOLERANCE = 1E-4;
	public static final ValueNode MINUS_ONE = new ValueNode(-1);
	public static final ValueNode HALF = new ValueNode(0.5);
	public static final ValueNode ZERO = new ValueNode(0);	
	public static final ValueNode ONE = new ValueNode(1);
	public static final ValueNode TWO = new ValueNode(2);
	
	private double value = 0;

	public ValueNode(double d) {
		value = d;
	}

	/**
	Returns the value.
	*/
	public double eval(VariableValues varVal) {
		return value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public Expression getDerivative(String variable) {
		return ZERO;
	}

	@Override
	public void toString(StringBuffer buffer) {
		buffer.append(value);
	}
	
	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (this.getClass() == obj.getClass()) {
			ValueNode node = (ValueNode) obj;
			
			return Math.abs(value - node.value) < TOLERANCE;
		}
		return false;
	}

}
