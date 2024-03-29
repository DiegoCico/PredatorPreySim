import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Frame for displaying a dynamic simulation graph.
 */
public class GraphFrame extends JFrame {
    private GraphPanel graphPanel;

    public GraphFrame() {
        setTitle("Simulation Graph");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphPanel = new GraphPanel();
        add(graphPanel);
    }

    /**
     * Updates the graph with new prey and predator counts.
     *
     * @param preyCount     the current count of prey
     * @param predatorCount the current count of predators
     */
    public void updateGraph(int preyCount, int predatorCount) {
        graphPanel.updateCounts(preyCount, predatorCount);
    }

    /**
     * Inner class for the graph panel.
     */
    private class GraphPanel extends JPanel {
        private List<Integer> preyCounts = new ArrayList<>();
        private List<Integer> predatorCounts = new ArrayList<>();

        public GraphPanel() {
            setPreferredSize(new Dimension(800, 300));
            setBackground(Color.WHITE);
        }

        /**
         * Draws gridlines on the graph.
         *
         * @param g2d the Graphics2D object
         */
        private void drawGridlines(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= 10; i++) {
                int x = i * getWidth() / 10;
                int y = i * getHeight() / 10;
                g2d.drawLine(x, 0, x, getHeight());
                g2d.drawLine(0, y, getWidth(), y);
            }
        }

        /**
         * Draws the graph lines for prey and predator counts.
         *
         * @param g2d    the Graphics2D object
         * @param counts the counts to plot
         * @param color  the color of the graph line
         * @param lineWidth the width of the graph line
         */
        private void drawGraph(Graphics2D g2d, List<Integer> counts, Color color, float lineWidth) {
            if (counts.isEmpty()) return;

            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(lineWidth));

            int maxCount = Math.max(
                    preyCounts.stream().max(Integer::compare).orElse(1),
                    predatorCounts.stream().max(Integer::compare).orElse(1)
            );

            for (int i = 1; i < counts.size(); i++) {
                int x1 = (i - 1) * getWidth() / (counts.size() - 1);
                int y1 = getHeight() - (getHeight() * counts.get(i - 1) / maxCount);

                int x2 = i * getWidth() / (counts.size() - 1);
                int y2 = getHeight() - (getHeight() * counts.get(i) / maxCount);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        /**
         * Draws axis labels for the graph.
         *
         * @param g2d the Graphics2D object
         */
        private void drawAxisLabels(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            // Add logic to draw axis labels if required
        }

        /**
         * Retrieves the last count of prey.
         *
         * @return the last prey count
         */
        private int getLastPreyCount() {
            if (preyCounts.isEmpty()) return 0;
            return preyCounts.get(preyCounts.size() - 1);
        }

        /**
         * Retrieves the last count of predators.
         *
         * @return the last predator count
         */
        private int getLastPredatorCount() {
            if (predatorCounts.isEmpty()) return 0;
            return predatorCounts.get(predatorCounts.size() - 1);
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
            drawAxisLabels(g2d);
            drawCurrentCounts(g2d);
        }

        /**
         * Draws the current prey and predator counts.
         *
         * @param g2d the Graphics2D object
         */
        private void drawCurrentCounts(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            String preyCountStr = "Current Prey Count: " + getLastPreyCount();
            String predatorCountStr = "Current Predator Count: " + getLastPredatorCount();
            g2d.drawString(preyCountStr, getWidth() - 220, getHeight() - 40);
            g2d.drawString(predatorCountStr, getWidth() - 220, getHeight() - 20);
        }

        /**
         * Draws a legend for the graph.
         *
         * @param g2d the Graphics2D object
         */
        private void drawLegend(Graphics2D g2d) {
            g2d.setColor(new Color(60, 179, 113));
            g2d.fillRect(10, 10, 10, 10);
            g2d.setColor(new Color(220, 20, 60));
            g2d.fillRect(10, 30, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Prey", 25, 20);
            g2d.drawString("Predator", 25, 40);
        }

        /**
         * Updates the prey and predator counts and repaints the graph.
         *
         * @param preyCount     the new prey count
         * @param predatorCount the new predator count
         */
        public void updateCounts(int preyCount, int predatorCount) {
            preyCounts.add(preyCount);
            predatorCounts.add(predatorCount);
            repaint();
        }
    }
}
