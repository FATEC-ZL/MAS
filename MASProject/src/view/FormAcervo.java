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
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import controller.AcervoController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FormAcervo extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField nome_artist;
	private JTextField nome_obra;
	private JTextField data_obra;
	private JComboBox<String> cbCategoria;
	private JComboBox<String> cbMaterial;
	private JComboBox <String> cbSetor;
	private JComboBox <String> comboStatus;
	private JTextField textField_valor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormAcervo frame = new FormAcervo();
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
	public FormAcervo() {
		setResizable(false);
		setTitle("Registro de Acervo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 674);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblId = new JLabel("ID.Obra");
		lblId.setBounds(103, 27, 62, 14);
		contentPane.add(lblId);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(166, 27, 86, 17);
		textField.setEditable(false);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblArtista = new JLabel("Artista");
		lblArtista.setBounds(113, 56, 52, 14);
		contentPane.add(lblArtista);
		
		nome_artist = new JTextField();
		nome_artist.setBounds(166, 56, 352, 20);
		contentPane.add(nome_artist);
		nome_artist.setColumns(10);
		
		JButton btnPesquisaArtist = new JButton("");
		btnPesquisaArtist.setIcon(new ImageIcon("../MASProject/icons/search.png"));
		btnPesquisaArtist.setBounds(522, 52, 29, 28);
		contentPane.add(btnPesquisaArtist);
		
		JLabel lblNomeDaObra = new JLabel("Nome da Obra");
		lblNomeDaObra.setBounds(67, 88, 98, 14);
		contentPane.add(lblNomeDaObra);
		
		nome_obra = new JTextField();
		nome_obra.setBounds(166, 88, 352, 20);
		contentPane.add(nome_obra);
		nome_obra.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Data de Composição");
		lblNewLabel.setBounds(28, 120, 139, 14);
		contentPane.add(lblNewLabel);
		
		data_obra = new JTextField();
		data_obra.setBounds(168, 120, 86, 20);
		contentPane.add(data_obra);
		data_obra.setColumns(10);
		
		JLabel lblCategoriaDaObra = new JLabel("Categoria da Obra");
		lblCategoriaDaObra.setBounds(44, 152, 122, 14);
		contentPane.add(lblCategoriaDaObra);
		
		cbCategoria = new JComboBox<String>();
		cbCategoria.setBounds(167, 152, 133, 20);
		contentPane.add(cbCategoria);
		
		JLabel lblMaterial = new JLabel("Material da Obra");
		lblMaterial.setBounds(49, 219, 122, 14);
		contentPane.add(lblMaterial);
		
		cbMaterial = new JComboBox<String>();
		cbMaterial.setBounds(166, 219, 133, 20);
		contentPane.add(cbMaterial);
		
		JLabel lblNewLabel_1 = new JLabel("Descrição da Obra");
		lblNewLabel_1.setBounds(28, 330, 139, 14);
		contentPane.add(lblNewLabel_1);
		
		JEditorPane editor_descricao = new JEditorPane();
		editor_descricao.setBounds(28, 346, 558, 81);
		contentPane.add(editor_descricao);
		
		JButton btnNovaCategoria = new JButton("Nova Categoria");
		btnNovaCategoria.setToolTipText("Não encontrou a categoria?");
		btnNovaCategoria.setBounds(167, 178, 133, 29);
		contentPane.add(btnNovaCategoria);
		
		JButton btnNovoMaterial = new JButton("Novo Material");
		btnNovoMaterial.setToolTipText("Não encontrou o material?");
		btnNovoMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormMaterial frame = new FormMaterial();  	  
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		btnNovoMaterial.setBounds(166, 245, 133, 29);
		contentPane.add(btnNovoMaterial);
		
		JTabbedPane abas = new JTabbedPane(JTabbedPane.TOP);
		abas.setBounds(21, 438, 576, 111);
		contentPane.add(abas);
		
		JPanel panel_proprio = new JPanel();
		abas.addTab("Obra Pr\u00F3pria", null, panel_proprio, null);
		panel_proprio.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(62, 11, 46, 14);
		panel_proprio.add(lblStatus);
		
		comboStatus = new JComboBox<String>();
		comboStatus.setBounds(118, 8, 95, 20);
		panel_proprio.add(comboStatus);
		
		JLabel Setor = new JLabel("Setor");
		Setor.setBounds(240, 11, 46, 14);
		panel_proprio.add(Setor);
		
		cbSetor = new JComboBox<String>();
		cbSetor.setBounds(279, 8, 95, 20);
		panel_proprio.add(cbSetor);
		
		JLabel lblValorDaAquisio = new JLabel("Valor da aquisi\u00E7\u00E3o (R$)");
		lblValorDaAquisio.setBounds(10, 54, 118, 14);
		panel_proprio.add(lblValorDaAquisio);
		
		textField_valor = new JTextField();
		textField_valor.setBounds(127, 51, 86, 20);
		panel_proprio.add(textField_valor);
		textField_valor.setColumns(10);
		
		JPanel panel_terceiros = new JPanel();
		abas.addTab("Obra de Terceiro", null, panel_terceiros, null);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.setIcon(new ImageIcon("../MASProject/icons/save.png"));
		btnGravar.setBounds(339, 595, 107, 34);
		contentPane.add(btnGravar);
		
		JLabel lblSelecImagem = new JLabel("");
		lblSelecImagem.setIcon(new ImageIcon("../MASProject/icons/painting.png"));
		lblSelecImagem.setBackground(SystemColor.inactiveCaption);
		lblSelecImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecImagem.setBounds(397, 137, 189, 147);
		lblSelecImagem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));  
		contentPane.add(lblSelecImagem);
		
		JButton btnPesquisarImagem = new JButton("");
		btnPesquisarImagem.setIcon(new ImageIcon("../MASProject/icons/add.png"));
		btnPesquisarImagem.setBounds(440, 299, 46, 35);
		contentPane.add(btnPesquisarImagem);
		
		JButton btnExcluirImagem = new JButton("");
		btnExcluirImagem.setIcon(new ImageIcon("../MASProject/icons/delete.png"));
		btnExcluirImagem.setBounds(497, 299, 46, 35);
		contentPane.add(btnExcluirImagem);
		
		AcervoController Acontroller = new AcervoController(lblSelecImagem,cbSetor,cbMaterial);
		
		btnPesquisarImagem.addActionListener(Acontroller.inserir_imagem);
		btnExcluirImagem.addActionListener(Acontroller.remover_imagem);
		
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon("../MASProject/icons/ok.png"));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFechar.setBounds(472, 594, 117, 34);
		contentPane.add(btnFechar);
		
		JButton btnEditarCategoria = new JButton("Editar Categoria");
		btnEditarCategoria.setBounds(29, 178, 133, 29);
		contentPane.add(btnEditarCategoria);
		
		JButton btnEditarMaterial = new JButton("Editar Material");
		btnEditarMaterial.setBounds(28, 245, 133, 29);
		contentPane.add(btnEditarMaterial);
		cbMaterial.addComponentListener(Acontroller);
	}
}
