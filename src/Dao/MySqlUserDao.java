package Dao;

import DTO.User;
import Exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao extends MySqlDao implements UserDaoInterface
{

    //ALL USERS
    @Override
    public List<User> getAllUsers() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM users";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String lastname = rs.getString("last_name");
                String firstname = rs.getString("first_name");
                User u = new User(userId, firstname, lastname, username, password);
                users.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUsers() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return users; 
    }

    
    //GET USER (LOGIN)
    @Override
    public User getUser(String username, String password) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User u = null;
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM users WHERE username= ? AND password= ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int userId = rs.getInt("ID");
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String lastname = rs.getString("last_name");
                String firstname = rs.getString("first_name");
                u = new User(userId, firstname, lastname, user, pass);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return u; 
    }

    
    //ADD USER / REGISTER
    @Override
    public void addUser(String firstName, String lastName, String username, String password) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = this.getConnection();
            
            String query = "INSERT INTO users (first_name, last_name, username, password) VALUES ( ?, ?, ?, ?)";
            ps = con.prepareStatement(query);

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, username);
            ps.setString(4, password);

            ps.executeUpdate();
            con.close();
            
            System.out.println("User registered.");
        } 
        catch (SQLException e) 
        {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }        
    }  
}
