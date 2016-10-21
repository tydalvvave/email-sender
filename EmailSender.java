import java.io.*;
import java.net.*;

public class EmailSender
{
   public static void main(String[] args) throws Exception
   {
      // Establish a TCP connection with the mail server.
      Socket socket = new Socket("aspmx.l.google.com", 25);

      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("220")) {
    	 socket.close();
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      OutputStream os = socket.getOutputStream();

      // Send HELO command and get server response.
      String command = "HELO tyler\r\n";
      System.out.print(command);
      os.write(command.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
    	  socket.close();
         throw new Exception("250 reply not received from server.");
      }

      // Send MAIL FROM command.
      String cmd1 = "MAIL FROM: <tpwians@gmail.com>\r\n";
      System.out.print(cmd1);
      os.write(cmd1.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
    	  socket.close();
         throw new Exception("250 reply not received from server.");
      }
      
      // Send RCPT TO command.
      String cmd2 = "RCPT TO: <twians18@cornellcollege.edu>\r\n";
      System.out.print(cmd2);
      os.write(cmd2.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
    	  socket.close();
         throw new Exception("250 reply not received from server.");
      }
      
      // Send DATA command.
      String cmd3 = "DATA\r\n";
      System.out.print(cmd3);
      os.write(cmd3.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("354")) {
    	  socket.close();
         throw new Exception("354 reply not received from server.");
      }
      
      // Send message data.
      String cmd4 = "SUBJECT: Java/Terminal to E-mail\r\n"
      + "TO: <twians18@cornellcollege.edu>\r\n"
      + "FROM: <tpwians@gmail.com>\r\n\n"
      + "This message was sent from a terminal.\r\n"
      + ".\r\n";
      System.out.print(cmd4);
      os.write(cmd4.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
    	  socket.close();
         throw new Exception("250 reply not received from server.");
      }
      
      // Send QUIT command.
      String cmd5 = "QUIT\r\n";
      System.out.print(cmd5);
      os.write(cmd5.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
     
     // Close the socket
     socket.close();
   }
}
