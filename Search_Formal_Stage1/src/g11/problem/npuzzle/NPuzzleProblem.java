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
	
	private int size;/// NPuzzle����Ĺ�ģ
	
	public NPuzzleProblem(int size, byte[] initialState, byte[] goalState) {/// ���캯��
		super((new NPuzzleState(size, initialState)), (new NPuzzleState(size, goalState)));
		this.size = size;
	}
	
	@Override
	public boolean solvable() {/// �жϵ�ǰ����ɽ����
		NPuzzleState initstate = ((NPuzzleState)this.getInitialState());/// ��ó���״̬����
		
		int empty = initstate.getEmpty();/// ��ÿո�λ��
		int reverseSum = initstate.getReverseSum();/// ����������
		/*
		 * �ɽ�����Reference: http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
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
		int direction = ((NPuzzleAction)action).getDirection();/// ����ƶ��ķ���
		byte[] newState;/// �µĴ��״̬������
		newState = Arrays.copyOf(((NPuzzleState)parent).getState(), ((NPuzzleState)parent).getState().length);/// ����
		/// DIR�������ڴ�ŷ������ֵ
		int[] DIR = new int[] {-((NPuzzleState)parent).getSize(), -1, ((NPuzzleState)parent).getSize(), 1};
		// Swapʵ��
		byte temp = newState[((NPuzzleState)parent).getEmpty()];
		newState[((NPuzzleState)parent).getEmpty()] = 
				newState[((NPuzzleState)parent).getEmpty() + DIR[direction - 1]];
		newState[((NPuzzleState)parent).getEmpty() + DIR[direction - 1]] = temp;
		
		long newzober = ((NPuzzleState)parent).getHashCode();/// ��״̬��hashֵ
		int empty = ((NPuzzleState)parent).getEmpty();/// ��ÿո��λ��
	
		int displacement = DIR[direction - 1];/// ��÷������ֵ
		
		int tempty = ((NPuzzleState)parent).getState()[empty];/// �ո�λ�õ�����
		int tdis = ((NPuzzleState)parent).getState()[empty + displacement];/// Ҫ�ƶ�λ�õ�����
		/// ����״̬��zoberistֵ
		newzober = newzober^SearchRunner.getZoberist()[empty][tempty]^SearchRunner.getZoberist()[empty][tdis]^
				SearchRunner.getZoberist()[empty+displacement][tdis]^SearchRunner.getZoberist()[empty+displacement][tempty];
		State nextState = new NPuzzleState( ((NPuzzleState)parent).getSize(), newState, newzober);///������״̬���ù��캯��2
		return nextState;
	}

	@Override
	public int stepCost(State parent, Action action) {
		return 1;// �϶���1��
	}

	@Override
	public int heuristic(State state) {
		return ((NPuzzleState)state).getHeuristic();/// ����NPuzzle���getHeuristic����
	}
	
	@Override
	public ArrayList<Action> Actions(State state) {
		ArrayList<Action> actions = new ArrayList<>();
		int empty = ((NPuzzleState)state).getEmpty();/// ��õ�ǰstate״̬�Ŀո�λ��
		int[] D = new int[4];/// ���ڴ�ŷ���ĸ���ֵ
		int num = this.size;
		D[0] = -num; D[1] = -1; D[2] = num; D[3] = 1;/// ����D����
		for (int i = 0; i < D.length; i ++) {
			int pos = empty + D[i];
			if (i % 2 == 0) {
				if (pos >= 0 && pos < ((NPuzzleState)state).getState().length) {/// �ж��Ƿ�Ϸ�
					NPuzzleAction na = new NPuzzleAction(i + 1);/// �����µĶ���
					actions.add(na);
				}
			}else {
				if (pos >= (empty / num) * num && pos < (empty / num + 1) * num) {/// �ж��Ƿ�Ϸ�
					NPuzzleAction na = new NPuzzleAction(i + 1);/// �����µĶ���
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
