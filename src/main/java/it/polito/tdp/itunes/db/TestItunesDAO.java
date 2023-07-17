package it.polito.tdp.itunes.db;

import java.util.List;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;

public class TestItunesDAO {

	public static void main(String[] args) {
		/*
		System.out.println(dao.getAllAlbums().size());
		System.out.println(dao.getAllArtists().size());
		System.out.println(dao.getAllPlaylists().size());
		System.out.println(dao.getAllTracks().size());
		System.out.println(dao.getAllGenres().size());
		System.out.println(dao.getAllMediaTypes().size());
		
		*
		
		*/
		ItunesDAO dao = new ItunesDAO();
		Model model = new Model();
		
		model.creaGrafo(60);
		//System.out.println( model.getNVertici());
		List<Album> vertici = model.getVertici();
		//System.out.println(vertici.get(35));
		
	//	List<Album> result = (List<Album>) model.ricercaSetMassimo(vertici.get(35), 10000);
		
	//	System.out.println(result);
	}

}
