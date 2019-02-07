import java.net.*;
import java.io.*;

public class HTTPAsk {
    public static void main( String[] args) throws IOException {
      int port  = Integer.parseInt(args[0]
      // This is where we put the data from client
      String fromClient;
      // Get the port number first argument and open port
      ServerSocket HTTPSocket = new ServerSocket(port);

      while(true){
        try {
          String servrOutput = TCPClient();
        } catch(IOException e) {
          System.err.println(e);
        }
      }
    }
}
