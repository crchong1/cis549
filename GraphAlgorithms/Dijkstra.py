import heapq 

class Dijkstra:
    def getShortestPath(graph, src, tgt):        
        l = []
        if(src == tgt): 
            return []
        
        heap = heapq.heapify([]) 
        fromMap = {} # create a mapping of children and their shortest path parents
        vertices = Set() # graph.vertexSet() get vertices from graph
        for(V v : vertices) 
            // initialize every vertex with weight as positive infinity and add to heap
            heap.add(v, Double.POSITIVE_INFINITY)
            from.put(v, src)
        
        heap.decreaseKey(src, 0.0) // initialize the distance of source to 0
        while(!heap.isEmpty()) 
            V vertex = heap.peek() // get min distance vertex
            double distToU = heap.getArray().get(heap.getKeys().get(vertex)).getKey() // get key of vertex
            vertex = heap.extractMin()
            Set<V> neighbors = graph.neighbors(vertex)
            for(V neigh : neighbors) 
                if(heap.containsValue(neigh) && !heap.isEmpty())  // check if already seen
                    double weight = graph.getWeight(vertex, neigh).get()
                    double distToV = heap.getArray().get(heap.getKeys().get(neigh)).getKey()
                    if(distToV > distToU + weight)  // check min distance
                        heap.decreaseKey(neigh, distToU + weight) // update min distance
                        from.put(neigh, vertex) // update mapping
                    
        
        curr = tgt
        l.add(curr)
        while(curr != src):
            curr = from[curr]
            l.append(curr)
        #   build path from tgt to source
        l.reverse() # reverse this to get path from src to tgt
        return l
    

