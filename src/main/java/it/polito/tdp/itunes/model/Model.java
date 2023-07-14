package it.polito.tdp.itunes.model;

import it.polito.tdp.itunes.db.ItunesDAO;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;

public class Model {
	
	ItunesDAO dao;
	Graph<Album, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new ItunesDAO();
	}
	
	public void creaGrafo(int sec) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertex(sec));
		
		for(Album a1 : this.grafo.vertexSet()) {
			for(Album a2 : this.grafo.vertexSet()) {
				if(a1.getDurata()<a2.getDurata()) {
					int tot = a1.getDurata()+a2.getDurata();
					if(tot>4*sec) {
						Graphs.addEdgeWithVertices(this.grafo, a1, a2, tot);
					}
				}
			}
		}
	}
	
	public LinkedList<Adiacenza> getAd(Album ab){
		
		LinkedList<Adiacenza> result = new LinkedList<>();
		
		for(Album a : Graphs.successorListOf(this.grafo, ab)) {
			result.add(new Adiacenza(a, this.calcola(a)));
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	private int calcola(Album a) {
		int i = 0;
		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(a)) {
			i += this.grafo.getEdgeWeight(e);
		}
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(a)) {
			i -= this.grafo.getEdgeWeight(e);
		}
		return i;
	}
	
	public List<Album> setCombos(){
		List<Album> result = new LinkedList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
	}
	
	public boolean isGraphLoaded() {
		if(grafo.vertexSet().size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Graph<Album, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}

	
	int bilancioA1;
	Album target;
	LinkedList<Album> best;
	int x;
	int bestscore;
	
	public List<Album> ricorsione(Album a1, Album a2, int X){
		 target = a2;
		 bilancioA1 = this.calcola(a1);
		 best = new LinkedList<>();
		 LinkedList<Album> parziale = new LinkedList<>();
		 x = X;
		 bestscore = 0;
		 parziale.add(a1);
		 
		 cerca(parziale);
		
		 return best;
	}
	
	private void cerca(LinkedList<Album> parziale) {
		
		if(parziale.contains(target)) {
			int score = this.calcolaScore(parziale);
			if(score>this.bestscore) {
				this.bestscore = score;
				best = new LinkedList<>(parziale);
			}
			return;
		}
		
		for(Album a : Graphs.successorListOf(this.grafo, parziale.getLast())) {
			
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.getLast(), a))>=x && !parziale.contains(a)) {
				
				parziale.add(a);
				
				cerca(parziale);
				
				parziale.remove(a);
				
			}
			
		}
		
	}
	
	private int calcolaScore(LinkedList<Album> parziale) {
		
		int i = 0;
		
		for(Album a : parziale) {
			if(this.calcola(a)>this.bilancioA1) {
				i++;
			}
		}
		
		return i;
	}
}

