import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class GraphFrame extends JFrame {
    private GraphPanel graphPanel;

    public GraphFrame() {
        setTitle("Simulation Graph");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphPanel = new GraphPanel();
        add(graphPanel);
    }

    public void updateGraph(int preyCount, int predatorCount) {
        graphPanel.updateCounts(preyCount, predatorCount);
    }

    private class GraphPanel extends JPanel {
        private List<Integer> preyCounts = new ArrayList<>();
        private List<Integer> predatorCounts = new ArrayList<>();

        public GraphPanel() {
            setPreferredSize(new Dimension(800, 300));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawGridlines(g2d);
            drawGraph(g2d, preyCounts, new Color(60, 179, 113), 2f); // Medium Sea Green
            drawGraph(g2d, predatorCounts, new Color(220, 20, 60), 2f); // Crimson
            drawLegend(g2d);
        }

        private void drawGridlines(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= 10; i++) {
                int x = i * getWidth() / 10;
                int y = i * getHeight() / 10;
                g2d.drawLine(x, 0, x, getHeight());
                g2d.drawLine(0, y, getWidth(), y);
            }
        }

        private void drawGraph(Graphics2D g2d, List<Integer> counts, Color color, float lineWidth) {
            if (counts.size() < 2) return;

            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(lineWidth));

            int maxCount = Math.max(1, counts.stream().max(Integer::compare).orElse(1));
            Point prevPoint = new Point(0, getHeight() - (getHeight() * counts.get(0) / maxCount));

            for (int i = 1; i < counts.size(); i++) {
                int x = i * getWidth() / counts.size();
                int y = getHeight() - (getHeight() * counts.get(i) / maxCount);
                Point currentPoint = new Point(x, y);
                g2d.draw(new Line2D.Float(prevPoint, currentPoint));
                prevPoint = currentPoint;
            }
        }

        private void drawLegend(Graphics2D g2d) {
            // Example legend
            g2d.setColor(new Color(60, 179, 113));
            g2d.fillRect(10, 10, 10, 10);
            g2d.setColor(new Color(220, 20, 60));
            g2d.fillRect(10, 30, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Prey", 25, 20);
            g2d.drawString("Predator", 25, 40);
        }

        public void updateCounts(int preyCount, int predatorCount) {
            preyCounts.add(preyCount);
            predatorCounts.add(predatorCount);
            repaint();
        }
    }
}
