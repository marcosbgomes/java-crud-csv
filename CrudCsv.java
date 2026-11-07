import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Object;
import java.time.Year;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;


public class CrudCsv {
    // Minha janela principal
    private Window window = new Window("CRUD de Livros");

    // Paineis das tabelas
    private PainelBuscaTabela telaConsultaBusca;
    private PainelBuscaTabela telaAtualizarBusca;
    private PainelBuscaTabela telaApagarBusca;

    // Cores
    private static final Color BG_SECUNDARIO = new Color(23, 55, 64);
    private static final Color BG_PRIMARIO = new Color(255, 255, 255);
    private static final Color BRANCO = new Color(255, 255, 255);
    private static final Color CINZA = new Color(148, 148, 148);
    private static final Color AZUL_CLARO = new Color(0, 175, 223);
    private static final Color BG_BOTAO = new Color(255, 255, 176);
    private static final Color HOVER_BOTAO = new Color(255, 255, 200);
    private static final Color BG_BOTAO_2 = new Color(0, 74, 143);
    private static final Color HOVER_BOTAO_2 = new Color(0, 86, 167);

    // Fontes
    private static final Font FONT_BOTOES = new Font("Arial", Font.BOLD, 14);

    private static final int ANOATUAL = Year.now().getValue();

    public static void main(String[] args) {
        CrudCsv app = new CrudCsv();
        app.iniciar();
    }

    private void iniciar() {
        // Gerenciador de visualização (alterna entre as telas de cada operação)
        CardLayout card = new CardLayout();
        // Painel principal onde devem ficar as telas que serão alternadas
        JPanel cardConteudo = new JPanel(card);

        // Métodos das operações
        criarTelaInicio(card, cardConteudo);
        criarTelaAdicionar(card, cardConteudo);
        criarTelaConsultar(card, cardConteudo);
        criarTelaAtualizar(card, cardConteudo);
        criarTelaApagar(card, cardConteudo);

        window.add(cardConteudo);

        window.setVisible(true);
    }

