/*
 * 2016年7月7日 
 */
package org.kvlibdemo.study.algorithm.graph;

import java.util.LinkedList;

/**
 * @author Kevin
 *
 */
public class Dfs {

	private boolean marked[];

	private int[] pathTo;

	private int[] path;

	private int pathIndex = 0;

	private Graph graph;

	public Dfs(Graph graph) {
		this.graph = graph;
		marked = new boolean[graph.V()];
		pathTo = new int[graph.V()];
		path = new int[graph.V()];
	}

	public void dfs1(int v) {
		marked[v] = true;
		path[pathIndex++] = v;
		for (int w : graph.adj(v)) {
			if (!marked[w]) {
				pathTo[w] = v;
				dfs1(w);
			}
		}
	}

	public void dfs2(int s) {
		LinkedList<Integer> stack = new LinkedList<>();
		stack.push(s);
		int pre = -1;
		while (!stack.isEmpty()) {
			int v = stack.pop();
			if (!marked[v]) {
				marked[v] = true;
				path[pathIndex++] = v;
				if (pre >= 0) {
					pathTo[v] = pre;
				}
				pre = v;
				for (int w : graph.adj(v)) {
					if (!marked[w]) {
						stack.push(w);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph(10);
		g.addEdge(0, 1).addEdge(0, 2).addEdge(1, 3).addEdge(2, 3).addEdge(3, 4)
				.addEdge(4, 5).addEdge(4, 6).addEdge(4, 7).addEdge(5, 8)
				.addEdge(7, 9);
		Dfs dfs = new Dfs(g);
		dfs.dfs1(0);
		for (int i = 0; i < g.V(); i++) {
			System.out.print(dfs.path[i] + " ");
		}
		System.out.println();
		Dfs dfs2 = new Dfs(g);
		dfs2.dfs2(0);
		for (int i = 0; i < g.V(); i++) {
			System.out.print(dfs2.path[i] + " ");
		}
	}
}
