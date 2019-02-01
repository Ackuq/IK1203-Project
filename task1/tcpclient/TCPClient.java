package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      // If nothing is going out from client, use other method
      if(!ToServer) return askServer(hostname, port);

      // Declare the response from server
      StringBuilder stringInput = new StringBuilder();
      String response = null;

      // Create connection to server
      Socket clientSocket = new Socket(hostname, port);

      // Timout if no response in 5 second
      clientSocket.setSoTimeout(5000);

      // Create input and output streams
      DataOutputStream  outputServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      // Write the output from client to server
      outputServer.writeBytes(ToServer + '\n');

      // Handle input from sercver
      try{
        // WHile there's more input, contiune reading
        while((response = inputServer.readLine()) != null && response != "\n"){
          stringInput.append(response + '\n');
        }
        clientSocket.close();
      }
      catch(IOException e){
        clientSocket.close();
      }
      // Return the input
      return stringInput.toString();
    }

    public static String askServer(String hostname, int port) throws  IOException {
      // Declare the response from server
      StringBuilder stringInput = new StringBuilder();
      String response = null;

      // Max 4096 lines of input
      const int max = 4096;

      // Create connection to server
      Socket clientSocket = new Socket(hostname, port);

      // Timeout if no response in 5 seconds
      clientSocket.setSoTimeout(5000);

      // Create input stream from server
      BufferedReader inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      // Counter to keep track of how many lines have been recieved
      int counter = 0;
      try{
        while((response = inputServer.readLine()) != null && response != "\n"){
          // WHile there's more input, contiune reading
          stringInput.append(response + '\n');
          counter++;
          if(counter >= max){
            clientSocket.close();
          }
        }
        clientSocket.close();
      }
      catch(IOException e){
        clientSocket.close();
      }
      // Return the input
      return stringInput.toString();
    }
}
