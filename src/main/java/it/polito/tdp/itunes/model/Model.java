package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private SimpleGraph<Album, DefaultEdge> grafo ;
	private Map<Integer, Album> idMap;
	private List<Album> best;
	private List<Album> connessi;
	
	
	public Model()	{
		this.dao = new ItunesDAO();
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.idMap = new HashMap<>();
		this.dao.loadAllAlbum(idMap);
		
		
	}
	
	
	public void creaGrafo(int d) {
		int durata = d*60*1000;
		List<Album> result = this.dao.getAlbumDurata(idMap, durata);
		Graphs.addAllVertices(this.grafo, result);
		
		//aggiungo i vertici
		for( Album a : this.grafo.vertexSet()) {
			
			for( Album a2 : this.grafo.vertexSet()) {
				if( ! (a.equals(a2))) {
					int n = this.dao.isStessoALbum(a, a2);
					if( n> 0) {
						this.grafo.addEdge(a, a2);
					}
				}
			}
		}
		
	}
	
	public void clearGrafo() {
		this.dao = new ItunesDAO();
		this.idMap = new HashMap<>();
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.dao.loadAllAlbum(idMap);
		

	}
	
	public int getNconnessi( Album a) {
		ConnectivityInspector<Album, DefaultEdge> connessi = new ConnectivityInspector<>(this.grafo);
		return connessi.connectedSetOf(a).size();
		
	}
	
	public double getSommaConnessi(Album a ) {
		ConnectivityInspector<Album, DefaultEdge> connessi = new ConnectivityInspector<>(this.grafo);
		List<Album> albums = new ArrayList<>( connessi.connectedSetOf(a));
		double durataTot = 0;
		
		for( Album album : albums) {
			durataTot += this.dao.getIntDurata(album);
		}
		
		durataTot = (durataTot/ 1000)/60;
		return durataTot;
		
	}
	
	public List<Album> getVertici(){
		List<Album> result = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
	}
	
	
	public List<Album> cerca(Album a , int durata) {
		this.best = new ArrayList<>();
		
		ConnectivityInspector<Album, DefaultEdge> listaC = new ConnectivityInspector<Album, DefaultEdge>(this.grafo);
		this.connessi = new ArrayList<>( listaC.connectedSetOf(a));
		connessi.remove(a);
		
		List<Album> parziale = new ArrayList<>();
		parziale.add(a);
		int dTot = durata * 60*1000;
		
		ricorsione( parziale, dTot, this.connessi);
		
		//Set<Album> result =  new HashSet<Album>(this.best) ;
		return best;
	}
	
	
	private void ricorsione(List<Album> parziale, int dTot, List<Album> restanti) {
		
		if( restanti.size() == 0) {
			if( parziale.size() > this.best.size()) {
				this.best = new ArrayList<>(parziale);
			}
			return; 
		}
		
		
		
		for( Album a : restanti) {
			parziale.add(a);
			
			if( MaxDurata( parziale, dTot)) {
				List<Album> nuoviRestanti = new ArrayList<>(restanti);
				nuoviRestanti.remove(a);
				ricorsione(parziale, dTot, nuoviRestanti);
			}
			parziale.remove(parziale.size()-1);
			
		}
		
	}


	private boolean MaxDurata(List<Album> parziale, int dTotMax) {
		int durataTot = 0;
		for(Album a : parziale) {
			durataTot += dao.getIntDurata(a);
		}
		
		if( durataTot <= dTotMax) {
			return true;
		}
		
		return false;
	}


	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
}
