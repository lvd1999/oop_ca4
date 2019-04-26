package Dao;

import DTO.Movie;
import Exception.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.*;

public class Server {

    //set up logger
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final Level LOG_LEVEL = Level.INFO;
    private FileHandler logFile = null;
    
    
    public final MovieDaoInterface IMovieDao = new MySqlMovieDao();
    public final WatchedDaoInterface WatchedDao = new MySqlWatchedDao();

    public static void main(String[] args) {
        LOGGER.setLevel(LOG_LEVEL);
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            logFile = new FileHandler("Server.log",true);  //true = append
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        logFile.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(logFile);
        
        LOGGER.info("Server starting ...");
        
        try {
            ServerSocket ss = new ServerSocket(8080);

            System.out.println("Server started. Listening for connections on port 8080...");

            int clientNumber = 0;

            while (true) {
                Socket socket = ss.accept();
                clientNumber++;

                System.out.println("Client " + clientNumber + " has connected.");

                System.out.println("Port of remote client: " + socket.getPort());
                
                LOGGER.info("A client has connected from: " + socket.getInetAddress());
                
                Thread t = new Thread(new ClientHandler(socket, clientNumber));
                t.start();

                System.out.println("ClientHandler started in thread for client " + clientNumber + ". ");
            }   
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true);

                this.clientNumber = clientNumber;

                this.socket = clientSocket;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            HashMap<String, Movie> cache = new HashMap<>();
            HashMap<String, List<Movie>> Listcache = new HashMap<>();

            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Command from client " + clientNumber + ": " + message);
                    LOGGER.info("Command received: " + message);
                    String[] tokens = message.split(" ");
                    String command = tokens[0];
                    String key = tokens[0].concat(tokens[1]);

                    if (cache.containsKey(key)) {
                        try {
                            Movie m = cache.get(key);
                            LOGGER.info("Command found in cache. Returning data from cache.");
                            socketWriter.println(toJson(m));
                        } catch (DaoException e) {
                            System.out.println("Error message" + e);
                        }
                    }
                     else if (Listcache.containsKey(key)) {
                        List<Movie> movies = Listcache.get(key);
                        LOGGER.info("Command found in cache. Returning data from cache.");
                        socketWriter.println(movieListJson(movies));
                    } 
                        else {

                        switch (command) {

                            //cases
                            case ("getallmovies"):  //get all movies
                                try {
                                    List<Movie> movies = IMovieDao.getAllMovies();
                                    Listcache.put(key, movies);
                                    LOGGER.info("Sending data ... ");
                                    socketWriter.println(movieListJson(movies)); 

                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("getmoviebyid"):  //get movies by id
                                int id = Integer.parseInt(tokens[1]);
                                try {
                                    Movie m = IMovieDao.getMovieById(id);
                                    cache.put(key, m);
                                    LOGGER.info("Sending data ... ");
                                    socketWriter.println(toJson(m));
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("getmoviesbygenre"):   //get movies by genre
                                String genre = tokens[1];
                                try {
                                    List<Movie> movies = IMovieDao.getMoviesByGenre(genre);
                                    Listcache.put(key, movies);
                                    LOGGER.info("Sending data ... ");
                                    socketWriter.println(movieListJson(movies));
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("getmoviesbytitle"):   //get movies by title
                                String title = tokens[1];
                                try {
                                    List<Movie> movies = IMovieDao.getMoviesByTitle(title);
                                    Listcache.put(key, movies);
                                    LOGGER.info("Sending data ... ");
                                    socketWriter.println(movieListJson(movies));
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("getmoviesbydirector"):    //get movies by director
                                String director = tokens[1];
                                try {
                                    List<Movie> movies = IMovieDao.getMovieByDirector(director);
                                    Listcache.put(key, movies);
                                    LOGGER.info("Sending data ... ");
                                    socketWriter.println(movieListJson(movies));
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("deletemovie"):    //delete movie
                                int deleteid = Integer.parseInt(tokens[1]);
                                try {
                                    IMovieDao.deleteMovie(deleteid);
                                    socketWriter.println("Movie id " + deleteid + "deleted");
                                    LOGGER.info("Movie id " + deleteid + "deleted");
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("updatemovietitle"):   //update movie title
                                int updateid = Integer.parseInt(tokens[1]);
                                String updatetitle = tokens[2];
                                try {
                                    IMovieDao.updateMovieTitle(updateid, updatetitle);
                                    socketWriter.println("Movie title updated.");
                                    LOGGER.info("Movie title updated.");
                                    
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("watchMovie"):     //watch movie    
                                int movieid = Integer.parseInt(tokens[1]);
                                String username = tokens[2];
                                try {
                                    WatchedDao.insertWatchedEntry(username, movieid);
                                    socketWriter.println("Watched movie recorded.");
                                    LOGGER.info("Watched movie recorded.");
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("recommend"):
                                String user = tokens[1];
                                try {
                                    List<Movie> movies = WatchedDao.findMoviesWatchedByUsername(user);
                                    System.out.println(movies);
                                    //test methods for recommend
                                    String genres[] = genres(movies);
                                        LOGGER.info("most frequent genre: " + mostFrequent(genres,genres.length));     //most occurence genre name
                                        List<Movie> recommendedMovieList = IMovieDao.getMoviesByGenre(mostFrequent(genres,genres.length));  //get movie list by the genre
                                        LOGGER.info("Sending data ...");
                                    socketWriter.println(movieListJson(recommendedMovieList));
                                } catch (DaoException e) {
                                    System.out.println("Error message" + e);
                                }
                                break;

                            case ("quit"):
                                LOGGER.info("Terminating server ... ");
                                break;

                            default:
                                socketWriter.println("No such command!");
                        }
                    }

                }
                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Handler for Client " + clientNumber + " is terminating .....");
        }
    }

    //movie list convert to json method
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

    public static String toJson(Movie m) throws DaoException {
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
    
    public static String[] genres(List<Movie> movies)
    {
        String[] genres = new String[movies.size()];
        
        for(int i=0;i<movies.size();i++)
        {
            genres[i] = movies.get(i).getGenre();
        }
        return genres;
    }
    
    public static String mostFrequent(String arr[], int n) 
    { 
          
        // Insert all elements in hash 
        Map<String, Integer> hp = 
               new HashMap<String, Integer>(); 
          
        for(int i = 0; i < n; i++) 
        { 
            String key = arr[i]; 
            if(hp.containsKey(key)) 
            { 
                int freq = hp.get(key); 
                freq++; 
                hp.put(key, freq); 
            } 
            else
            { 
                hp.put(key, 1); 
            } 
        } 
          
        // find max frequency. 
        int max_count = 0;
        String res = ""; 
          
        for(Entry<String, Integer> val : hp.entrySet()) 
        { 
            if (max_count < val.getValue()) 
            { 
                res = val.getKey(); 
                max_count = val.getValue(); 
            } 
        } 
          
        return res; 
    }

}
