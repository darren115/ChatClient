package main;

import java.awt.FlowLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserInterface {

	private String username;
	private JTextArea textArea;
	private JTextField textField;
	
	private LocalTime time = LocalTime.now();

	public UserInterface() {
		JFrame frame = new JFrame("Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		while (username == null || "".equals(username)) {

			username = JOptionPane.showInputDialog("Enter a username");
		}

		frame.setTitle("Chat Client: " + username);
		ChatClient cc = new ChatClient(username, this);
		//ChatInput ci = new ChatInput(cc);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(cc);
		//executor.execute(ci);

		textArea = new JTextArea(15, 25);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
//		textArea.setPreferredSize(new Dimension(100, 100));
//		textArea.setVisible(true);
//		
		JScrollPane scrollArea = new JScrollPane(textArea);
		scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		textField = new JTextField(20);
		textField.addActionListener(e -> {

			String field = textField.getText();
			if (field.length() > 0) {
				appendMessage();
				cc.sendMessage(textField.getText());
				textField.setText("");

			}

		});

//		JLabel label = new JLabel("JFrame By Example");  
//        JButton button = new JButton();  
//        button.setText("Button");  
//        
//        button.addActionListener(e->{
//			System.out.println(e.toString());
//			textArea.append("Sausages");
//			
//			
//		});
//        panel.add(label);  
//        panel.add(button); 

		panel.add(scrollArea);
		panel.add(textField);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	public void appendMessage(String message) {
		time = LocalTime.now();
		String current = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		textArea.append(current + ": " + message + "\n");
	}

	public void appendMessage() {
		time = LocalTime.now();
		String current = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		textArea.append(current + ": " + username + ": " + textField.getText() + "\n");
	}
}
