//description: fibonacci tree data structure

import java.util.*;

class FibonacciTree<T> {

	FibonacciNode<T> smallest; // the min node

	int count; // amount of nodes

	// getters and setters
	public FibonacciNode<T> getMinNode() {
		return smallest;
	}

	public void setMinNode(FibonacciNode<T> minNode) {
		this.smallest = minNode;
	}

	public int getnNodes() {
		return count;
	}

	public void setnNodes(int nNodes) {
		this.count = nNodes;
	}

	// constructor
	public FibonacciTree() {
		// super();
		this.smallest = null;
		this.count = 0;
	}

	public void removeAll() {
		// super();
		this.smallest = null;
		this.count = 0;
	}

	public boolean empty() {
		return smallest == null;
	}

	//inserts a new element into the tree
	public void insert(FibonacciNode<T> newNode, int newVal) {
		newNode.key = newVal;

		// concatenate node into min list
		if (smallest != null) {
			newNode.rt = smallest.rt;
			newNode.lt = smallest;

			smallest.rt = newNode;
			newNode.rt.lt = newNode;

			if (smallest.key > newVal) {
				smallest = newNode;
			}

		} else {
			smallest = newNode;
		}

		count = count + 1;
	}

	//removes the smallest element and performs a pairwise combine
	public FibonacciNode<T> removeMin() {
		FibonacciNode<T> tmp;
		FibonacciNode<T> min = smallest;
		int childCount = min.degree;
		FibonacciNode<T> workingNode = min.child;

		if (min != null) {

			for (int i = childCount; i > 0; i--) { // move children of min up
				tmp = workingNode.rt;
				workingNode.rt.lt = workingNode.lt;
				workingNode.lt.rt = workingNode.rt;
				workingNode.lt = smallest;
				workingNode.rt = smallest.rt;
				smallest.rt = workingNode;
				workingNode.rt.lt = workingNode;
				workingNode.parent = null;
				workingNode = tmp;

			}

			// get min out of list
			min.lt.rt = min.rt;
			min.rt.lt = min.lt;

			if (min == min.rt) {
				smallest = null;
			} else {
				// set new min
				smallest = min.rt;

				// pairwise combine

				// this is the upper bound of the degree list used for pairwise
				// combine
				// size = floor(log(n)/log(phi)) where phi is the golden ratio
				int dsize = ((int) Math.floor(Math.log(count)
						/ (Math.log((1.0 + Math.sqrt(5.0)) / 2.0)))) + 1;

				List<FibonacciNode<T>> degList = new ArrayList<FibonacciNode<T>>(
						dsize);

				// Initialize degree array
				for (int i = 0; i < dsize; i++) {
					degList.add(null);
				}

				// Find the number of root nodes.
				int topNodes = 0;
				FibonacciNode<T> newMin = smallest;

				if (newMin != null) {
					do {
						topNodes = topNodes + 1;
						newMin = newMin.rt;
					} while (newMin != smallest);

				}

				// check if there is a node with same degree
				while (topNodes > 0) {

					int deg = newMin.degree;
					FibonacciNode<T> next = newMin.rt;

					while (true) {
						FibonacciNode<T> sameDeg = degList.get(deg);
						if (sameDeg == null) {

							break;
						}

						// found same degree node, need to merge
						if (sameDeg.key < newMin.key) {
							FibonacciNode<T> temp;
							temp = newMin;
							newMin = sameDeg;
							sameDeg = temp;
						}

						// add samedeg as child of newmin

						// first remove child
						sameDeg.lt.rt = sameDeg.rt;
						sameDeg.rt.lt = sameDeg.lt;

						// adopt child
						sameDeg.parent = newMin;

						if (newMin.child != null) {
							sameDeg.lt = newMin.child;
							sameDeg.rt = newMin.child.rt;
							newMin.child.rt = sameDeg;
							sameDeg.rt.lt = sameDeg;

						} else {
							newMin.child = sameDeg;
							sameDeg.rt = sameDeg;
							sameDeg.lt = sameDeg;
						}

						// set child cut
						sameDeg.childCut = false;
						// new child adds to degree
						newMin.degree = newMin.degree + 1;

						degList.set(deg, null);
						deg = deg + 1;
					}

					// store combined node
					degList.set(deg, newMin);

					newMin = next;
					topNodes = topNodes - 1;
				}

				smallest = null; // remove and reconstruct tree

				for (int i = 0; i <= dsize - 1; i++) {
					FibonacciNode<T> result = degList.get(i);
					if (result == null) {
						continue;
					}

					// add value
					if (smallest == null) {
						smallest = result;

					} else {

						result.lt.rt = result.rt;
						result.rt.lt = result.lt;

						result.rt = smallest.rt;
						result.lt = smallest;

						smallest.rt = result;
						result.rt.lt = result;

						if (smallest.key > result.key) {
							smallest = result;
						}
					}
				}

			}

			// decrease node count
			count = count - 1;
		}

		return min;
	}

	//decrease key operation with cascading cut
	public void decreaseKey(FibonacciNode<T> currentValue, int newValue) {
		if (newValue > currentValue.key) {
			return;
		}

		currentValue.key = newValue;

		FibonacciNode<T> currParent = currentValue.parent;

		if (currParent != null) {
			if (currParent.key > currentValue.key) {

				// perform a cut(currentValue from currParent);

				currentValue.lt.rt = currentValue.rt;
				currentValue.rt.lt = currentValue.lt;
				currParent.degree = currParent.degree - 1;

				if (currParent.degree == 0) {
					currParent.child = null;
				}
				if (currParent.child == currentValue) {
					currParent.child = currentValue.rt;
				}

				// set the child cut value
				currentValue.childCut = false;

				// insert cut child into tree
				currentValue.parent = null;
				currentValue.rt = smallest.rt;
				currentValue.lt = smallest;
				currentValue.rt.lt = currentValue;
				smallest.rt = currentValue;

				// perform cascading cut
				cCut(currParent);
			}

		}

		if (smallest.key > currentValue.key) {
			smallest = currentValue;
		}
	}

	//function to perform a cascading cut from a starting node
	public void cCut(FibonacciNode<T> start) {
		FibonacciNode<T> sParent = start.parent;

		if (sParent != null) {
			// set child cut value
			if (start.childCut) {

				// perform a cut (start from sParent)

				start.lt.rt = start.rt;
				start.rt.lt = start.lt;
				sParent.degree = sParent.degree - 1;

				if (sParent.degree == 0) {
					sParent.child = null;
				}
				if (sParent.child == start) {
					sParent.child = start.rt;
				}

				// set the child cut value
				start.childCut = false;

				// insert cut child into tree
				start.parent = null;
				start.rt = smallest.rt;
				start.lt = smallest;
				start.rt.lt = start;
				smallest.rt = start;

				// continue cCut up tree
				cCut(sParent);

			} else {
				// stop cCut and set child cut value
				start.childCut = true;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("hello world");
	}

}