import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SimpleClientTest {

    private static Socket cliente;
    private static Scanner entrada;
    private static PrintStream saida;

    public static void main(String[] args) {
        try {
            iniciaCliente();
            conversaComServidor();
            encerraConexaoServidor();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex.getMessage());
        }
    }

    private static void iniciaCliente() throws IOException {
        cliente = new Socket(SimpleServerTest.ENDERECO, SimpleServerTest.PORTA);
        System.out.println("Cliente IP " + SimpleServerTest.ENDERECO +
        " conectado ao Servidor pela porta " + SimpleServerTest.PORTA);
        entrada = new Scanner(cliente.getInputStream());
    }

    private static void conversaComServidor() throws IOException {
        String msg, echo = "";
        do {
            System.out.println("Digite na Entrada a mensagem para o Servidor!");
            msg = JOptionPane.showInputDialog("Digite aqui a mensagem para o Servidor (ou <sair> para encerrar)");
            if (!msg.equalsIgnoreCase("sair")) {
                enviaMensagemServidor(msg);
                echo = leMensagemServidor();
                verificaComunicacao(echo, msg);
            }
        } while(!msg.equalsIgnoreCase("sair"));
    }

    private static void enviaMensagemServidor(String msg) throws IOException {
        saida = new PrintStream(cliente.getOutputStream());
        saida.println(msg);
        System.out.print("Enviou ao servidor: ");
        System.out.println(msg);
    }

    private static String leMensagemServidor() {
        String msg = entrada.nextLine();
        System.out.print("Ecoou do servidor: ");
        System.out.println(msg);
        return msg;
    }

    private static void verificaComunicacao(String echo, String msg) {
        if (echo.equals(msg)) {
            System.out.println("Comunicacao OK!");
        } else {
            System.out.println("Comunicacao com problema!");
        }
    }

    private static void encerraConexaoServidor() {
        System.out.println("Cliente se deconectou do Servidor!");
        System.out.println("Cliente finalizado!");
    }
}
