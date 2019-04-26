package BusinessObject;

import DTO.Movie;
import DTO.User;
import Dao.MovieDaoInterface;
import Dao.MySqlMovieDao;
import Dao.MySqlUserDao;
import Dao.UserDaoInterface;
import Exception.DaoException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);

            System.out.println("Port of this client : " + socket.getLocalPort());
            System.out.println("Port of Server :" + socket.getPort());

            System.out.println("Client is running and has connected to the server");

            System.out.println("Enter username: ");
            String username = input.nextLine();

            System.out.println("\"getallmovies\" , \"getmoviebyid_(id)\" , \"getmoviesbydirector_(director)\" , \"getmoviesbygenre_(genre)\" , \"getmoviesbytitle_(title)\" , \"deletemovie\" , \"updatemovietitle_id_title\" , \"watchMovie\" , \"recommend\"");
            boolean restart = true;
            while (restart) {
                OutputStream os = socket.getOutputStream();
                PrintWriter socketWriter = new PrintWriter(os, true);
                System.out.println("Command:");

                String command = input.nextLine();
                
                Scanner socketReader = new Scanner(socket.getInputStream());

                if (command.startsWith("getallmovies")) {       //get all movies
                    socketWriter.println(command += " " + "t");
                    String in = socketReader.nextLine();
                    System.out.println(in);

                } else if (command.startsWith("getmoviebyid")) {    //get movie by id
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);

                } else if (command.startsWith("getmoviesbygenre")) {    //get movies by genre
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("getmoviesbytitle")) {    //get movies by genre
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("deletemovie")) {    //get movies by genre
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("updatemovietitle")) {    //get movies by genre
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("getmoviesbydirector")) {    //get movies by genre                
                    socketWriter.println(command);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("watchMovie")) {              //watch movie
                    socketWriter.println(command += " " + username);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                } else if (command.startsWith("recommend")) {       //currently working as getmoviewatchedbyuser
                    socketWriter.println(command += " " + username);
                    String in = socketReader.nextLine();
                    System.out.println(in);
                    
                } else if(command.startsWith("quit")) {
                    System.out.println("bye...");
                    
                    socketWriter.close();
                    socketReader.close();
                    socket.close();
                    break;
                } else {
                    System.out.println("No such command!");
                    restart = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }

}
