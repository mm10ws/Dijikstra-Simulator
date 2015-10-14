//description: leftist tree data structure

class LeftistTree<T> {
	LeftistNode<T> root;

	
	//getters and setters
	public LeftistNode<T> getRoot() {
		return root;
	}

	public void setRoot(LeftistNode<T> root) {
		this.root = root;
	}

	// constructor
	public LeftistTree() {

		this.root = null;
	}

	// empty the tree
	public void removeAll() {
		this.root = null;
	}

	// combine two leftist trees into one
	public LeftistNode<T> meld(LeftistNode<T> a, LeftistNode<T> b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}

		// meld the right child of smaller rooted tree with other
		if (a.value > b.value) {
			b.rightChild = meld(b.rightChild, a);
			return meldHelper(b);
		} else {
			a.rightChild = meld(a.rightChild, b);

			return meldHelper(a);

		}
	}

	// performs swapping and updating s values
	public LeftistNode<T> meldHelper(LeftistNode<T> t) {
		if (t.leftChild != null) {
			if (t.rightChild.s > t.leftChild.s) {
				// swap left and right tree to maintain leftist property
				LeftistNode<T> temp = t.rightChild;
				t.rightChild = t.leftChild;
				t.leftChild = temp;
			} else {
				// good
			}

			// update s value
			t.s = 1 + t.rightChild.s;
		} else {
			t.leftChild = t.rightChild;
			t.rightChild = null;
		}
		return t;

	}

	// checks if tree is empty
	public boolean empty() {
		if (this.root == null) {
			return true;
		} else {
			return false;
		}
	}

	// adds a node in the tree my melding
	public void insert(LeftistNode<T> node, int value) {

		node.value = value;
		root = meld(node, root);
	}

	// return smallest element
	public LeftistNode<T> removeMin() {
		if (empty() == true) {
			return null;
		} else {
			LeftistNode<T> min = root;

			// meld left and right child of root
			root = meld(root.leftChild, root.rightChild);
			return min;
		}

	}

	// delete the node and replace it with new value
	public void decreaseKey(LeftistNode<T> currentValue, int newValue) {

		// check if new value is valid
		if (newValue < 0 || newValue > currentValue.value) {
			return;
		}
		if (root == currentValue) {

			currentValue.value = newValue;

		} else {

			delete(currentValue, newValue);

		}

	}

	// search for node starting from root using inorder traversal
	public void delete(LeftistNode<T> value, int newValue) {
		deleteHelper(root, null, value, newValue);
	}

	//helper function which performs the deletion from tree
	public boolean deleteHelper(LeftistNode<T> r, LeftistNode<T> parent,
			LeftistNode<T> value, int newValue) {

		if (r != null) {
			if (r == value) { // found node
				// save its children
				LeftistNode<T> left = r.leftChild;
				LeftistNode<T> right = r.rightChild;

				// unlink from parent
				if (parent != null) {
					if (parent.leftChild != null && parent.rightChild != null) {
						if (parent.leftChild.value == r.value) {
							parent.leftChild = null;
						} else {
							parent.rightChild = null;
						}
					} else if (parent.leftChild == null
							&& parent.rightChild != null) {
						parent.rightChild = null;
					} else if (parent.leftChild != null
							&& parent.rightChild == null) {
						parent.leftChild = null;
					} else {
						System.out.println("problem at deleteHelper");
					}

				}

				// remove node
				r.leftChild = null;
				r.rightChild = null;
				r.s = 0;

				fixTree(); // fix s values in tree
				rebalance(); // maintain leftist property by swapping trees if
								// needed

				// insert new value into tree
				LeftistNode<T> result = meld(root, left);
				meld(result, right);
				insert(r, newValue);
				return true;

			} else {
				// if not found contine checking its children
				boolean found = deleteHelper(r.leftChild, r, value, newValue);
				if (found) {
					return true;
				}

				return deleteHelper(r.rightChild, r, value, newValue);
			}

		} else {
			System.out.println("not found");
			return false;
		}

	}

	// start fixing s values at root
	public void fixTree() {
		fixTreeHelper(root);
	}

	// recalculate s values
	public int fixTreeHelper(LeftistNode<T> r) {
		if (r == null || r.leftChild == null || r.rightChild == null) {
			return 0;
		} else {
			r.s = Math.min(fixTreeHelper(r.leftChild),
					fixTreeHelper(r.rightChild)) + 1;
			return r.s;
		}

	}

	// start rebalancing at root
	public void rebalance() {
		rebalanceHelper(root);
		// System.out.println();
	}

	// rebalance tree
	private void rebalanceHelper(LeftistNode<T> r) {
		if (r != null) {
			if (r.leftChild != null && r.rightChild != null) {
				if (r.leftChild.s < r.rightChild.s) {
					// swap if leftist property is violated
					LeftistNode<T> temp = r.leftChild;
					r.leftChild = r.rightChild;
					r.rightChild = temp;
				}

			} else if (r.leftChild == null && r.rightChild != null) {
				// swap if leftist property is violated
				LeftistNode<T> temp = r.leftChild;
				r.leftChild = r.rightChild;
				r.rightChild = temp;
			} else {
				// System.out.println("problem at deleteHelper");
			}

			// continue rebalancing tree
			rebalanceHelper(r.leftChild);

			rebalanceHelper(r.rightChild);
		}
	}

	public static void main(String[] args) {
		System.out.println("hello world");
	}
}