package g11.problem.blocks;

import core.problem.State;
import core.runner.SearchRunner;

public class BlockState extends State {
	
	BlockState(int size, byte[] state){
		super();
		
		this.setState(state);
		this.setEmpty();
		this.setSize(size);
		this.setHeuristic();
		this.setHashCode();
	}
	
	BlockState(int size, byte[] state, long zoberist){
		super();
		this.setState(state);
		this.setSize(size);
		this.setEmpty();
		this.setHeuristic();
		this.setZoberist(zoberist);
	}
	
	private void setZoberist(long zoberist) {
		this.hashCode = (int) zoberist;
		
	}

	private byte[] state;
	private int empty;
	private int size;
	private int heuristic;
	private int hashCode;
	
	@Override
	public void draw() {
		for (int i = 0; i < this.state.length; i ++) {
			if (this.state[i] == 1) {
				System.out.print("[ºÚ]");
			}else if (this.state[i] == -1) {
				System.out.print("[°×]");
			}else {
				System.out.print("[¿Õ]");
			}
		}	
		System.out.println("");
	}


	public byte[] getState() {
		return state;
	}


	public void setState(byte[] state) {
		this.state = state;
	}


	public int getEmpty() {
		return empty;
	}


	public void setEmpty() {
		for (int i = 0; i < this.state.length; i ++) {
			if (this.state[i] == 0) {
				this.empty = i;
				break;
			}
		}
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getHeuristic() {
		return heuristic;
	}


	public void setHeuristic() {
		int presum = 0;
		this.heuristic = 0;
		for (int i = 0; i < this.state.length; i ++){
			if(this.state[i] == 1)
				presum ++;
			if(this.state[i] == -1)
				this.heuristic += presum;
		}
	}


	public int getHashCode() {
		return hashCode;
	}


	public void setHashCode() {
		long zob = 0;
		for (int i = 0; i < this.state.length; i ++) {
			int temp;
			if (this.state[i] == -1)
				temp = 2;
			else 
				temp = this.state[i];
			zob = zob^(SearchRunner.getZoberist())[i][temp];
		}
		this.hashCode = (int)zob;
	}
	
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.hashCode() == obj.hashCode();
	}


	public int hashCode() {/// ´ýÈ·¶¨
		// TODO Auto-generated method stub
		return this.getHashCode();
	}

}