    private void criarTelaInicio(
        CardLayout card, JPanel cardConteudo
    ) {
        // Painel principal da tela de início
        JPanel inicio = new JPanel(new BorderLayout());

        // Painel esquerdo para funcionalidades
        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(BG_SECUNDARIO);
        menu.setPreferredSize(new Dimension(250, 400));

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel titulo = new JPanel(new GridBagLayout());
        titulo.setBackground(BG_SECUNDARIO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 30;
        JLabel txtTitulo = new JLabel("CRUD de Livros");
        txtTitulo.setFont(new Font("Georgia", Font.BOLD, 18));
        txtTitulo.setForeground(BG_BOTAO);
        titulo.add(txtTitulo, gbc);
        gbc.gridy = 1;
        gbc.insets.top = 3;
        gbc.insets.left = 25;
        gbc.insets.right = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSeparator linhaHorizontal = new JSeparator();
        linhaHorizontal.setForeground(BG_BOTAO);
        linhaHorizontal.setPreferredSize(new Dimension(0, 2));
        titulo.add(linhaHorizontal, gbc);
        menu.add(titulo, BorderLayout.NORTH);

        // Painel inferior esquerdo para botão de saída
        JPanel saida = new JPanel(new BorderLayout());
        saida.setBackground(BG_SECUNDARIO);
        Button sair = new Button("[🡐", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2);
        sair.setMargin(new Insets(0, 3, 0, 0));
        sair.setFont(new Font(null, Font.PLAIN, 24));
        sair.setPreferredSize(new Dimension(40, 50));
        saida.add(sair, BorderLayout.WEST);
        menu.add(saida, BorderLayout.SOUTH);

        // Painel para os botões das operações CRUD
        JPanel botoesCrud = new JPanel(new GridBagLayout());
        botoesCrud.setBackground(BG_SECUNDARIO);
        gbc.insets = new Insets(5, 10, 5, 10);  // Espaçamentos

        Dimension tamanhoBotoes = new Dimension(0, 28);

        // Botão 1 - Adicionar
        gbc.gridx = 0;
        gbc.gridy = 0;
        Button adicionar = new Button(
            "Adicionar", BG_BOTAO, AZUL_CLARO, HOVER_BOTAO
        );
        adicionar.setFont(FONT_BOTOES);
        adicionar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(adicionar, gbc);

        // Botão 2 - Consultar
        gbc.gridy = 1;
        Button consultar = new Button(
            "Consultar", BG_BOTAO, AZUL_CLARO, HOVER_BOTAO
        );
        consultar.setFont(FONT_BOTOES);
        consultar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(consultar, gbc);

        // Botão 3 - Ataulizar
        gbc.gridy = 2;
        Button atualizar = new Button(
            "Atualizar", BG_BOTAO, AZUL_CLARO, HOVER_BOTAO
        );
        atualizar.setFont(FONT_BOTOES);
        atualizar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(atualizar, gbc);

        // Botão 4 - Apagar
        gbc.gridy = 3;
        Button apagar = new Button(
            "Apagar", BG_BOTAO, AZUL_CLARO, HOVER_BOTAO
        );
        apagar.setFont(FONT_BOTOES);
        apagar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(apagar, gbc);

        gbc.weightx = 0;
        // Adiciona o painel dos botões no painel esquerdo
        menu.add(botoesCrud, BorderLayout.CENTER);

        // Painel direito para mostrar um banner
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(BG_PRIMARIO);
        banner.setPreferredSize(new Dimension(350, 400));

        gbc.insets = new Insets(1, 5, 1, 5);  // Espaçamentos
        gbc.gridx = 0;
        gbc.gridy = 0;
        banner.add(new Label("C", 1), gbc);
        gbc.gridx = 1;
        banner.add(new Label("R", 1), gbc);
        gbc.gridx = 2;
        banner.add(new Label("U", 1), gbc);
        gbc.gridx = 3;
        banner.add(new Label("D", 1), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        banner.add(new Label("Create", 2), gbc);
        gbc.gridx = 1;
        banner.add(new Label("Read", 2), gbc);
        gbc.gridx = 2;
        banner.add(new Label("Update", 2), gbc);
        gbc.gridx = 3;
        banner.add(new Label("Delete", 2), gbc);

        gbc.insets = new Insets(30, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        JSeparator linhaHorizontal2 = new JSeparator();
        linhaHorizontal2.setForeground(AZUL_CLARO);
        banner.add(linhaHorizontal2, gbc);
        gbc.gridy = 3;
        gbc.insets.top = 3;
        banner.add(new Label("Gerencie seus registros de livros", 2), gbc);

        // Adicionar os dois paineis que dividem a tela no painel principal
        inicio.add(menu, BorderLayout.WEST);
        inicio.add(banner, BorderLayout.CENTER);

        // Adiciona as telas no card para gerenciar alternância de conteúdo
        cardConteudo.add(inicio, "inicio");
        // Mostra a tela de inicio como inicial
        card.show(cardConteudo, "inicio");

        // ******************** EVENTOS ********************
        // Evento para fechar a aplicação
        sair.addActionListener(e -> {
            window.dispatchEvent(new WindowEvent(
                window, WindowEvent.WINDOW_CLOSING
            ));
        });

        // Muda para a aba de adicionar
        adicionar.addActionListener(e -> {
            card.show(cardConteudo, "adicionar");
        });

        // Muda para a aba de consultar
        consultar.addActionListener(e -> {
            card.show(cardConteudo, "consultar");
        });

        // Muda para a aba de atualizar
        atualizar.addActionListener(e -> {
            card.show(cardConteudo, "atualizar");
        });

        // Muda para a aba de apagar
        apagar.addActionListener(e -> {
            card.show(cardConteudo, "apagar");
        });
    }

    private void criarTelaAdicionar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelAdicionar = new Panel(0, card, cardConteudo);

        JPanel camposDados = new JPanel(new GridBagLayout());
        camposDados.setBackground(BRANCO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        camposDados.add(new Rotulo("Título"), gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 0, 5);
        camposDados.add(new Rotulo("Autor"), gbc);
        gbc.gridy = 2;
        camposDados.add(new Rotulo("Ano"), gbc);
        gbc.gridy = 3;
        camposDados.add(new Rotulo("Gênero"), gbc);

        gbc.weightx = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 5, 0, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        TextField tituloLivro = new TextField();
        camposDados.add(tituloLivro, gbc);
        TextField autorLivro = new TextField();
        gbc.insets = new Insets(10, 5, 0, 20);
        gbc.gridy = 1;
        camposDados.add(autorLivro, gbc);

        Spinner anoLivro = new Spinner(
            new SpinnerNumberModel(ANOATUAL, 1, ANOATUAL, 1)
        );
        gbc.gridy = 2;
        camposDados.add(anoLivro, gbc);

        // TextField generoLivro = new TextField();
        String[] opcoes = {
            "Ficção Científica",
            "Fantasia",
            "Mistério",
            "Autoajuda",
            "Ciências e Tecnologia",
            "Terror",
            "Romance",
        };
        ComboBox generoLivro = new ComboBox(opcoes);
        generoLivro.setSelectedIndex(0);
        gbc.gridy = 3;
        camposDados.add(generoLivro, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets.top = 15;
        gbc.gridwidth = 2;
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setBackground(BRANCO);
        Button limpar = new Button("Limpar", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2);
        limpar.setPreferredSize(new Dimension(100, 28));
        botoes.add(limpar);
        Button salvar = new Button("Salvar", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2);
        salvar.setPreferredSize(new Dimension(100, 28));
        botoes.add(salvar);
        camposDados.add(botoes, gbc);
        painelAdicionar.add2(camposDados, BorderLayout.NORTH);

        cardConteudo.add(painelAdicionar, "adicionar");

        // Eventos
        // Limpa os campos
        limpar.addActionListener(e -> {
            tituloLivro.setText("");
            autorLivro.setText("");
            anoLivro.setValue(ANOATUAL);
            generoLivro.setSelectedIndex(0);
        });

        // Salvar um livro
        salvar.addActionListener(e -> {
            File arquivo = new File("livros.csv");

            String msg = "";
            boolean erro = false;

            // Cria o arquivo, caso não exista
            if (!arquivo.exists()) {
                try (FileWriter writer = new FileWriter(arquivo)) {
                    writer.write("Título;Autor;Ano;Gênero");
                } catch (IOException err) {
                    erro = true;
                    err.printStackTrace();
                    msg = "Erro ao criar arquivo 'livros.csv'.";
                }
            }

            if (!erro) {
                String novaLinha = tituloLivro.getText().trim().replace(";", ",") + ";" +
                    autorLivro.getText().trim().replace(";", ",") + ";" +
                    anoLivro.getValue().toString().trim() + ";" +
                    generoLivro.getSelectedItem().toString().trim();

                // Verifica se o mesmo livro já existe no arquivo
                boolean duplicado = false;
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (linha.toLowerCase().equals(novaLinha.toLowerCase())) {
                            duplicado = true;
                            msg = "O livro já existe no arquivo!";
                            break;
                        }
                    }
                } catch (IOException err) {
                    msg = "Erro ao ler 'livros.csv'.";
                }

                if (!duplicado) {
                    try (FileWriter writer = new FileWriter(arquivo, true)) {
                        writer.write("\n" + novaLinha);
                        msg = "Livro salvo com sucesso!";
                    } catch (IOException err) {
                        msg = "Erro ao escrever em 'livros.csv'.";
                    }
                }

                JOptionPane.showMessageDialog(null, msg);
            }

        });

        // Limpar os campos ao sair da tela
        painelAdicionar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                tituloLivro.setText("");
                autorLivro.setText("");
                anoLivro.setValue(ANOATUAL);
                generoLivro.setSelectedIndex(0);
            }
        });
    }

    private void criarTelaConsultar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelConsultar = new Panel(1, card, cardConteudo);

        telaConsultaBusca = new PainelBuscaTabela(220);

        painelConsultar.add2(telaConsultaBusca, BorderLayout.NORTH);
        cardConteudo.add(painelConsultar, "consultar");

        // Limpa a busca ao sair da tela
        painelConsultar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                telaConsultaBusca.getOpcaoBusca().setSelectedIndex(0);
                telaConsultaBusca.getTxtBusca().setText("");
            }
        });
    }

    private void criarTelaAtualizar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelAtualizar = new Panel(2, card, cardConteudo);
        JPanel camposDados = new JPanel(new GridBagLayout());
        camposDados.setBackground(BRANCO);
        GridBagConstraints gbc = new GridBagConstraints();

        // Tabela
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        telaAtualizarBusca = new PainelBuscaTabela(100);
        camposDados.add(telaAtualizarBusca, gbc);

        // Campos para atualização de informações
        gbc.gridy = 1;
        // gbc.insets.top = 10;
        gbc.insets = new Insets(10, 15, 0, 15);
        JPanel camposAtualizar = new JPanel(new GridBagLayout());
        camposAtualizar.setBackground(BRANCO);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 0, 0);
        gbc2.weightx = 1.0;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 4;
        gbc2.insets.bottom = 5;
        Button atualizar = new Button(
            "Atualizar", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2
        );
        atualizar.setPreferredSize(new Dimension(150, 26));
        camposAtualizar.add(atualizar, gbc2);

        gbc2.anchor = GridBagConstraints.EAST;
        gbc2.insets.bottom = 0;
        gbc2.gridwidth = 1;
        gbc2.gridy = 1;
        camposAtualizar.add(new Rotulo("Título"), gbc2);
        gbc2.gridx = 1;
        TextField titulo = new TextField();
        titulo.setPreferredSize(new Dimension(200, 28));
        camposAtualizar.add(titulo, gbc2);

        gbc2.gridx = 2;
        camposAtualizar.add(new Rotulo("Ano"), gbc2);
        gbc2.gridx = 3;
        Spinner anoPublicacao = new Spinner(
            new SpinnerNumberModel(2026, 1, 2026, 1)
        );
        anoPublicacao.setPreferredSize(new Dimension(150, 28));
        camposAtualizar.add(anoPublicacao, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        camposAtualizar.add(new Rotulo("Autor"), gbc2);
        gbc2.gridx = 1;
        TextField autor = new TextField();
        autor.setPreferredSize(
            new Dimension(200, 28)
        );
        camposAtualizar.add(autor, gbc2);

        gbc2.gridx = 2;
        camposAtualizar.add(new Rotulo("Gênero"), gbc2);
        gbc2.gridx = 3;
        String[] opcoesGenero = {
            "Ficção Científica",
            "Fantasia",
            "Mistério",
            "Autoajuda",
            "Ciências e Tecnologia",
            "Terror",
            "Romance",
        };
        ComboBox genero = new ComboBox(opcoesGenero);
        genero.setPreferredSize(new Dimension(150, 28));
        camposAtualizar.add(genero, gbc2);

        camposDados.add(camposAtualizar, gbc);
        painelAtualizar.add2(camposDados, BorderLayout.NORTH);
        cardConteudo.add(painelAtualizar, "atualizar");

        // Evento: Preencher a área de atualização ao clicar em uma linha
        telaAtualizarBusca.getTabela().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = telaAtualizarBusca.getTabela().getSelectedRow();
                if (linha != -1) {
                    titulo.setText(telaAtualizarBusca.getTabela().getValueAt(linha, 0).toString());
                    autor.setText(telaAtualizarBusca.getTabela().getValueAt(linha, 1).toString());
                    anoPublicacao.setValue(telaAtualizarBusca.getTabela().getValueAt(linha, 2));
                    genero.setSelectedItem(telaAtualizarBusca.getTabela().getValueAt(linha, 3));
                }
            }
        });

        // Limpar os campos ao exibir a tela
        painelAtualizar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                // Remove seleção da linha
                telaAtualizarBusca.getTabela().clearSelection();
                // Limpa texto de busca
                telaAtualizarBusca.getOpcaoBusca().setSelectedIndex(0);
                telaAtualizarBusca.getTxtBusca().setText("");
                // Limpa os campos de atualização
                titulo.setText("");
                autor.setText("");
                anoPublicacao.setValue(ANOATUAL);
                genero.setSelectedIndex(0);
            }
        });
    }

    private void criarTelaApagar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelApagar = new Panel(3, card, cardConteudo);
        JPanel camposDados = new JPanel(new GridBagLayout());
        camposDados.setBackground(BRANCO);
        GridBagConstraints gbc = new GridBagConstraints();

        // Tabela
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        telaApagarBusca = new PainelBuscaTabela(170);
        camposDados.add(telaApagarBusca, gbc);

        // Botão de esxluir
        Button excluir = new Button(
            "Excluir", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2
        );
        excluir.setPreferredSize(new Dimension(200, 26));
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets.top = 10;
        camposDados.add(excluir, gbc);

        painelApagar.add2(camposDados, BorderLayout.NORTH);
        cardConteudo.add(painelApagar, "apagar");

        // Limpas os camos de busca
        painelApagar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                telaApagarBusca.getOpcaoBusca().setSelectedIndex(0);
                telaApagarBusca.getTxtBusca().setText("");
            }
        });
    }

    private static class PainelBuscaTabela extends JPanel {
        private ComboBox opcaoBusca;
        private TextField txtBusca;
        private Button buscar;
        private Table tabela;
        private DefaultTableModel dadosTabela;

        PainelBuscaTabela(int alturaTabela) {
            this.setLayout(new GridBagLayout());
            this.setBackground(BRANCO);
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.insets = new Insets(10, 15, 0, 15);
            gbc.gridx = 0;  // primeira coluna
            gbc.gridy = 0;  // primeira linha
            gbc.fill = GridBagConstraints.HORIZONTAL;  // expande na horizontal

            // Opções de busca
            JPanel filtroBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            filtroBusca.setBackground(BRANCO);
            // 01 - Rótulo
            filtroBusca.add(new Rotulo("Buscar por"));
            String[] opcoes = {"Título", "Autor", "Ano", "Gênero"};
            // 02 - Opções da busca
            this.opcaoBusca = new ComboBox(opcoes);
            this.opcaoBusca.setSelectedIndex(0);
            filtroBusca.add(this.opcaoBusca);
            // 03 - Texto da busca
            this.txtBusca = new TextField();
            this.txtBusca.setPreferredSize(
                new Dimension(180, txtBusca.getPreferredSize().height)
            );
            filtroBusca.add(this.txtBusca);
            // 04 - Botão para realizar a busca
            this.buscar = new Button(
                "Bucar", BG_BOTAO_2, BRANCO, HOVER_BOTAO_2
            );
            this.buscar.setPreferredSize(new Dimension(80, 28));
            filtroBusca.add(this.buscar);
            // 05 - Adiciona o painel de opções no painel principal
            this.add(filtroBusca, gbc);

            // Painel, tabela e barra de rolagem
            gbc.gridy = 1;  // segunda linha
            gbc.fill = GridBagConstraints.BOTH;  // expande horizontal e vertical
            JPanel areaTabela = new JPanel(new BorderLayout());
            // Colunas da tabela
            String[] colunas = {"Título", "Autor", "Ano", "Gênero"};
            this.dadosTabela = new DefaultTableModel(colunas, 0);

            // Preenchimento para teste
            for (int i = 1; i < 40; i++) {
                this.dadosTabela.addRow(new Object[] {
                    "Título " + i, "Autor " + i, 2026, "Fantasia"
                });
            }
            // Tabela
            this.tabela = new Table(this.dadosTabela);
            this.tabela.setDefaultEditor(Object.class, null);
            // Painel com barra de rolagem
            JScrollPane painelScroll = new JScrollPane(this.tabela);
            areaTabela .add(painelScroll, BorderLayout.CENTER);
            areaTabela.setPreferredSize(
                new Dimension(areaTabela.getPreferredSize().width, alturaTabela)
            );
            // Adiciona a tabela no painel principal
            this.add(areaTabela, gbc);
        }

        Table getTabela() {
            return tabela;
        }

        DefaultTableModel getDadosTabela() {
            return dadosTabela;
        }

        Button getBotaoBusca() {
            return buscar;
        }

        TextField getTxtBusca() {
            return txtBusca;
        }

        ComboBox getOpcaoBusca() {
            return opcaoBusca;
        }
    }

    private static class Window extends JFrame {
        Window(String titulo) {
            super(titulo);
            // Configurações da janela
            this.setSize(600, 400);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);  // Centralizada na tela
            this.setResizable(false);  // Não redimensionável
        }
    }

    private static class Rotulo extends JLabel {
        Rotulo(String texto) {
            super(texto);
            this.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    private static class Label extends JLabel {
        Label(String texto, int tipo) {
            super(texto);

            switch (tipo) {
                case 1:
                    letra();
                    break;
                default:
                    texto();
                    break;
            }

            // Centraliza o texto
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }

        private void letra() {
            this.setFont(new Font("Arial", Font.BOLD, 56));
            this.setPreferredSize(new Dimension(64, 64));
            this.setForeground(BRANCO);
            this.setBackground(AZUL_CLARO);
            this.setOpaque(true);  // Mostra background do label
        }

        private void texto() {
            this.setFont(new Font("Arial", Font.BOLD, 12));
            this.setForeground(CINZA);
        }
    }

    private static class ComboBoxUI extends MetalComboBoxUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            comboBox.setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        protected JButton createArrowButton() {
            JButton botao = super.createArrowButton();
            botao.setBorder(
                BorderFactory.createEmptyBorder()
            );
            return botao;
        }

        @Override
        public void paintCurrentValueBackground(
            Graphics g, Rectangle bounds, boolean hasFocus
        ) {
            // Não desenha o contorno.
        }
    }

    private static class ComboBox extends JComboBox<String> {
        ComboBox(String[] arg0) {
            super(arg0);
            personalizar();
        }

        private void personalizar() {
            // Utiliza um ComboBoxUI personalizado
            this.setUI(new ComboBoxUI());
            // Cor, tamanho e fonte
            this.setBackground(BRANCO);
            this.setFont(new Font("Arial", Font.PLAIN, 14));
            this.setPreferredSize(new Dimension(90, 28));
            // Cria uma borda
            this.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        AZUL_CLARO, 1
                    ),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        }
    }

    private static class Spinner extends JSpinner {
        Spinner(SpinnerModel model) {
            super(model);

            this.setBackground(BRANCO);
            this.setFont(new Font("Arial", Font.PLAIN, 16));
            this.setPreferredSize(new Dimension(90, 28));
            this.setBorder(
                BorderFactory.createLineBorder(
                    AZUL_CLARO, 1
                )
            );
        }
    }

    private static class TextField extends JTextField {
        TextField() {
            this.setFont(new Font("Arial", Font.PLAIN, 16));
            int width = this.getPreferredSize().width;
            this.setPreferredSize(new Dimension(width, 28));
            this.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        AZUL_CLARO, 1
                    ),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        }
    }

    // Remove bordas de células selecionada
    private static class RendererCellTable extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
            JTable tabela, Object valor, boolean selecionado,
            boolean foco, int linha, int coluna
        ) {
            super.getTableCellRendererComponent(
                tabela, valor, selecionado, foco, linha, coluna
            );

            this.setBorder(BorderFactory.createEmptyBorder());

            return this;
        }
    }

    private static class Table extends JTable {
        Table(TableModel dm){
            super(dm);

            this.setDefaultRenderer(
                Object.class,
                new RendererCellTable()
            );

            // Cabeçalho
            JTableHeader header = this.getTableHeader();
            header.setFont( new Font("Arial", Font.BOLD, 12));
            header.setBackground(AZUL_CLARO);
            header.setForeground(BRANCO);
            header.setPreferredSize(new Dimension(0, 20));

            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setRowHeight(20);
            this.setBackground(BRANCO);
            this.setForeground(Color.BLACK);

            // Cor da seleção
            this.setSelectionBackground(BG_BOTAO);
            this.setSelectionForeground(Color.BLACK);

            // Remove o grid
            this.setShowGrid(false);
            // Largura da coluna de indice 2
            this.getColumnModel().getColumn(2).setPreferredWidth(20);
            // Só permite selecionar uma linha por vez
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    private static class Panel extends JPanel {
        // Painel onde uma instância adiciona o conteúdo
        private JPanel conteudo = new JPanel(new BorderLayout());

        Panel(
            int indiceLetra, CardLayout card, JPanel cardConteudo
        ) {
            this.setLayout(new BorderLayout());
            this.setBackground(BRANCO);

            JPanel barraLateral = new JPanel(new BorderLayout());
            barraLateral.setBackground(BRANCO);

            JPanel voltar = new JPanel(new BorderLayout());
            voltar.setBackground(BRANCO);
            Button btVoltar = new Button(
                " < Início", BG_BOTAO, AZUL_CLARO, HOVER_BOTAO
            );
            btVoltar.setFont(FONT_BOTOES);
            btVoltar.setMargin(new Insets(2, 2, 0, 0));
            btVoltar.setOpaque(false);
            voltar.add(btVoltar, BorderLayout.WEST);

            barraLateral.add(voltar, BorderLayout.NORTH);

            JPanel crud = new JPanel(new GridBagLayout());
            crud.setBackground(BRANCO);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 10, 10);  // Espaçamentos

            gbc.gridx = 0;
            String[] letras = {"C", "R", "U", "D"};
            String[] titulos = {
                "Adicionar Novo Livro",
                "Consultar Livro",
                "Atualizar Livro",
                "Apagar Livro"
            };

            for (int indice = 0; indice < letras.length; indice++) {
                gbc.gridy = indice;
                if (indice == indiceLetra) {
                    Label letra = new Label(letras[indice], 1);
                    letra.setBackground(BG_BOTAO);
                    letra.setForeground(AZUL_CLARO);
                    crud.add(letra, gbc);

                    // Área central
                    JPanel painelPrincipal = new JPanel(new BorderLayout());

                    // Painel superior de título
                    JPanel titulo = new JPanel(new GridBagLayout());
                    titulo.setBackground(BRANCO);
                    GridBagConstraints gbc2 = new GridBagConstraints();
                    gbc2.insets = new Insets(10, 20, 0, 0);
                    gbc2.gridx = 0;
                    gbc2.gridy = 0;
                    titulo.add(new Label(letras[indice], 1), gbc2);
                    gbc2.gridx = 1;
                    gbc2.insets.left = 10;
                    gbc2.anchor = GridBagConstraints.SOUTHWEST;
                    JLabel txtTitulo = new JLabel(titulos[indice]);
                    txtTitulo.setFont(new Font("Georgia", Font.PLAIN, 32));
                    titulo.add(txtTitulo, gbc2);

                    // Separador
                    JSeparator sep = new JSeparator();
                    sep.setForeground(AZUL_CLARO);
                    gbc2.insets = new Insets(5, 15, 0, 15);
                    gbc2.gridx = 0;
                    gbc2.gridy = 1;
                    gbc2.fill = GridBagConstraints.HORIZONTAL;
                    gbc2.gridwidth = 2;
                    gbc2.weightx = 1.0;
                    titulo.add(sep, gbc2);

                    painelPrincipal.add(titulo, BorderLayout.NORTH);

                    // Painel central
                    conteudo.setBackground(BRANCO);
                    painelPrincipal.add(conteudo, BorderLayout.CENTER);

                    this.add(painelPrincipal, BorderLayout.CENTER);
                } else {
                    crud.add(new Label(letras[indice], 1), gbc);
                }
            }

            barraLateral.add(crud, BorderLayout.CENTER);
            this.add(barraLateral, BorderLayout.WEST);

            // Evento: Voltar para a tela inicial
            btVoltar.addActionListener(e -> {
                card.show(cardConteudo, "inicio");
            });
        }

        void add2(Component comp, Object constraints) {
            conteudo.add(comp, constraints);
        }
    }

    private static class Button extends JButton {
        Button(String texto, Color bg, Color fg, Color hover) {
            super(texto);

            // Sobreescreve os métodos de eventos do mouse no botão
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(bg);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (getModel().isRollover()) {
                        setBackground(hover);
                    } else {
                        setBackground(bg);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(bg);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hover);
                }

            });

            this.setBackground(bg);
            this.setForeground(fg);

            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setFocusPainted(false);
            this.setOpaque(true);

            this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        }
    }
}
