package Dao;

import DTO.Movie;
import Exception.DaoException;
import java.util.List;

public interface MovieDaoInterface 
{
    public List<Movie> getAllMovies() throws DaoException;
    public Movie getMovieById(int id) throws DaoException ;
    public List<Movie> getMoviesByGenre(String genreFilter) throws DaoException;
    public List<Movie> getMoviesByTitle(String title) throws DaoException;
    public Movie getMovieByTitle(String title) throws DaoException;
    public List<Movie> getMovieByDirector(String director) throws DaoException;
    public void addMovie( String title, String genre, String director, String runtime, String plot, String location, String poster, String rating, String format, String year, String starring, int copies, String barcode, String user_rating) throws DaoException;
    public void deleteMovie(int id) throws DaoException;
    public void updateMovieUser_Rating(int id, String User_Rating) throws DaoException;
    public void updateMovieTitle(int id, String title) throws DaoException;
}
