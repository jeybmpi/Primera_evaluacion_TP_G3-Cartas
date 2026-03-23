import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    private JTabbedPane tpJugadores;
    private Jugador jugador1 = new Jugador();
    private Jugador jugador2 = new Jugador();


    public FrmJuego() {
        setSize(500, 600);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 50, 460, 500);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);

        btnRepartir.addActionListener(e -> {
            repartir();
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });

    }

private void repartir() {
    pnlJugador1.removeAll();
    pnlJugador2.removeAll();

    jugador1.repartir();
    jugador2.repartir();

    jugador1.mostrar(pnlJugador1);
    jugador2.mostrar(pnlJugador2);

    int grupito = jugador1.mostrarGrupos(pnlJugador1, 50, 150);
    int grupito2 = jugador1.mostrarEscaleras(pnlJugador1, 50, grupito + 10);

    jugador1.mostrarSobrantes(pnlJugador1, 50, grupito2 + 10);
    jugador1.calcularPuntaje(pnlJugador1, 50, 450); 

    int grupito3 = jugador2.mostrarGrupos(pnlJugador2, 50, 150);
    int grupito4 = jugador2.mostrarEscaleras(pnlJugador2, 50, grupito3 + 10);
    
    jugador2.mostrarSobrantes(pnlJugador2, 50, grupito4 + 10);
    jugador2.calcularPuntaje(pnlJugador2, 50, 450);

    pnlJugador1.repaint();
    pnlJugador2.repaint();
}

    private void verificar() {
        if(tpJugadores.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null, jugador1.getGrupos());
        }else{
            JOptionPane.showMessageDialog(null, jugador2.getGrupos());
        }
    }

}
