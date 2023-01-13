package org.acme.graph.model;

import java.util.ArrayList;
import java.util.List;

import org.acme.graph.TestGraphFactory;
import org.acme.graph.errors.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import static org.junit.Assert.*;

public class GraphTest {

	@Test
	public void testFindVertexById() {
		Vertex v = TestGraphFactory.createGraph01().findVertex("a");
		assertNotNull(v);
		assertEquals("a", v.getId());
	}

	@Test
	public void testFindVertexByIdNotFound() {
		Graph g = TestGraphFactory.createGraph01();
		NotFoundException e = Assert.assertThrows(NotFoundException.class, () -> g.findVertex("missing"));
		assertEquals("Vertex 'missing' not found", e.getMessage());
	}

	@Test
	public void testFindVertexByCoordinate() {
		Coordinate c = new Coordinate(0.0, 0.0);
		Vertex v = TestGraphFactory.createGraph01().findVertex(c);
		assertNotNull(v);
		assertEquals("a", v.getId());
	}

	@Test
	public void testFindVertexByCoordinateNotFound() {
		Graph g = TestGraphFactory.createGraph01();
		Coordinate c = new Coordinate(888.0, 999.0);
		NotFoundException e = Assert.assertThrows(NotFoundException.class, () -> g.findVertex(c));
		assertEquals("Vertex not found at [888.0,999.0]", e.getMessage());
	}

	@Test
	public void testGetOutEdges() {
		Graph g = TestGraphFactory.createGraph01();
		Vertex a = g.findVertex("a");
		assertNotNull(a);
		List<Edge> result = g.getOutEdges(a);
		assertEquals(2, result.size());
		assertEquals("ab (a->b)", result.get(0).toString());
		assertEquals("ad (a->d)", result.get(1).toString());
	}

	@Test
	public void testGetInEdges() {
		Graph g = TestGraphFactory.createGraph01();
		Vertex b = g.findVertex("b");
		assertNotNull(b);
		List<Edge> result = g.getInEdges(b);
		assertEquals(1, result.size());
		assertEquals("ab (a->b)", result.get(0).toString());
	}

	@Test
	public void testGetCost() {
		Graph graph = new Graph();

		Coordinate coordinateA = new Coordinate(0.0,0.0);
		Coordinate coordinateB = new Coordinate(1.0,0.0);
		Coordinate coordinateC = new Coordinate(1.0,1.0);

		Vertex a = graph.createVertex(coordinateA,"a");
		Vertex b = graph.createVertex(coordinateB,"b");
		Vertex c = graph.createVertex(coordinateC,"c");

		Edge edge = graph.createEdge(a,c,"ac");

		double firstCost = edge.getCost();

		Coordinate[] coords = new Coordinate[]{
				coordinateA,
				coordinateB,
				coordinateC
		};
		GeometryFactory gf = new GeometryFactory();
		edge.setGeometry(gf.createLineString(coords));

		Assert.assertNotEquals(firstCost,edge.getCost(),TestGraphFactory.EPSILON);
	}

}
