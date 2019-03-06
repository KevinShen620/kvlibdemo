/*
 * 2016年7月7日 
 */
package org.kvlibdemo.study.algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kevin
 *
 */
public class Bfs {

	private Graph graph;

	private boolean[] marked;

	private int[] path;

	private int pathIndex = 0;

	private int[] pathTo;

	public Bfs(Graph graph) {
		this.graph = graph;
		marked = new boolean[graph.V()];
		pathTo = new int[graph.V()];
		path = new int[graph.V()];
	}

	public void bfs(int s) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(s);
		marked[s] = true;
		this.path[pathIndex++] = s;
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int w : graph.adj(v)) {
				if (!marked[w]) {
					marked[w] = true;
					pathTo[w] = v;
					path[pathIndex++] = w;
					queue.add(w);
				}
			}
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph(10);
		g.addEdge(0, 1).addEdge(0, 2).addEdge(1, 3).addEdge(2, 3).addEdge(3, 4)
				.addEdge(4, 5).addEdge(4, 6).addEdge(4, 7).addEdge(5, 8)
				.addEdge(7, 9);
		Bfs bfs = new Bfs(g);
		bfs.bfs(0);
		for (int i = 0; i < g.V(); i++) {
			System.out.print(bfs.path[i] + " ");
		}

	}

}
