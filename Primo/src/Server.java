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
            System.out.println("Server started on port: " + port);
        } catch (IOException e) {
            System.out.println("Error during creation of server: " + e.getMessage());
        }
    }

    public void listen() {
        try {
            Socket socket = this.serverSocket.accept();
            String clientSocketInfos = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("New client connected - " + clientSocketInfos);
            Thread t = new Thread(() -> this.manageRequests(socket));
            t.start();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String returnUpper(String value) {
        final int FIRST_LETTER_CODE = 97;
        final int LAST_LETTER_CODE = 122;
        final int DIFFERENCE = 32;
        StringBuilder sb = new StringBuilder();

        for (var i = 0; i < value.length(); ++i)
            sb.append(value.charAt(i) >= FIRST_LETTER_CODE && value.charAt(i) <= LAST_LETTER_CODE
                    ? (char) (value.charAt(i) - DIFFERENCE)
                    : value.charAt(i));

        return sb.toString();
    }

    public void manageRequests(Socket s) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            System.out.println("Connection acquired returning the UPPERCASE value");


            String request;

            while ((request = br.readLine()) != null) {
                System.out.println("Request received: " + request);

                if (request.equals("0")) {
                    try {
                        out.println("Closing connection");
                        s.close();
                        System.out.println("Closed connection with client");
                    } catch (IOException e) {
                        System.out.println("Error while closing connection with client " + e.getMessage());
                    }
                } else if (request.isBlank()) {
                    out.println("Error");
                } else {
                    String up = this.returnUpper(request);
                    out.println(up);
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}

void main() {
    Server server = new Server(8000);
    server.listen();
}
