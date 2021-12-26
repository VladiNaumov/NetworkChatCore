
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DemoChar {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(3349);
        System.out.println("SERVER STAR");
        Socket socket = serverSocket.accept();
        System.out.println("Client connect");

        int x;
        while ((x = socket.getInputStream().read()) != -1 ){
            System.out.println((char)x);
        }

        socket.close();
    }
}
