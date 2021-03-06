package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import model.IngressoMdl;
import model.VisitanteMdl;

public class RelatorioEstCtrl implements ActionListener {
	private JPanel form;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JInternalFrame internalFrameGrafico;
	private final String[] categorias = { "", "Idade", "Sexo", "Idioma", "Nacionalidade" };
	private JComboBox<String> cbFiltro;
	private JTextField txtDataIni, txtDataFim;
	private JButton btnGerar, btnSalvar, btnLimparCampos;
	private ArquivosCtrl arquivos;
	private String categoria = "";
	private Date dataIni;
	private Date dataFinal;
	private List<VisitanteMdl> visitas = new ArrayList<VisitanteMdl>();
	private List<IngressoMdl> ingressos = new ArrayList<IngressoMdl>();

	public RelatorioEstCtrl(JFreeChart chart, ChartPanel chartPanel, JInternalFrame internalFrameGrafico,
			JTextField txtDataIni, JTextField txtDataFim, JComboBox<String> cbFiltro, JButton btnGerar,
			JButton btnSalvarimprimir, JButton btnLimparCampos) {

		this.internalFrameGrafico = internalFrameGrafico;
		this.chart = chart;
		this.chartPanel = chartPanel;
		this.btnGerar = btnGerar;
		this.btnSalvar = btnSalvarimprimir;
		this.txtDataFim = txtDataFim;
		this.txtDataIni = txtDataIni;
		this.cbFiltro = cbFiltro;
		this.btnLimparCampos = btnLimparCampos;

		sessao();
		carregaComboCategoria();

	}
	
	public void sessao() {

		SessaoCtrl log = SessaoCtrl.getInstance();

		if (("Operacional").equalsIgnoreCase(log.getLogon().get(0).getNivel()) ||
				("Administrativo").equalsIgnoreCase(log.getLogon().get(0).getNivel())
				){

			log.registrar(
					log.getLogon().get(0).getId(), 
					log.getLogon().get(0).getUsuario(), 
					log.getLogon().get(0).getNivel(),
					"RLE");
		}
	}
	

