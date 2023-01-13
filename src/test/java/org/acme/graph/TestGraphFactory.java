package org.acme.graph;

import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;
import org.locationtech.jts.geom.Coordinate;

public class TestGraphFactory {

	/**
	 * d / / a--b--c
	 * 
	 * @return
	 */
	public static Graph createGraph01() {
		Graph graph = new Graph();

		Vertex a = graph.createVertex(new Coordinate(0.0, 0.0),"a");
		Vertex b = graph.createVertex(new Coordinate(1.0, 0.0),"b");
		Vertex c = graph.createVertex(new Coordinate(2.0, 0.0),"c");
		Vertex d = graph.createVertex(new Coordinate(1.0, 1.0),"d");

		Edge ab = graph.createEdge(a,b,"ab");
		Edge bc = graph.createEdge(b,c,"bc");
		Edge ad = graph.createEdge(a,d,"ad");

		return graph;
	}

}
