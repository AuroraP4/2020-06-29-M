package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Director> listAllDirectorsByYear(int anno){
		String sql = "SELECT d.id, d.first_name, d.last_name "
				+ "FROM movies m, movies_directors md, directors d "
				+ "WHERE year= ? "
				+ "AND m.id=md.movie_id AND d.id=md.director_id "
				+ "ORDER BY d.id ";
		Connection conn = DBConnect.getConnection();
		List<Director> result = new ArrayList<Director>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
			result.add(new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name")));
			}
			res.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getArchi(int anno){
		String sql = "SELECT md1.director_id AS regista1, md2.director_id AS regista2, COUNT(DISTINCT r1.actor_id) AS N "
				+ "FROM movies_directors md1, movies_directors md2, movies m1, movies m2, "
				+ "roles r1, roles r2 "
				+ "WHERE md1.director_id <> md2.director_id AND md1.movie_id = m1.id AND "
				+ "  md2.movie_id = m2.id AND m1.id = r1.movie_id AND m2.id = r2.movie_id AND "
				+ "   r1.actor_id = r2.actor_id AND m1.year = m2.year AND m1.year=? "
				+ " GROUP BY md1.director_id, md2.director_id ";
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new Arco(res.getInt("regista1"), res.getInt("regista2"), res.getInt("N")));  }
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public Director directorById (int id){
		String sql = "SELECT d.id, d.first_name, d.last_name " 
				+ "FROM directors d "
				+ "WHERE d.id = ? ";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();
			
			res.next();
			Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
			
			res.close();
			conn.close();
			return director;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}