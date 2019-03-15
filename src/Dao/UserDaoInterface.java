package Dao;

import DTO.User;
import Exception.DaoException;
import java.util.List;

public interface UserDaoInterface 
{
    public List<User> getAllUsers() throws DaoException;
    public User getUser(String username, String password) throws DaoException ;
    public void addUser(String firstName, String lastName, String username, String password) throws DaoException;
}
