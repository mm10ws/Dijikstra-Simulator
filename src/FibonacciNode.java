//description: a representation of a node in a fibonacci tree

class FibonacciNode<T> {

	int key; 
	FibonacciNode<T> lt; //left child
	FibonacciNode<T> rt; //right child
	// int value;
	FibonacciNode<T> parent; //parent
	FibonacciNode<T> child; //first child
	int degree; //number of children
	boolean childCut; 

	T data; //node data

	// getters and setters
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public FibonacciNode<T> getLeft() {
		return lt;
	}

	public void setLeft(FibonacciNode<T> left) {
		this.lt = left;
	}

	public FibonacciNode<T> getRight() {
		return rt;
	}

	public void setRight(FibonacciNode<T> right) {
		this.rt = right;
	}

	public FibonacciNode<T> getParent() {
		return parent;
	}

	public void setParent(FibonacciNode<T> parent) {
		this.parent = parent;
	}

	public FibonacciNode<T> getChild() {
		return child;
	}

	public void setChild(FibonacciNode<T> child) {
		this.child = child;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public boolean isMark() {
		return childCut;
	}

	public void setMark(boolean mark) {
		this.childCut = mark;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	//constructor
	public FibonacciNode(T data, int key) {
		this.key = key;
		this.data = data;
		lt = this;
		rt = this;

	}

	public static void main(String args[]) {
		System.out.println("hello world");
	}

}