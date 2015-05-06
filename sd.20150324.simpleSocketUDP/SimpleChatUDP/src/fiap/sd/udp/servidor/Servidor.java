package fiap.sd.udp.servidor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fiap.sd.udp.chat.Sala;
import fiap.sd.udp.chat.Usuario;

public class Servidor {
	public static List<Sala> salas;
	public static Set<Usuario> usuarios;
	public static ServerSender sender;	
	private static Map<Integer, String> _mapMenu = new HashMap<Integer, String>();
	
	static {
		salas = new ArrayList<Sala>();
		usuarios = new HashSet<Usuario>();
		sender = new ServerSender();
		_mapMenu.put(1, "Listar Salas");
		_mapMenu.put(2, "Criar Sala");
		_mapMenu.put(3, "Entrar Sala");
	}

	public static void acessar(String ipUsuario) {
		Usuario usuario = new Usuario();
		usuario.setIpUsuario(ipUsuario);
		usuarios.add(usuario);
	}

	public static void registrar(String nomeUsuario) {
		// TODO
	}
	
	public static String menuToString() {
		String menuToString = "";
		for(Entry<Integer, String> menu : _mapMenu.entrySet()) {			
		    Integer key = menu.getKey();
		    String value = menu.getValue();		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;
	}
	
	public static String menuToString(int keyToSkip) {
		String menuToString = "";
		for(Entry<Integer, String> menu : _mapMenu.entrySet()) {			
		    Integer key = menu.getKey();
		    String value = menu.getValue();
		    
		    if (keyToSkip == key)
		    	continue;
		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;

	}
	
	public static String getMenuByKey(int key) {
		return _mapMenu.get(key);
	}

	public static Sala obterSalaPorNome(String nome) {
		for (Sala s : salas) {
			if (s != null && s.nome.equals(nome)) {
				return s;
			}
		}
		return null;

	}

	public static void entrar(String nomeUsuario, String nomeSala) {
		Sala sala = obterSalaPorNome(nomeSala);
		Usuario usuario = obterUsuarioPorNome(nomeUsuario);

		if (sala != null) {
			sala.usuarios.add(usuario);
		} else {
			// throw new Exception("Sala nao encontrada");
		}
	}

	public static void sair(String nomeUsuario, String nomeSala) {
		Sala sala = obterSalaPorNome(nomeSala);
		Usuario usuario = obterUsuarioPorNome(nomeUsuario);

		Usuario user = obterUsuarioPorNome(usuario.getNome());

		if (sala != null) {
			sala.usuarios.remove(user);
		} else {
			// throw new Exception("Sala nao encontrada");
		}
	}

	public static Usuario obterUsuarioPorNome(String nome) {
		for (Usuario user : usuarios) {
			if (user.getNome() != null && user.getNome().equalsIgnoreCase(nome)) {
				return user;
			}
		}
		return null;
	}

	public static String listarSalas() {
		if (salas.size() <= 0)
			return "Não existem salas cadastradas.\n";
		
		String retorno = "Salas:\n";
		for (int i = 0; i < salas.size(); i++) {
			retorno += (i + 1) + " - " + salas.get(i).nome + "\n";
		}
		return retorno;
	}

	public static String listarUsuarios(String nomeSala) {
		Sala sala = obterSalaPorNome(nomeSala);
		String retorno = "Usuários da sala " + nomeSala + ":\n";
		for (int i = 0; i < sala.usuarios.size(); i++) {
			retorno += (i + 1) + " - " + sala.usuarios.get(i).getNome() + "\n";
		}
		return retorno;

	}

	// criarSala(usuárioCriador, nomeDaSala, descSala)

	public static String criarSala(String nomeCriador, String nomeSala,
			String descSala) {
		Usuario user = obterUsuarioPorNome(nomeCriador);
		Sala sala = new Sala();
		sala.nome = nomeSala;
		sala.descricao = descSala;
		sala.criador = user;

		entrar(nomeCriador, nomeSala);

		salas.add(sala);

		return "Criado com sucesso";

	}

	public static Usuario getUsuarioByIp(InetAddress ip) {
		Usuario user = null;

		for (Usuario usuario : usuarios) {
			if (usuario.getIpUsuario().equals(ip)) {
				user = usuario;
			}
		}

		return user;
	}

	/*
	 * enviarMensagem(usuárioRemetente, nomeDaSala, msg) – envia a mensagem
	 * “msg” para a sala “nomeDaSala”, garantindo o envio a todos os outros
	 * usuários. ◦ enviarMensagemPrivada(usuárioOrigem, usuárioDestino, msg) –
	 * Mensagem privada.
	 */

	public static void enviarMensagem(String remetente, String nomeSala,
			String msg) {

	}

	public static void trataMensagem(InetAddress ipOrigem, Integer portaOrigem,
			String mensagem) {

	}

}
