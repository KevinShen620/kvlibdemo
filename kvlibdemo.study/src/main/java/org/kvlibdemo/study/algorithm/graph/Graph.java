/*
 * 2016年7月7日 
 */
package org.kvlibdemo.study.algorithm.graph;

import java.util.ArrayList;

/**
 * @author Kevin
 *
 */
public class Graph {

	private int E;

	private final int V;

	private ArrayList<Integer>[] adj;// 邻接表

	@SuppressWarnings("unchecked")
	public Graph(int V) {
		this.V = V;
		this.E = 0;
		adj = new ArrayList[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new ArrayList<>();
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	public Graph addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
		E++;
		return this;
	}

}
