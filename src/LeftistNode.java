//description: a representation of a node in a leftist tree

class LeftistNode<T> {
	int s; // the s value
	int value; // the key value
	T data;// data stored in the node

	LeftistNode<T> leftChild;
	LeftistNode<T> rightChild;

	// getters and setters
	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LeftistNode<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(LeftistNode<T> leftChild) {
		this.leftChild = leftChild;
	}

	public LeftistNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(LeftistNode<T> rightChild) {
		this.rightChild = rightChild;
	}

	// constructor
	public LeftistNode(T data, int value) {
		this.data = data;
		this.value = value;
		this.s = 0;
		this.leftChild = null;
		this.rightChild = null;
	}

	public static void main(String args[]) {
		System.out.println("hello world");
	}
}