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
import java.awt.Component;

import java.lang.Object;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class CrudCsv {
    // Minha janela principal
    private static Window window = new Window("CRUD de Livros");

    // Cores
    private static Color fundoMenu = new Color(23, 55, 64);
    private static Color fundoPrimario = new Color(255, 255, 255);
    private static Color branco = new Color(255, 255, 255);
    private static Color cinza = new Color(148, 148, 148);
    private static Color azulClaro = new Color(0, 175, 223);
    private static Color bgBotao = new Color(255, 255, 176);
    private static Color hoverBotao = new Color(255, 255, 200);
    private static Color bgBotao2 = new Color(0, 74, 143);
    private static Color hoverBotao2 = new Color(0, 86, 167);

    // Fontes
    private static Font fontBotoes = new Font("Arial", Font.BOLD, 14);

    public static void main(String[] args) {
        iniciar();
    }

    private static void iniciar() {
        // Gerenciador de visualização (alterna entre as telas de cada operação)
        CardLayout card = new CardLayout();
        // Painel principal onde devem ficar as telas que serão alternadas
        JPanel cardConteudo = new JPanel(card);

        // Métodos das operações
        criarAbaInicio(card, cardConteudo);
        criarAbaAdicionar(card, cardConteudo);
        criarAbaConsultar(card, cardConteudo);
        criarAbaAtualizar(card, cardConteudo);
        criarAbaApagar(card, cardConteudo);

        window.add(cardConteudo);

        window.setVisible(true);
    }

    private static void criarAbaInicio(
        CardLayout card, JPanel cardConteudo
    ) {
        // Painel principal da tela de início
        JPanel inicio = new JPanel(new BorderLayout());

        // Painel esquerdo para funcionalidades
        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(fundoMenu);
        menu.setPreferredSize(new Dimension(250, 400));

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel titulo = new JPanel(new GridBagLayout());
        titulo.setBackground(fundoMenu);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 30;
        JLabel txtTitulo = new JLabel("CRUD de Livros");
        txtTitulo.setFont(new Font("Georgia", Font.BOLD, 18));
        txtTitulo.setForeground(bgBotao);
        titulo.add(txtTitulo, gbc);
        gbc.gridy = 1;
        gbc.insets.top = 3;
        gbc.insets.left = 25;
        gbc.insets.right = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSeparator linhaHorizontal = new JSeparator();
        linhaHorizontal.setForeground(bgBotao);
        linhaHorizontal.setPreferredSize(new Dimension(0, 2));
        titulo.add(linhaHorizontal, gbc);
        menu.add(titulo, BorderLayout.NORTH);

        // Painel inferior esquerdo para botão de saída
        JPanel saida = new JPanel(new BorderLayout());
        saida.setBackground(fundoMenu);
        Button sair = new Button("[🡐", bgBotao2, branco, hoverBotao2);
        sair.setMargin(new Insets(0, 3, 0, 0));
        sair.setFont(new Font(null, Font.PLAIN, 24));
        sair.setPreferredSize(new Dimension(40, 50));
        saida.add(sair, BorderLayout.WEST);
        menu.add(saida, BorderLayout.SOUTH);

        // Painel para os botões das operações CRUD
        JPanel botoesCrud = new JPanel(new GridBagLayout());
        botoesCrud.setBackground(fundoMenu);
        gbc.insets = new Insets(5, 10, 5, 10);  // Espaçamentos

        Dimension tamanhoBotoes = new Dimension(0, 28);

        // Botão 1 - Adicionar
        gbc.gridx = 0;
        gbc.gridy = 0;
        Button adicionar = new Button(
            "Adicionar", bgBotao, azulClaro, hoverBotao
        );
        adicionar.setFont(fontBotoes);
        adicionar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(adicionar, gbc);

        // Botão 2 - Consultar
        gbc.gridy = 1;
        Button consultar = new Button(
            "Consultar", bgBotao, azulClaro, hoverBotao
        );
        consultar.setFont(fontBotoes);
        consultar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(consultar, gbc);

        // Botão 3 - Ataulizar
        gbc.gridy = 2;
        Button atualizar = new Button(
            "Atualizar", bgBotao, azulClaro, hoverBotao
        );
        atualizar.setFont(fontBotoes);
        atualizar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(atualizar, gbc);

        // Botão 4 - Apagar
        gbc.gridy = 3;
        Button apagar = new Button(
            "Apagar", bgBotao, azulClaro, hoverBotao
        );
        apagar.setFont(fontBotoes);
        apagar.setPreferredSize(tamanhoBotoes);
        botoesCrud.add(apagar, gbc);

        gbc.weightx = 0;
        // Adiciona o painel dos botões no painel esquerdo
        menu.add(botoesCrud, BorderLayout.CENTER);

        // Painel direito para mostrar um banner
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(fundoPrimario);
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
        linhaHorizontal2.setForeground(azulClaro);
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

    private static void criarAbaAdicionar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelAdicionar = new Panel(0, card, cardConteudo);

        JPanel camposDados = new JPanel(new GridBagLayout());
        camposDados.setBackground(branco);
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
        // gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        TextField tituloLivro = new TextField();
        camposDados.add(tituloLivro, gbc);
        TextField autorLivro = new TextField();
        gbc.insets = new Insets(10, 5, 0, 20);
        gbc.gridy = 1;
        camposDados.add(autorLivro, gbc);
        TextField anoLivro = new TextField();
        gbc.gridy = 2;
        camposDados.add(anoLivro, gbc);
        TextField generoLivro = new TextField();
        gbc.gridy = 3;
        camposDados.add(generoLivro, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets.top = 15;
        gbc.gridwidth = 2;
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setBackground(branco);
        Button limpar = new Button("Limpar", bgBotao2, branco, hoverBotao2);
        limpar.setPreferredSize(new Dimension(100, 28));
        botoes.add(limpar);
        Button salvar = new Button("Salvar", bgBotao2, branco, hoverBotao2);
        salvar.setPreferredSize(new Dimension(100, 28));
        botoes.add(salvar);
        camposDados.add(botoes, gbc);
        painelAdicionar.add2(camposDados, BorderLayout.NORTH);

        cardConteudo.add(painelAdicionar, "adicionar");
    }

    private static void criarAbaConsultar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelConsultar = new Panel(1, card, cardConteudo);
        painelConsultar.add2(
            new Label("Consultar", 2),
            BorderLayout.CENTER
        );
        cardConteudo.add(painelConsultar, "consultar");
    }

    private static void criarAbaAtualizar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelAtualizar = new Panel(2, card, cardConteudo);
        painelAtualizar.add2(
            new Label("Atualizar", 2),
            BorderLayout.CENTER
        );
        cardConteudo.add(painelAtualizar, "atualizar");
    }

    private static void criarAbaApagar(
        CardLayout card, JPanel cardConteudo
    ) {
        Panel painelApagar = new Panel(3, card, cardConteudo);
        painelApagar.add2(
            new Label("Apagar", 2),
            BorderLayout.CENTER
        );
        cardConteudo.add(painelApagar, "apagar");
    }

    static class Window extends JFrame {
        public Window(String titulo) {
            super(titulo);
            // Configurações da janela
            this.setSize(600, 400);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);  // Centralizada na tela
            this.setResizable(false);  // Não redimensionável
        }
    }

    static class Rotulo extends JLabel {
        public Rotulo(String texto) {
            super(texto);
            this.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    static class Label extends JLabel {
        public Label(String texto, int tipo) {
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
            this.setForeground(branco);
            this.setBackground(azulClaro);
            this.setOpaque(true);  // Mostra background do label
        }

        private void texto() {
            this.setFont(new Font("Arial", Font.BOLD, 12));
            this.setForeground(cinza);
        }
    }

    static class TextField extends JTextField {
        public TextField() {
            this.setFont(new Font("Arial", Font.PLAIN, 16));
            int width = this.getPreferredSize().width;
            this.setPreferredSize(new Dimension(width, 28));
            this.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        azulClaro, 1
                    ),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        }
    }

    static class Panel extends JPanel {
        // Painel onde uma instância adiciona o conteúdo
        private JPanel conteudo = new JPanel(new BorderLayout());

        public Panel(
            int indiceLetra, CardLayout card, JPanel cardConteudo
        ) {
            this.setLayout(new BorderLayout());
            this.setBackground(branco);

            JPanel barraLateral = new JPanel(new BorderLayout());
            barraLateral.setBackground(branco);

            JPanel voltar = new JPanel(new BorderLayout());
            voltar.setBackground(branco);
            Button btVoltar = new Button(
                " < Início", bgBotao, azulClaro, hoverBotao
            );
            btVoltar.setFont(fontBotoes);
            btVoltar.setMargin(new Insets(2, 2, 0, 0));
            btVoltar.setOpaque(false);
            voltar.add(btVoltar, BorderLayout.WEST);

            barraLateral.add(voltar, BorderLayout.NORTH);

            JPanel crud = new JPanel(new GridBagLayout());
            crud.setBackground(branco);
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
                    letra.setBackground(bgBotao);
                    letra.setForeground(azulClaro);
                    crud.add(letra, gbc);

                    // Área central
                    JPanel painelPrincipal = new JPanel(new BorderLayout());

                    // Painel superior de título
                    JPanel titulo = new JPanel(new GridBagLayout());
                    titulo.setBackground(branco);
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
                    sep.setForeground(azulClaro);
                    gbc2.insets = new Insets(5, 15, 0, 15);
                    gbc2.gridx = 0;
                    gbc2.gridy = 1;
                    gbc2.fill = GridBagConstraints.HORIZONTAL;
                    gbc2.gridwidth = 2;
                    gbc2.weightx = 1.0;
                    titulo.add(sep, gbc2);

                    painelPrincipal.add(titulo, BorderLayout.NORTH);

                    // Painel central
                    conteudo.setBackground(branco);
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

        public void add2(Component comp, Object constraints) {
            conteudo.add(comp, constraints);
        }
    }

    static class Button extends JButton {
        public Button(String texto, Color bg, Color fg, Color hover) {
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
