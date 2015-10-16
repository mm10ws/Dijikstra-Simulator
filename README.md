# Dijikstra-Simulator

An implementation of Dijikstra's algorithm to find the length of the shortest path from a specified source vertex to every other vertex in a undirected graph. The  performance of the algorithm is tested with a leftist tree and a fibonnaci heap in the simulation.


Usage:

1. make

2. Use the following:

    a. To generate a random graph with n nodes, d edge density, and source node x. The output will be the runtime of the algorithm using a leftist tree and fibonacci tree. The shortest paths will also be listed:
    
        i. java dijikstra –r n d x
        
    b. To run the algorithm using a leftist tree and a user defined graph. The output will be a list of the shortest paths:
    
        i. java dijikstra –l file-name
        
    c. To run the algorithm using a fibonacci tree and a user defined graph. The output will be a list of the shortest paths:
    
        i. java dijikstra –f file-name
        
3. User defined graphs are specified in a file with the following format:

        x //source vertex
        
        n m //n is the number of vertices and m is the number of edges
        
        v1 v2 c1 //the edge from vertex v1 to vertex v2 with cost c1
        
        v2 v3 c2 //the edge from vertex v2 to vertex v3 with cost c2
        
        ... //list continues until all edges are defined
        
               

