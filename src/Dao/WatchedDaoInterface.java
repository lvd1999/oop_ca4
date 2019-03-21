/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DTO.Movie;
import Exception.DaoException;
import java.util.List;
/**
 *
 * @author lvd_9
 */
public interface WatchedDaoInterface {
    public void insertWatchedEntry(String user, int movie_id) throws DaoException;
//    public List<Movie> findMoviesWatchedByUsername(String user) throws DaoException;
}
