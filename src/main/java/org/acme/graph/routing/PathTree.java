package org.acme.graph.routing;

import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PathTree {

    private Map<Vertex, PathNode> nodes = new HashMap<Vertex, PathNode>();

    private static final Logger log = LogManager.getLogger(DijkstraPathFinder.class);

    /**
     * Prépare le graphe pour le calcul du plus court chemin
     *
     * @param origin
     */
    public PathTree(Graph graph, Vertex origin) {
        log.trace("initGraph({})", origin);
        for (Vertex vertex : graph.getVertices()) {
            PathNode pathNode = new PathNode();
            pathNode.setCost(origin == vertex ? 0.0 : Double.POSITIVE_INFINITY);
            pathNode.setReachingEdge(null);
            pathNode.setVisited(false);
            nodes.put(vertex,pathNode);
        }
    }

    /**
     * Construit le chemin en remontant les relations incoming edge
     *
     * @param destination
     * @return
     */
    List<Edge> getPath(Vertex destination) {
        List<Edge> result = new ArrayList<>();

        Edge current = nodes.get(destination).getReachingEdge();
        do {
            result.add(current);
            current = nodes.get(current.getSource()).getReachingEdge();
        } while (current != null);

        Collections.reverse(result);
        return result;
    }

    Map<Vertex, PathNode> getNodes(){
        return nodes;
    }


}
