package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Setor;
import persistence.SetorArquivo;

public class SetorCtrl {

	private JLabel mensagemGravado, mensagemVazio;
	private JTextField nomeset, idsetor;
	private JButton btnGravar;
	private Setor setor = new Setor();
	private static int contador = 1;
	private String registro_set[] = new String[2];
	private ArquivosCtrl arqController = new ArquivosCtrl();

	public SetorCtrl(JTextField id_setor, JTextField nomeDigit, JLabel mensagemGravado, JLabel mensagemVazio,
			JButton btnGravar) {
		this.idsetor = id_setor;
		this.mensagemGravado = mensagemGravado;
		this.mensagemVazio = mensagemVazio;
		this.nomeset = nomeDigit;
		this.btnGravar = btnGravar;
	}

	public void gerarIdSetor() {
		//Chamada deste metodo no gravaSetor e no FormRegisSetor
		GeradordeID geraID = new GeradordeID();
	
		idsetor.setText("SET"+geraID.getIndice());
	}

	public void gravaSetor() {
		Setor setor = new Setor();
		SetorArquivo setorImpl = new SetorArquivo();

		setor.setIdentificacao(idsetor.getText());
		setor.setNome(nomeset.getText());

		if (!nomeset.getText().isEmpty()) {
			try {
				setorImpl.escreveArquivo("../MASProject/dados/", "setores", nomeset.getText(), setor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mensagemGravado.setText(nomeset.getText() + " salvo com sucesso!!!");
			mensagemGravado.setVisible(true);
			gerarIdSetor();
		} else {
			mensagemGravado.setVisible(false);
			mensagemVazio.setVisible(true);
		}
		// implementar a acao de apagar o campo de nome e criar uma nova id
		// quando clicar em gravar
	}

	public void excluiSetor() {
		// teste
		registro_set[1] = setor.getNome();
		registro_set[0] = setor.getIdentificacao();

		try {
			arqController.excluiDadosArquivo("../MASProject/dados", "setores", registro_set);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ActionListener gravarSetor = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gravaSetor();
			nomeset.setText(null);
		}
	};

	public ActionListener excluiSetor = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			excluiSetor();
		}
	};

	public MouseListener limpaCampo = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// se for clicado pela primeira vez o campo fica limpo para
			// preencher com o nome do setor
			if (contador == 1) {
				nomeset.setText(null);
				contador += 1;
			}
			// para que a mensagem n�o fique visivel a todo momento
			btnGravar.setEnabled(true);
			mensagemGravado.setVisible(false);
			mensagemVazio.setVisible(false);
		}
	};

}