package core.astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import core.problem.State;

//The set of all leaf nodes available for expansion at any given point
public class Fringe {
	//��Fringe��ȡ����ɢֵ��С�Ľڵ�
	public Node pop() {
		map.remove(nodes.element().getState());
		return nodes.remove();
	}
	
	//��һ���ڵ���뵽Fringe��
	public void insert(Node node){
		//Fix me
		map.put(node.getState(), node);
		nodes.add(node);
	}
	
	//�ж�Finge���Ƿ����״̬Ϊstate�Ľڵ�
	public boolean contains(State state) {
		/*
		 * equals Reference : https://www.cnblogs.com/dolphin0520/p/3592500.html 
		 */
		if (map.containsKey(state)) {
			return true;
		}else
			return false;
	}
	
	//����Fringe�У���״̬��state��ͬ�Ľڵ㣻��������ڣ��򷵻�null
	public Node revisited(State state) {
		if (map.containsKey(state)) {
			return map.get(state);
		}else
			return null;
	}
	
	//Fringe�Ƿ�Ϊ��
	public boolean isEmpty() {
		return nodes.isEmpty(); 
	}
		
	//�ýڵ�to����Finge�е�from�ڵ�
	public void replace(Node from, Node to) {
		nodes.remove(from); 
		nodes.add(to);	
		map.remove(from.getState());
		map.put(to.getState(), to);
		from = null;///from�ڵ��Ѿ�������Ҫ
	}
	Queue<Node> nodes = new PriorityQueue<Node>();/// ���ȶ���
	Map<State, Node>map = new HashMap<State, Node>();/// map
}
