package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import controller.ArquivosICtrl;
import model.ArtistaMdl;

public class ArtistaFile implements ArquivosICtrl {

	StringBuffer buffer;

	public String getBuffer() {
		return buffer.toString();
	}

	@Override
	public void leArquivo(String diretorio, String arquivo) throws IOException {
		File arq = new File(diretorio, arquivo);
		if (arq.exists()) {
			FileInputStream leFluxo = new FileInputStream(arq);
			InputStreamReader leDados = new InputStreamReader(leFluxo);
			BufferedReader bufferLeitura = new BufferedReader(leDados);
			String linha = bufferLeitura.readLine();
			buffer = new StringBuffer();
			while (linha != null) {
				// System.out.println(linha);
				buffer.append(linha);
				buffer.append(";");
				linha = bufferLeitura.readLine();
			}
			bufferLeitura.close();
			leDados.close();
			leFluxo.close();
		} else {
			//throw new IOException("Arquivo inexistente");
			File novoArquivo = new File(diretorio, arquivo);
			novoArquivo.createNewFile();
			buffer = new StringBuffer();
			buffer.append("");
		}
	}

	@Override
	public void escreveArquivo(String diretorio, String arquivo, String texto, Object object) throws IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID      : " + ((ArtistaMdl) object).getId());
		buffer.append("\r\n");
		buffer.append("Artista : " + ((ArtistaMdl) object).getNome());
		buffer.append("\r\n");
		buffer.append("---------------------------");
		buffer.append("\r\n");
		File arq = new File(diretorio, arquivo);
		boolean arquivoExiste;
		if (arq.exists()) {
			arquivoExiste = true;
		} else {
			arq.createNewFile();
			arquivoExiste = false;
		}
		FileWriter escreveArquivo = new FileWriter(arq, arquivoExiste);
		PrintWriter gravaDados = new PrintWriter(escreveArquivo);
		gravaDados.write(buffer.toString());
		gravaDados.flush();
		gravaDados.close();
		escreveArquivo.close();

	}

	@Override
	public void leDiretorio(String diretorio) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void excluiDadosArquivo(String diretorio, String arquivo, String[] registro) throws IOException {
		// TODO Auto-generated method stub
	}
}