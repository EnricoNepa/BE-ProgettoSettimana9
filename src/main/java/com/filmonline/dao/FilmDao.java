package com.filmonline.dao;

import java.util.List;

import com.filmonline.model.Film;

public interface FilmDao {

	public void save(Film f);
	
	public List<Film> findByRegista(String regista);
	
	public void update(Film f);

	public void delete(Long id);
	
	public Film findById(Long id);
	
	public List<Film> getAllFilms();
}
