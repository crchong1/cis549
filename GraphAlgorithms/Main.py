from Graph import DirectedGraph, UndirectedGraph, Node
from TopoSort import TopoSort
from BFS import BFS
from DFS import DFS

def parser(graph):
    f = open('./data.txt', "r")
    line = f.readline()
    while line:
        values = line.split(".")
        start = values[0].strip() # remove more whitespace
        s = values[1].replace("(", "").replace(")", "") # remove paranthesis
        s = s.strip() # remove whitespace
        s = s[:-1] # remove comma at the end
        endNodes = s.split(",")
        for n in endNodes:
            n = n.strip()
            graph.updateNeighbor(start, n)
        line = f.readline()
    f.close()
    return graph

def main():
    g = DirectedGraph()
    parsedGraph = parser(g)
    # g.updateNeighbor(1,2)
    # g.updateNeighbor(1,3)
    # g.updateNeighbor(1,4)
    # g.updateNeighbor(2,5)
    # g.updateNeighbor(2,6)

    t = TopoSort(parsedGraph.getGraph())
    print(t.topologicalSort())

if __name__ == "__main__":
    main()