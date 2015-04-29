package fiap.sd.udp.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;

public class Cliente {

	public static ClientSender sender;

	private static final BufferedReader console = new BufferedReader(
			new InputStreamReader(System.in));

	public static void solicitarInputUsuario(Comandos operacao) {
		String message = null;
		
		Mensagem msg = new Mensagem();
		message = solicitarInput();
		
		msg.setComando(operacao);
		msg.setMensagem(message);
		sender.enviarMensagem(msg);
	}

	public static String solicitarInput() {
		try {
			return console.readLine();
		} catch (IOException e) {
			System.out.println("Erro na entrada do teclado.");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
}
