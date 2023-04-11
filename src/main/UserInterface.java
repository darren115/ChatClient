package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class UserInterface {

	private String username;
//	private JTextArea textArea;
	private JTextField textField;
	private JTextPane textArea;

	private LocalTime time = LocalTime.now();

	public UserInterface() {
		JFrame frame = new JFrame("Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		while (username == null || "".equals(username)) {

			username = JOptionPane.showInputDialog("Enter a username");
		}

		frame.setTitle("Chat Client: " + username);
		ChatClient cc = new ChatClient(username, this);
		// ChatInput ci = new ChatInput(cc);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(cc);
		// executor.execute(ci);

//		textArea = new JTextArea(15, 25);
		textArea = new JTextPane();
		textArea.setEditable(false);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
//		textArea.setPreferredSize(new Dimension(100, 100));
//		textArea.setVisible(true);
//		
		JScrollPane scrollArea = new JScrollPane(textArea);
		scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollArea.setPreferredSize(new Dimension(300, 200));

		textField = new JTextField(20);
		textField.addActionListener(e -> {

			String field = textField.getText();
			if (field.length() > 0) {
				appendMessage();
				cc.sendMessage(textField.getText());
				textField.setText("");

			}

		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;

		panel.add(scrollArea, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(textField, c);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	public void appendMessage(String message) {
		time = LocalTime.now();
		String current = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		try {
			StyledDocument doc = textArea.getStyledDocument();
			Style style = textArea.addStyle("", null);
			StyleConstants.setForeground(style, Color.GREEN);
			doc.insertString(doc.getLength(), current + ": ", style);
			StyleConstants.setForeground(style, Color.BLACK);
			doc.insertString(doc.getLength(), message + "\n", style);
		} catch (Exception e) {

		}
	}

	public void appendMessage() {
		appendMessage(username + ": " + textField.getText());
	}
}
