package core.astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import core.problem.State;

//The set of all leaf nodes available for expansion at any given point
public class Fringe {
	//从Fringe中取出耗散值最小的节点
	public Node pop() {
		map.remove(nodes.element().getState());
		return nodes.remove();
	}
	
	//将一个节点插入到Fringe中
	public void insert(Node node){
		//Fix me
		map.put(node.getState(), node);
		nodes.add(node);
	}
	
	//判断Finge中是否存在状态为state的节点
	public boolean contains(State state) {
		/*
		 * equals Reference : https://www.cnblogs.com/dolphin0520/p/3592500.html 
		 */
		if (map.containsKey(state)) {
			return true;
		}else
			return false;
	}
	
	//返回Fringe中，其状态与state相同的节点；如果不存在，则返回null
	public Node revisited(State state) {
		if (map.containsKey(state)) {
			return map.get(state);
		}else
			return null;
	}
	
	//Fringe是否为空
	public boolean isEmpty() {
		return nodes.isEmpty(); 
	}
		
	//用节点to代替Finge中的from节点
	public void replace(Node from, Node to) {
		nodes.remove(from); 
		nodes.add(to);	
		map.remove(from.getState());
		map.put(to.getState(), to);
		from = null;///from节点已经不再需要
	}
	Queue<Node> nodes = new PriorityQueue<Node>();/// 优先队列
	Map<State, Node>map = new HashMap<State, Node>();/// map
}
