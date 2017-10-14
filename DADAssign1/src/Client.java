import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.application.Application.launch;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class Client extends Application{
	
	private Socket clientSocket;
	private BufferedReader input;
	private PrintStream output;
	private Scanner scan = new Scanner(System.in);
	private boolean ConnectionStatus = false;
	private String UserType = "";
	TextArea messageArea;
	
	public void start(Stage primaryStage) throws Exception{
		
		//--------------------------------Client Connecting to server----------------------
		String serverAddress = JOptionPane.showInputDialog(
				"Enter SKOPE server address :");
		//try to connect to server
		try {
		clientSocket = new Socket(serverAddress,8080);
		}catch(Exception e) {
    		JOptionPane.showConfirmDialog(null, "Server is not responding", "Server Error", 0);
    		//Safe exit mode
    		System.exit(1);
		}
		
		
		output = new PrintStream(clientSocket.getOutputStream());
		input = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		
		//read message to test whether connected to server
		String message = input.readLine();
		System.out.println(message);
		
		//----------------------------------------GUI (FX) ---------------------------------
		Pane rootPane = new Pane();
		
		// Background Layout
		Pane backgroundPane = new Pane();
			try {
					FileInputStream imageStream = new FileInputStream("Background.jpg");
					backgroundPane.setPadding(new Insets(0,0,0,0));
					Image image = new Image(imageStream);
					backgroundPane.getChildren().add(new ImageView(image));
				
				}catch(FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
				}catch(IOException e) {
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
				}
		
		// Logo Layout
		HBox LogoContainer = new HBox();
			try {
				FileInputStream imageStream2 = new FileInputStream("Logo.png");
				LogoContainer.setPadding(new Insets(40,0,0,170));
				Image Logo = new Image(imageStream2);
				ImageView LogoView = new ImageView(Logo);
				LogoView.setFitWidth(250);
				LogoView.setFitHeight(95);
				LogoContainer.getChildren().add(LogoView);
		
				}catch(FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
				}catch(IOException e) {
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
				}
		
		
		// User Credentials Pane	
		GridPane UserPane = new GridPane();
			UserPane.setVgap(25);
			UserPane.setHgap(25);
			UserPane.setPadding(new Insets(160,0,0,140));
			Label lbl1 = new Label("Username :");
			lbl1.setStyle("-fx-text-fill: orange;\r\n" + 
						  "    -fx-font-weight: bold;\r\n" + 
						  "    -fx-font-family: Sans-serif ;\r\n" +
					      "    -fx-font-size: 20;");
			TextField Username = new TextField();
			Username.setStyle("-fx-faint-focus-color: transparent;\r\n" +
		    				  "-fx-focus-color: orange;");
			Username.setMaxWidth(320);
			Label lbl2 = new Label("Password :");
			lbl2.setStyle("-fx-text-fill: orange;\r\n" + 
					  "    -fx-font-weight: bold;\r\n" + 
					  "    -fx-font-family: Sans-serif ;\r\n" +
				      "    -fx-font-size: 20;");
			TextField Password = new TextField();
			Password.setStyle("-fx-faint-focus-color: transparent;\r\n" +
  				  			  "-fx-focus-color: orange;");
			Password.setMaxWidth(320);
			
			UserPane.add(lbl1, 0, 0);
			UserPane.add(Username, 1, 0);
			UserPane.add(lbl2, 0, 1);
			UserPane.add(Password, 1, 1);
		
		// Layout container for Line and buttons
		VBox MainLayout = new VBox(25);
			MainLayout.setPadding(new Insets(290,0,0,120));
			Line line = new Line();
			line.setStyle("-fx-stroke: orange;");
			line.setStartX(0.0f);
			line.setStartY(0.0f);
			line.setEndX(360.0f);
			line.setEndY(0.0f);
			line.setStrokeWidth(2.5f);
			
			
		// Button Holder
		HBox Buttons = new HBox(30);
			Buttons.setPadding(new Insets(0,0,0,165));
			Button btnLogin = new Button("Login");
			btnLogin.setPrefWidth(70);
			btnLogin.setStyle("-fx-font: 16 arial;\r\n" +
							  "-fx-base: orange;\r\n" +
							  "-fx-font-weight: bold;\r\n" +
							  "-fx-background: orange;\r\n "+
							  "-fx-focus-color: orange;");
			Button btnExit = new Button("Exit");
			btnExit.setPrefWidth(70);
			btnExit.setStyle("-fx-font: 16 arial;\r\n" +
					  "-fx-base: orange;\r\n" +
					  "-fx-font-weight: bold;\r\n" +
					  "-fx-background: orange;\r\n "+
					  "-fx-focus-color: orange;");
		Buttons.getChildren().addAll(btnLogin,btnExit);
		MainLayout.getChildren().addAll(line,Buttons);	
		
		rootPane.getChildren().addAll(backgroundPane,LogoContainer,MainLayout,UserPane);
		Scene scene1 = new Scene(rootPane, 600, 400);
		primaryStage.setTitle("SKOPE");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		// ----------------------------------------------SCENE 2 -------------------------------------------------
		Pane rootPane2 = new Pane();
		
		Pane backgroundPane2 = new Pane();
		try {
				FileInputStream imageStream = new FileInputStream("Background.jpg");
				backgroundPane2.setPadding(new Insets(0,0,0,0));
				Image image = new Image(imageStream);
				backgroundPane2.getChildren().add(new ImageView(image));
			
			}catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
			}catch(IOException e) {
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
			}
	
		// Logo Layout
		HBox LogoContainer2 = new HBox();
		try {
			FileInputStream imageStream2 = new FileInputStream("Logo.png");
			LogoContainer2.setPadding(new Insets(40,0,0,170));
			Image Logo = new Image(imageStream2);
			ImageView LogoView = new ImageView(Logo);
			LogoView.setFitWidth(250);
			LogoView.setFitHeight(95);
			LogoContainer2.getChildren().add(LogoView);
	
			}catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
			}catch(IOException e) {
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); 
			}
	
		
		// Main container for text area, text box and buttons
		VBox MessageContainer = new VBox(20);
			MessageContainer.setPadding(new Insets(150,0,0,50));
			
		// Container for Text area
		HBox MessageAreaContainer = new HBox();
				messageArea = new TextArea();
				messageArea.setPrefWidth(500);
				messageArea.setPrefHeight(300);
				messageArea.setEditable(false);
				messageArea.textProperty().unbind();
				
				// Buttons (send and exit)
				Button SaveMsg = new Button("Save");
				Button SendMsg = new Button("Send");
				Button ExitMsg = new Button("Exit");
				SendMsg.setPrefWidth(70);
				SendMsg.setStyle("-fx-font: 16 arial;\r\n" +
								  "-fx-base: orange;\r\n" +
								  "-fx-font-weight: bold;\r\n" +
								  "-fx-background: orange;\r\n "+
								  "-fx-focus-color: orange;");
				
				ExitMsg.setPrefWidth(70);
				ExitMsg.setStyle("-fx-font: 16 arial;\r\n" +
								  "-fx-base: orange;\r\n" +
								  "-fx-font-weight: bold;\r\n" +
								  "-fx-background: orange;\r\n "+
								  "-fx-focus-color: orange;");
				
				SaveMsg.setPrefWidth(70);
				SaveMsg.setStyle("-fx-font: 16 arial;\r\n" +
								  "-fx-base: orange;\r\n" +
								  "-fx-font-weight: bold;\r\n" +
								  "-fx-background: orange;\r\n "+
								  "-fx-focus-color: orange;");
				
				MessageAreaContainer.getChildren().addAll(messageArea);
				
				
		// Text Box container
		HBox Messanger = new HBox(20);
				TextField messageSend = new TextField();
				messageSend.setMinWidth(500);
				Messanger.getChildren().addAll(messageSend);
				
		// Button container
		HBox ButtonPanel = new HBox(20);
				ButtonPanel.setPadding(new Insets(0,0,0,0));
				ButtonPanel.getChildren().addAll(SendMsg,ExitMsg);
				
				
				
		MessageContainer.getChildren().addAll(MessageAreaContainer,Messanger,ButtonPanel);
		
		
		
		rootPane2.getChildren().addAll(backgroundPane2,LogoContainer2,MessageContainer);
		
		/*
		 * BUTTON ACTIONS
		 */
		
		// Send Message (Customer)
		SaveMsg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	
            	BufferedWriter out = null;
            	try  
            	{
            	    PrintWriter outputStream = new PrintWriter("out.txt"); //true tells to append data.
            	    for (String InputStream : messageArea.getText().split("\\n")) {
            	    	outputStream.println(line);
            	    }
            	    outputStream.close();
            	}
            		
            		
            	catch (IOException e)
            	{
            	    System.err.println("Error: " + e.getMessage());
            	}
            	  
            	}
            
            });
		
		SendMsg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	//messageArea.setEditable(true);
            	 messageArea.appendText("You : " +(messageSend.getText()) + "\n");
            	 output.println(messageSend.getText());
            	 
            	}
            
            });
		
		// Exit Program
		btnExit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	System.exit(0);
            	
            	}
            
            });
		
		// Login Button -- Second Scene
		btnLogin.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			String textReturned = "Client";
			String textReturned2 = "Agent";
          
            public void handle(ActionEvent event) {
            	
            	//Send username and password to server
            	output.println(Username.getText()+";"+Password.getText());
            	try {
            	
            	//Server should respond (true) or (false) based on the username and password given 
            	String command = input.readLine();
            		
            	//Command is true will launch scene 2
            	if(command.matches(textReturned)) {
            	ConnectionStatus = true;
            	UserType = "Client";

            	//Show second scene (CUSTOMER USE)
            	Scene scene2 = new Scene(rootPane2 ,600,600);
            	primaryStage.setScene(scene2);
            	new MessageThread().start();
            	}
            	else if(command.matches(textReturned2)) {
            		
            	ConnectionStatus = true;
            	UserType = "Agent";
                //Show second scene (ADMIN USE)
            	
            	//Save button available to agent
            	if(UserType.matches("Agent"))
					ButtonPanel.getChildren().add(SaveMsg);
            	
            	new MessageThread().start();
                Scene scene2 = new Scene(rootPane2 ,600,600);
                primaryStage.setScene(scene2);
            	}
            	//if false promt JPane and Request entry of password again
            	else {
            		int dialogButton = JOptionPane.CANCEL_OPTION;
            		dialogButton = JOptionPane.showConfirmDialog(null, "Re-enter Password", "Inccorect Password", dialogButton);
            		if(dialogButton == JOptionPane.OK_OPTION){
            		
            	}
            	}
            	}
            	catch(IOException e)
            	{
            		System.out.println(e);
            	}
            	}
            
            });
		
		// Exit Program
				ExitMsg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

		            
		            public void handle(ActionEvent event) {
		            	
		            	System.exit(0);
		            	
		            	}
		            
		            });
		
		primaryStage.show();
	}
	
	//Main - start
	public static void main(String[]args) {
		Application.launch(args);
	}
	
	
	//Thread to read message
	public class MessageThread extends Thread{
		
		public void run()//Override
		{
			String line;
			try {
				while(true)
				{
					line = input.readLine();
					messageArea.appendText("Agent : " + line+"\n");
					
				}
				
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}

}
