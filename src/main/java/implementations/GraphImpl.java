package implementations;

public class GraphImpl<T> extends Graph<T> {
    /**
     * Creates an empty graph implementation.
     */
    public GraphImpl() {
        super();
    }

    /**
     * Checks whether there is an edge between two vertices in the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if an edge exists between the vertices, false otherwise
     */
    public boolean hasEdge(T vertex1, T vertex2) {
        int index1 = super.findVertex(vertex1);
        int index2 = super.findVertex(vertex2);

        if (index1 == -1 || index2 == -1) {
            return false;
        }

        return super.adjMatrix[index1][index2];
    }


}
