package munk.emesp.test;

import static munk.emesp.ExpressionParser.parse;
import static org.junit.Assert.assertEquals;
import munk.emesp.Expression;
import munk.emesp.FunctionMap;
import munk.emesp.exceptions.ExpressionParseException;
import munk.emesp.node.function.Cos;
import munk.emesp.node.values.ValueNode;
import munk.emesp.node.values.VariableNode;
import munk.emesp.visitor.CollapseConstantsVisitor;

import org.junit.Before;
import org.junit.Test;

public class CollapseConstantsVisitorTest {

	private CollapseConstantsVisitor collapser = CollapseConstantsVisitor.getInstance();
	private FunctionMap fm;
	
	@Before
	public void init() {
		fm = new FunctionMap();
		fm.loadDefaultFunctions();
	}
	
	@Test
	public void testVisitPlusNode() throws ExpressionParseException {
		Expression collapsable = parse("0 + 2 + (2 + 5)", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(9);
		assertEquals(collapsed, expected);
		
		
		Expression unCollapsable = parse("2 + x", null);
		Expression nonCollapsed = unCollapsable.accept(collapser);
		assertEquals(unCollapsable, nonCollapsed);
	}
	
	@Test
	public void plusZeroCollapsing() throws ExpressionParseException {
		Expression collapsable = parse("0 + x", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new VariableNode("x");
		
		assertEquals(collapsed, expected);
	}

	@Test
	public void testVisitMinusNode() throws ExpressionParseException {
		Expression collapsable = parse("7 - 5", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(2);
		assertEquals(collapsed, expected);
		
		
		Expression unCollapsable = parse("2 - x", null);
		Expression nonCollapsed = unCollapsable.accept(collapser);
		assertEquals(unCollapsable, nonCollapsed);
	}
	
	@Test
	public void minusZeroCollapsing() throws ExpressionParseException {
		Expression collapsable = parse("0 - x", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new VariableNode("x");
		
		assertEquals(collapsed, expected);
	}

	@Test
	public void testVisitDivideNode() throws ExpressionParseException {
		Expression collapsable = parse("7 / 5", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(7. / 5);
		assertEquals(collapsed, expected);
		
		
		Expression unCollapsable = parse("1 / x", null);
		Expression nonCollapsed = unCollapsable.accept(collapser);
		assertEquals(unCollapsable, nonCollapsed);
		

	}
	
	@Test
	public void divideWithOne() throws ExpressionParseException {
		Expression divideWithOne = parse("x / 1", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected2 = new VariableNode("x");
		assertEquals(x, expected2);
	}

	@Test
	public void testVisitMultiplyNode() throws ExpressionParseException {
		Expression collapsable = parse("7 * 5", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(35);
		assertEquals(collapsed, expected);
		
		
		Expression unCollapsable = parse("2 * x", null);
		Expression nonCollapsed = unCollapsable.accept(collapser);
		assertEquals(unCollapsable, nonCollapsed);
	}
	
	@Test
	public void multiplyZeroCollapsing() throws ExpressionParseException {
		Expression collapsable = parse("0 * x", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(0);
		
		assertEquals(collapsed, expected);
	}
	
	@Test
	public void multiplyOneCollapsing() throws ExpressionParseException {
		Expression collapsable = parse("1 * y", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new VariableNode("y");
		
		assertEquals(collapsed, expected);
	}

	@Test
	public void testVisitPowerNode() throws ExpressionParseException {
		Expression divideWithOne = parse("2^4", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new ValueNode(16);
		assertEquals(x, expected);
	}
	
	@Test
	public void ToZerothPower() throws ExpressionParseException {
		Expression divideWithOne = parse("2^0", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new ValueNode(1);
		assertEquals(x, expected);
	}
	
	@Test
	public void OneToNthPower() throws ExpressionParseException {
		Expression divideWithOne = parse("1^1000", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new ValueNode(1);
		assertEquals(x, expected);
	}
	
	@Test
	public void PowerOfZero() throws ExpressionParseException {
		Expression divideWithOne = parse("0^4", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new ValueNode(0);
		assertEquals(x, expected);
	}

	@Test
	public void testVisitValueNode() throws ExpressionParseException {
		Expression divideWithOne = parse("1", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new ValueNode(1);
		assertEquals(x, expected);
	}

	@Test
	public void testVisitVariableNode() throws ExpressionParseException {
		Expression divideWithOne = parse("x", null);
		Expression x = divideWithOne.accept(collapser);
		Expression expected = new VariableNode("x");
		assertEquals(x, expected);
	}

	@Test
	public void sinFunctionCollapse() throws ExpressionParseException {
		Expression sin = parse("sin(0 * ( 1 + 20 * (9 + 3)))", fm);
		Expression sinCollaps = sin.accept(collapser);
		ValueNode sin0 = ValueNode.ZERO;
		
		assertEquals(sinCollaps, sin0);
		
		
		Expression cos = parse("cos(0 * ( 1 + 20 * (9 + 3)))", fm);
		Expression cosCollaps = cos.accept(collapser);
		ValueNode cos0 = ValueNode.ONE;
		
		assertEquals(cosCollaps, cos0);
		
		
		Expression cosX = parse("cos(x)", fm);
		Expression cosXCollaps = cosX.accept(collapser);
		Cos cosToX = new Cos(new VariableNode("x"), false);
		
		assertEquals(cosXCollaps, cosToX);
	}
	
	@Test
	public void AllInOneCollapsing() throws ExpressionParseException {
		Expression collapsable = parse("5 * 7 - 0*(x + 0*y) - 1*(2*7 + 7)", null);
		Expression collapsed = collapsable.accept(collapser);
		Expression expected = new ValueNode(5*7 - (2*7 + 7));
		
		assertEquals(collapsed, expected);
	}

}
