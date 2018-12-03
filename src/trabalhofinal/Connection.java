/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mylle
 */
public abstract class Connection {
    private static boolean initialized = false;
    private static Socket cliente;
    private static PrintStream out;
    private static Scanner in;
    
    public static boolean isConnected() {
        return initialized;
    }
    
    public static void connect(String ip, int port) throws IOException {
        if (!initialized) {
            cliente = new Socket(ip, port);

            out = new PrintStream(cliente.getOutputStream());
            in = new Scanner(cliente.getInputStream());
            
            initialized = true;
        }
    }
    
    public static String run(String command) {
        
        out.println(command);
        return getResponse();
    }
    
    public static String getResponse() {
        while (true) {
            if (in.hasNextLine()) {
                String msg = in.nextLine();
                String[] m = msg.split(":");

                System.out.println(msg);
                switch(m[0].toUpperCase()) {
                    case "SUCCESS":
                        JOptionPane.showMessageDialog(null, m[1]);
                        break;
                    case "ERROR":
                        JOptionPane.showMessageDialog(null, m[1], "ERRO", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                return msg;
            }
        }
    }
}
