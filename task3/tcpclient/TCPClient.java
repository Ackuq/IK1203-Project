package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

  final static int max = 1024;

  public static String askServer(String hostname, int port, String ToServer) throws  IOException {
    // If nothing is going out from client, use other method
    if(ToServer == null) return askServer(hostname, port);

    // Declare the response from server
    StringBuilder stringInput = new StringBuilder();
    String response = null;

    // Create connection to server
    Socket clientSocket = new Socket(hostname, port);

    // Timout if no response in 3 second
    clientSocket.setSoTimeout(3000);

    // Create input and output streams
    DataOutputStream  outputServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    // Write the output from client to server
    outputServer.writeBytes(ToServer + '\n');

    int counter = 0;
    // Handle input from sercver
    try{
      // WHile there's more input, contiune reading
      while((response = inputServer.readLine()) != null && counter++ < max){
        stringInput.append(response + '\n');
      }
      clientSocket.close();
      // Return the input of the server response
      return stringInput.toString();
    }
    catch(IOException e){
      clientSocket.close();
      // Return the input of the server response
      return stringInput.toString();
    }
  }

  public static String askServer(String hostname, int port) throws  IOException {
    // Declare the response from server
    StringBuilder stringInput = new StringBuilder();
    String response = null;

    // Create connection to server
    Socket clientSocket = new Socket(hostname, port);

    // Timeout if no response in 3 seconds
    clientSocket.setSoTimeout(3000);

    // Create input stream from server
    BufferedReader inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    // Counter to keep track of how many lines have been recieved
    int counter = 0;
    try{
      while((response = inputServer.readLine()) != null && counter++ < max){
        // While there's more input, contiune reading
        stringInput.append(response + '\n');
      }
      clientSocket.close();
      // Return the input of the server response
      return stringInput.toString();
    }
    catch(IOException e){
      clientSocket.close();
      // Return the input of the server response
      return stringInput.toString();
    }
  }
}
