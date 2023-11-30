import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class Cliente {
    final String HOST = "localhost"; //Host para la conexión
    final int PUERTO = 1234; //Puerto para la conexión
    protected Socket cs; //Socket del cliente
    protected BufferedReader entrada; //Flujo de datos de entrada
    protected PrintWriter salida; //Flujo de datos de salida
    String respuesta; //Mensaje de respuesta del servidor

    public Cliente() throws IOException //Constructor
    {
        //Socket para el cliente en localhost en puerto 1234
        cs = new Socket(HOST, PUERTO);

        //Flujo de datos de entrada desde el servidor
        entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

        // Flujo de datos de salida hacia el servidor
        salida = new PrintWriter(cs.getOutputStream(), true);
    }

    public void startClient() //Método para iniciar el cliente
    {
        try {
            // Leer texto desde la consola
            System.out.print("Introduce texto: ");
            BufferedReader lectorConsola = new BufferedReader(new InputStreamReader(System.in));
            String texto = lectorConsola.readLine();

            // Codificar en base64 y enviar al servidor
            String mensaje = "#" + Base64.getEncoder().encodeToString(texto.getBytes()) + "#";
            salida.println(mensaje);

            //Se cierra la conexión con el servidor
            cs.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        try {
            Cliente cli = new Cliente(); //Se crea el cliente
            cli.startClient(); //Se inicia el cliente

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
