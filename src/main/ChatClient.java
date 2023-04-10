package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable{

	private boolean running = true;
	
	private Socket socket;
	private BufferedReader input;
	private PrintStream output;
	private String str;
	
	private String username;
	
	private UserInterface UI;
	
	public ChatClient(String username, UserInterface main) {
		try {
			this.username = username;
			this.UI = main;
			
			socket = new Socket("localhost", 5000); 
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			output = new PrintStream(socket.getOutputStream());
			
			output.println(username + " connected");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (running) {
//			System.out.print("Client : ");

			try {			
				str = input.readLine();
				UI.appendMessage(str);
				
				System.out.print("Server : " + str + "\n");
			} catch (IOException e) {
				UI.appendMessage("Server closed the connection");
				System.out.println("Server closed the connection");
				closeClient();
			}

		}

		closeClient();
		
	}
	
	public void sendMessage(String message) {
		if(message.length()!= 0)
			output.println(message);
	}
	
	public void closeClient() {
		this.running = false;
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

}
