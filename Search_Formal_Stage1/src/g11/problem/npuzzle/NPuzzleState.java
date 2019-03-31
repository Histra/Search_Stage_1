package g11.problem.npuzzle;

import java.util.Arrays;
import java.util.Random;

import core.problem.State;
import core.runner.SearchRunner;

public class NPuzzleState extends State {
	
	public NPuzzleState(int size, byte[] state) {/// 构造函数1
		super();
		this.setState(state);
		
		this.setEmpty();
		this.setSize(size);
		this.setHeuristic();
		this.setReverseSum();
		this.setHashCode();
	}
	
	public NPuzzleState(int size, byte[] state, long zober) {/// 构造函数2
		super();
		this.setState(state);
		this.setSize(size);
		this.setEmpty();
		this.setHeuristic();
		this.setZoberist(zober);
	}
	
	private void setZoberist(long zober) {/// Zoberist set方法
		this.hashCode = (int) zober;
	}

	private byte[] state;/// 用于存放当前状态
	private int empty;/// 空格所在位置
	private int size;/// 状态的大小
	private int heuristic;/// 评价函数值
	private int reverseSum;/// 逆序对和
	private int hashCode;/// 哈希数值
 
	@Override
	public void draw() {/// 格式化输出
		int num = this.size;
		
		System.out.print("+");
		for (int i = 0; i < num; i ++) {
			System.out.print("---+");
		}
		System.out.println();
		
		for (int i = 0; i < num; i ++) {
			System.out.print("|");
			for (int j = 0; j < num; j ++) {
				if (this.state[i* num +j] != 0) {
					System.out.printf("%2d |", this.state[i * num + j]);
				}else {
					System.out.printf(" # |");
				}
			}
			System.out.println();
			
			System.out.print("+");
			for (int ii = 0; ii < num; ii ++) {
				System.out.print("---+");
			}
			System.out.println();
		}
	}


	public byte[] getState() {/// get
		return state;
	}


	public void setState(byte[] state) {/// set
		this.state = state;
	}


	public int getEmpty() {/// get
		return empty;
	}


	public void setEmpty() {/// set
		int empty = 0;
		for (int i = 0; i < this.state.length; i ++) {/// 遍历state数组
			if (this.state[i] == 0) {
				empty = i;
				break;
			}
		}
		this.empty = empty;
	}


	public int getSize() {/// get
		return size;
	}


	public void setSize(int size) {/// set
		this.size = size;
	}


	public int getHeuristic() {/// get
		return heuristic;
	}

	public void setHeuristic() {/// set. 曼哈顿距离和
		int heur = 0;
		for (int i = 0; i < this.getState().length; i ++) {
			if (i != this.getEmpty())
				heur += getDis(i, this.getState()[i] - 1, this.getSize());/// 曼哈顿距离
		}
		this.heuristic = heur;
	}

	public static int getDis(int start, int end, int size) {/// 求曼哈顿距离
		int sLine = start / size + 1; /// 开始位置的行数
		int sColumn = start % size + 1;/// 开始位置的列数
		int eLine = end / size + 1;/// 终止位置的行数
		int eColumn = end % size + 1;/// 终止位置的列数
		
		return Math.abs(eLine - sLine) + Math.abs(eColumn - sColumn);/// 曼哈顿距离的计算
	}
	
	public int getReverseSum() {/// get
		return reverseSum;
	}
	
	public void setReverseSum() {/// 堆排序求逆序对和，O(nlogn)
		/*
		 * 逆序对参考：
		 * 		https://www.cnblogs.com/adelalove/p/8484644.html
		 * 		https://blog.csdn.net/afei__/article/details/82951905
		 */
		byte[] s = new byte[this.state.length];
		s = Arrays.copyOf(this.state, this.state.length);
		this.reverseSum = merge_sort(s, 0, this.state.length) - this.empty;
	}
	
	private int merge_sort(byte[] s, int start, int end) {
		int reverseSum = 0;
		if (start != end - 1) {
			int mid = (start + end) / 2;
			reverseSum += merge_sort(s, start, mid);/// 左数组逆序对和
			reverseSum += merge_sort(s, mid, end);/// 右数组逆序对和
			reverseSum += merge(s, start, mid, end);/// 左右逆序对之和
		}
		return reverseSum;		
	}

	private int merge(byte[] s, int start, int mid, int end) {
		if (s == null || start < 0 || end > s.length) {/// 非法情况
			return 0;
	    }
		byte[] temp = new byte[end - start];
		int reversesum = 0;
		int i = start; /// 左数组指针
        int j = mid; /// 右数组指针
        int k = 0; 
		while(i < mid && j < end) {
			if (s[i] <= s[j])
				temp[k++] = s[i++];
			else {
				temp[k++] = s[j++];
				reversesum += (mid - i);/// 这个地方是核心
			}	
		}
		if (i != mid) {/// 复制
			System.arraycopy(s, i, temp, k, mid - i);
			/*
			 * arraycopy: https://blog.csdn.net/csdn_dengfan/article/details/78213492
			 */
		}
		if (j != end) {/// 复制
			System.arraycopy(s, j, temp, k, end - j);
		}
        System.arraycopy(temp, 0, s, start, temp.length);/// 复制
        return reversesum;
	}
	
	
	@Override
	public boolean equals(Object obj) {/// 重写euqals方法
		// TODO Auto-generated method stub
		return this.hashCode() == obj.hashCode();
	}


	public int hashCode() {/// 重写
		// TODO Auto-generated method stub
		return this.getHashCode();
	}


	public int getHashCode() {/// get
		return hashCode;
	}


	public void setHashCode() {/// 使用zoberist进行hash
		long zob = 0;
		for (int i = 0; i < this.state.length; i ++) {
			zob = zob^(SearchRunner.getZoberist())[i][this.getState()[i]];
		}
		this.hashCode = (int)zob;/// 强制类型转换
	}
}
