import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationGUI extends JFrame {
    private SimulationPanel panel;
    private List<Prey> preys = new ArrayList<>();
    private List<Predator> predators = new ArrayList<>();

    public SimulationGUI() {
        setTitle("Predator/Prey Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new SimulationPanel();
        getContentPane().add(panel);

        // Create some Prey and Predators instances
        for (int i = 0; i < 10; i++) {
            preys.add(new Prey((int) (Math.random() * 800), (int) (Math.random() * 600)));
        }
        for (int i = 0; i < 5; i++) {
            predators.add(new Predator((int) (Math.random() * 800), (int) (Math.random() * 600)));
        }

        // Timer to update and repaint the simulation for movement
        new Timer(50, e -> {
            for (Prey prey : preys) {
                prey.move(800, 600); // Adjusted to the size of the GUI window
            }
            for (Predator predator : predators) {
                predator.move(800, 600); // Adjusted to the size of the GUI window
            }
            panel.repaint();
        }).start();

        // Timer to update age and handle breeding
        new Timer(100, e -> {
            List<Prey> newPreys = new ArrayList<>();
            for (Prey prey : preys) {
                prey.increaseAge();
                Prey offspring = prey.breed();
                if(offspring != null)
                    newPreys.add(offspring);
            }
            preys.addAll(newPreys);
            panel.repaint();
        }).start();
    }

    private class SimulationPanel extends JPanel {
        public SimulationPanel(){
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            for (Prey prey : preys) {
                g.setColor(Color.GREEN);
                g.fillOval(prey.getX(), prey.getY(), 10, 10); // Represent each Prey with a circle
            }
            for (Predator predator : predators) {
                g.setColor(Color.RED);
                g.fillOval(predator.getX(), predator.getY(), 10, 10); // Represent each Predator with a circle
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}
