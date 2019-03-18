/** CLIENT                                                  February 2019 DL 08/03/19
 *
 * This Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 *
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */
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
            Socket socket = new Socket("localhost", 8080);  // connect to server socket, and open new socket

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client: This Client is running and has connected to the server");

            //commands
//            System.out.println("[Commands: \"Time\" to get time, or \"Echo message\" to get echo)]");
//            System.out.println("Please enter a command: ");
//            
            System.out.println("\"getallmovies\" , \"getmoviebyid_(id)\" , \"getmoviesbygenre_(genre)\" , \"getmoviesbytitle_(title)\" , \"deletemovie\" , \"updatemovietitle_id_title\" ");
            System.out.println("Command:");
            
            String command = input.nextLine();  // read a command from the user          

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            socketWriter.println(command);  // write command to socket

            Scanner socketReader = new Scanner(socket.getInputStream());

            
            if (command.startsWith("getallmovies")) {       //get all movies
                String in = socketReader.nextLine();
                System.out.println(in);
                
                
            } else if (command.startsWith("getmoviebyid")) {    //get movie by id
                String in = socketReader.nextLine();
                System.out.println(in);
                
                
            } else if (command.startsWith("getmoviesbygenre")) {    //get movies by genre
                String in = socketReader.nextLine();
                System.out.println(in);
            } 
            
            else if (command.startsWith("getmoviesbytitle")) {    //get movies by genre
                String in = socketReader.nextLine();
                System.out.println(in);
            }
            
            else if (command.startsWith("deletemovie")) {    //get movies by genre
                String in = socketReader.nextLine();
                System.out.println(in);
            }
            
            else if (command.startsWith("updatemovietitle")) {    //get movies by genre
                String in = socketReader.nextLine();
                System.out.println(in);
            }
            
            else if (command.startsWith("getmoviesbydirector")) {    //get movies by genre
                String in = socketReader.nextLine();
                System.out.println(in);
            }
            
            else {
                String in = socketReader.nextLine();// wait for, and retrieve the echo ( or other message)
                System.out.println("Client: Response from server: \"" + in + "\"");
            }
            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }

}
