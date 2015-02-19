package main.java.de.oc.dojo.forestfire;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CAL
 */
public class EmptySimulationProcessor implements ISimulationProcessor {

	@Override
	public List<String> process(List<String> land) {
		int height = land.size();
		int width = land.get(0).length();

		List<String> newLand = new ArrayList<String>(height);

		for (int row = 0; row < height; row++) {
			StringBuffer newRow = new StringBuffer();
			for (int column = 0; column < width; column++) {
				Position current = new Position(row, column);
				char newCell = getNewCell(land, current);
				newRow.append(newCell);
			}
			newLand.add(newRow.toString());
		}
		return newLand;

	}

	private char getNewCell(List<String> land, Position current) {

		char currentCell = land.get(current.getRow()).charAt(
				current.getColumn());
		double prob = Math.random();

		char cellAfterBurn = ruleBurn(currentCell);
		if (currentCell != cellAfterBurn) {
			return cellAfterBurn;
		}

		char cellAfterSelfBurn = ruleSelfBurning(currentCell, prob);
		if (currentCell != cellAfterSelfBurn) {
			return cellAfterSelfBurn;
		}

		char cellAfterNewTree = ruleNewTree(currentCell, prob);
		if (currentCell != cellAfterNewTree) {
			return cellAfterNewTree;
		}

		char cellAfterNeighbour = ruleNeighbourBurning(land, current);
		if (currentCell != cellAfterNeighbour) {
			return cellAfterNeighbour;
		}

		return currentCell;
	}

	public char ruleBurn(char in) {
		if (in == 'w') {
			return '.';
		}

		return in;

	}

	public char ruleSelfBurning(char in, double probability) {
		if (in == 'T' && probability < ForestFireSimulator.F) {
			return 'w';
		}
		return in;
	}

	public char ruleNewTree(char in, double probability) {
		if (in == '.' && probability < ForestFireSimulator.P) {
			return 'T';
		}
		return in;
	}

	public char ruleNeighbourBurning(List<String> land, Position pos) {

		int y = pos.getRow();
		int x = pos.getColumn();

		char currentForestItem = land.get(y).charAt(x);

		if (currentForestItem != 'T') {
			return currentForestItem;
		}

		boolean burningTop = false;
		boolean burningRight = false;
		boolean burningLeft = false;
		boolean burningBottom = false;

		burningTop = isFieldBurning(land, x, y - 1);
		burningRight = isFieldBurning(land, x + 1, y);
		burningLeft = isFieldBurning(land, x - 1, y);
		burningBottom = isFieldBurning(land, x, y + 1);

		if (burningTop || burningRight || burningLeft || burningBottom) {
			return 'w';
		}

		return currentForestItem;

	}

	private boolean isFieldBurning(List<String> land, int x, int y) {

		if (x < 0) {
			return false;
		}

		if (y < 0) {
			return false;
		}

		if (x >= 267) {
			return false;
		}

		if (y >= 200) {
			return false;
		}

		if (land.get(y).charAt(x) == 'w') {
			return true;
		}

		return false;
	}

}
