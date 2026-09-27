import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server created successfully");
        } catch (IOException e) {
            System.out.println("Error during server creation: " + e.getMessage());
        }
    }

    public void listen() {
        try {
            Socket socket = this.serverSocket.accept();
            System.out.println("New client connected");
            System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

            Thread t = new Thread(() -> this.manageOperations(socket));
            t.start();

        } catch (IOException e) {
            System.out.println("Error while listening for requests: " + e.getMessage());
        }
    }

    private String calculate(String[] parts) {
        String operation = parts[0];

        try {
            int firstOp = Integer.parseInt(parts[1]);
            int secondOp = Integer.parseInt(parts[2]);

            return switch (operation) {
                case "+" -> String.valueOf(firstOp + secondOp);
                case "-" -> String.valueOf(firstOp - secondOp);
                case "*" -> String.valueOf(firstOp * secondOp);
                case "/" -> String.valueOf(firstOp / secondOp);
                default -> "Error";
            };

        } catch (NumberFormatException | ArithmeticException e) {
            return "Error";
        }
    }

    public void manageOperations(Socket s) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            System.out.println("Connection with the client established");
            String request;

            while ((request = br.readLine()) != null) {
                if (request.equals("0")) {
                    break;
                }

                String[] parts = request.split(" ");
                
                if (parts.length != 3) {
                    out.println("Error");
                    continue;
                }
                
                if (parts[0].equals("/") && parts[2].equals("0")) {
                    out.println("Error");
                    continue;
                }

                String result = calculate(parts);
                out.println(result);
            }

            s.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

void main() {
    Server server = new Server(8000);
    server.listen();
}
