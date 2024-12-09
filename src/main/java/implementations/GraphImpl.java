package implementations;

public class GraphImpl<T> extends Graph<T> {
    public GraphImpl() {
        super();
    }

    public boolean hasEdge(T vertex1, T vertex2) {
        int index1 = super.findVertex(vertex1);
        int index2 = super.findVertex(vertex2);

        if (index1 == -1 || index2 == -1) {
            return false;
        }

        return super.adjMatrix[index1][index2];
    }


}
