/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DTO.Movie;
import Exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lvd_9
 */
public class MySqlWatchedDao extends MySqlDao implements WatchedDaoInterface {

    @Override
    public void insertWatchedEntry(String user, int movie_id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = this.getConnection();

            String query = "INSERT INTO watched (username,movie_id,time_stamp) VALUES (?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setInt(2, movie_id);
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(3, timestamp);

            ps.executeUpdate();
            con.close();

            System.out.println("Movie id = " + movie_id + "watched. ");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public List<Movie> findMoviesWatchedByUsername(String user) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        
        try {
            con = this.getConnection();
            System.out.println("getting movies...");
            String query = "SELECT * from movies , watched where watched.username = ? AND movies.id = watched.movie_id";
            ps = con.prepareStatement(query);
            ps.setString(1, user);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                int movieId = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String plot = rs.getString("plot");
                String location = rs.getString("location");
                String poster = rs.getString("poster");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                String starring = rs.getString("starring");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");

                Movie m = new Movie(movieId, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        return movies;
    }
}
