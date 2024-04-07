package Ejercicio.servidor.client;

import mp9.uf1.cryptoutils.MyCryptoUtils;
import java.io.*;
import java.net.*;
import java.security.Key;
import java.security.PublicKey;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conectado al servidor.");

            // Recibir clave pública del servidor
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PublicKey publicKey = (PublicKey) in.readObject(); // Cambio a PublicKey

            // Leer mensajes del usuario y enviarlos al servidor
            Scanner scanner = new Scanner(System.in);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                System.out.print("Yo: ");
                String message = scanner.nextLine();
                byte[] encryptedMessage = MyCryptoUtils.encryptData(message.getBytes(), publicKey); // Uso de PublicKey
                out.writeObject(encryptedMessage);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}