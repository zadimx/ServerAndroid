import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Max on 15.07.2017.
 */
public class Server implements Runnable {

    /*
     * Реалезация шаблона Singleton
     * {@link https://en.wikipedia.org/wiki/Singleton_pattern}
     */
    private static volatile Server instane = null;



    /* Порт, на который сервер принимает соеденения */
    private final int SERVER_PORT = 7777;

    public int getSERVER_PORT() {
        return SERVER_PORT;
    }
    /* Сокет, который обрабатывает соединения на сервере */
    private ServerSocket serverSoket = null;

    private Server() {}

    public static Server getServer() {

        if (instane == null) {
            synchronized (Server.class) {
                if (instane == null) {
                    instane = new Server();
                }
            }
        }
        return instane;
    }


    @Override
    public void run() {

        try {
            /* Создаем серверный сокет, которые принимает соединения */
            serverSoket = new ServerSocket(SERVER_PORT);
            System.out.println("Start server on port: "+SERVER_PORT);

            /*
             * старт приема соединений на сервер
             */
            while(true) {

                ConnectionWorker worker = null;

                try {
                    /* ждем нового соединения  */
                    worker = new ConnectionWorker(serverSoket.accept());
                    System.out.println("Get client connection");

                    /* создается новый поток, в котором обрабатывается соединение */
                    Thread t = new Thread(worker);
                    t.start();

                } catch (Exception e) {
                    System.out.println("Connection error: "+e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Cant start server on port "+SERVER_PORT+":"+e.getMessage());
        } finally {
            /* Закрываем соединение */
            if (serverSoket != null) {
                try {
                    serverSoket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}