package Dao;

import DTO.Movie;
import Exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface {

    //GET ALL MOVIES
    @Override
    public List<Movie> getAllMovies() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
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
            throw new DaoException("getAllMovies() " + e.getMessage());
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
                throw new DaoException("getAllMovies() " + e.getMessage());
            }
        }
        return movies;     // may be empty
    }

    //GET MOVIES BY ID
    @Override
    public Movie getMovieById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(id));

            rs = ps.executeQuery();
            if (rs.next()) {
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

                m = new Movie(movieId, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
            }
        } catch (SQLException e) {
            throw new DaoException("getMoviebyId() " + e.getMessage());
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
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return m;     // m may be null 
    }

    //GET MOVIES BY GENRE
    @Override
    public List<Movie> getMoviesByGenre(String genreFilter) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            con = this.getConnection();
            String query = "SELECT * FROM movies WHERE genre LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + genreFilter + "%");
            rs = ps.executeQuery();

            System.out.println(query);
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
            throw new DaoException("getMoviesByGenre() " + e.getMessage());
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
                throw new DaoException("getMoviesByGenre() " + e.getMessage());
            }
        }
        return movies;
    }

    //GET MOVIES BY TITLE (list)
    @Override
    public List<Movie> getMoviesByTitle(String searchTitle) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE title LIKE ?";

            ps = con.prepareStatement(query);
            ps.setString(1, "%" + searchTitle + "%");

            //Using a PreparedStatement to execute SQL...
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
            throw new DaoException("getMoviesByTitle() " + e.getMessage());
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
                throw new DaoException("getMoviesByTitle() " + e.getMessage());
            }
        }
        return movies;
    }
    
    //GET MOVIE BY TITLE (single)
    @Override
    public Movie getMovieByTitle(String searchTitle) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE title = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, searchTitle);

            rs = ps.executeQuery();
            if (rs.next()) {
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

                m = new Movie(movieId, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
            }
        } catch (SQLException e) {
            throw new DaoException("getMovieByTitle() " + e.getMessage());
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
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return m;     // m may be null 
    }

    //GET MOVIES BY DIRECTOR
    @Override
    public List<Movie> getMovieByDirector(String searchdirector) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE director LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + searchdirector + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
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
            throw new DaoException("getMovieByDirector() " + e.getMessage());
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
                throw new DaoException("getMovieByDirector() " + e.getMessage());
            }
        }
        return movies;
    }

    public String toJson(Movie m) throws DaoException
    {
        String json = " { \"id\" : " + m.getId() + ","
                    + "\"title\" : \"" + m.getTitle() + "\","
                    + "\"genre\" : \"" + m.getGenre() + "\","
                    + "\"director\" : \"" + m.getDirector() + "\","
                    + "\"runtime\" : \"" + m.getRuntime() + "\","
                    + "\"plot\" : \"" + m.getPlot() + "\","
                    + "\"location\" :\" " + m.getLocation() + "\","
                    + "\"poster\" : \"" + m.getPoster() + "\","
                    + "\"rating\" : \"" + m.getRating() + "\","
                    + "\"format\" : \"" + m.getFormat() + "\","
                    + "\"year\" : \"" + m.getYear() + "\","
                    + "\"starring\" : \"" + m.getStarring() + "\","
                    + "\"copies\" : " + m.getCopies() + ","
                    + "\"barcode\" : \"" + m.getBarcode() + "\","
                    + "\"user_rating\" : \"" + m.getUser_rating()
                    + "\"}";
        
        return json;
    }
    
    //UPDATE MOVIE
    @Override
    public void updateMovieUser_Rating(int mId, String mUser_Rating) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;     

        try {
            con = this.getConnection();
            
            String query = "UPDATE MOVIES SET user_rating = ? WHERE id = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, mUser_Rating);
            ps.setInt(2, mId);
            
            ps.executeUpdate();
            con.close();
            
            System.out.println("User rating updated.");
        } 
        catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }  
    
    @Override
    public void updateMovieTitle(int id, String title) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;     

        try {
            con = this.getConnection();
            
            String query = "UPDATE MOVIES SET title = ? WHERE id = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            con.close();
            
            System.out.println("Movie title updated.");
        } 
        catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //ADD MOVIE
    @Override
    public void addMovie(String title, String genre, String director, String runtime, String plot, String location, String poster, String rating, String format, String year, String starring, int copies, String barcode, String user_rating) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "INSERT INTO movies( title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setString(3, director);
            ps.setString(4, runtime);
            ps.setString(5, plot);
            ps.setString(6, location);
            ps.setString(7, poster);
            ps.setString(8, rating);
            ps.setString(9, format);
            ps.setString(10, year);
            ps.setString(11, starring);
            ps.setInt(12, copies);
            ps.setString(13, barcode);
            ps.setString(14, user_rating);
            
            ps.executeUpdate();
            con.close();
            System.out.println("Movie added successfully.");
            
        } catch (SQLException e) {
            throw new DaoException("addMovie() " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("addMovie()" + e.getMessage());
        }

    }
    

}
    
    //DELETE MOVIE
    @Override
    public void deleteMovie(int id) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;     

        try {
            con = this.getConnection();
            
            String query = "DELETE FROM movies WHERE id= ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            con.close();
            
            System.out.println("Movie id:" + id + " deleted.");
        } 
        catch (SQLException e) {
            throw new DaoException("deleteMovie() " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("deleteMovie()" + e.getMessage());
        }

    }
    }
    
    
    
    
}

