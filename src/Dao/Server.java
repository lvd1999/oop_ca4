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
import java.util.List;

public class Server {

    public final MovieDaoInterface IMovieDao = new MySqlMovieDao();
    public final WatchedDaoInterface WatchedDao = new MySqlWatchedDao();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(8080);

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0; 

            while (true) 
            {
                Socket socket = ss.accept();    
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); 
                t.start();                                                 

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable
    {

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
            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);
                    
                    String[] tokens = message.split(" ");
                    String command = tokens[0];
                    
                    switch (command) {
                        
                        //cases
                        
                        case ("getallmovies"):  //get all movies
                            try {
                                List<Movie> movies = IMovieDao.getAllMovies();
                                socketWriter.println(movieListJson(movies)); //reply output back to client (in json)
                            } catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;

                        case("getmoviebyid"):  //get movies by id
                            int id = Integer.parseInt(tokens[1]);
                            try {
                                Movie m = IMovieDao.getMovieById(id);   
                                socketWriter.println(toJson(m));    
                            } catch(DaoException e){
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("getmoviesbygenre"):   //get movies by genre
                            String genre = tokens[1];
                            try {
                                List<Movie> movies = IMovieDao.getMoviesByGenre(genre);
                                socketWriter.println(movieListJson(movies));
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("getmoviesbytitle"):   //get movies by title
                            String title = tokens[1];
                            try {
                                List<Movie> movies = IMovieDao.getMoviesByTitle(title);
                                socketWriter.println(movieListJson(movies));
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("getmoviesbydirector"):    //get movies by director
                            String director = tokens[1];
                            try {
                                List<Movie> movies = IMovieDao.getMovieByDirector(director);
                                socketWriter.println(movieListJson(movies));
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("deletemovie"):    //delete movie
                            int deleteid = Integer.parseInt(tokens[1]);
                            try {
                                IMovieDao.deleteMovie(deleteid);
                                socketWriter.println("Movie with id:" + deleteid + "deleted");
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                                
                        case("updatemovietitle"):   //update movie title
                            int updateid = Integer.parseInt(tokens[1]);
                            String updatetitle = tokens[2];
                            try {
                                IMovieDao.updateMovieTitle(updateid, updatetitle);
                                socketWriter.println("Movie title updated.");
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("watchMovie"):     //watch movie    
                           int movieid = Integer.parseInt(tokens[1]);
                           String username = tokens[2];
                           try {
                               WatchedDao.insertWatchedEntry(username, movieid);
                               socketWriter.println("Watched movie recorded.");
                           } catch (DaoException e) {
                               System.out.println("Error message" + e);
                           }
                           break;
                           
                        case("recommend"):
                            String user = tokens[1];
                            try {
                                List<Movie> movies = WatchedDao.findMoviesWatchedByUsername(user);
                                socketWriter.println(movieListJson(movies));
                            }catch (DaoException e) {
                                System.out.println("Error message" + e);
                            }
                            break;
                            
                        case("quit"):
                            break;
//                            socket.close();
                            
                        default:
                            socketWriter.println("No such command!");
                    }
                }

                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
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
        
        
}
