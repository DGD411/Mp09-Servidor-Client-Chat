package Ejercicio.servidor.client;

import mp9.uf1.cryptoutils.MyCryptoUtils;
import java.io.*;
import java.net.*;
import java.security.KeyPair;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones...");
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            // Generar par de claves
            KeyPair keyPair = MyCryptoUtils.randomGenerate(1024);

            // Enviar clave pública al cliente
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(keyPair.getPublic());
            out.flush();

            // Recibir mensajes cifrados y mostrarlos en claro
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                byte[] encryptedMessage = (byte[]) in.readObject();
                byte[] decryptedMessage = MyCryptoUtils.decryptData(encryptedMessage, keyPair.getPrivate());
                System.out.println("Mensaje recibido: " + new String(decryptedMessage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
