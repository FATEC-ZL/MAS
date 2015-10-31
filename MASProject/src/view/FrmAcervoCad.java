package view;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.ImageIcon;

import controller.AcervoCtrl;

import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.UIManager;

public class FrmAcervoCad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idObra;
	private JTextField nome_artist;
	private JTextField nome_obra;
	private JFormattedTextField data_obra;
	private MaskFormatter maskData;
	private DecimalFormat maskValor;
	private JComboBox<String> cbCategoria;
	private JComboBox<String> cbMaterial;
	private JComboBox<String> comboSetor;
	private JComboBox<String> comboSetorT;
	private JComboBox<String> comboStatus;
	private JComboBox<String> comboStatusT;
	private JTextField textField_valor;
	private JButton btnPesquisaArtist, btnNovoArtista, btnEditarArtista; // deixar os demais botoes private

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAcervoCad frame = new FrmAcervoCad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmAcervoCad() throws ParseException {
		setResizable(false);
		setTitle("Registro de Acervo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quando ela for chamada pelo menu trocar para
		setBounds(100, 100, 625, 737); // DISPOSE_ON_CLOSE
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblId = new JLabel("ID.Obra");
		lblId.setBounds(103, 27, 62, 14);
		contentPane.add(lblId);

		idObra = new JTextField();
		idObra.setEnabled(false);
		idObra.setBounds(166, 27, 150, 17);
		idObra.setEditable(false);
		idObra.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(idObra);
		idObra.setColumns(10);

		JLabel lblArtista = new JLabel("Artista");
		lblArtista.setBounds(113, 56, 52, 14);
		contentPane.add(lblArtista);

		JLabel msgGravado = new JLabel("Dados Salvos com sucesso");
		msgGravado.setIcon(new ImageIcon("../MASProject/icons/ok.png"));
		msgGravado.setBounds(50, 669, 230, 23);
		msgGravado.setVisible(false);
		contentPane.add(msgGravado);

		JLabel msgVazio = new JLabel("");
		msgVazio.setIcon(new ImageIcon("../MASProject/icons/delete.png"));
		msgVazio.setBounds(50, 669, 192, 23);
		msgVazio.setVisible(false);
		contentPane.add(msgVazio);

		nome_artist = new JTextField();
		nome_artist.setEditable(false);
		nome_artist.setBounds(166, 56, 352, 20);
		contentPane.add(nome_artist);
		nome_artist.setColumns(10);

		// Modificado a visibilidade do botão para privado - Vitor
		// Ações transferido para o acervoController - Vitor
		btnPesquisaArtist = new JButton("");
		btnPesquisaArtist.setIcon(new ImageIcon("../MASProject/icons/search.png"));
		btnPesquisaArtist.setBounds(522, 52, 29, 28);
		contentPane.add(btnPesquisaArtist);

		btnNovoArtista = new JButton("Novo Artista");
		btnNovoArtista.setBounds(303, 81, 133, 29);
		contentPane.add(btnNovoArtista);

		btnEditarArtista = new JButton("Editar Artista");
		btnEditarArtista.setBounds(166, 81, 133, 29);
		contentPane.add(btnEditarArtista);

		JLabel lblNomeDaObra = new JLabel("Nome da Obra");
		lblNomeDaObra.setBounds(67, 133, 98, 14);
		contentPane.add(lblNomeDaObra);

		nome_obra = new JTextField();
		nome_obra.setBounds(166, 133, 352, 20);
		contentPane.add(nome_obra);
		nome_obra.setColumns(10);

		JLabel lblNewLabel = new JLabel("Data de Composição");
		lblNewLabel.setBounds(28, 165, 139, 14);
		contentPane.add(lblNewLabel);

		maskData = new MaskFormatter("##/##/####");
		data_obra = new JFormattedTextField(maskData);
		data_obra.setBounds(168, 165, 98, 20);
		contentPane.add(data_obra);
		data_obra.setColumns(10);
		data_obra.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblCategoriaDaObra = new JLabel("Categoria da Obra");
		lblCategoriaDaObra.setBounds(44, 221, 122, 14);
		contentPane.add(lblCategoriaDaObra);

		cbCategoria = new JComboBox<String>();
		cbCategoria.setBounds(167, 221, 133, 20);
		contentPane.add(cbCategoria);

		JLabel lblMaterial = new JLabel("Material da Obra");
		lblMaterial.setBounds(49, 288, 122, 14);
		contentPane.add(lblMaterial);

		cbMaterial = new JComboBox<String>();
		cbMaterial.setBounds(166, 288, 133, 20);
		contentPane.add(cbMaterial);

		JLabel lblNewLabel_1 = new JLabel("Descri\u00E7\u00E3o da Obra");
		lblNewLabel_1.setBounds(30, 388, 139, 14);
		contentPane.add(lblNewLabel_1);

		JEditorPane editor_descricao = new JEditorPane();
		editor_descricao.setForeground(UIManager.getColor("TableHeader.foreground"));
		editor_descricao.setBounds(30, 404, 558, 81);
		contentPane.add(editor_descricao);

		JButton btnNovaCategoria = new JButton("Nova Categoria");
		btnNovaCategoria.setToolTipText("Não encontrou a categoria?");
		btnNovaCategoria.setBounds(167, 247, 133, 29);
		contentPane.add(btnNovaCategoria);

		JButton btnEditarCategoria = new JButton("Editar Categoria");
		btnEditarCategoria.setBounds(29, 247, 133, 29);
		contentPane.add(btnEditarCategoria);

		JButton btnNovoMaterial = new JButton("Novo Material");
		btnNovoMaterial.setToolTipText("Não encontrou o material?");
		btnNovoMaterial.setBounds(166, 314, 133, 29);
		contentPane.add(btnNovoMaterial);

		JButton btnEditarMaterial = new JButton("Editar Material");
		btnEditarMaterial.setBounds(28, 314, 133, 29);
		contentPane.add(btnEditarMaterial);

		JTabbedPane abas = new JTabbedPane(JTabbedPane.TOP);
		abas.setBounds(28, 507, 558, 144);
		contentPane.add(abas);

		JPanel panel_proprio = new JPanel();
		abas.addTab("Obra Pr\u00F3pria", null, panel_proprio, null);
		panel_proprio.setLayout(null);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(88, 23, 46, 14);
		panel_proprio.add(lblStatus);

		comboStatus = new JComboBox<String>();
		comboStatus.setBounds(133, 21, 128, 20);
		panel_proprio.add(comboStatus);

		JLabel Setor = new JLabel("Setor");
		Setor.setBounds(317, 23, 32, 14);
		panel_proprio.add(Setor);

		comboSetor = new JComboBox<String>();
		comboSetor.setBounds(357, 20, 110, 20);
		panel_proprio.add(comboSetor);

		JLabel lblValorDaAquisio = new JLabel("Valor da aquisi\u00E7\u00E3o (R$)");
		lblValorDaAquisio.setBounds(206, 64, 143, 14);
		panel_proprio.add(lblValorDaAquisio);

		maskValor = new DecimalFormat("#,###,###.00");
		NumberFormatter formatter = new NumberFormatter(maskValor);
		formatter.setFormat(maskValor);
		formatter.setAllowsInvalid(false);
		textField_valor = new JFormattedTextField(maskValor);
		textField_valor.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_valor.setBounds(357, 61, 110, 20);
		panel_proprio.add(textField_valor);
		textField_valor.setColumns(10);

		JPanel panel_terceiros = new JPanel();
		abas.addTab("Obra de Terceiro", null, panel_terceiros, null);
		panel_terceiros.setLayout(null);

		JLabel lblStatus_1 = new JLabel("Status");
		lblStatus_1.setBounds(88, 23, 46, 14);
		panel_terceiros.add(lblStatus_1);

		comboStatusT = new JComboBox<String>();
		comboStatusT.setBounds(133, 21, 128, 20);
		panel_terceiros.add(comboStatusT);

		JLabel lblSetor = new JLabel("Setor");
		lblSetor.setBounds(317, 23, 46, 14);
		panel_terceiros.add(lblSetor);

		comboSetorT = new JComboBox<String>();
		comboSetorT.setBounds(357, 20, 110, 20);
		panel_terceiros.add(comboSetorT);

		JButton btnGravar = new JButton("Gravar");
		btnGravar.setIcon(new ImageIcon("../MASProject/icons/save.png"));
		btnGravar.setBounds(338, 670, 107, 34);
		contentPane.add(btnGravar);

		JLabel lblSelecImagem = new JLabel("");
		lblSelecImagem.setIcon(new ImageIcon("../MASProject/icons/painting.png"));
		lblSelecImagem.setBackground(SystemColor.inactiveCaption);
		lblSelecImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecImagem.setBounds(338, 188, 250, 155);
		lblSelecImagem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(lblSelecImagem);

		JButton btnPesquisarImagem = new JButton("");
		btnPesquisarImagem.setIcon(new ImageIcon("../MASProject/icons/add.png"));
		btnPesquisarImagem.setBounds(409, 354, 46, 23);
		contentPane.add(btnPesquisarImagem);

		JButton btnExcluirImagem = new JButton("");
		btnExcluirImagem.setIcon(new ImageIcon("../MASProject/icons/delete.png"));
		btnExcluirImagem.setBounds(465, 354, 46, 23);
		contentPane.add(btnExcluirImagem);

		JButton btnFechar = new JButton("Sair");
		btnFechar.setIcon(new ImageIcon("../MASProject/icons/out.png"));
		btnFechar.setBounds(471, 669, 117, 34);
		contentPane.add(btnFechar);

		// botão de pesquisar artista e o Jpanel passados como parametro -
		// Vitor
		AcervoCtrl Acontroller = new AcervoCtrl(idObra, lblSelecImagem, comboSetor, comboSetorT,
				comboStatus, comboStatusT, cbCategoria, cbMaterial, nome_artist, nome_obra, data_obra, editor_descricao,
				msgGravado, msgVazio, textField_valor, btnPesquisaArtist, contentPane, btnNovoArtista,
				btnEditarArtista);

		btnPesquisaArtist.addActionListener(Acontroller);
		btnNovoArtista.addActionListener(Acontroller);
		btnEditarArtista.addActionListener(Acontroller);

		Acontroller.gerarIdSetor();
		btnPesquisarImagem.addActionListener(Acontroller.inserir_imagem);
		btnGravar.addActionListener(Acontroller.gravarAcervo);
		btnExcluirImagem.addActionListener(Acontroller.remover_imagem);
		btnNovaCategoria.addActionListener(Acontroller.novaCategoria);
		btnEditarCategoria.addActionListener(Acontroller.editarCategoria);
		btnNovoMaterial.addActionListener(Acontroller.novoMaterial);
		btnEditarMaterial.addActionListener(Acontroller.editarMaterial);
		btnFechar.addActionListener(Acontroller.fecharTela);
		Acontroller.preencherComboBoxCategoria();
		Acontroller.preencherComboBoxMaterial();
		Acontroller.preencherComboBoxSetores();
		Acontroller.preencherComboStatusProprio();
		Acontroller.preencherComboStatusTerceiro();

	}
}