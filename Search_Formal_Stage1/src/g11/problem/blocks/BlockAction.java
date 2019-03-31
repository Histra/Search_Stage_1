package g11.problem.blocks;

import core.problem.Action;

public class BlockAction extends Action{
	
	public BlockAction(int displacement) {
		super();
		this.setDisplacement(displacement);
	}
	
	private int displacement;// -3, -2, -1, 1, 2, 3
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	public int getDisplacement() {
		return displacement;
	}
	public void setDisplacement(int displacement) {
		this.displacement = displacement;
	}
	
}
