package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import model.ExposicaoMdl;
import persistence.ExposicaoFile;
import view.FrmCalendario;
import view.FrmObraArtistaSelec;

public class ExposicaoCtrl{

	private static JCalendar calendar;
	private static JTextField txtDataIni, txtDataFim, txtNomeArtista, txtId;
	private JTextField txtTitulo;
	private JTable tObras;
	private static int flag;
	private boolean validar;
	private List <ExposicaoMdl> expos;
	private JTextField txtTema;
	private JTextArea txtAreaDescri;
	private ExposicaoFile arquivo = new ExposicaoFile();
	private DefaultTableModel tableModel;

	public ExposicaoCtrl(JTextField txtDataI, JTextField txtDataF, JTextField txtNomeArtista, JTextField txtId,
			JTable tObras, JTextField txtTitulo, JTextField txtTema, JTextArea txtAreaDescri,
			DefaultTableModel tableModel) {
		
		ExposicaoCtrl.txtDataIni = txtDataI; // neste caso nao se usa this,porque o metodo que utiliza a variavel � estatico
		ExposicaoCtrl.txtDataFim = txtDataF;
		this.txtNomeArtista = txtNomeArtista;
		this.tObras = tObras;
		this.txtId = txtId;
		this.txtTitulo = txtTitulo;
		this.txtTema = txtTema;
		this.txtAreaDescri = txtAreaDescri;
		this.expos = new ArrayList<ExposicaoMdl>();
		this.tableModel = tableModel;
	}

	public ExposicaoCtrl(JCalendar calendar) {
		ExposicaoCtrl.calendar = calendar;
	}
	
	/*
	 * As flags funcionam para quando se tem mais de uma chamada de calendario
	 * na mesma tela, ajuda no tratamento de retorno
	 */
	public static int getFlag() {
		return flag;
	}

	public void setFlag(int n) {
		flag = n;
	}

