package g11.problem.npuzzle;

import core.problem.Action;

public class NPuzzleAction extends Action {
	
	public NPuzzleAction(int dir) {/// ���캯��
		super();
		this.setDirection(dir);
	}
	static int time = 1;/// ���ڼ�¼�����˶��ٴ�Draw����
	static char[] DIR = new char[] {'U', 'L', 'D', 'R'};
	private int direction;// ��ǰ�ƶ�����  up:1; left:2; down:3; right:4
	@Override
	public void draw() {/// ���
		System.out.println("   ��");
		System.out.println("   ��" + (time++) + "(#, " + DIR[this.direction - 1] + ")");
		System.out.println("   ��");		
	}
	public int getDirection() {/// get����
		return direction;
	}
	public void setDirection(int direction) {/// set����
		this.direction = direction;
	}

}
