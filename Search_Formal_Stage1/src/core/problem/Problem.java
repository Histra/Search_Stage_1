package core.problem;

import java.util.ArrayList;

public abstract class Problem {
	public Problem() {
		
	}
	public Problem(State initialState, State goal) {
		super();// �ڻ����е���super��Ҫ�����
		/*
		 * Super Reference: https://www.cnblogs.com/hasse/p/5023392.html
		 */
		this.initialState = initialState;
		this.goal = goal;
	}
	
	public abstract boolean solvable();

	//The resulting state from parent through action.
	public abstract State result(State parent, Action action);
	
	//The cost of the path from parent through action to its successors.
	public abstract int stepCost(State parent, Action action);
	
	//estimated cost of the cheapest path from the state to a goal state
	public abstract int heuristic(State state);
	
	//all the possible actions from current state.
	public abstract ArrayList<Action> Actions(State state);
	
	//test if the state is a goal.
	public boolean goalTest(State state) {
		return state.equals(goal);
	}
	
	public State getInitialState() {
		return initialState;
	}
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	//�軭���������World State������ܶ�ϸ�ڽ�����Ⱦ
	public abstract void drawWorld();
	
	//�軭��parent״̬��Action�ı仯����
	public abstract void simulateResult(State parent, Action action);

	private State initialState;
	private State goal;
	
	public State getGoal() {
		return goal;
	}

	public void setGoal(State goal) {
		this.goal = goal;
	}
}
