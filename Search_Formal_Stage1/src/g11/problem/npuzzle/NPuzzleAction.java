package g11.problem.npuzzle;

import core.problem.Action;

public class NPuzzleAction extends Action {
	
	public NPuzzleAction(int dir) {/// 构造函数
		super();
		this.setDirection(dir);
	}
	static int time = 1;/// 用于记录调用了多少次Draw函数
	static char[] DIR = new char[] {'U', 'L', 'D', 'R'};
	private int direction;// 当前移动方向  up:1; left:2; down:3; right:4
	@Override
	public void draw() {/// 输出
		System.out.println("   ↓");
		System.out.println("   ↓" + (time++) + "(#, " + DIR[this.direction - 1] + ")");
		System.out.println("   ↓");		
	}
	public int getDirection() {/// get方法
		return direction;
	}
	public void setDirection(int direction) {/// set方法
		this.direction = direction;
	}

}
