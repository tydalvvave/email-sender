import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to a mailserver and send one mail.
 *
 * @author Tyler Wians, Initial code provided by Jussi Kangasharju (see README for more details)
 * @version October 21 2016
 *
 */
public class SMTPConnection {
    /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final int SMTP_PORT = 25;
    private static final String NL = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /** Create an SMTPConnection object. Create the socket and the 
    *   associated streams. Initialize SMTP connection. 
	*  
	*  @param: envelope - the header and details of the message
	*  @throws IOexception - throws an exception if the reply from the server is not the expected reply code 
	*/
    public SMTPConnection(Envelope envelope) throws IOException {
	connection = new Socket(envelope.DestAddr, SMTP_PORT);
	fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	toServer =   new DataOutputStream(connection.getOutputStream());

	/* Read a line from server and check that the reply code is 220.
	   If not, throw an IOException. */
	String response = fromServer.readLine();
	if (!response.startsWith("220"))
	{
		connection.close();
		throw new IOException("220 reply not received from server.");
	}

	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
	String localhost = envelope.DestHost;
	sendCommand("HELO " + localhost + NL, 250);

	isConnected = true;
    }

    /** Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller.
	*  
	*  @param: envelope - the header and details of the message
	*  @throws IOexception - throws an exception if the reply from the server is not the expected reply code 
	*/
    public void send(Envelope envelope) throws IOException {
    	sendCommand("MAIL FROM: <" + envelope.Sender + ">" + NL, 250);
    	sendCommand("RCPT TO: <" + envelope.Recipient + ">" + NL, 250);
    	sendCommand("DATA" + NL, 354);
    	sendCommand(envelope.Message + NL + "." + NL, 250);
    	return;
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
	isConnected = false;
	try {
	    sendCommand("QUIT" + NL, 221);
	    connection.close();
	} catch (IOException e) 
	{
	    System.out.println("Unable to close connection: " + e);
	    isConnected = true;
	}
    }

    /** Send an SMTP command to the server. Check that the reply code is
    *  what is is supposed to be according to RFC 821.
	*  
	*  @param: envelope - the header and details of the message
	*  @param: rc - a string that represents the expected reply code from the socket
	*  @throws: IOexception - throws an exception if the reply from the server is not the expected reply code 
	*/
    private void sendCommand(String command, int rc) throws IOException {
	
	/* Write command to server and read reply from server. */
    toServer.write(command.getBytes("US-ASCII"));
    String response = fromServer.readLine();
	
	/* Check that the server's reply code is the same as the parameter
	   reply code. If not, throw an IOException. */
	String rCode = Integer.toString(rc);
	if (!response.startsWith(rCode))
	{	
		System.out.println(rc + " something when wrong with a command: " + command);
		System.out.println(response);
		close();
		throw new IOException(rc + "reply not received from server.");
	}
	
    }
    
    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
	if(isConnected) {
	    close();
	}
	super.finalize();
    }
}
