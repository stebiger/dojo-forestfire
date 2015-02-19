package test.java.de.oc.dojo.forestfire;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.de.oc.dojo.forestfire.EmptySimulationProcessor;
import main.java.de.oc.dojo.forestfire.ForestFireSimulator;
import main.java.de.oc.dojo.forestfire.Position;

import org.junit.Test;

public class EmptySimulationProcessorTest {

	@Test
	public void testBurnToEmptyForRuleBurn() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = 'w';
		char out = proc.ruleBurn(in);
		
		
		assertEquals("Expect burning cell to be empty after process", '.',out);
	}
	
	@Test
	public void testEmptyRemainsEmptyForRuleBurn() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = '.';
		char out = proc.ruleBurn(in);
		
		assertEquals("Expect burning cell to be empty after process", '.',out);
	}
	
	@Test
	public void testSelfBurningTreeToBurn() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = 'T';
		double F = ForestFireSimulator.F - 0.00000001;
		
		char out = proc.ruleSelfBurning(in, F);
		assertEquals("Expect Tree to burn", 'w', out);
		
	}

	@Test
	public void testSelfBurningTreeStaysTree() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = 'T';
		double F = ForestFireSimulator.F + 0.00000001;
		
		char out = proc.ruleSelfBurning(in, F);
		assertEquals("Expect Tree to burn", 'T', out);
		
	}

	@Test
	public void testSelfBurninNoTreeStaysNoTree() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = '.';
		double F = ForestFireSimulator.F + 0.00000001;
		
		char out = proc.ruleSelfBurning(in, F);
		assertEquals("Expect no Tree to nothing", '.', out);
	}
	
	
	@Test
	public void testNewTree() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = '.';
		double P = ForestFireSimulator.P - 0.00000001;
		
		char out = proc.ruleNewTree(in, P);
		assertEquals("Expect new Tree", 'T', out);
	}
	
	@Test
	public void testNoNewTree() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();
		char in = '.';
		double P = ForestFireSimulator.P + 0.00000001;
		
		char out = proc.ruleNewTree(in, P);
		assertEquals("Expect new Tree", '.', out);
	}
	
	/**
	 * Hello Mr. Burns!
	 */
	@Test
	public void testNeighbourBurnsTreeBurns() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();

		String line0 = new String("T.w.T.TT..T.T..");
		String line1 = new String("TTTTTTT...T.TT.");
		String line2 = new String("TTT..TTTTT.T..T");
		
		List<String> land = new ArrayList<String>();
		land.add(line0);
		land.add(line1);
		land.add(line2);
		
		Position pos = new Position(1,2);
		char out = proc.ruleNeighbourBurning(land, pos);
		
		assertEquals("This Tree is on fire!", 'w', out);
	}
	
	/**
	 * Goodbye Mr. Burns!
	 */
	@Test
	public void testNeighbourIceIceBaby() {
		EmptySimulationProcessor proc = new EmptySimulationProcessor();

		String line0 = new String("T.w.T.TT..T.T..");
		String line1 = new String("TT.TTTT...T.TT.");
		String line2 = new String("TTT..TTTTT.T..T");
		
		List<String> land = new ArrayList<String>();
		land.add(line0);
		land.add(line1);
		land.add(line2);
		
		Position pos = new Position(1,2);
		char out = proc.ruleNeighbourBurning(land, pos);
		char expected = '.';
		
		assertEquals("Ice ice baby!", expected, out);
	}

}
