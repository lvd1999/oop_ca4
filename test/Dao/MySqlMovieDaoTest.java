/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DTO.Movie;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lvd_9
 */
public class MySqlMovieDaoTest {
    
    public MySqlMovieDaoTest() {
    }

    @Test
    public void testGetMovieById() throws Exception {
        System.out.println("getMovieById");
        int id = 14;
        MySqlMovieDao instance = new MySqlMovieDao();
        Movie expResult = new Movie(14,"iron man","Marvel, Action, Adventure, Sci-Fi","Jon Favreau","126 min","Tony Stark. Genius, billionaire, playboy, philanthropist. Son of legendary inventor and weapons contractor Howard Stark. When Tony Stark is assigned to give a weapons presentation to an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. That ride ends badly when Stark's Humvee that he's riding in is attacked by enemy combatants. He survives - barely - with a chest full of shrapnel and a car battery attached to his heart. In order to survive he comes up with a way to miniaturize the battery and figures out that the battery can power something else. Thus Iron Man is born. He uses the primitive device to escape from the cave in Iraq. Once back home, he then begins work on perfecting the Iron Man suit. But the man who was put in charge of Stark Industries has plans of his own to take over Tony's technology for other matters.","",null,"PG-13","DVD","2008","Robert Downey Jr., Terrence Howard, Jeff Bridges, Gwyneth Paltrow",3,null,"8.8");
        Movie result = instance.getMovieById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoviesByGenre method, of class MySqlMovieDao.
     */
    @Test
    public void testGetMoviesByGenre() throws Exception {
        System.out.println("getMoviesByGenre");
        String genreFilter = "test";
        MySqlMovieDao instance = new MySqlMovieDao();
        List<Movie> expResult = new ArrayList<Movie>();
        expResult.add(new Movie(1069,"test title","test","test","test","test","test","test","test","test","test","test",1,"test","test"));
        expResult.add(new Movie(1067,"test","test","test","test","test","test","test","test","test","test","test",1,"test","test"));
        expResult.add(new Movie(1068,"test","test","test","test","test","test","test","test","test","test","test",1,"test","test"));
        List<Movie> result = instance.getMoviesByGenre(genreFilter);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoviesByTitle method, of class MySqlMovieDao.
     */
    @Test
    public void testGetMoviesByTitle() throws Exception {
        System.out.println("getMoviesByTitle");
        String searchTitle = "test title";
        MySqlMovieDao instance = new MySqlMovieDao();
        List<Movie> expResult =  new ArrayList<Movie>();
        expResult.add(new Movie(1069,"test title","test","test","test","test","test","test","test","test","test","test",1,"test","test"));
        List<Movie> result = instance.getMoviesByTitle(searchTitle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieByTitle method, of class MySqlMovieDao.
     */
    @Test
    public void testGetMovieByTitle() throws Exception {
        System.out.println("getMovieByTitle");
        String searchTitle = "iron man";
        MySqlMovieDao instance = new MySqlMovieDao();
        Movie expResult = new Movie(14,"iron man","Marvel, Action, Adventure, Sci-Fi","Jon Favreau","126 min","Tony Stark. Genius, billionaire, playboy, philanthropist. Son of legendary inventor and weapons contractor Howard Stark. When Tony Stark is assigned to give a weapons presentation to an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. That ride ends badly when Stark's Humvee that he's riding in is attacked by enemy combatants. He survives - barely - with a chest full of shrapnel and a car battery attached to his heart. In order to survive he comes up with a way to miniaturize the battery and figures out that the battery can power something else. Thus Iron Man is born. He uses the primitive device to escape from the cave in Iraq. Once back home, he then begins work on perfecting the Iron Man suit. But the man who was put in charge of Stark Industries has plans of his own to take over Tony's technology for other matters.","",null,"PG-13","DVD","2008","Robert Downey Jr., Terrence Howard, Jeff Bridges, Gwyneth Paltrow",3,null,"8.8");
        Movie result = instance.getMovieByTitle(searchTitle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieByDirector method, of class MySqlMovieDao.
     */
    @Test
    public void testGetMovieByDirector() throws Exception {
        System.out.println("getMovieByDirector");
        String searchdirector = "test";
        MySqlMovieDao instance = new MySqlMovieDao();
        List<Movie> expResult = new ArrayList<Movie>();
        expResult.add(new Movie(1069,"test title","test","test","test","test","test","test","test","test","test","test",1,"test","test"));
        List<Movie> result = instance.getMovieByDirector(searchdirector);
        assertEquals(expResult, result);
    }

    /**
     * Test of toJson method, of class MySqlMovieDao.
     */
    //W E I R D
    @Test
    public void testToJson() throws Exception {
        System.out.println("toJson");
        Movie m = new Movie(1069,"test title","test","test","test","test","test","test","test","test","test","test",1,"test","test");
        MySqlMovieDao instance = new MySqlMovieDao();
        String expResult = "{ \"id\" : 1069,\"title\" : \"test title\",\"genre\" : \"test\",\"director\" : \"test\",\"runtime\" : \"test\",\"plot\" : \"test\",\"location\" :\" test\",\"poster\" : \"test\",\"rating\" : \"test\",\"format\" : \"test\",\"year\" : \"test\",\"starring\" : \"test\",\"copies\" : 1,\"barcode\" : \"test\",\"user_rating\" : \"test\"}";
        String result = instance.toJson(m);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateMovieUser_Rating method, of class MySqlMovieDao.
     */
    @Test
    public void testUpdateMovieUser_Rating() throws Exception {
        System.out.println("updateMovieUser_Rating");
        int mId = 0;
        String mUser_Rating = "";
        MySqlMovieDao instance = new MySqlMovieDao();
        instance.updateMovieUser_Rating(mId, mUser_Rating);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of updateMovieTitle method, of class MySqlMovieDao.
     */
    @Test
    public void testUpdateMovieTitle() throws Exception {
        System.out.println("updateMovieTitle");
        int id = 0;
        String title = "";
        MySqlMovieDao instance = new MySqlMovieDao();
        instance.updateMovieTitle(id, title);
    }

    /**
     * Test of addMovie method, of class MySqlMovieDao.
     */
    @Test
    public void testAddMovie() throws Exception {
        System.out.println("addMovie");
        String title = "";
        String genre = "";
        String director = "";
        String runtime = "";
        String plot = "";
        String location = "";
        String poster = "";
        String rating = "";
        String format = "";
        String year = "";
        String starring = "";
        int copies = 0;
        String barcode = "";
        String user_rating = "";
        MySqlMovieDao instance = new MySqlMovieDao();
        instance.addMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
    }

    /**
     * Test of deleteMovie method, of class MySqlMovieDao.
     */
    @Test
    public void testDeleteMovie() throws Exception {
        System.out.println("deleteMovie");
        int id = 0;
        MySqlMovieDao instance = new MySqlMovieDao();
        instance.deleteMovie(id);
    }
    
}
