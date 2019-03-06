/*
 * 2016年3月29日 
 */
package org.kvlibdemo.study.algorithm.tree;

/**
 * @author Kevin
 *
 */
public class KTreeMap<K extends Comparable<? super K>, V> {

	Node root;

	public void put(K key, V value) {
		if (root == null) {
			root = new Node();

		} else {

		}
	}

	private void add(Node node, Pair p) {
		if (p.compareTo(node.p1) < 0) {
			if (node.left == null) {
				node.left = createNode(p);
			} else {
				add(node.left, p);
			}
		} else if (p.compareTo(node.p1) == 0) {
			node.p1 = p;
		}
	}

	private Node createNode(Pair p) {
		Node node = new Node();
		node.p1 = p;
		return node;
	}

	class Node {
		Pair p1;
		Pair p2;
		Node left;
		Node center;
		Node right;

		void swapPair() {
			Pair temp = p1;
			p1 = p2;
			p2 = temp;
		}
	}

	class Pair implements Comparable<Pair> {
		K key;
		V value;

		Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(Pair o) {
			return this.key.compareTo(o.key);
		}
	}
}
