package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	ImdbDAO dao;
	List<Director> directors;
	Map<Integer, Director> directorsIdMap;
	Graph<Director, DefaultWeightedEdge> grafo;
	
public String creaGrafo(int anno) {
	grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	dao = new ImdbDAO();
	
	directors = dao.listAllDirectorsByYear(anno);
	
	directorsIdMap = new HashMap<>();
	for(Director d: directors) {
		directorsIdMap.put(d.getId(), d);   }
	
	Graphs.addAllVertices(grafo, directors);
	
	List<Arco> archi = dao.getArchi(anno);
	for(Arco a: archi) {
		Director d1 = dao.directorById(a.getRegista1());
		Director d2 = dao.directorById(a.getRegista2());
		
		if(grafo.vertexSet().contains(d1) && grafo.vertexSet().contains(d2)) {
			Graphs.addEdge(grafo, directorsIdMap.get(a.getRegista1()),
					directorsIdMap.get(a.getRegista2()), a.getnAttoriInComune());
		}
	}
	return String.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(),
			this.grafo.edgeSet().size());
}

public List<Adiacente> getDirectorsAdiacenti(Director g) {
	List<Director> vicini = Graphs.neighborListOf(grafo, g);
	List<Adiacente> result = new ArrayList<>();
	for(Director d: vicini) {
		result.add(new Adiacente(d, grafo.getEdgeWeight(grafo.getEdge(g, d))));	
	}
	Collections.sort(result);
	return result;
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public Set<Director> getVertici() {
	return this.grafo.vertexSet();
}

}
