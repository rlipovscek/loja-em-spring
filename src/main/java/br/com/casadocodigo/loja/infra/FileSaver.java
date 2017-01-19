package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/*
 * Classe responsável por salvar os arquivo no servidor
 * 
 */

// informa é um componente do Spring
@Component
public class FileSaver {
	
	//usado para extrair o contexto que o usuário se encontra
	@Autowired
	private HttpServletRequest request;

	/*
	 * Função que recebe o arquivo e seu caminho base e o salva no servidor,
	 * retornando o endereço do arquivo no servidor
	 */
	public String write(String baseFolder, MultipartFile file) {
		
		try {
			//Obtem o caminho real concatenado com a pasta onde será salvo o arquivo
			String realPath = request.getServletContext().getRealPath("/"+baseFolder);
			// concatena o caminho real com o nome do arquivo
			String path = realPath + "\\" + file.getOriginalFilename();
			//grava o arquivo no diretório definido, e com o nome definido
			file.transferTo(new File(path));
			//retorna o caminho que o arquivo foi salvo
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IllegalStateException | IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
