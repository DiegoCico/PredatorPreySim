import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationGUI extends JFrame {
    private SimulationPanel panel;
    private List<Prey> preys = new ArrayList<>();
    private List<Predator> predators = new ArrayList<>();
    private GraphFrame graphFrame;

    private int maxX = 800;
    private int maxY = 600;

    public SimulationGUI() {
        setTitle("Predator/Prey Simulation");
        setSize(maxX, maxY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new SimulationPanel();
        getContentPane().add(panel);

        initAnimals();

        graphFrame = new GraphFrame();
        graphFrame.setVisible(true);

        setupTimers();
    }

    private void initAnimals() {
        for (int i = 0; i < 10; i++) {
            preys.add(new Prey((int) (Math.random() * maxX), (int) (Math.random() * maxY)));
        }
        for (int i = 0; i < 5; i++) {
            predators.add(new Predator((int) (Math.random() * maxX), (int) (Math.random() * maxY)));
        }
    }

    private void setupTimers() {
        new Timer(50, e -> {
            for (Prey prey : preys) {
                prey.move(maxX, maxY, predators);
            }
            for (Predator predator : predators) {
                predator.move(maxX, maxY, preys);
            }
            panel.repaint();
        }).start();

        new Timer(3000, e -> {
            updateAnimals();
            panel.repaint();
        }).start();
    }

    private void updateAnimals() {
        Iterator<Prey> preyIterator = preys.iterator();
        while (preyIterator.hasNext()) {
            Prey prey = preyIterator.next();
            prey.increaseAge();
            if (prey.getIsDead()) {
                preyIterator.remove();
            }
        }

        Iterator<Predator> predatorIterator = predators.iterator();
        while (predatorIterator.hasNext()) {
            Predator predator = predatorIterator.next();
            predator.increaseAge();
            if (predator.getIsDead()) {
                predatorIterator.remove();
            }
        }

        graphFrame.updateGraph(preys.size(), predators.size());
    }

    private class SimulationPanel extends JPanel {
        public SimulationPanel() {
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Prey prey : preys) {
                g.setColor(Color.GREEN);
                g.fillOval(prey.getX(), prey.getY(), 10, 10);
            }
            for (Predator predator : predators) {
                g.setColor(Color.RED);
                g.fillOval(predator.getX(), predator.getY(), 10, 10);
            }
        }
    }

}
