import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

/*
 * Created by Max on 15.07.2017.
 */
public class ConnectionWorker implements Runnable {
    /* сокет, через который происходит обмен данными с клиентом*/
    private Socket clientSocket = null;
    long i = 0;
    public static String string;
    /* входной поток, через который получаем данные с сокета */
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    public ConnectionWorker(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {

        /* получаем входной поток */
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Cant get input stream");
        }

        /* создаем буфер для данных */
        byte[] buffer = new byte[1024*4];

        while(true) {

            try {
                /*
                 * получаем очередную порцию данных
                 * в переменной count хранится реальное количество байт, которое получили
                 */
                int count = inputStream.read(buffer,0,buffer.length);
                /* проверяем, какое количество байт к нам прийшло */
                if (count > 0) {
                    string = new String(buffer,0,count);
                    i++;
                    System.out.println(string+""+i);
                    clientSocket.getOutputStream().write(MainApp.getGlobal().getBytes());
                    clientSocket.getOutputStream().flush();
                } else
                    /* если мы получили -1, значит прервался наш поток с данными  */
                    if (count == -1 ) {
                        System.out.println("close socket");
                        clientSocket.close();
                        break;
                    }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
