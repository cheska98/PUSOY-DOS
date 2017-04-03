package Connection;

import java.io.*;  // Imported because we need the InputStream and OuputStream classes
import java.net.*; // Imported because the Socket class is needed
 
public class Client {
	
	private static ClientSide cs;
 
    public static void main(String args[]) throws Exception {  
 
        // The default port     
        int clientport = 7777;
        String host = "localhost";   //change to localhost if ever
 
        if (args.length < 1) {
           System.out.println("Usage: UDPClient " + "Now using host = " + host + ", Port# = " + clientport);
        } 
        // Get the port number to use from the command line
        else {      
           //host = args[0];
           clientport = Integer.valueOf(args[0]).intValue();
           System.out.println("Usage: UDPClient " + "Now using host = " + host + ", Port# = " + clientport);
        } 
 
        // Get the IP address of the local machine - we will use this as the address to send the data to
        InetAddress ia = InetAddress.getByName(host);
        
        cs = new ClientSide();
        
        SenderThread sender = new SenderThread(ia, clientport, cs);
        sender.start();
        ReceiverThread receiver = new ReceiverThread(sender.getSocket(), cs);
        receiver.start();
    }
}      
 
class SenderThread extends Thread {
 
    public static InetAddress serverIPAddress;
    public static int serverport;
    private DatagramSocket udpClientSocket;
    private boolean stopped = false;
    private ClientSide cs;
 
    public SenderThread(InetAddress address, int serverport, ClientSide cs) throws SocketException {
        this.serverIPAddress = address;
        this.serverport = serverport;
        this.cs = cs;
        // Create client DatagramSocket
        this.udpClientSocket = new DatagramSocket();
        this.udpClientSocket.connect(serverIPAddress, serverport);
    }
    public void halt() {
        this.stopped = true;
    }
    public DatagramSocket getSocket() {
        return this.udpClientSocket;
    }
 
    public void run() {       
        try {    
        	//send blank message
        	byte[] data = new byte[1024];
        	data = "".getBytes();
        	DatagramPacket blankPacket = new DatagramPacket(data,data.length , serverIPAddress, serverport);
            udpClientSocket.send(blankPacket);
            
        	// Create input stream
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            while (true) 
            {
                if (stopped)
                    return;
 
                // Message to send
                String clientMessage = inFromUser.readLine();
 
                if (clientMessage.equals("."))
                	break;
 
                // Create byte buffer to hold the message to send
                byte[] sendData = new byte[1024];
 
                // Put this message into our empty buffer/array of bytes
                sendData = clientMessage.getBytes();
 
                // Create a DatagramPacket with the data, IP address and port number
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);
 
                // Send the UDP packet to server
                System.out.println("I just sent: "+clientMessage);
                udpClientSocket.send(sendPacket);
 
                Thread.yield();
            }
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}   
 
class ReceiverThread extends Thread {
 
    private DatagramSocket udpClientSocket;
    private boolean stopped = false;
    private ClientSide cs;
 
    public ReceiverThread(DatagramSocket ds, ClientSide cs) throws SocketException {
        this.udpClientSocket = ds;
        this.cs = cs;
    }
 
    public void halt() {
        this.stopped = true;
    }
 
    public void run() {
 
        // Create a byte buffer/array for the receive Datagram packet
        byte[] receiveData = new byte[1024];
 
        while (true) {            
            if (stopped)
            return;
 
            // Set up a DatagramPacket to receive the data into
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Waiting for stuff...");
            try {
                // Receive a packet from the server (blocks until the packets are received)
                udpClientSocket.receive(receivePacket);
                System.out.println("Receiving...");
                // Extract the reply from the DatagramPacket      
                String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());
                
                //determines what the client is receiving
                if(serverReply.equals("sendPlayerInfo"))
                	receivePlayerInfo();
                else if(serverReply.equals("isItYou"))
                	sendNum(); 
                else if(serverReply.equals("printStatus"))
                	receiveStatus();
                
                // print to the screen
                System.out.println("UDPClient: Response from Server: \"" + serverReply + "\"\n");
 
                Thread.yield();
            } 
            catch (IOException ex) {
            System.err.println(ex);
            }
        }
    }
    
    public void receivePlayerInfo() throws IOException{
    	byte[] receiveData = new byte[1024];
    	DatagramPacket receivePacket;
    	
    	//receive port num
    	receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	udpClientSocket.receive(receivePacket);
    	String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());
    	cs.setPlayerPort(Integer.parseInt(serverReply));
    	
    	confirm();
      	
    	//receive player num
    	receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	udpClientSocket.receive(receivePacket);
    	String serverReply2 =  new String(receivePacket.getData(), 0, receivePacket.getLength());
    	cs.setPlayerNum(Integer.parseInt(serverReply2));
    	
    	confirm(); 
    	
    	//receiving the cards
    	while(true){
    		receivePacket = new DatagramPacket(receiveData, receiveData.length);
        	udpClientSocket.receive(receivePacket);
        	String cardNum =  new String(receivePacket.getData(), 0, receivePacket.getLength());
        	
        	//check if dapat na magbreak
        	if(cardNum.equals("taposNaAngCards"))
        		break;
        	
        	confirm();
        	
        	receivePacket = new DatagramPacket(receiveData, receiveData.length);
        	udpClientSocket.receive(receivePacket);
        	String cardSuit =  new String(receivePacket.getData(), 0, receivePacket.getLength());
        	
        	confirm();
        	
        	cs.addCard(Integer.parseInt(cardNum), Integer.parseInt(cardSuit));
    	}
    	//testLang();
    }
    
    public void confirm() throws IOException{
    	byte[] data = new byte[1024];
    	data = "confirm mah boi".getBytes();
    	DatagramPacket confirmPacket = new DatagramPacket(data,data.length , SenderThread.serverIPAddress, 
    																		 SenderThread.serverport);
        udpClientSocket.send(confirmPacket);
    }
    
    public void sendNum() throws IOException{
    	byte[] data = new byte[1024];
    	data = Integer.toString(cs.getPlayerNum()).getBytes();
    	DatagramPacket numPacket = new DatagramPacket(data,data.length , SenderThread.serverIPAddress, 
    																		 SenderThread.serverport);
        udpClientSocket.send(numPacket);
    }
    
    public void receiveStatus() throws IOException{
    	confirm();  //to tell server client is ready to accept
    	
    	byte[] receiveData = new byte[1024];
    	
    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	udpClientSocket.receive(receivePacket);
    	String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());
    	System.out.println(serverReply);
    }
    
    public void testLang(){
    	cs.testLang();
    }
}