import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main( String[] args) throws IOException {

      int httpPort;
      if(args.length > 0){
        httpPort = Integer.parseInt(args[0]);
      } else {
        httpPort = 8888;
      }

      // Get the port number first argument and open port
      ServerSocket HTTPSocket = new ServerSocket(httpPort);

      // Our HTTP headers
      String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
      String HTTP400 = "HTTP/1.1 400 Bad Request\r\n";
      String HTTP404 = "HTTP/1.1 404 Not Found\r\n";
      // This is where we put the data from client
      String request;
      // Data from client
      String port;
      String hostname;
      String string;

      while(true){
        try {
          // accept client
          Socket conSocket = HTTPSocket.accept();
          // Create input and output streams
          BufferedReader inStream = new BufferedReader(new InputStreamReader(conSocket.getInputStream()));
          DataOutputStream outStream = new DataOutputStream(new DataOutputStream(conSocket.getOutputStream()));
          // Our response to the server
          StringBuilder response =  new StringBuilder();
          // Get the request from client
          request = inStream.readLine();

          if(request != null){
            // Split the request into parts
            String[] params = request.split("[?&= ]");

            // Reset the data variables
            port = null;
            hostname = null;
            string = null;
            // Get the values from the string array
            for(int i = 0; i < params.length; i++){
              if(params[i].equals("hostname"))
                hostname = params[++i];
              else if(params[i].equals("port"))
                port = params[++i];
              else if(params[i].equals("string"))
                string  = params[++i];
            }
            // If both hostname and port are assignd
            if(hostname != null && port != null && port.matches("[0-9]+") && params[1].equals("/ask")){
              try{
                // Get response from server
                String serverResp = TCPClient.askServer(hostname,  Integer.parseInt(port), string);
                // First add the HTTP header response
                response.append(HTTP200);
                // Add the server response as data
                response.append(serverResp);
              } catch(IOException e) {
                // If error, we couldn't connect to server
                response.append(HTTP404);
              }
            } else {
              // If either hostname or port is left empty (or we don't include a ask)
              response.append(HTTP400);
            }
            // Return our response to client
            outStream.writeBytes(response.toString());
          }
          // close connectiion
          conSocket.close();
        } catch(IOException e) {
          System.err.println(e);
        }
      }
    }

}
