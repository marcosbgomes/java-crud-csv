import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CrudCsv {
    private static Color fundoMenu = new Color(23, 55, 64);
    private static Color fundoPrimario = new Color(255, 255, 255);

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
}
