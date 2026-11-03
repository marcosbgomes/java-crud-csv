import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class CrudCsv {
    private static Color fundoMenu = new Color(23, 55, 64);
    private static Color fundoPrimario = new Color(255, 255, 255);
    private static Color branco = new Color(255, 255, 255);
    private static Color azulClaro = new Color(0, 175, 223);
    private static Color bgBotao = new Color(255, 255, 176);
    private static Color hoverBotao = new Color(255, 255, 200);
    private static Color bgBotao2 = new Color(0, 74, 143);
    private static Color hoverBotao2 = new Color(0, 86, 167);

    public static void main(String[] args) {
        iniciar();
    }

    private static void iniciar() {
        Window window = new Window("CRUD csv");

        // Gerenciador de visualização (alterna entre as telas de cada operação)
        CardLayout card = new CardLayout();
        // Painel principal onde devem ficar as telas que serão alternadas
        JPanel cardConteudo = new JPanel(card);

        // Métodos das operações
        criarAbaInicio(window, card, cardConteudo);

        window.add(cardConteudo);

        window.setVisible(true);
    }

    private static void criarAbaInicio(
        JFrame window, CardLayout card, JPanel cardConteudo
    ) {
        // Painel principal da tela de início
        JPanel inicio = new JPanel(new BorderLayout());

        // Painel esquerdo para funcionalidades
        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(fundoMenu);
        menu.setPreferredSize(new Dimension(250, 400));

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // Espaçamentos

        // Botão 1 - Adicionar
        gbc.gridx = 0;
        gbc.gridy = 0;
        Button adicionar = new Button(
            "Adicionar", bgBotao, azulClaro, hoverBotao
        );
        adicionar.setFont(new Font("Arial", Font.BOLD, 14));
        adicionar.setPreferredSize(new Dimension(180, 28));
        botoesCrud.add(adicionar, gbc);

        // Botão 2 - Consultar
        gbc.gridy = 1;
        Button consultar = new Button(
            "Consultar", bgBotao, azulClaro, hoverBotao
        );
        consultar.setFont(new Font("Arial", Font.BOLD, 14));
        consultar.setPreferredSize(new Dimension(180, 28));
        botoesCrud.add(consultar, gbc);

        // Botão 3 - Ataulizar
        gbc.gridy = 2;
        Button atualizar = new Button(
            "Atualizar", bgBotao, azulClaro, hoverBotao
        );
        atualizar.setFont(new Font("Arial", Font.BOLD, 14));
        atualizar.setPreferredSize(new Dimension(180, 28));
        botoesCrud.add(atualizar, gbc);

        // Botão 4 - Apagar
        gbc.gridy = 3;
        Button apagar = new Button(
            "Apagar", bgBotao, azulClaro, hoverBotao
        );
        apagar.setFont(new Font("Arial", Font.BOLD, 14));
        apagar.setPreferredSize(new Dimension(180, 28));
        botoesCrud.add(apagar, gbc);

        // Adiciona o painel dos botões no painel esquerdo
        menu.add(botoesCrud, BorderLayout.CENTER);

        // Painel direito para mostrar um banner
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(fundoPrimario);
        banner.setPreferredSize(new Dimension(350, 400));

        // Adicionar os dois paineis que dividem a tela no painel principal
        inicio.add(menu, BorderLayout.WEST);
        inicio.add(banner, BorderLayout.CENTER);

        // Adiciona as telas no card para gerenciar alternância de conteúdo
        cardConteudo.add(inicio, "inicio");
        // Mostra a tela de inicio como inicial
        card.show(cardConteudo, "inicio");
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
