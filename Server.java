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
            System.out.println("Server created successfully and running on port: " + port);
        } catch (IOException e) {
            System.out.println("Error during server creation: " + e.getMessage());
        }
    }

    public void listen() {
        try {
            Socket socket = this.serverSocket.accept();
            String clientInfos = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("New client connected successfully");
            System.out.println("Client informations: " + clientInfos);

            Thread t = new Thread(() -> this.manageRequests(socket));
            t.start();

        } catch (IOException e) {
            System.out.println("Error while listening for requests: " + e.getMessage());
        }
    }

    private boolean isPalindrome(String content) {
        return content.contentEquals(new StringBuilder(content).reverse());
    }

    public void manageRequests(Socket socket) {
        try {
            var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connection with the server established");

            String request;

            while ((request = br.readLine()) != null) {
                if (request.equals("0")) {
                    out.println("Closing connection");
                    socket.close();
                    return;
                }

                else if (this.isPalindrome(request))
                    out.println("MIRROR");
                else
                    out.println(new StringBuilder(request).reverse());
            }

        } catch (IOException e) {
            System.out.println("Error while managing the requests");
        }
    }
}

void main() {
    Server s = new Server(8000);
    s.listen();
}
