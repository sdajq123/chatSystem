
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

/*
 * Agent and Client GUI Application 
 * @author : Jonathan Foong
 */

public class Client extends Application{
	
	private Socket clientSocket;  //Socket
	private BufferedReader input; //Socket reader
	private PrintStream output;   //Socket writer
	private Scanner scan = new Scanner(System.in); //Input scanner
	private boolean ConnectionStatus = false;      //Connected (true/false)
	private String UserType = "";                  //UserType (Client/Agent)
	TextArea messageArea;                          //Text area 
	
	public void start(Stage primaryStage) throws Exception{
		
		/*----------------------------------Client Connecting to server-------------------------*/
		String serverAddress = JOptionPane.showInputDialog("Enter SKOPE server address :");
		
		try { 	//try to connect to server
		
			clientSocket = new Socket(serverAddress,8080); // connect to server socket (Ip,Port)
			
		} catch (Exception e) {	//Error catcher (server does not exist)
    		
			JOptionPane.showConfirmDialog(null, "Server is not responding", "Server Error", 0);
    		
    		System.exit(1);	//Safe exit mode
		}//end try/catch
		
		output = new PrintStream(clientSocket.getOutputStream());//output to write
		
		input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//input to read
		
		String message = input.readLine();//test
		
		System.out.println(message);//test
		
		//----------------------------------------GUI (FX) ---------------------------------
		Pane rootPane = new Pane();//Main Container
		
		Pane backgroundPane = new Pane();//Background Container
		
			try {
				
					FileInputStream imageStream = new FileInputStream("Background.jpg");//background image
					backgroundPane.setPadding(new Insets(0,0,0,0));
					Image image = new Image(imageStream);
					backgroundPane.getChildren().add(new ImageView(image));
				
				}catch(FileNotFoundException e){
					
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e);//missing background file
					
				}catch(IOException e) {
					
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); //error in IO
					
				}
		
		HBox LogoContainer = new HBox();//Logo container
		
			try {
				
				FileInputStream imageStream2 = new FileInputStream("Logo.png");//logo image
				LogoContainer.setPadding(new Insets(40,0,0,170));
				Image Logo = new Image(imageStream2);
				ImageView LogoView = new ImageView(Logo);
				LogoView.setFitWidth(250);
				LogoView.setFitHeight(95);
				LogoContainer.getChildren().add(LogoView);
		
				}catch(FileNotFoundException e){
					
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e); //missing background file
					
				}catch(IOException e) {
					
					JOptionPane.showMessageDialog(null, "File Missing Error : " + e);//error in IO
				}
		
		GridPane UserPane = new GridPane();//User credentials container
		
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
		
		
		VBox MainLayout = new VBox(25);// Layout container for Line and buttons
		
			MainLayout.setPadding(new Insets(290,0,0,120));
			Line line = new Line();
			line.setStyle("-fx-stroke: orange;");
			line.setStartX(0.0f);
			line.setStartY(0.0f);
			line.setEndX(360.0f);
			line.setEndY(0.0f);
			line.setStrokeWidth(2.5f);
			
		
		HBox Buttons = new HBox(30);// Button container
		
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
		
		rootPane.getChildren().addAll(backgroundPane,LogoContainer,MainLayout,UserPane);//login container
		Scene scene1 = new Scene(rootPane, 600, 400);//Login scene
		primaryStage.setTitle("SKOPE");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		// ----------------------------------------------SCENE 2 -------------------------------------------------
		Pane rootPane2 = new Pane();//main container 2
		
		Pane backgroundPane2 = new Pane();//Background container
		try {
			
				FileInputStream imageStream = new FileInputStream("Background.jpg");
				backgroundPane2.setPadding(new Insets(0,0,0,0));
				Image image = new Image(imageStream);
				backgroundPane2.getChildren().add(new ImageView(image));
			
		}catch(FileNotFoundException e){
			
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); //missing file error
				
		}catch(IOException e) {
			
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); //error in IO
		}
	
		HBox LogoContainer2 = new HBox();// Logo Layout
		
		try {
			
			FileInputStream imageStream2 = new FileInputStream("Logo.png");//Logo image
			LogoContainer2.setPadding(new Insets(40,0,0,170));
			Image Logo = new Image(imageStream2);
			ImageView LogoView = new ImageView(Logo);
			LogoView.setFitWidth(250);
			LogoView.setFitHeight(95);
			LogoContainer2.getChildren().add(LogoView);
	
		}catch(FileNotFoundException e){
			
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); //missing file error
				
		}catch(IOException e) {
			
				JOptionPane.showMessageDialog(null, "File Missing Error : " + e); // error in IO
		}
	
		VBox MessageContainer = new VBox(20);// Main container for text area, text box and buttons
		
			MessageContainer.setPadding(new Insets(150,0,0,50));
			
		// Container for Text area
		HBox MessageAreaContainer = new HBox();//Text area 
		
				messageArea = new TextArea();
				messageArea.setPrefWidth(500);
				messageArea.setPrefHeight(300);
				messageArea.setEditable(false);
				messageArea.setStyle("-fx-faint-focus-color: transparent;\r\n" +
									 "-fx-focus-color: orange;");
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
		
		HBox Messanger = new HBox(20);// Text Box container
		
				TextField messageSend = new TextField();
				
				messageSend.setStyle("-fx-faint-focus-color: transparent;\r\n" +
									 "-fx-focus-color: orange;");
				
				messageSend.setMinWidth(500);
				Messanger.getChildren().addAll(messageSend);
				
		HBox ButtonPanel = new HBox(20);// Button container
		
				ButtonPanel.setPadding(new Insets(0,0,0,0));
				ButtonPanel.getChildren().addAll(SendMsg,ExitMsg);
				
		Line line2 = new Line();
		
				line2.setStyle("-fx-stroke: orange;");
				line2.setStartX(0.0f);
				line2.setStartY(0.0f);
				line2.setEndX(495.0f);
				line2.setEndY(0.0f);
				line2.setStrokeWidth(3.0f);		
				
		MessageContainer.getChildren().addAll(MessageAreaContainer,line2,Messanger,ButtonPanel);//add stuff in message container
		rootPane2.getChildren().addAll(backgroundPane2,LogoContainer2,MessageContainer);//add all into main container 2
		
		/*
		 * BUTTON ACTIONS
		 */
		
		//Save Message (Agent Only)
		SaveMsg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	
            	BufferedWriter out = null;
            	try  
            	{
            	    PrintWriter outputStream = new PrintWriter("out.txt"); 
            	    for (String InputStream : messageArea.getText().split("\\n")) {
            	    	outputStream.println(InputStream);
            	    }
            	    outputStream.close();
            	}
            		
            		
            	catch (IOException e)
            	{
            	    System.err.println("Error: " + e.getMessage());
            	}
            	  
            	}
            
            });
		
		//Send message(Client and Agent)
		SendMsg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	 messageArea.appendText("You : " +(messageSend.getText()) + "\n");
            	 output.println(messageSend.getText());
            	 messageSend.setText("");
            	 
            	}
            
            });
		
		//Exit Program(Client and Agent)
		btnExit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            
            public void handle(ActionEvent event) {
            	
            	System.exit(0);
            	
            	}
            
            });
		
		//Login Button(Client and Agent)
		btnLogin.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			String textReturned = "Client";
			String textReturned2 = "Agent";
          
            public void handle(ActionEvent event) {
            	
            	output.println(Username.getText()+";"+Password.getText());//Send username and password to server
            	
            	try {
            	
            	//Server should respond (true) or (false) based on the username and password given 
            	String command = input.readLine();
            		
            	//Command is true will launch scene 2
            	if(command.matches(textReturned)) {
            	
            			ConnectionStatus = true;
            			UserType = "Client";
            			Scene scene2 = new Scene(rootPane2 ,600,600);//Show second scene (CUSTOMER USE)
            			primaryStage.setScene(scene2);
            			new MessageThread().start();
            			
            	}
            	
            	else if(command.matches(textReturned2)) {
            		
            			ConnectionStatus = true;
            			UserType = "Agent";
            			//Show second scene (ADMIN USE)
            	
            			//Save button available to agent
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
            	}//end else
            	}//end try
            	
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
					messageArea.appendText("End User : " + line+"\n");
					
				}
				
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}

}
