package g11.problem.npuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.runner.SearchRunner;
import g11.problem.blocks.BlockState;
import xu.problem.mc.McState;

public class NPuzzleProblem extends Problem{
	
	private int size;/// NPuzzle问题的规模
	
	public NPuzzleProblem(int size, byte[] initialState, byte[] goalState) {/// 构造函数
		super((new NPuzzleState(size, initialState)), (new NPuzzleState(size, goalState)));
		this.size = size;
	}
	
	@Override
	public boolean solvable() {/// 判断当前问题可解与否
		NPuzzleState initstate = ((NPuzzleState)this.getInitialState());/// 获得出事状态数组
		
		int empty = initstate.getEmpty();/// 获得空格位置
		int reverseSum = initstate.getReverseSum();/// 获得逆序对数
		/*
		 * 可解条件Reference: http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
		 */
		if (this.size % 2 != 0) {
			if (reverseSum % 2 == 0)
				return true;
			else
				return false;
		}else {
			if (( (empty / this.size + 1) + reverseSum) % 2 == 0)
				return true;
			else
				return false;
		}
	}
	
	@Override
	public State result(State parent, Action action) {
		int direction = ((NPuzzleAction)action).getDirection();/// 获得移动的方向
		byte[] newState;/// 新的存放状态的数组
		newState = Arrays.copyOf(((NPuzzleState)parent).getState(), ((NPuzzleState)parent).getState().length);/// 拷贝
		/// DIR数组用于存放方向更改值
		int[] DIR = new int[] {-((NPuzzleState)parent).getSize(), -1, ((NPuzzleState)parent).getSize(), 1};
		// Swap实现
		byte temp = newState[((NPuzzleState)parent).getEmpty()];
		newState[((NPuzzleState)parent).getEmpty()] = 
				newState[((NPuzzleState)parent).getEmpty() + DIR[direction - 1]];
		newState[((NPuzzleState)parent).getEmpty() + DIR[direction - 1]] = temp;
		
		long newzober = ((NPuzzleState)parent).getHashCode();/// 新状态的hash值
		int empty = ((NPuzzleState)parent).getEmpty();/// 获得空格的位置
	
		int displacement = DIR[direction - 1];/// 获得方向更改值
		
		int tempty = ((NPuzzleState)parent).getState()[empty];/// 空格位置的数码
		int tdis = ((NPuzzleState)parent).getState()[empty + displacement];/// 要移动位置的数码
		/// 求新状态的zoberist值
		newzober = newzober^SearchRunner.getZoberist()[empty][tempty]^SearchRunner.getZoberist()[empty][tdis]^
				SearchRunner.getZoberist()[empty+displacement][tdis]^SearchRunner.getZoberist()[empty+displacement][tempty];
		State nextState = new NPuzzleState( ((NPuzzleState)parent).getSize(), newState, newzober);///生成新状态调用构造函数2
		return nextState;
	}

	@Override
	public int stepCost(State parent, Action action) {
		return 1;// 肯定是1啦
	}

	@Override
	public int heuristic(State state) {
		return ((NPuzzleState)state).getHeuristic();/// 调用NPuzzle类的getHeuristic方法
	}
	
	@Override
	public ArrayList<Action> Actions(State state) {
		ArrayList<Action> actions = new ArrayList<>();
		int empty = ((NPuzzleState)state).getEmpty();/// 获得当前state状态的空格位置
		int[] D = new int[4];/// 用于存放方向的更改值
		int num = this.size;
		D[0] = -num; D[1] = -1; D[2] = num; D[3] = 1;/// 更新D数组
		for (int i = 0; i < D.length; i ++) {
			int pos = empty + D[i];
			if (i % 2 == 0) {
				if (pos >= 0 && pos < ((NPuzzleState)state).getState().length) {/// 判断是否合法
					NPuzzleAction na = new NPuzzleAction(i + 1);/// 生成新的动作
					actions.add(na);
				}
			}else {
				if (pos >= (empty / num) * num && pos < (empty / num + 1) * num) {/// 判断是否合法
					NPuzzleAction na = new NPuzzleAction(i + 1);/// 生成新的动作
					actions.add(na);
				}
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
		
		action.draw();
		child.draw();		
	}

	public int getSize() {/// get
		return size;
	}

	public void setSize(int size) {/// set
		this.size = size;
	}
	
}
