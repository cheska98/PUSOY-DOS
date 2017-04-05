package Connection;

import java.io.IOException;
import java.net.*; // Imported because the Socket class is needed
import java.util.HashSet;
import Model.*;

public class Server {

	public HashSet<Integer> portSet = new HashSet<Integer>();
	private DatagramSocket udpServerSocket;
	private int tempPort;
	private InetAddress tempIP;

	public Server() throws Exception {

		// The chosen port
		int serverport = 7777;

		System.out.println("Usage: UDPServer " + "Now using Port# = " + serverport);

		// Open a new datagram socket on the specified port
		udpServerSocket = new DatagramSocket(serverport);

		System.out.println("Server started...\n");
	}

	public int receiveStuff(int sendWhere) throws IOException {
		// Create byte buffers to hold the messages to send and receive
		byte[] receiveData = new byte[1024];

		// Create an empty DatagramPacket packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Block until there is a packet to receive, then receive it (into our
		// empty packet)
		udpServerSocket.receive(receivePacket);

		// Extract the message from the packet and make it into a string, then
		// trim off any end characters
		String clientMessage = (new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength())).trim();

		// Print some status messages
		System.out.println("Client Connected - Socket Address: " + receivePacket.getSocketAddress());
		System.out.println("Client message: \"" + clientMessage + "\"");

		// Get the IP address and the the port number which the received
		// connection came from
		InetAddress clientIP = receivePacket.getAddress();

		// Print out status message
		System.out.println("Client IP Address & Hostname: " + clientIP + ", " + clientIP.getHostName() + "\n");

		if (sendWhere == 1) // send to all other clients
			sendStuff(receivePacket, clientIP, clientMessage);
		else if (sendWhere == 2) { // send data to newly joined client
			this.tempPort = receivePacket.getPort();
			this.tempIP = clientIP;
			portSet.add(tempPort);
			return tempPort;
		}

		return 0;
	}

	public int receiveResponse() throws IOException {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		udpServerSocket.receive(receivePacket);
		String clientMessage = (new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength())).trim();

		return Integer.parseInt(clientMessage);
	}

	public void sendStuff(DatagramPacket receivePacket, InetAddress clientIP, String clientMessage) throws IOException {
		// Get the port number which the received connection came from
		int clientport = receivePacket.getPort();
		System.out.println("Adding " + clientport);
		portSet.add(clientport);

		// Response message
		String returnMessage = clientMessage;
		System.out.println(returnMessage);
		// Create an empty buffer/array of bytes to send back
		byte[] sendData = new byte[1024];

		// Assign the message to the send buffer
		sendData = returnMessage.getBytes();

		for (Integer port : portSet) {
			System.out.println(port != clientport);
			if (port != clientport) {
				// Create a DatagramPacket to send, using the buffer, the
				// clients IP address, and the clients port
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
				System.out.println("Sending");
				// Send the echoed message
				udpServerSocket.send(sendPacket);
			}
		}
	}

	public void sendPlayerInfo(Player player) throws IOException {

		int num = player.getNum();
		byte[] sendData = new byte[1024];
		DatagramPacket sendPacket;

		String series = "sendPlayerInfo";
		sendData = series.getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
		udpServerSocket.send(sendPacket);

		// acknowledge("packet series: player info sent");

		sendData = Integer.toString(tempPort).getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
		udpServerSocket.send(sendPacket);

		acknowledge("port sent");

		sendData = Integer.toString(num).getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
		udpServerSocket.send(sendPacket);

		acknowledge("player num sent");

		// loop for giving out the cards
		for (Card c : player.getHand()) {
			sendData = String.valueOf(c.getNumber()).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
			udpServerSocket.send(sendPacket);

			acknowledge("a card num was sent");

			sendData = String.valueOf(c.getSuit()).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
			udpServerSocket.send(sendPacket);

			acknowledge("a card suit was sent");
		}
		sendData = "taposNaAngCards".getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, tempPort);
		udpServerSocket.send(sendPacket);
	}

	public void acknowledge(String message) throws IOException {
		byte[] receiveData = new byte[1024];

		// Create an empty DatagramPacket packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Block until there is a packet to receive, then receive it (into our
		// empty packet)
		udpServerSocket.receive(receivePacket);
		String clientMessage = (new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength())).trim();

		System.out.println(clientMessage);
		System.out.println(message);
	}

	public int search(int playerNum) throws IOException {
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		sendData = "isItYou".getBytes();

		for (Integer port : portSet) {
			// Create a DatagramPacket to send, using the buffer, the
			// clients IP address, and the clients port
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, port);
			System.out.println("Searching..");
			// Send the echoed message
			udpServerSocket.send(sendPacket);

			// Create an empty DatagramPacket packet
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// Block until there is a packet to receive, then receive it (into
			// our
			// empty packet)
			udpServerSocket.receive(receivePacket);
			String searchResult = (new String(receivePacket.getData(), receivePacket.getOffset(),
					receivePacket.getLength())).trim();
			if (Integer.parseInt(searchResult) == playerNum) {
				return port;
			}
		}
		return 0;
	}

	public void printStatus(String message, int port) throws IOException {
		byte[] sendData = new byte[1024];

		sendData = "printStatus".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, port);
		udpServerSocket.send(sendPacket);

		//acknowledge("notified status");

		sendData = message.getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, port);
		udpServerSocket.send(sendPacket);
	}

	public void removalOfCards(int port) throws IOException {
		byte[] sendData = new byte[1024];

		sendData = "removeNa".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, port);
		udpServerSocket.send(sendPacket);
	}
	
	public void showThatHand(int port) throws IOException{
		byte[] sendData = new byte[1024];

		sendData = "patinginNaman".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tempIP, port);
		udpServerSocket.send(sendPacket);
	}

	public void sendField(Field field) throws IOException{    //actually sends a card
		String j, k;
		for (Integer port : portSet) {
			printStatus("Cards on Field: ", port);
			
			for(Card c: field.getCurrCombi()){
				switch(c.getNumber()){
					case 11: j = "J";
						break;
					case 12: j = "Q";
						break;
					case 13: j = "K";
						break;
					case 14: j = "A";
						break;
					case 15: j = "2";
						break;
					default: j = Integer.toString(c.getNumber());
				}
				
				switch(c.getSuit()){
					case 1: k = "C";
						break;
					case 2: k = "S";
						break;
					case 3: k = "H";
						break;
					case 4: k = "D";
						break;
					default: k = "wat happened";
				}
				printStatus(j + " " + k, port);
			}
		}
	}
}