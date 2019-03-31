package core.runner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import core.astar.AStar;
import core.astar.Fringe;
import core.astar.Node;
import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import g11.problem.blocks.BlockProblem;
import g11.problem.npuzzle.NPuzzleProblem;
import g11.problem.npuzzle.NPuzzleState;
import xu.problem.mc.McProblem;
import xu.problem.mc.McState;

public class SearchRunner {

	public SearchRunner() {

	}
	static private final String INPUT = "C:\\Users\\Histranger\\Desktop\\AI_project1\\Project_1\\in.txt";
	static private final String OUTPUT = "C:\\Users\\Histranger\\Desktop\\AI_project1\\Project_1\\out.txt";
	public static void ResetIO() {/// 文件读写
	      FileInputStream instream = null;
	      PrintStream outstream = null;
	   
	      try {
	          instream = new FileInputStream(INPUT);
	          outstream = new PrintStream(new FileOutputStream(OUTPUT));
	          System.setIn(instream);
	          System.setOut(outstream);
	      } catch (Exception e) {
	          System.err.println("Error Occurred.");
	      }
	   
	      Scanner in = new Scanner(System.in);
	      for (;in.hasNext();) {
	          int x = in.nextInt();
	          System.out.println(x);
	      }
	   
	      System.err.println("done.");		
	}
	
	private static int MAX = Math.max(16, 25);/// 定义Zoberist数组的边界最大值
	private static long[][] zoberist;/// Zoberist数组
	
