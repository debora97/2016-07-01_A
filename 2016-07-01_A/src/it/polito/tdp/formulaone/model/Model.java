package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.FormulaOneController;
import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	FormulaOneDAO dao;
	List<Season> season;
	List<Driver> drivers;
	Map<Integer, Driver> mapId;
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	private List<Classifiche> classifiche;
	List<Driver> ottima;
	double tassosconfitta;

	public Model() {
		dao = new FormulaOneDAO();
		season = new LinkedList<Season>(dao.getAllSeasons());
		drivers = new LinkedList<Driver>();
		mapId = new HashMap<Integer, Driver>();

	}

	public List<Season> getSeason() {
		return this.season;
	}

	public List<Driver> getDriver(Season s) {
		this.drivers = dao.getAllDrivers(s);
		for (Driver d : drivers) {
			mapId.put(d.getDriverId(), d);

		}
		classifiche = new LinkedList<Classifiche>(dao.getClassifiche(s));
		// System.out.println(classifiche);
		return this.drivers;
	}

	public void creaGrafo() {

		grafo = new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, drivers);

		for (Classifiche c : classifiche) {
			Driver d1 = mapId.get(c.getD1());
			Driver d2 = mapId.get(c.getD2());
			// if(d1!=null && d2!= null)
			Graphs.addEdgeWithVertices(grafo, d1, d2, c.getRes());
			// Graphs.addEdge(grafo, mapId.get(c.getD1()), mapId.get(c.getD2()),
			// c.getRes());
		}
		System.out.format("Grafo creato: %d archi, %d nodi\n", grafo.edgeSet().size(), grafo.vertexSet().size());
	}

	public Driver getBestDriver() {
		if (grafo == null) {
			new RuntimeException("Creare il grafo!");
		}

		// Inizializzazione
		Driver bestDriver = null;
		int best = Integer.MIN_VALUE;

		for (Driver d : grafo.vertexSet()) {
			int sum = 0;

			// Itero sugli archi uscenti
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(d)) {
				sum += grafo.getEdgeWeight(e);
			}

			// Itero sugli archi entranti
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(d)) {
				sum -= grafo.getEdgeWeight(e);
			}

			if (sum > best || bestDriver == null) {
				bestDriver = d;
				best = sum;
			}
		}

		if (bestDriver == null) {
			new RuntimeException("BestDriver not found!");
		}

		return bestDriver;
	}

	public List<Driver> getDreamTeam(int n) {
		this.ottima =new LinkedList<Driver>();
		this.tassosconfitta = Integer.MAX_VALUE;
		List<Driver> parziale = new LinkedList<Driver>();
		//List<Driver> parziale = null;
		cerca(parziale, n);
		return ottima;

	}

	private void cerca(List<Driver> parziale, int n) {
		if (parziale.size() == n) {
			if (this.tassoSconfitta(parziale) < this.tassosconfitta) {
				ottima = new LinkedList<Driver>(parziale);
				this.tassosconfitta = this.tassoSconfitta(parziale);
				System.out.println(this.tassosconfitta);
			}
		}
		if(parziale.size()>n) return;
			for (Driver d : grafo.vertexSet()) {
				if (!parziale.contains(d)) {
					parziale.add(d);
					this.cerca(parziale, n);
					parziale.remove(parziale.size() - 1);
				}
			
		}

	}

	private double tassoSconfitta(List<Driver> parziale) {
		int valorePilotiTeam = 0;
		int valoreEsterni = 0;
		List<Driver> esterni=new LinkedList<Driver>(grafo.vertexSet());
		esterni.removeAll(parziale);
		for (Driver d : parziale) {

			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(d)) {
				valorePilotiTeam += grafo.getEdgeWeight(e);
			}
			for(Driver driv: esterni)
			{for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(driv)) {
				valoreEsterni += grafo.getEdgeWeight(e);
			}
			}
		if(valorePilotiTeam==0) {
			return Integer.MAX_VALUE;
		}
		
		}
		//System.out.println((valoreEsterni / valorePilotiTeam)+"\n");
		return (valoreEsterni / valorePilotiTeam);
			
   /*int sum = 0;
		
		Set<Driver> in = new HashSet<Driver>(parziale);
		Set<Driver> out = new HashSet<Driver>(grafo.vertexSet());
		out.removeAll(in);
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if (out.contains(grafo.getEdgeSource(e)) && in.contains(grafo.getEdgeTarget(e))) {
				sum += grafo.getEdgeWeight(e);
			}
		}
		return sum;
			

		
		*/
		
		
	}
}

