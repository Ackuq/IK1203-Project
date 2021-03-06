import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws  IOException{
      // This is where we put the data from client
      String fromClient;
      // Get the port number first argument and open port
      ServerSocket HTTPSocket = new ServerSocket(Integer.parseInt(args[0]));
      // The http header
      String HTTPHeader = "HTTP/1.1 200 OK\r\n\r\n";

      while(true) {
        // This is what we return to the client
        StringBuilder toClient = new StringBuilder();
        // accept client
        Socket conSocket = HTTPSocket.accept();
        // Create input and output streams
        BufferedReader inStream = new BufferedReader(new InputStreamReader(conSocket.getInputStream()));
        DataOutputStream outStream = new DataOutputStream(new DataOutputStream(conSocket.getOutputStream()));
        // The header should return first
        toClient.append(HTTPHeader);
        // While the client is inputting, read the line
        while((fromClient = inStream.readLine()) != null &&  fromClient.length() != 0){
          toClient.append(fromClient + '\n');
        }
        // Echo the input from client
        outStream.writeBytes(toClient.toString());
        // Close the connection
        conSocket.close();
      }
    }
}
