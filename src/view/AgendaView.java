package view;

import controller.ContatoController;
import model.Contato;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AgendaView extends JFrame {
    private JTextField txtNome = new JTextField(20);
    private JTextField txtTelefone = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);

    private JTable tabelaContatos;
    private DefaultTableModel modeloTabela;
    private ContatoController controller;

    public AgendaView() {
        super("📱 Agenda de Contatos");

        controller = new ContatoController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Estiliza os campos de texto (cor de fundo, fonte, etc)
        configurarCampoTexto(txtNome);
        configurarCampoTexto(txtTelefone);
        configurarCampoTexto(txtEmail);

        // Painel principal com layout BorderLayout e fundo escuro
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(18, 18, 18));

        // Painel formulário com BoxLayout (empilha verticalmente os componentes)
        JPanel painelFormulario = new JPanel();
        painelFormulario.setBackground(new Color(28, 28, 28));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));

        // Adiciona labels e campos de texto no formulário, com espaçamento
        painelFormulario.add(criarLabel("Nome"));
        painelFormulario.add(txtNome);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 15)));

        painelFormulario.add(criarLabel("Telefone"));
        painelFormulario.add(txtTelefone);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 15)));

        painelFormulario.add(criarLabel("Email"));
        painelFormulario.add(txtEmail);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 15)));

        // Painel dos botões Adicionar e Remover Selecionados
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(28, 28, 28));
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAdicionar = criarBotao("Adicionar", new Color(76, 175, 80));
        JButton btnRemover = criarBotao("Remover Selecionados", new Color(244, 67, 54));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);

        // Define o modelo da tabela com coluna extra para checkbox (Boolean)
        modeloTabela = new DefaultTableModel(new Object[]{"Selecionar", "Nome", "Telefone", "Email"}, 0) {
            // Define tipos de dados das colunas
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // checkbox na primeira coluna
                return String.class;
            }

            // Permite editar somente a coluna do checkbox
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // só checkbox editável
            }
        };

        tabelaContatos = new JTable(modeloTabela);
        // Permite seleção múltipla de linhas na tabela (útil para outras interações)
        tabelaContatos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tabelaContatos.setShowGrid(false);
        tabelaContatos.setRowHeight(28);
        tabelaContatos.setBackground(new Color(18, 18, 18));
        tabelaContatos.setForeground(Color.WHITE);
        tabelaContatos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaContatos.setFillsViewportHeight(true);

        // Renderer para linhas alternadas com cores escuras
        tabelaContatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(38, 116, 190));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? new Color(28, 28, 28) : new Color(38, 38, 38));
                    setForeground(Color.WHITE);
                }
                return this;
            }
        });

        // Centraliza o texto das colunas Nome, Telefone e Email
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabelaContatos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelaContatos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelaContatos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tabelaContatos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        scrollPane.setBackground(new Color(18, 18, 18));

        // Adiciona os painéis na janela principal
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        add(painelPrincipal);

        pack();  // Ajusta tamanho da janela automaticamente
        setLocationRelativeTo(null); // Centraliza a janela

        // Carrega os contatos existentes na tabela
        atualizarTabela();

        // Evento botão Adicionar
        btnAdicionar.addActionListener(e -> {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Cria contato novo com dados do formulário
            Contato c = new Contato(txtNome.getText().trim(), txtTelefone.getText().trim(), txtEmail.getText().trim());
            controller.adicionar(c); // adiciona no banco/controlador
            limparCampos(); // limpa os campos para próximo cadastro
            atualizarTabela(); // atualiza tabela com o novo contato
        });

        // Evento botão Remover Selecionados
        btnRemover.addActionListener(e -> {
            boolean algumSelecionado = false;
            // Percorre todas as linhas da tabela
            for (int i = modeloTabela.getRowCount() - 1; i >= 0; i--) {
                Boolean marcado = (Boolean) modeloTabela.getValueAt(i, 0);
                if (marcado != null && marcado) {
                    // Se checkbox marcado, remove contato no controlador pelo nome
                    String nome = (String) modeloTabela.getValueAt(i, 1);
                    controller.remover(nome);
                    algumSelecionado = true;
                }
            }
            if (!algumSelecionado) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos um contato para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            atualizarTabela(); // atualiza tabela após remoção
        });
    }

    // Método para estilizar campos de texto com tema dark
    private void configurarCampoTexto(JTextField campo) {
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(48, 48, 48));
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // Atualiza a tabela de contatos com os dados do controlador
    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // limpa tabela
        List<Contato> lista = controller.listar(); // pega lista do controlador
        for (Contato c : lista) {
            // Adiciona linha com checkbox falso (não marcado) e dados do contato
            modeloTabela.addRow(new Object[]{false, c.getNome(), c.getTelefone(), c.getEmail()});
        }
    }

    // Limpa os campos do formulário para novo cadastro
    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtNome.requestFocus();
    }

    // Cria JLabel estilizado com texto branco e fonte customizada
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return lbl;
    }

    // Cria JButton estilizado com cor de fundo, fonte e cursor personalizados
    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(corFundo);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }
}
