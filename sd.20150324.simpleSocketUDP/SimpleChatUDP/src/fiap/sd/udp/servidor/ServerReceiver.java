package fiap.sd.udp.servidor;

import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.chat.Operacoes;
import fiap.sd.udp.chat.Usuario;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ServerReceiver extends Receiver {

	public ServerReceiver(int port) {
		super(port);
	}

	@Override
	protected void trataMensagem(String ipOrigem, Integer portaOrigem,
			Mensagem mensagem) {

		mensagem.setIpDestino(ipOrigem);

		// TODO mudar esquema de tratamento de comandos
		
		String[] dados = mensagem.getMensagem().split("\\|");
		String operacao = dados[0].trim() + "|";

		switch (operacao) {
		case Operacoes.ACESSAR:
			System.out.println("> IP " + ipOrigem
					+ " acessando pela primeira vez");
			Servidor.acessar(ipOrigem);
			mensagem.setMensagem(Operacoes.REQUISITAR_NOME_USUARIO
					+ "CHAT > Digite o seu nome de usu�rio: ");
			Servidor.sender.enviarMensagem(mensagem);

			break;

		case Operacoes.ENVIAR_NOME_USUARIO:
			String nomeUsuario = dados[1].trim();
			Usuario user = Servidor.obterUsuarioPorNome(nomeUsuario);
			if (user == null) { // usu�rio n�o existe
				Servidor.registrar(nomeUsuario);
				mensagem.setMensagem(Operacoes.USUARIO_REGISTRADO_SUCESSO + "CHAT > "
						+ nomeUsuario + " registrado com sucesso!\n"
						+ Servidor.menu);
			} else { // usu�rio j� existe
				mensagem.setMensagem(Operacoes.REQUISITAR_NOME_USUARIO
						+ "CHAT > O usu�rio "
						+ nomeUsuario
						+ " j� est� registrado\nCHAT > Digite o seu nome de usu�rio: ");
			}

			Servidor.sender.enviarMensagem(mensagem);

			break;

		case Operacoes.ESCOLHE_MENU:
			System.out.println("Op��o escolhida: " + dados[1].trim());
			break;

		case Operacoes.REGISTRAR:

			System.out.println("Registrar " + dados[1]);

			break;
		default:
			mensagem.setMensagem("CHAT > Comando inv�lido!");
			Servidor.sender.enviarMensagem(mensagem);

			break;

		}

		// System.out.println("IP: " + ipOrigem + "\nPorta: " + portaOrigem
		// + "\nmensagem: " + mensagem);

	}

}
