package core.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import core.problem.State;

//The set that remembers every expanded node
public class Explored {
	
	public void insert(State state){
		nodes.add(state);
	}
	
	public boolean contains(State state) {
		return nodes.contains(state); 
	}
	Set<State>nodes = new HashSet<State>();/// set
}
