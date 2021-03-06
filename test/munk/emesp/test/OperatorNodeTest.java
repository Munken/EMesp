package munk.emesp.test;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;
import munk.emesp.Expression;
import munk.emesp.ExpressionParser;
import munk.emesp.exceptions.ExpressionParseException;
import munk.emesp.node.operator.*;
import munk.emesp.node.values.ValueNode;

import org.junit.Test;

public class OperatorNodeTest {
	
	double left = 200;
	double right = 24.5;
	
	@Test
	public void divideNodeTest() throws ExpressionParseException {
		DivideNode div = new DivideNode(new ValueNode(left), new ValueNode(right));
		
		double value = div.eval(null);
		
		Assert.assertEquals(value, left/right, 10E-5);
		
		Expression ex = ExpressionParser.parse(left + "/" + right, null);
		
		assertEquals(div, ex);
	}
	
	@Test
	public void multiplyNodeTest() throws ExpressionParseException {
		MultiplyNode div = new MultiplyNode(new ValueNode(left), new ValueNode(right));
		
		double value = div.eval(null);
		
		Assert.assertEquals(value, left*right, 10E-5);
		
		Expression ex = ExpressionParser.parse(left + "*" + right, null);
		
		assertEquals(div, ex);
	}
	
	@Test
	public void plusNodeTest() throws ExpressionParseException {
		PlusNode div = new PlusNode(new ValueNode(left), new ValueNode(right));
		
		double value = div.eval(null);
		
		Assert.assertEquals(value, left+right, 10E-5);
		
		Expression ex = ExpressionParser.parse(left + "+" + right, null);
		
		assertEquals(div, ex);
	}
	
	@Test
	public void minusNodeTest() throws ExpressionParseException {
		MinusNode div = new MinusNode(new ValueNode(left), new ValueNode(right));
		
		double value = div.eval(null);
		
		Assert.assertEquals(value, left-right, 10E-5);
		
		Expression ex = ExpressionParser.parse(left + "-" + right, null);
		
		assertEquals(div, ex);
	}
	
	@Test
	public void powerNodeTest() throws ExpressionParseException {
		PowerNode div = new PowerNode(new ValueNode(left), new ValueNode(right));
		
		double value = div.eval(null);
		
		Assert.assertEquals(value, Math.pow(left, right), 10E-5);
		
		Expression ex = ExpressionParser.parse(left + "^" + right, null);
		
		assertEquals(div, ex);
	}

}