	// O Metodo de coleta deverá chamar esse método passando um titulo, e um
	// array de objeto carregado
	public void criaGrafico(String titulo, List<?> dados) {
		PieDataset dataset = criaDataset(dados, titulo);
		chart = criaChart(dataset, titulo);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(internalFrameGrafico.getContentPane().getWidth(),
				internalFrameGrafico.getContentPane().getHeight()));
		internalFrameGrafico.setContentPane(chartPanel);
	}

	public PieDataset criaDataset(List<?> dados, String titulo) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (titulo.contains("gênero")) {
				int qtdeGenero[] = new int[2];
				for (int i = 0; i < dados.size(); i++) {
					String sexo = ((VisitanteMdl) dados.get(i)).getSexo();
					if (sexo.contains("Masc"))
						qtdeGenero[0] += 1;
					if (sexo.contains("Fem"))
						qtdeGenero[1] += 1;
				}

				result.setValue("Masculino", qtdeGenero[0]);
				result.setValue("Feminino", qtdeGenero[1]);

			} else if (titulo.contains("idioma")) {
				int qtdeIdioma[] = new int[3];
				for (int i = 0; i < dados.size(); i++) {
					String idioma = ((VisitanteMdl) dados.get(i)).getIdioma();
					if (idioma.contains("Port"))
						qtdeIdioma[0] += 1;
					if (idioma.contains("Ingl"))
						qtdeIdioma[1] += 1;
					if (idioma.contains("Esp"))
						qtdeIdioma[2] += 1;
				}
				result.setValue("Português", qtdeIdioma[0]);
				result.setValue("Inglês", qtdeIdioma[1]);
				result.setValue("Espanhol", qtdeIdioma[2]);

			} else if (titulo.contains("nacionalidade")) {
				String nacionalidade[] = new String[dados.size()];
				int qtdeNacionalidade[] = new int[dados.size()];
				for (int i = 0; i < dados.size(); i++) {
					nacionalidade[i] = ((VisitanteMdl) dados.get(i)).getNacionalidade();
				}
				for (int i = 0; i < dados.size(); i++) {
					for(int j = 0; j < dados.size(); j++){
						if(nacionalidade[i].equals(((VisitanteMdl) dados.get(j)).getNacionalidade())){ 
							qtdeNacionalidade[i] += 1;
						}
					}
				}
				for (int i = 0; i < dados.size(); i++) {
					result.setValue(nacionalidade[i], qtdeNacionalidade[i]);		
				}
				
			} else if (titulo.contains("id")) {
				String intervalo[] = {"Menor que 10 anos de idade","Entre 10 e 20 anos de idade","Entre 21 e 30 anos de idade",
						"Entre 31 e 40 anos de idade","Entre 41 e 50 anos de idade","Maior que 50 anos de idade"};
				int quantidade[] = new int[6];
				boolean valida = false;
				int g = 0;
				for (int i = 0; i < dados.size(); i++) {
					String data = selecionaIntervaloIdade(((VisitanteMdl) dados.get(i)).getDataNasc());
					System.out.println(data);
					while (valida == false && g < intervalo.length) {
						if (intervalo[g].contains(data)) {
							quantidade[g] += 1;
							g = 0;
							valida = true;
						} else {
							g += 1;
						}

					}
					valida = false;
				}
				result.setValue(intervalo[0], quantidade[0]);
				result.setValue(intervalo[1], quantidade[1]);
				result.setValue(intervalo[2], quantidade[2]);
				result.setValue(intervalo[3], quantidade[3]);
				result.setValue(intervalo[4], quantidade[4]);
				result.setValue(intervalo[5], quantidade[5]);

			}
		return result;

	}

	public JFreeChart criaChart(PieDataset dataset, String titulo) {
		JFreeChart chart = ChartFactory.createPieChart3D(titulo, // titulo do
																	// grafico
				dataset, // dados
				true, // incluir legenda
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	private void carregaComboCategoria() {
		for (String category : categorias) {
			cbFiltro.addItem(category);
		}
	}

	private boolean filtroGrafico() {
		String dataInicio = txtDataIni.getText();
		String dataFim = txtDataFim.getText();

		try {
			categoria = cbFiltro.getSelectedItem().toString();
		} catch (NullPointerException e) {
			categoria = "";
		}

		String titulo = "";
		if (!dataInicio.trim().equals("/  /") || !dataFim.trim().equals("/  /")) {
			if (!categoria.equals("")) {
				SimpleDateFormat mascara = new SimpleDateFormat("ddMMyyyy");
				try {
					dataIni = (Date) mascara.parse(dataInicio.replace("/", "").replace("/", ""));
					dataFinal = (Date) mascara.parse(dataFim.replace("/", "").replace("/", ""));
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(form, e.getMessage());
				}
				if (validaData(dataInicio)) {
					if (categoria.contains("Id")) {
						lerArquivoIngresso();
						lerArquivoVisitante();
						if (visitas.size() > 0) {
							titulo = "Estatística de idades dos visitantes " + categoria + " - Período: " + dataInicio
									+ " a " + dataFim;
							criaGrafico(titulo, visitas);
							return true;
						}
					} else if (categoria.contains("Sex")) { // Funcionando
						lerArquivoIngresso();
						lerArquivoVisitante();
						if (visitas.size() > 0) {
							titulo = "Estatística de gênero dos visitantes " + categoria + " - Período: " + dataInicio
									+ " a " + dataFim;
							criaGrafico(titulo, visitas);
							return true;
						}
					} else if (categoria.contains("Ing")) {
						lerArquivoIngresso();
						if (ingressos.size() > 0) {
							titulo = "Estatistica dos tipos de ingresso utilizados" + categoria + " - Período: "
									+ dataInicio + " a " + dataFim;
							criaGrafico(titulo, ingressos);
							return true;
						}

					} else if (categoria.contains("Grup")) {

					} else if (categoria.contains("Idio")) {
						lerArquivoIngresso();
						lerArquivoVisitante();
						if (visitas.size() > 0) {
							titulo = "Estatística de idioma dos visitantes " + categoria + " - Período: " + dataInicio
									+ " a " + dataFim;
							criaGrafico(titulo, visitas);
							return true;
						}
					} else if (categoria.contains("Nacio")) {
						lerArquivoIngresso();
						lerArquivoVisitante();
						if (visitas.size() > 0) {
							titulo = "Estatística de nacionalidade dos visitantes " + categoria + " - Período: "
									+ dataInicio + " a " + dataFim;
							criaGrafico(titulo, visitas);
							return true;
						}
					}
				} else {
					JOptionPane.showMessageDialog(form, "Selecione a categoria");
				}
			} else {
				JOptionPane.showMessageDialog(form, "Insira data de inicio e/ou final para continuar!");
			}
		}
		return false;
	}

	private void lerArquivoVisitante() {
		arquivos = new ArquivosCtrl();
		String linha = new String();
		ArrayList<String> list = new ArrayList<>();
		try {
			arquivos.leArquivo("../MASProject/dados/", "visitante");
			linha = arquivos.getBuffer();
			String[] listaVisita = linha.split(";");
			if (categoria.contains("Sex") || categoria.contains("Idio") || categoria.contains("Ida")  || categoria.contains("Nacio")) {
				// int masc = 0, fem = 0;
				for (String s : listaVisita) {
					String text = s.replaceAll(".*: ", "");
					list.add(text);
					if (s.contains("---")) {
						for (int i = 0; i < ingressos.size(); i++) {
							if (list.get(0).equals(ingressos.get(i).getVisitaId())) {
								VisitanteMdl visita = new VisitanteMdl();
								visita.setId(list.get(0));
								visita.setNome(list.get(1));
								System.out.println(list.get(1));
								visita.setDataNasc(list.get(2));
								visita.setNacionalidade(list.get(3));
								visita.setSexo(list.get(4));
								visita.setIdioma(list.get(5));
								System.out.println(list.get(5));
								// if(list.get(4).contains("Masc")) masc += 1;
								// if(list.get(4).contains("Femi")) fem += 1;
								visitas.add(visita);
								list.clear();
								i = ingressos.size() + 1;
							}
						}
						list.clear();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		  
	private void lerArquivoIngresso() {
		try {
			arquivos = new ArquivosCtrl();
			arquivos.leArquivo("../MASProject/dados/", "ingresso");
			String linha = new String();
			ArrayList<String> list = new ArrayList<>();
			linha = arquivos.getBuffer();
			String[] listaIngresso = linha.split(";");
			for (String s : listaIngresso) {
				String text = s.replaceAll(".*: ", "");
				list.add(text);
				if (s.contains("---")) {
					String data = list.get(1).toString();
					if (validaData(data)) {
						IngressoMdl ingresso = new IngressoMdl();
						ingresso.setId(null);
						ingresso.setData(list.get(1));
						System.out.println(list.get(1));
						ingresso.setHora(null);
						ingresso.setBilhete(null);
						ingresso.setExpo(null);
						ingresso.setVisitaId(list.get(5));
						System.out.println(list.get(5));
						ingresso.setVisitante(null);
						System.out.println(list.get(6));
						ingresso.setIngresso(list.get(7));
						System.out.println(list.get(7));
						ingresso.setQtd(null);
						ingresso.setValor(null);
						ingresso.setPagamento(null);
						ingressos.add(ingresso);
						list.clear();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean validaData(String dataDoArquivo) {
		SimpleDateFormat mascara = new SimpleDateFormat("ddMMyyyy");
		try {
			Date dataAtual = mascara.parse(dataDoArquivo.replace("/", "").replace("/", ""));
			if (dataIni.before(dataFinal) || dataIni.compareTo(dataFinal) == 0) {
				if (dataAtual.after(dataIni) && dataAtual.before(dataFinal) || dataAtual.compareTo(dataIni) == 0
						|| dataAtual.compareTo(dataFinal) == 0) {
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(form, "Data inicial é maior do que a data final");
				if (dataIni.before(dataFinal) || dataIni.compareTo(dataFinal) == 0) {
					if (dataAtual.after(dataIni) && dataAtual.before(dataFinal) || dataAtual.compareTo(dataIni) == 0
							|| dataAtual.compareTo(dataFinal) == 0) {
						return true;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Data inicial é maior do que a data final");
					return false;
				}
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(form, "Não foi possível converter a data do arquivo");
		}
		return false;
	}

	private void salvaGrafico(/* OutputStream out */) throws IOException {

		if (chart != null) {
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Pasta de Arquivos", "dir");

			String diretorioBase = System.getProperty("user.home") + "/Desktop";
			File dir = new File(diretorioBase);

			JFileChooser choose = new JFileChooser();
			choose.setCurrentDirectory(dir);
			choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			choose.setAcceptAllFileFilterUsed(false);
			choose.addChoosableFileFilter(filtro);
			String caminhoArquivo = "";

			int retorno = choose.showOpenDialog(null);
			if (retorno == JFileChooser.APPROVE_OPTION) {
				caminhoArquivo = choose.getSelectedFile().getAbsolutePath();
				OutputStream out = new FileOutputStream(caminhoArquivo + "/" + "novoGrafico.png");
				ChartUtilities.writeChartAsPNG(out, chart, 500, 350);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"ATENÇÃO!\nNão há grafico para gravar.\n\nPor favor, complete todos os campos necessários e clique em Gerar gráfico.",
					"Erro", JOptionPane.PLAIN_MESSAGE, new ImageIcon("../MASProject/icons/error.png"));
		}
	}

	public void limpaCampos() {
		txtDataIni.setText(null);
		txtDataFim.setText(null);
		chartPanel.setChart(null);

	}

	public static String selecionaIntervaloIdade(String dataNasc) {
		String[] separaData = dataNasc.split("/");
		Date dataSis = new Date();

		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String data = df.format(dataSis);
		int AnoAtual = Integer.parseInt(data);
		int AnoNasc = Integer.parseInt(separaData[2]);
		int idade = AnoAtual - AnoNasc;
		// System.out.println(idade);

		String intervalo = "";
		if (idade < 10)
			intervalo = "Menor que 10 anos de idade";

		if (idade >= 10 && idade <= 20)
			intervalo = "Entre 10 e 20 anos de idade";

		if (idade >= 21 && idade <= 30)
			intervalo = "Entre 21 e 30 anos de idade";

		if (idade >= 31 && idade <= 40)
			intervalo = "Entre 31 e 40 anos de idade";

		if (idade >= 41 && idade <= 50)
			intervalo = "Entre 41 e 50 anos de idade";

		if (idade > 50)
			intervalo = "Maior que 50 anos de idade";

		return intervalo;
	}

	public void actionPerformed(ActionEvent actEvt) {
		Object source = actEvt.getSource();

		if (source == btnGerar) {
			if (filtroGrafico())
				internalFrameGrafico.pack();
		}
		if (source == btnSalvar) {
			try {
				salvaGrafico();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			limpaCampos();
		}
		if (source == btnLimparCampos) {
			limpaCampos();
		}

	}

}
