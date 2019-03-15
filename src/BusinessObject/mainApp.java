package BusinessObject;

import DTO.Movie;
import DTO.User;
import Dao.MySqlMovieDao;
import Dao.MovieDaoInterface;
import Dao.MySqlUserDao;
import Dao.UserDaoInterface;
import Exception.DaoException;
import java.util.List;
import java.util.Scanner;

public class mainApp {

    static Scanner input = new Scanner(System.in);
    static MovieDaoInterface IMovieDao = new MySqlMovieDao();
    static UserDaoInterface IUserDao = new MySqlUserDao();

    public static void main(String[] args) throws DaoException {

        boolean mainLoop = true;

        int choice;
        while (true) {
            System.out.println("==================================");
            System.out.println("Main Menu");
            System.out.println("==================================");
            System.out.print("0.) Quit \n");
            System.out.print("1.) Search \n");
            System.out.print("2.) Add.\n");
            System.out.print("3.) Remove.\n");
            System.out.print("4.) Update.\n");
            System.out.print("5.) Register.\n");
            System.out.print("\nEnter Your Choice: ");

            choice = input.nextInt();

            OUTER:
            switch (choice) {
                case 1:
                    System.out.println("Search movies by: ");
                    System.out.println("1 - Title");
                    System.out.println("2 - Genre");
                    System.out.println("3 - Director");
                    System.out.println("0 - Back");
                    System.out.println("Select option: ");
                    int select = input.nextInt();
                    switch (select) {
                        case 1:
                            //get movies by title   (multiple)
                            System.out.println("Get movies by title:");
                            input.nextLine();
                            String searchtitle = input.nextLine();
                            System.out.println("Searched title: " + searchtitle);
                            List<Movie> titleResults = IMovieDao.getMoviesByTitle(searchtitle);
                            if (titleResults.isEmpty()) {
                                System.out.println("Movie NOT found.");
                            } else {
                                System.out.println("movie found : ");
                                System.out.println(movieListJson(titleResults));
                            }   break OUTER;
                        case 2:
                            {
                                //get movies by genre
                                System.out.println("Get movies by genre:");
                                input.nextLine();
                                String searchgenre = input.nextLine();
                                System.out.println("Searched genre: " + searchgenre);
                                List<Movie> movies = IMovieDao.getMoviesByGenre(searchgenre);
                                if (movies.isEmpty()) {
                                    System.out.println("Movie NOT found.");
                                } else {
                                    System.out.println("movie found : ");
                                    System.out.println(movieListJson(movies));
                                }       break OUTER;
                            }
                        case 3:
                            {
                                //get movies by director
                                System.out.println("Get movies by director:");
                                input.nextLine();
                                String searchdirector = input.nextLine();
                                System.out.println("Searched director: " + searchdirector);
                                List<Movie> movies = IMovieDao.getMovieByDirector(searchdirector);
                                if (movies.isEmpty()) {
                                    System.out.println("Movie NOT found.");
                                } else {
                                    System.out.println("movie found : ");
                                    System.out.println(movieListJson(movies));
                                }       break OUTER;
                            }
                            
                        case 0:
                            {
                                break OUTER;
                            }
                        
                        
                        default:
                            System.out.println("Invalid input");
                            break OUTER;
                    }
                case 2:     //add
                    try {
                        System.out.println("Add movie");
                        System.out.print("Title:");
                        String title = input.next();
                        System.out.print("Genre:");
                        String genre = input.next();
                        System.out.print("Director:");
                        String director = input.next();
                        System.out.print("Runtime:");
                        String runtime = input.next();
                        System.out.print("Plot:");
                        String plot = input.next();
                        System.out.print("Location:");
                        String location = input.next();
                        System.out.print("Poster:");
                        String poster = input.next();
                        System.out.print("Rating:");
                        String rating = input.next();
                        System.out.print("Format:");
                        String format = input.next();
                        System.out.print("Year:");
                        String year = input.next();
                        System.out.print("Starring:");
                        String starring = input.next();
                        System.out.print("Copies:");
                        int copies = input.nextInt();
                        System.out.print("Barcode:");
                        String barcode = input.next();
                        System.out.print("User rating:");
                        String user_rating = input.next();

                        IMovieDao.addMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);

                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case 3:     //remove

                    System.out.println("Delete movie with id: ");
                    int deleteId = input.nextInt();
                    IMovieDao.deleteMovie(deleteId);
                    break;
                    
                case 4:     //update
                    System.out.println("Update movies: ");
                    System.out.println("1 - Title");
                    System.out.println("2 - User rating");
                    System.out.println("0 - Back");
                    System.out.println("Select option: ");
                    
                    int selectOption = input.nextInt();
                    switch (selectOption) {
                        case 1:
                        {
                            //update movie title
                            System.out.println("Updating movie title");
                            input.nextLine();
                            System.out.println("Movie id:");
                            int id = input.nextInt();
                            System.out.println("New title: ");
                            input.nextLine();
                            String newtitle = input.nextLine();                            
                            IMovieDao.updateMovieTitle(id,newtitle);
                            
                            break OUTER;
                            }
                            
                        case 2:
                            {
                              //update movie user rating
                              System.out.println("Updating movie user rating");
                              input.nextLine();
                              System.out.println("Movie id:");
                              int id = input.nextInt();
                              System.out.println("New user_rating: ");
                              input.nextLine();
                              String user_rating = input.nextLine();                            
                              IMovieDao.updateMovieUser_Rating(id,user_rating);

                              break OUTER;
                            }

                            
                        case 0:
                            {
                                break OUTER;
                            }
                        
                        
                        default:
                            System.out.println("Invalid input");
                            break OUTER;
                    }
                    
                case 5: //register
                    System.out.println("Please fill in details to register");
                    System.out.println("Username: ");
                    input.nextLine();
                    String username = input.nextLine();
                    System.out.println("Password: ");
                    String password = input.nextLine();
                    System.out.println("First name: ");
                    String firstname = input.nextLine();
                    System.out.println("Last name: ");
                    String lastname = input.nextLine();

                    IUserDao.addUser(username, password, firstname, lastname);
                    break;
                case 0:
                    System.out.println("Exiting Program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("This is not a valid Menu Option! Please Select Another");
                    break;
            }
        }
    }

    public static String movieListJson(List<Movie> list) {
        String jsonString = " { \" movies \" : [";
        int i = 1;
        for (Movie movie : list) {

            jsonString += "{"
                    + "\"id\":" + movie.getId() + ","
                    + "\"title\":\"" + movie.getTitle() + "\","
                    + "\"genre\":\"" + movie.getGenre() + "\","
                    + "\"director\":\"" + movie.getDirector() + "\","
                    + "\"runtime\":\"" + movie.getRuntime() + "\","
                    + "\"plot\":\"" + movie.getPlot() + "\","
                    + "\"location\":\"" + movie.getLocation() + "\","
                    + "\"poster\":\"" + movie.getPoster() + "\","
                    + "\"rating\":\"" + movie.getRating() + "\","
                    + "\"format\":\"" + movie.getFormat() + "\","
                    + "\"year\":" + movie.getYear() + ","
                    + "\"starring\":\"" + movie.getStarring() + "\","
                    + "\"copies\":" + movie.getCopies() + ","
                    + "\"barcode\":" + movie.getBarcode() + ","
                    + "\"userRating\":" + movie.getUser_rating()
                    + "}";
            if (i < list.size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]"
                + "}";

        return jsonString;
    }

    //movie to json (single)
    public static String toJson(Movie m) throws DaoException
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
    
    public static String userListJson(List<User> list) {
        String jsonString = " { \" movies \" : [";
        int i = 1;
        for (User u : list) {

            jsonString += "{"
                    + "\"user_id\":" + u.getId() + ","
                    + "\"password\":\"" + u.getPassword() + "\","
                    + "\"last_name\":\"" + u.getLastName() + "\","
                    + "\"first_name\":\"" + u.getFirstName() + "\","
                    + "}";

            if (i < list.size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]"
                + "}";

        return jsonString;
    }

}
