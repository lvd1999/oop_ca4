/**
 * SERVER                                                   February 2019 DL 08/03/19
 *
 * Server accepts client connections, creates a ClientHandler to handle the
 * Client communication, creates a socket and passes the socket to the handler,
 * runs the handler in a separate Thread.
 *
 *
 * The handler reads requests from clients, and sends replies to clients, all in
 * accordance with the rules of the protocol. as specified in
 * "ClientServerBasic" sample program
 *
 * The following PROTOCOL is implemented:
 *
 * If ( the Server receives the request "Time", from a Client ) then : the
 * server will send back the current time
 *
 * If ( the Server receives the request "Echo message", from a Client ) then :
 * the server will send back the message
 *
 * If ( the Server receives the request it does not recognize ) then : the
 * server will send back the message "Sorry, I don't understand"
 *
 * This is an example of a simple protocol, where the server's response is based
 * on the client's request.
 *
 *
 */
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

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection, 
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable // each ClientHandler communicates with one Client
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
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

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
                    String prmt1 = tokens[1];
                    
                    switch (command) {
                        
                        //cases
                        
                        case ("getallmovies"):  //get all movies
                            try {
                                List<Movie> movies = IMovieDao.getAllMovies();
                                socketWriter.println(movieListJson(movies)); //sends message to client
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
                            
                        
                            
                            
                            
                        default:
                            socketWriter.println("I'm sorry I don't understand :(");
                    }
                }

                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
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
