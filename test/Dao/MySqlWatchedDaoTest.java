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
public class MySqlWatchedDaoTest {
    
    public MySqlWatchedDaoTest() {
    }

    /**
     * Test of insertWatchedEntry method, of class MySqlWatchedDao.
     */
    @Test
    public void testInsertWatchedEntry() throws Exception {
        System.out.println("insertWatchedEntry");
        String user = "tom";
        int movie_id = 14;
        MySqlWatchedDao instance = new MySqlWatchedDao();
        instance.insertWatchedEntry(user, movie_id);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of findMoviesWatchedByUsername method, of class MySqlWatchedDao.
     */
    @Test
    public void testFindMoviesWatchedByUsername() throws Exception {
        System.out.println("findMoviesWatchedByUsername");
        String user = "ali";
        MySqlWatchedDao instance = new MySqlWatchedDao();
        List<Movie> expResult = new ArrayList<Movie>();
        expResult.add(new Movie(14,"iron man","Marvel, Action, Adventure, Sci-Fi","Jon Favreau","126 min","Tony Stark. Genius, billionaire, playboy, philanthropist. Son of legendary inventor and weapons contractor Howard Stark. When Tony Stark is assigned to give a weapons presentation to an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. That ride ends badly when Stark's Humvee that he's riding in is attacked by enemy combatants. He survives - barely - with a chest full of shrapnel and a car battery attached to his heart. In order to survive he comes up with a way to miniaturize the battery and figures out that the battery can power something else. Thus Iron Man is born. He uses the primitive device to escape from the cave in Iraq. Once back home, he then begins work on perfecting the Iron Man suit. But the man who was put in charge of Stark Industries has plans of his own to take over Tony's technology for other matters.","",null,"PG-13","DVD","2008","Robert Downey Jr., Terrence Howard, Jeff Bridges, Gwyneth Paltrow",3,null,"8.8"));
        List<Movie> result = instance.findMoviesWatchedByUsername(user);
        assertEquals(expResult, result);
    }
    
}
