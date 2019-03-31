package g11.problem.npuzzle;

import java.util.Arrays;
import java.util.Random;

import core.problem.State;
import core.runner.SearchRunner;

public class NPuzzleState extends State {
	
	public NPuzzleState(int size, byte[] state) {/// ���캯��1
		super();
		this.setState(state);
		
		this.setEmpty();
		this.setSize(size);
		this.setHeuristic();
		this.setReverseSum();
		this.setHashCode();
	}
	
	public NPuzzleState(int size, byte[] state, long zober) {/// ���캯��2
		super();
		this.setState(state);
		this.setSize(size);
		this.setEmpty();
		this.setHeuristic();
		this.setZoberist(zober);
	}
	
	private void setZoberist(long zober) {/// Zoberist set����
		this.hashCode = (int) zober;
	}

	private byte[] state;/// ���ڴ�ŵ�ǰ״̬
	private int empty;/// �ո�����λ��
	private int size;/// ״̬�Ĵ�С
	private int heuristic;/// ���ۺ���ֵ
	private int reverseSum;/// ����Ժ�
	private int hashCode;/// ��ϣ��ֵ
 
	@Override
	public void draw() {/// ��ʽ�����
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
		for (int i = 0; i < this.state.length; i ++) {/// ����state����
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

	public void setHeuristic() {/// set. �����پ����
		int heur = 0;
		for (int i = 0; i < this.getState().length; i ++) {
			if (i != this.getEmpty())
				heur += getDis(i, this.getState()[i] - 1, this.getSize());/// �����پ���
		}
		this.heuristic = heur;
	}

	public static int getDis(int start, int end, int size) {/// �������پ���
		int sLine = start / size + 1; /// ��ʼλ�õ�����
		int sColumn = start % size + 1;/// ��ʼλ�õ�����
		int eLine = end / size + 1;/// ��ֹλ�õ�����
		int eColumn = end % size + 1;/// ��ֹλ�õ�����
		
		return Math.abs(eLine - sLine) + Math.abs(eColumn - sColumn);/// �����پ���ļ���
	}
	
	public int getReverseSum() {/// get
		return reverseSum;
	}
	
	public void setReverseSum() {/// ������������Ժͣ�O(nlogn)
		/*
		 * ����Բο���
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
			reverseSum += merge_sort(s, start, mid);/// ����������Ժ�
			reverseSum += merge_sort(s, mid, end);/// ����������Ժ�
			reverseSum += merge(s, start, mid, end);/// ���������֮��
		}
		return reverseSum;		
	}

	private int merge(byte[] s, int start, int mid, int end) {
		if (s == null || start < 0 || end > s.length) {/// �Ƿ����
			return 0;
	    }
		byte[] temp = new byte[end - start];
		int reversesum = 0;
		int i = start; /// ������ָ��
        int j = mid; /// ������ָ��
        int k = 0; 
		while(i < mid && j < end) {
			if (s[i] <= s[j])
				temp[k++] = s[i++];
			else {
				temp[k++] = s[j++];
				reversesum += (mid - i);/// ����ط��Ǻ���
			}	
		}
		if (i != mid) {/// ����
			System.arraycopy(s, i, temp, k, mid - i);
			/*
			 * arraycopy: https://blog.csdn.net/csdn_dengfan/article/details/78213492
			 */
		}
		if (j != end) {/// ����
			System.arraycopy(s, j, temp, k, end - j);
		}
        System.arraycopy(temp, 0, s, start, temp.length);/// ����
        return reversesum;
	}
	
	
	@Override
	public boolean equals(Object obj) {/// ��дeuqals����
		// TODO Auto-generated method stub
		return this.hashCode() == obj.hashCode();
	}


	public int hashCode() {/// ��д
		// TODO Auto-generated method stub
		return this.getHashCode();
	}


	public int getHashCode() {/// get
		return hashCode;
	}


	public void setHashCode() {/// ʹ��zoberist����hash
		long zob = 0;
		for (int i = 0; i < this.state.length; i ++) {
			zob = zob^(SearchRunner.getZoberist())[i][this.getState()[i]];
		}
		this.hashCode = (int)zob;/// ǿ������ת��
	}
}
