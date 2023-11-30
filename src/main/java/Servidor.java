import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class Servidor {
    private final int PUERTO = 1234; //Puerto para la conexión
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected ServerSocket ss; //Socket del servidor
    protected Socket cs; //Socket del cliente
    protected DataOutputStream salidaCliente; //Flujo de datos de salida
    protected BufferedReader entrada; //Flujo de datos de entrada
    protected PrintWriter salida; //Flujo de datos de salida

    public Servidor() throws IOException {
        ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
        cs = new Socket(); //Socket para el cliente
    }

    public void startServer(){
        final int puerto = 1234;

        try {
            // Se espera una conexión de un cliente
            System.out.println("Servidor esperando conexiones en el puerto " + puerto);

            while (true) {
                // Cuando se recibe una conexión, se crea un nuevo socket para comunicarse con el cliente
                cs = ss.accept();

                // Se muestra información sobre el cliente
                System.out.println("Cliente conectado desde " + cs.getInetAddress());

                // Se obtienen los flujos de escritura y lectura
                entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                salida = new PrintWriter(cs.getOutputStream(), true);

                // Se lee la petición del cliente
                String mensaje = entrada.readLine();

                if (mensaje != null) {
                    String contenido = obtenerMensaje(mensaje);
                    decodificarYGuardar(contenido);
                }

                // Se cierra la conexión con el cliente
                cs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String obtenerMensaje(String mensaje) {
        int inicio = mensaje.indexOf("#") + 1;
        int fin = mensaje.lastIndexOf("#");
        return mensaje.substring(inicio, fin);
    }

    public static void decodificarYGuardar(String contenido) {
        try {
            byte[] bytesDecodificados = Base64.getDecoder().decode(contenido);
            String textoDecodificado = new String(bytesDecodificados);

            // Guardar en un archivo o realizar otras operaciones necesarias
            FileWriter fileWriter = new FileWriter("archivo.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(textoDecodificado);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            servidor.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
