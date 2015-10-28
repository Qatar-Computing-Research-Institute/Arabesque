package io.arabesque.optimization;

import io.arabesque.graph.Edge;
import io.arabesque.graph.MainGraph;
import io.arabesque.graph.Vertex;
import io.arabesque.utils.IntArrayList;
import net.openhft.koloboke.collect.IntCollection;
import net.openhft.koloboke.collect.map.IntIntMap;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class OrderedNeighboursMainGraphDecorator<
        VD extends Writable,
        QV extends Vertex<VD>,
        EL extends WritableComparable,
        QE extends Edge<EL>> implements OrderedNeighboursMainGraph<VD, QV, EL, QE> {
    protected MainGraph<VD, QV, EL, QE> underlyingMainGraph;

    protected IntArrayList[] orderedNeighbours;

    public OrderedNeighboursMainGraphDecorator(MainGraph<VD, QV, EL, QE> underlyingMainGraph) {
        this.underlyingMainGraph = underlyingMainGraph;

        int numVertices = underlyingMainGraph.getNumberVertices();

        orderedNeighbours = new IntArrayList[numVertices];

        for (int i = 0; i < numVertices; ++i) {
            IntCollection neighboursOfI = underlyingMainGraph.getVertexNeighbours(i);

            if (neighboursOfI != null) {
                orderedNeighbours[i] = new IntArrayList(neighboursOfI);
                orderedNeighbours[i].sort();
            }
        }
    }

    @Override
    public void reset() {
        underlyingMainGraph.reset();
    }

    @Override
    public boolean isNeighborVertex(int v1, int v2) {
        return underlyingMainGraph.isNeighborVertex(v1, v2);
    }

    @Override
    public MainGraph<VD, QV, EL, QE> addVertex(QV vertex) {
        return underlyingMainGraph.addVertex(vertex);
    }

    @Override
    public QV[] getVertices() {
        return underlyingMainGraph.getVertices();
    }

    @Override
    public QV getVertex(int vertexId) {
        return underlyingMainGraph.getVertex(vertexId);
    }

    @Override
    public int getNumberVertices() {
        return underlyingMainGraph.getNumberVertices();
    }

    @Override
    public QE[] getEdges() {
        return underlyingMainGraph.getEdges();
    }

    @Override
    public QE getEdge(int edgeId) {
        return underlyingMainGraph.getEdge(edgeId);
    }

    @Override
    public int getNumberEdges() {
        return underlyingMainGraph.getNumberEdges();
    }

    @Override
    public int getEdgeId(int v1, int v2) {
        return underlyingMainGraph.getEdgeId(v1, v2);
    }

    @Override
    public MainGraph<VD, QV, EL, QE> addEdge(QE edge) {
        return underlyingMainGraph.addEdge(edge);
    }

    @Override
    public boolean areEdgesNeighbors(int edge1Id, int edge2Id) {
        return underlyingMainGraph.areEdgesNeighbors(edge1Id, edge2Id);
    }

    @Override
    public boolean isNeighborEdge(int src1, int dest1, int edge2) {
        return underlyingMainGraph.isNeighborEdge(src1, dest1, edge2);
    }

    @Override
    public IntIntMap getVertexNeighbourhood(int vertexId) {
        return underlyingMainGraph.getVertexNeighbourhood(vertexId);
    }

    @Override
    public IntCollection getVertexNeighbours(int vertexId) {
        return orderedNeighbours[vertexId];
    }
}
