import javax.swing.JFrame;

public class CrudCsv {
    public static void main(String[] args) {
        iniciar();
    }

    private static void iniciar() {
        Window window = new Window("CRUD csv");
        window.setVisible(true);
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
