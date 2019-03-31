package g11.problem.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.runner.SearchRunner;

public class BlockProblem extends Problem{
	
	public BlockProblem(int size, byte[] initState, byte[] goalState) {
		super(new BlockState(size, initState), new BlockState(size, goalState));
		
		this.size = size;
	}
	
	private int size;

	@Override
	public boolean solvable() {
		return true;
	}

	@Override
	public State result(State parent, Action action) {
		int displacement = ((BlockAction)action).getDisplacement();
		
		byte[] temp = Arrays.copyOf(((BlockState)parent).getState(), ((BlockState)parent).getState().length);
		int empty = ((BlockState)parent).getEmpty();
		
		byte t = temp[empty];
		temp[empty] = temp[empty + displacement];
		temp[empty + displacement] = t;
		
		long newzober = ((BlockState)parent).getHashCode();/// youwenti!!
		
		int tt = ((BlockState)parent).getState()[empty + displacement];
		if (tt == -1)
			tt = 2;
		newzober = newzober^SearchRunner.getZoberist()[empty][tt]^SearchRunner.getZoberist()[empty + displacement][tt];
		
		State nextState = new BlockState(((BlockState)parent).getSize(), temp, newzober);
				
		return nextState;
	}

	@Override
	public int stepCost(State parent, Action action) {
		int displacement = ((BlockAction)action).getDisplacement();
		if (displacement == 3 || displacement == -3) {
			return 2;
		}else {
			return 1;
		}
	}

	@Override
	public int heuristic(State state) {
		return ((BlockState)state).getHeuristic();
	}

	@Override
	public ArrayList<Action> Actions(State state) {
		ArrayList<Action> actions = new ArrayList<Action>();
		final int RULE[] = {-3, -2, -1, 1, 2, 3};
		
		int empty = ((BlockState)state).getEmpty();
		for (int i = 0; i < RULE.length; i ++) {
			if (empty + RULE[i] >= 0 && empty + RULE[i] < ((BlockState)state).getState().length) {
				BlockAction newaction = new BlockAction(RULE[i]);
				actions.add(newaction);
			}
		}
		return actions;
	}

	@Override
	public void drawWorld() {
		this.getInitialState().draw();
		
	}

	@Override
	public void simulateResult(State parent, Action action) {
		State child = result(parent, action);
		
		action.draw();/// ÔõÃ´draw£¿
		child.draw();	
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
