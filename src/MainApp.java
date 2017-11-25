import sun.awt.windows.ThemeReader;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Max on 15.07.2017.
 */
public class MainApp extends JFrame{
    static String a = "asd";
    static String b = "asd";
    private static String global;
    private static JLabel label = new JLabel("asd");
    private static JLabel label1 = new JLabel("asd");
    private static JLabel label2 = new JLabel("asd");
    private final JButton b1 = new JButton("Send");

    public static String getGlobal() {
        return global;
    }

    public MainApp(String name) {
        super(name);
        setLayout(new FlowLayout());
        global = "null";
        label.setText(global);
        label1.setText("port: "+Server.getServer().getSERVER_PORT());
        label2.setText(ConnectionWorker.string);
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setAlwaysOnTop(true);


        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == b1) {
                    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                    try {
                        global = (String) clpbrd.getData(DataFlavor.stringFlavor);
                        label.setText("output: "+global);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                label2.setText("input: "+ConnectionWorker.string);
                            }
                        });
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        b1.setPreferredSize(new Dimension(250,80));
        b1.setBackground(Color.decode("#FFD700"));
        add(b1);
        add(label);
        add(label1);
        add(label2);
        setVisible(true);
    }
    public static void main(String[] args) {
        if (a == b) {
            System.out.println("true");
        }
        else System.out.println("false");
        new MainApp("Сервер");
        Server server = Server.getServer();
        Thread t = new Thread(server);
        t.start();
    }

}