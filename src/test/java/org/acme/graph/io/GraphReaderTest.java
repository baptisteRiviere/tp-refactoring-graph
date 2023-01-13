package org.acme.graph.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Test de la lecture d'un graphe au format SHP
 * 
 * @author MBorne
 *
 */
public class GraphReaderTest {

	private GraphReader reader;

	@Before
	public void setUp() {
		this.reader = new GraphReader();
	}

	/**
	 * Récupération d'une ressource à partir de src/main/resources
	 * 
	 * @param name
	 * @return
	 */
	private File getResourceFile(String name) {
		URL url = GraphReader.class.getResource(name);
		Assert.assertNotNull("resource not found : " + name, url);
		File file = new File(url.getPath());
		return file;
	}

	@Test
	public void testRoute500() throws Exception {
		//File file = getResourceFile("/route500/idf/troncon_route.shp");
		File file = new File("/home/formation/Documents/design pattern/tp-refactoring-graph/src/test/resources/route500/idf/troncon_route.shp");
		Assert.assertTrue(file.exists());

		Graph graph = reader.read(file);
		for (Vertex vertex : graph.getVertices()) {
			assertNotNull(vertex.getId());
			assertNotNull(vertex.getCoordinate());
		}

		for (Edge edge : graph.getEdges()) {
			assertNotNull(edge.getId());
			assertNotNull(edge.getSource());
			assertNotNull(edge.getTarget());
			assertTrue(edge.getCost() > 0.0);
		}

		assertEquals(19207, graph.getVertices().size());
		assertEquals(44348, graph.getEdges().size());

		{
			Vertex v = graph.findVertex("1");
			assertNotNull(v);
			assertEquals("1", v.getId());
			assertEquals(3, graph.getOutEdges(v).size());
			assertEquals(3, graph.getInEdges(v).size());
		}
		{
			Vertex v = graph.findVertex("2");
			assertNotNull(v);
			assertEquals("2", v.getId());
			assertEquals(2, graph.getOutEdges(v).size());
			assertEquals(2, graph.getInEdges(v).size());
		}
	}

}
