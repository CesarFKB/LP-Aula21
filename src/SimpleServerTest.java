import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleServerTest {

    public static final String ENDERECO = "localhost";
    public static final int PORTA = 3334;

    private static ServerSocket servidor;
    private static Socket clienteAceito;
    private static Scanner entrada;
    private static PrintStream saida;

    public static void main(String[] args) {

        try {
            iniciaServidor();
            aguardaConexaoCliente();
            conversaComCliente();
            aguardaConexaoCliente();
            encerraServidor();
        } catch (IOException ex) {
            System.out.println("Erro no Servidor: " + ex.getMessage());
        }
    }

    private static void iniciaServidor() throws IOException {
        servidor = new ServerSocket(PORTA);
        System.out.println("Servidor iniciado e escutando porta: " + PORTA);
    }

    private static void aguardaConexaoCliente() throws IOException {
        clienteAceito = servidor.accept();
        System.out.println("Cliente IP " + 
        clienteAceito.getInetAddress().getHostAddress() + " conectado pela porta " + 
        PORTA);
        entrada = new Scanner(clienteAceito.getInputStream());
        saida = new PrintStream(clienteAceito.getOutputStream());
    }

    private static void conversaComCliente() {
        String msg;
        while (entrada.hasNextLine()) {
            msg = leMensagemCliente();
            retornaMensagemCliente(msg);
        }
    }

    private static void retornaMensagemCliente(String msg) {
        saida.println(msg);
        System.out.print("Ecoou ao cliente: ");
        System.out.println(msg);
    }

    private static String leMensagemCliente() {
        String msg = entrada.nextLine();
        System.out.print("Chegou do cliente: ");
        System.out.println(msg);
        return msg;
    }

    private static void encerraServidor() throws IOException {
        servidor.close();
        System.out.println("Servidor finalizado!");
    }
}
