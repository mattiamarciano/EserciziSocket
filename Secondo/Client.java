import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;

    public Client(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            System.out.println("Connected to server");
        } catch (IOException e) {
            System.out.println("Error while creating the client: " + e.getMessage());
        }
    }

    public void execution() {
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            Scanner sc = new Scanner(System.in);
            String request = "";

            while (!request.equals("0")) {
                System.out.println("Enter your string: ");
                request = sc.nextLine();
                out.println(request);
                System.out.println("Server response: " + in.readLine());
            }

            sc.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

void main() {
    Client client = new Client("127.0.0.1", 8000);
    client.execution();
}