	public static void setZoberist() {/// 设置Zoberist数组，静态函数
		int N = MAX * MAX;
		zoberist = new long[N][N];
		Random random = new Random();
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j ++) {
				zoberist[i][j] = random.nextLong();
			}
		}///(i, j)代表(位置 , 状态)
	}
	public static long[][] getZoberist(){/// 获得Zoberist数组，静态函数
		return zoberist;
	}
	
	public static void main(String[] args) {
		final boolean flag = false;/// 是否需要输入到文件
		if (flag)	ResetIO();
		
		//testMC(); ///测试MC
			
		test3Puzzle();/// 测试3-Puzzle问题
		
		//test4Puzzle();/// 测试4-Puzzle问题
		
		//testNPuzzle();/// 测试N-Puzzle问题
		
		//testBlock(); /// 测试滑动积木块问题
	}
	
	public static void testBlock() {/// 测试滑动积木块函数
		
		final int JIE = 9;/// 阶数	
		// black 1; white -1; empty 0
		//byte[] goalState = {-1, -1, -1, 1, 1, 1, 0};/// 何为goalState？
		//byte[] initState = {1, 1, 1, -1, -1, -1, 0};
		byte[] goalState = new byte[JIE * 2 + 1]; byte[] initState = new byte[JIE * 2 + 1];
		for (int i = 0; i < JIE; i ++) {
			goalState[i] = -1;
			initState[i] = 1;
			goalState[JIE + i] = 1;
			initState[JIE + i] = -1;
		}
		goalState[JIE * 2] = 0; initState[JIE * 2] = 0;
		//BlockProblem _3Block = new BlockProblem(7, initState, goalState);
		BlockProblem _3Block = new BlockProblem(JIE * 2 + 1, initState, goalState);
		if (!_3Block.solvable()) {
			System.out.println("The Problem is UNSOLVABLE !");
		}else {
			long startTime = System.currentTimeMillis();
			
			//NPuzzleState initial = (NPuzzleState)_3Puzzle.getInitialState();
			AStar a = new AStar(_3Block);
			Node node = a.Search();
			//node.getState();/// 为什么getState会调用draw？
			ArrayList<Node> record = new ArrayList<Node>();
			record.add(node);
			while (node.getParent() != null) {
				node = node.getParent();
				record.add(node);
			}
			
			for (int i = record.size() - 1; i >= 0; i --) {// 0 ~ size-1
				record.get(i).draw();
				/*if (record.get(i).getAction() != null) {
					record.get(i).getAction().draw();
				}*/
			}
			/*
			 * ArrayList遍历 reference: https://www.cnblogs.com/zxg-6/p/5683560.html
			 */
			long endTime = System.currentTimeMillis();
			System.out.println("Time Consuming : " + (endTime - startTime) + "ms");
		}		
	}
	
	public static void test3Puzzle() {	
		setZoberist();// 使用Zoberist进行hash
		
		byte[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 0};/// 最终状态
		byte[] initState = {8, 6, 7, 2, 5, 4, 3, 0, 1};/// 起始状态Sample_1
		//byte[] initState = {6, 4, 7, 8, 5, 0, 3, 2, 1};/// 起始状态Sample_2
 		NPuzzleProblem _3Puzzle = new NPuzzleProblem(3, initState, goalState); /// 生成问题
 		
		if (!_3Puzzle.solvable()) {/// 判断可解与否
			System.out.println("The Problem is UNSOLVABLE !");
		}else {/// 可解
			long startTime = System.currentTimeMillis();/// 获取当前系统时间
			AStar a = new AStar(_3Puzzle);/// 生成一个AStar对象
			Node node = a.Search();/// A*搜索
			ArrayList<Node> record = new ArrayList<Node>();/// 用于存放路径，并正序输出
			/// 把最优路径写入到record数组中
			record.add(node);
			while (node.getParent() != null) {
				node = node.getParent();
				record.add(node);
			}
			/// 正序输出
			for (int i = record.size() - 1; i >= 0; i --) {// 0 ~ size-1
				if (record.get(i).getAction() != null) {
					record.get(i).getAction().draw();
				}
				record.get(i).draw();
			}
			/*
			 * ArrayList遍历用法reference: https://www.cnblogs.com/zxg-6/p/5683560.html
			 */
			long endTime = System.currentTimeMillis();/// 获得当前系统时间
			System.out.println("Time Consuming : " + (endTime - startTime) + "ms");/// 输出执行时间
		}	
	}
	
	public static void test4Puzzle() {
		setZoberist();// 使用Zoberist进行hash
		
		byte[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};/// 目标状态
		/// Samples
		//byte[] initState = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 4, 2, 1, 0, 5, 3};	//OUT OF MEMORY
		byte[] initState = {8, 13, 0, 6, 1, 15, 9, 14, 3, 4, 5, 11, 7, 2, 10, 12};	//1719ms
		//byte[] initState = {2, 9, 5, 11, 8, 3, 4, 14, 7, 10, 1, 12, 0, 15, 6, 13};	//39085ms
		//byte[] initState = {4, 7, 0, 9, 12, 10, 11, 8, 14, 6, 15, 1, 2, 5, 3, 13};	//114180ms
		NPuzzleProblem _4Puzzle = new NPuzzleProblem(4, initState, goalState);/// 生成问题
		if (!_4Puzzle.solvable()) {/// 判断可解与否
			System.out.println("The Problem is UNSOLVABLE !");
		}else {/// 可解
			long startTime = System.currentTimeMillis();/// 获得当前系统时间
			AStar a = new AStar(_4Puzzle);/// 生成一个Astar对象
			Node node = a.Search();// A*搜索
			ArrayList<Node> record = new ArrayList<Node>();/// 用于存放路径，并正序输出
			/// 把最优路径写入到record数组中
			record.add(node);
			while (node.getParent() != null) {
				node = node.getParent();
				record.add(node);
			}
			/// 正序输出
			for (int i = record.size() - 1; i >= 0; i --) {// 0 ~ size-1
				if (record.get(i).getAction() != null) {
					record.get(i).getAction().draw();
				}
				record.get(i).draw();
			}
			long endTime = System.currentTimeMillis();/// 获得系统当前时间
			System.out.println("Time Consuming : " + (endTime - startTime) + "ms");/// 输出执行时间
		}		
	}

	public static void testNPuzzle() {
		setZoberist();// 使用Zoberist进行hash
		byte[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 
				16, 17, 18, 19, 20, 21, 22, 23, 24, 0};/// 目标状态
		byte[] initState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 
				16, 17, 18, 19, 20, 21, 22, 23, 0, 24};/// 初始状态
		int size = (int) Math.sqrt(initState.length);
		NPuzzleProblem _NPuzzle = new NPuzzleProblem(size, initState, goalState);/// 生成问题
		if (!_NPuzzle.solvable()) {/// 判断可解与否
			System.out.println("The Problem is UNSOLVABLE !");
		}else {/// 可解
			long startTime = System.currentTimeMillis();/// 获得当前系统时间
			AStar a = new AStar(_NPuzzle);/// 生成一个Astar对象
			Node node = a.Search();// A*搜索
			ArrayList<Node> record = new ArrayList<Node>();/// 用于存放路径，并正序输出
			/// 把最优路径写入到record数组中
			record.add(node);
			while (node.getParent() != null) {
				node = node.getParent();
				record.add(node);
			}
			/// 正序输出
			for (int i = record.size() - 1; i >= 0; i --) {// 0 ~ size-1
				if (record.get(i).getAction() != null) {
					record.get(i).getAction().draw();
				}
				record.get(i).draw();
			}
			long endTime = System.currentTimeMillis();/// 获得系统当前时间
			System.out.println("Time Consuming : " + (endTime - startTime) + "ms");/// 输出执行时间
		}		
	}
	
	public static void testMC() {/// Teacher's Code, Ellipsis annotation
		Problem mc = new McProblem(3, 2);
		AStar a = new AStar(mc);
		Node node = a.Search();
		node.getState();

		node.draw();
		node.getAction().draw();
		while (node.getParent() != null) {
			node = node.getParent();
			node.draw();
			if (node.getAction() != null)
				node.getAction().draw();
		}

	}
}
