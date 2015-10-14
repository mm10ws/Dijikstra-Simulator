//description: implements dijisktras algorithm using leftist tree and fibonacci tree. 
//input can be a file (ex. test.txt) or a graph can be generated randomly. 
//The output is only a list of min distances from the source to all other vertices. 
//in random mode the runtime of each scheme is also in the output.

import java.util.*;
import java.io.*;

class dijikstra {

	int dist[]; // stores distance values
	int adjMatrix[][]; //adjacency matrix
	int vertexCount; //matrix size n
	Set<Integer> s; // stores visited vertices
	LeftistTree<Vertex> l; //leftist tree
	FibonacciTree<Vertex> f; //fib tree

	// used only for DFS to check if graph is connected
	int tempMatrix[][];
	int visited[];

	// Getters and setters
	public int[] getDist() {
		return dist;
	}

	public void setDist(int[] dist) {
		this.dist = dist;
	}

	public int[][] getAdjMatrix() {
		return adjMatrix;
	}

	public void setAdjMatrix(int[][] adjMatrix) {
		this.adjMatrix = adjMatrix;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public Set<Integer> getS() {
		return s;
	}

	public void setS(Set<Integer> s) {
		this.s = s;
	}

	public LeftistTree<Vertex> getL() {
		return l;
	}

	public void setL(LeftistTree<Vertex> l) {
		this.l = l;
	}

	public FibonacciTree<Vertex> getF() {
		return f;
	}

	public void setF(FibonacciTree<Vertex> f) {
		this.f = f;
	}

	// constructor
	public dijikstra(int vertexCount) {
		int size = 1 + vertexCount;
		adjMatrix = new int[size][size];
		dist = new int[size];
		this.vertexCount = vertexCount;
		l = new LeftistTree<Vertex>();
		f = new FibonacciTree<Vertex>();
		s = new HashSet<Integer>();
	}

	// set up adjacency matrix and distance list before executing
	public void initialize() {
		for (int i = 1; i <= vertexCount; i++) {
			for (int j = 1; j <= vertexCount; j++) {
				this.adjMatrix[i][j] = adjMatrix[i][j];
			}
		}

		// set all distances to be max value
		for (int i = 1; i <= vertexCount; i++) {
			dist[i] = Integer.MAX_VALUE;
		}

	}

	//main alg to solve using dijisktra
	//the final argument will determine if a leftist or fibonacci tree is used
	//LeftistOrFib == true -> use leftist tree
	//LeftistOrFib == false -> use fibonacci tree
	public void dijikstraSolve(int start, int adjMatrix[][],
			boolean LeftistOrFib) { // if true then it will solve with leftist,
									// else with fib
		int workingVertex;
		initialize();

		if (LeftistOrFib == true) { // evaluate with leftist tree
			// with leftist tree

			// dijikstra algorithm
			Vertex n1 = new Vertex(start, 0);
			LeftistNode<Vertex> l1 = new LeftistNode<Vertex>(n1, 0);
			l.insert(l1, 0);
			dist[start] = 0;
			while (!l.empty()) {
				int weight = -1;
				int newDist = -1;
				workingVertex = l.removeMin().data.node; // get best vertex

				s.add(workingVertex);

				for (int i = 1; i <= vertexCount; i++) { // for every neighbor
															// of workingVertex
					if (!s.contains(i)) {
						if (adjMatrix[workingVertex][i] != Integer.MAX_VALUE) {
							weight = adjMatrix[workingVertex][i];
							newDist = dist[workingVertex] + weight;
							if (newDist < dist[i]) { // update if better path
														// found
								dist[i] = newDist;
								Vertex n2 = new Vertex(i, dist[i]);
								LeftistNode<Vertex> l2 = new LeftistNode<Vertex>(
										n2, dist[i]);
								l.insert(l2, dist[i]);
							}

						}
					}
				}
			}
		} else {// evaluate with fibonacci tree
				// with fib tree

			// dijikstra algorithm
			Vertex n1 = new Vertex(start, 0);
			FibonacciNode<Vertex> l1 = new FibonacciNode<Vertex>(n1, 0);
			f.insert(l1, 0);
			dist[start] = 0;
			while (!f.empty()) {
				int weight = -1;
				int newDist = -1;
				workingVertex = f.removeMin().data.node; // get best vertex
				s.add(workingVertex);

				for (int i = 1; i <= vertexCount; i++) { // for every neighbor
															// of workingVertex
					if (!s.contains(i)) {
						if (adjMatrix[workingVertex][i] != Integer.MAX_VALUE) {
							weight = adjMatrix[workingVertex][i];
							newDist = dist[workingVertex] + weight;
							if (newDist < dist[i]) { // update if better path
														// found
								dist[i] = newDist;
								Vertex n2 = new Vertex(i, dist[i]);
								FibonacciNode<Vertex> l2 = new FibonacciNode<Vertex>(
										n2, dist[i]);
								f.insert(l2, dist[i]);
							}

						}
					}
				}
			}
		}

	}

	// generate a random matrix given size and num of edges
	public int[][] generateMatrix(int n, int edges) {

		int aMatrix[][] = new int[n][n];
		Random random = new Random();

		do {
			for (int i = 0; i < edges; i++) {
				int v1;
				int v2;
				int c = random.nextInt(1000) + 1; // generate edge cost
				do {
					v1 = random.nextInt(n);
					v2 = random.nextInt(n);

				} while (v1 == v2 || aMatrix[v1][v2] != 0); // check if edge is
															// valid

				aMatrix[v1][v2] = c;
				aMatrix[v2][v1] = c;
			}
		} while (!validateMatrix(aMatrix)); // check if graph is connected
		return aMatrix;
	}

	// check if matrix is connected using a DFS
	public boolean validateMatrix(int[][] test) {
		tempMatrix = test;
		int n = test[0].length;
		visited = new int[n];

		validateMatrixHelper(0); // start DFS from first vertex

		// check if every vertex is visited by DFS
		for (int i = 0; i < visited.length; i++) {
			if (visited[i] == 0) {
				return false;
			}
		}
		return true;
	}

	// DFS search of graph. results of DFS are stored in visited array
	public void validateMatrixHelper(int start) {
		int next;
		visited[start] = 1;
		for (next = 0; next < tempMatrix[0].length; next++) {
			if (visited[next] == 0 && tempMatrix[start][next] != 0) {
				validateMatrixHelper(next);
			}
		}

	}

	public static void main(String args[]) {

		if (args.length == 2) { // either -f or -l options are used
			if (args[0].equals("-l")) {

				String fileName = args[1];
				int aMatrix[][];
				int x;
				int m;
				int n;
				try {
					File fr = new File(fileName);
					Scanner sr = new Scanner(fr);

					// read in args from file
					x = sr.nextInt();
					n = sr.nextInt();
					m = sr.nextInt();
					aMatrix = new int[n + 1][n + 1];

					// set up matrix
					for (int i = 1; i <= n; i++) {
						for (int j = 1; j <= n; j++) {
							if (i != j) {
								aMatrix[i][j] = Integer.MAX_VALUE;
							}
						}
					}

					// set the edge costs in matrix
					for (int i = 0; i < m; i++) {
						int v1 = sr.nextInt();
						int v2 = sr.nextInt();
						int cost = sr.nextInt();
						aMatrix[v1 + 1][v2 + 1] = cost;
						aMatrix[v2 + 1][v1 + 1] = cost;
					}

					sr.close();

					// start dijikstra using leftist tree
					dijikstra d = new dijikstra(n);
					d.dijikstraSolve(x + 1, aMatrix, true);

					for (int i = 1; i <= d.dist.length - 1; i++) {
						// print out only the min path distances
						System.out.println(d.dist[i]);
					}

				} catch (Exception e) {
					System.out.println("Error reading file");
				}

			} else if (args[0].equals("-f")) {

				String fileName = args[1];
				int aMatrix[][];
				int x;
				int m;
				int n;
				try {
					File fr = new File(fileName);
					Scanner sr = new Scanner(fr);

					// read in args from file
					x = sr.nextInt();
					n = sr.nextInt();
					m = sr.nextInt();
					aMatrix = new int[n + 1][n + 1];

					// set up matrix
					for (int i = 1; i <= n; i++) {
						for (int j = 1; j <= n; j++) {
							if (i != j) {
								aMatrix[i][j] = Integer.MAX_VALUE;
							}
						}
					}

					// set the edge costs in matrix
					for (int i = 0; i < m; i++) {
						int v1 = sr.nextInt();
						int v2 = sr.nextInt();
						int cost = sr.nextInt();
						aMatrix[v1 + 1][v2 + 1] = cost;
						aMatrix[v2 + 1][v1 + 1] = cost;
					}
					sr.close();

					// start dijikstra using fibonacci tree
					dijikstra d = new dijikstra(n);
					d.dijikstraSolve(x + 1, aMatrix, false);

					for (int i = 1; i <= d.dist.length - 1; i++) {
						// print out only the min path distances
						System.out.println(d.dist[i]);
					}

				} catch (Exception e) {
					System.out.println("Error reading file");
				}
			} else {
				System.out.println("incorrect arguemnts");
			}
		}

		else if (args.length == 4) { // -r option is being used
			if (args[0].equals("-r")) {

				// get args
				int n = Integer.parseInt(args[1]);
				int d = Integer.parseInt(args[2]);
				int x = Integer.parseInt(args[3]);
				int result[][];
				int aMatrix[][] = new int[n + 1][n + 1];

				// calculate number of edges (density * n * (n-1)/2)
				int numEdges = (int) ((d / 100.0f) * ((n * (n - 1)) / 2));

				dijikstra dl = new dijikstra(n);
				dijikstra df = new dijikstra(n);

				// generate random matrix with parameters
				result = dl.generateMatrix(n, numEdges);

				// set up matrix for dijikstra
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						if (i != j) {
							if (result[i - 1][j - 1] == 0) {
								aMatrix[i][j] = Integer.MAX_VALUE;
							} else {
								aMatrix[i][j] = result[i - 1][j - 1];
							}

						}
					}
				}

				// measure time for leftist
				long start1 = 0;
				long stop1 = 0;
				start1 = System.currentTimeMillis();
				dl.dijikstraSolve(x + 1, aMatrix, true);
				stop1 = System.currentTimeMillis();
				long timeLeftist = stop1 - start1;

				// measure time for fibonacci
				long start2 = 0;
				long stop2 = 0;
				start2 = System.currentTimeMillis();
				df.dijikstraSolve(x + 1, aMatrix, false);
				stop2 = System.currentTimeMillis();
				long timeFib = stop2 - start2;

				// print out the times
				System.out.println("Time LeftistTree: " + timeLeftist);
				System.out.println("Time fibonacciTree: " + timeFib);

				// print out only the min path distances
				for (int i = 1; i <= dl.dist.length - 1; i++) {
					System.out.println(dl.dist[i]);
				}

			} else {
				System.out.println("incorrect arguemnts");
			}

		} else {
			// error
			System.out.println("incorrect arguemnts");
		}
	}
}