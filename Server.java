import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
public static void main(String[] args) {
		
		ServerSocket listener;
		
		Socket connection;
		
		try {
			listener = new ServerSocket(8080);
			System.out.println("Server is up. Running and waiting ");
			while(true) {
				connection = listener.accept();
				ConnectionHandler handler = new ConnectionHandler(connection);
				handler.start();
			}
		}catch(Exception e) {
			System.out.println("Exception");
			return;
		}
	}
}
