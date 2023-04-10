package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatInput implements Runnable {
	
	private boolean running = true;
	private String str;

	private BufferedReader stdin;

	private ChatClient client;

	public ChatInput(ChatClient client) {
		stdin = new BufferedReader(new InputStreamReader(System.in));
		this.client = client;
	}

	@Override
	public void run() {
		while (running) {
			try {
				str = stdin.readLine();
				client.sendMessage(str);
				
				if (str.equalsIgnoreCase("FINISHED")) {
					System.out.println("Connection ended...");
					running = false;
					client.closeClient();
					stdin.close();
					break;
				}
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}

	}
	
	private void close() {
		try {
			client.closeClient();
			stdin.close();
		}
		catch(IOException e) {
			
		}
		running = false;
	}

}