	public static void leCalendario() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String data = formato.format(calendar.getDate()); // le data selecionada
															// pelo usuario
		insereDataTextField(data);

	}
	
	public void gerarId() {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmmss");
		Date date = new Date();
		String id = (dateFormat.format(date));
		txtId.setText("EXP" + id);
	}

	public static void insereDataTextField(String data) {
		switch (getFlag()) {
		// tratamento das flags
		case 1:
			txtDataIni.setText(null);
			txtDataIni.setText(data);
			break;
		case 2:
			txtDataFim.setText(null);
			txtDataFim.setText(data);
			break;
		}
	}

	public void chamaCalendario() {
		FrmCalendario frmCal = new FrmCalendario();
		frmCal.setLocationRelativeTo(null);
		frmCal.setVisible(true);
	}
	
	public void chamaSelecaoObras(){
		JDialog frmOASelec = new FrmObraArtistaSelec(txtNomeArtista.getText(), tableModel, tObras);
		frmOASelec.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmOASelec.setLocationRelativeTo(null);
		frmOASelec.setModal(true);
		frmOASelec.setVisible(true);
	}

	//CRUD
	public void gravarExposicao(){
		ExposicaoMdl exposicao = new ExposicaoMdl();
		 new ExposicaoFile();
		validar = false;
		if(!txtTitulo.getText().isEmpty()){
			for(int i = 0;i < expos.size();i++){
				if (txtTitulo.getText().equalsIgnoreCase(expos.get(i).getTitulo())){
					msg("erroredit", expos.get(i).getTitulo());
					validar = true;
				}
			}
			if(!(validar==true)){
				exposicao.setId(txtId.getText());
				exposicao.setTitulo(txtTitulo.getText());
				exposicao.setDataIni(txtDataIni.getText());
				exposicao.setDataFim(txtDataFim.getText());
				exposicao.setTema(txtTema.getText());
				exposicao.setDescricao(txtAreaDescri.getText());
			//	exposicao.setObrasExp(tObras.get); //Pega itens da Jtable********
				expos.add(exposicao);
				msg("save",txtTitulo.getText());
				atualizaDados(expos);
				limpaCampos();
				gerarId();
			}
		}else{
			msg("errornull", txtTitulo.getText());
		}
		
	}
	
	public void limpaCampos() {
		//txtId.setText(null);
		txtTitulo.setText(null);
		txtDataIni.setText(null);
		txtDataFim.setText(null);
		txtTema.setText(null);
		txtAreaDescri.setText(null);
		//tObras.setSelected  //limapar a jTable
	}

	public void atualizaDados(List<ExposicaoMdl> listExpo) {
		File f = new File("../MASProject/dados/exposicoes");
		f.delete();
		for (ExposicaoMdl exposicao : listExpo) {
			try {
				arquivo.escreveArquivo("../MASProject/dados/", "exposicoes", "", exposicao);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ActionListener abreCalendario1 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			chamaCalendario();
			setFlag(1);
		}
	};

	public ActionListener abreCalendario2 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			chamaCalendario();
			setFlag(2);
		}
	};

	public ActionListener pesquisaArtista = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			chamaSelecaoObras();
		}
	};

	// Este listener trata a busca da data selecionada ao fechar a tela
	public WindowListener fechaTela = new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
			gerarId();
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// leCalendario();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			leCalendario();
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
		}
	};
	
	
	public ActionListener gravarExpo = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			gravarExposicao();
		}
	};

	
	public void msg(String tipo, String mensagem) {

		switch (tipo) {

		case "errornull":
			JOptionPane.showMessageDialog(null, 
					"ATENÇÃO!\nCampo Vazio.\nPor favor, digite o ID ou nome do Setor.", 
					"Erro", 
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		case "nosearch":
			JOptionPane.showMessageDialog(null, 
					"ATENÇÃO!\n\nNão localizamos o registro: '" + mensagem + "' !\nVerifique sua digitação.", 
					"Não Localizado", 
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		case "errorsearch":
			JOptionPane.showMessageDialog(null, 
					"ATENÇÃO! Por favor, digite para pesquisar!", 
					"Erro",
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		case "save":
			JOptionPane.showMessageDialog(null, 
					"Registro '" + mensagem + "' salvo com sucesso.", 
					"Confirmação", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/record.png"));
			break;
		case "errorrec":
			JOptionPane.showMessageDialog(null, 
					"ATENÇÃO!\nNão foi possível apagar o registro: " + txtId.getText() + " "
					+ txtTitulo.getText() + "!\nVerifique sua digitação!", 
					"Erro", 
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		case "edit":
			JOptionPane.showMessageDialog(null, 
					"Registro '" + mensagem + "' editado com sucesso.", 
					"Confirmação", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/record.png"));
			break;
		case "erroredit":
			JOptionPane.showMessageDialog(null, 
					"Registro '" + mensagem + "' já existe!",
					"Já Cadastrado", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		case "deleteconfirm":
			Object[] options = { "Confirmar", "Cancelar" };  
			int r = JOptionPane.showOptionDialog(null, "Você confirma a exclusão do registro '" + mensagem + "'?",
					"Exclusão de Registro", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
					new ImageIcon("../MASProject/icons/warning.png"), options, options[0]);
			if (r == 0) {
				validar = false;
			}
			break;
		case "delete":
			JOptionPane.showMessageDialog(null, 
					"Registro '" + mensagem + "' excluído com sucesso.", 
					"Confirmação", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/record.png"));
			break;
		case "errordelete":
			JOptionPane.showMessageDialog(null, 
					"Registro '" + mensagem + "' não pode ser alterado para a exclusão.",
					"Erro", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon("../MASProject/icons/warning.png"));
			break;
		default:
			JOptionPane.showMessageDialog(null, 
					"OOPS!\n\nQue feio, Ed Stark perdeu a cabeça, e algo não deveria ter acontecido…\n\nTermo: " + mensagem
					+ "\n\nVolte ao trabalho duro e conserte isso!!!", 
					"Erro no Controller", 
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("../MASProject/icons/warning.png"));
		}
	}
}
