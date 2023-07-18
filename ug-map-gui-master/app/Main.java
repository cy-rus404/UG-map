package app;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

import app.algorithms.AStar;
import app.algorithms.DFS;
import app.algorithms.Dijkstra;
import app.algorithms.VogelsApproximationMethod;
import app.graph.Edge;
import app.graph.Graph;
import app.graph.Node;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Nodes in the graph.
        Node gym = new Node("Gym");
        Node diaspora = new Node("Diaspora");
        Node ish = new Node("ISH");
        Node nightMarket = new Node("Night Market");
        Node sarbahHall = new Node("Sarbah Hall");
        Node commonWealth = new Node("Common Wealth");
        Node greatHall = new Node("Great Hall");
        Node legonHall = new Node("Legon Hall");
        Node akuafoHall = new Node("Akuafo Hall");
        Node voltaHall = new Node("Volta Hall");
        Node balmeLibrary = new Node("Balme Library");
        Node cbas = new Node("CBAS");
        Node mainGate = new Node("Main Gate");
        Node jqb = new Node("JQB Building");
        Node lawSchool = new Node("Law School");
        Node busSchool = new Node("Business School");
        Node gcb = new Node("GCB");
        Node csdepartment = new Node("CS Department");
        Node polictialScienceDepartment = new Node("Political Science Department");
        Node nb = new Node("NB");
        Node nnb = new Node("NNB");

        graph.addEdge(new Edge(gym, diaspora, 500, 10, "George Benne Round About"));
        graph.addEdge(new Edge(gym, ish, 415, 8, "George Benne Round About"));
        graph.addEdge(new Edge(gym, nightMarket, 634, 9, "George Benne Round About"));

        graph.addEdge(new Edge(diaspora, ish, 400, 8, "grassland"));
        graph.addEdge(new Edge(ish, nightMarket, 214, 3, "banking square, banks, atm"));

        graph.addEdge(new Edge(nightMarket, commonWealth, 1025, 20, "sarbah field"));
        graph.addEdge(new Edge(nightMarket, legonHall, 914, 17, "sarbah field"));
        graph.addEdge(new Edge(nightMarket, sarbahHall, 350, 5));

        graph.addEdge(new Edge(commonWealth, greatHall, 515, 9, "greater hall tower"));
        graph.addEdge(new Edge(commonWealth, voltaHall, 440, 5, "atm"));
        graph.addEdge(new Edge(commonWealth, legonHall, 460, 5, "atm"));

        graph.addEdge(new Edge(sarbahHall, legonHall, 630, 12, "athletic oval, sarbah field"));
        graph.addEdge(new Edge(sarbahHall, akuafoHall, 460, 8, "athletic oval"));

        graph.addEdge(new Edge(legonHall, akuafoHall, 583, 7, "absa bank"));
        graph.addEdge(new Edge(legonHall, balmeLibrary, 530, 6, "economic department"));
        graph.addEdge(new Edge(legonHall, voltaHall, 260, 3, "atm"));

        graph.addEdge(new Edge(akuafoHall, cbas, 385, 5, "agric department"));
        graph.addEdge(new Edge(akuafoHall, csdepartment, 780, 13, "chemistry department"));
        graph.addEdge(new Edge(akuafoHall, balmeLibrary, 580, 7, "absa bank"));

        graph.addEdge(new Edge(cbas, mainGate, 624, 6));
        graph.addEdge(new Edge(cbas, jqb, 610, 9));
        graph.addEdge(new Edge(jqb, lawSchool, 466, 5));

        graph.addEdge(new Edge(lawSchool, csdepartment, 384, 4, "mathematics department"));

        graph.addEdge(new Edge(balmeLibrary, lawSchool, 960, 18));
        graph.addEdge(new Edge(balmeLibrary, busSchool, 203, 4));

        graph.addEdge(new Edge(voltaHall, busSchool, 390, 3));
        graph.addEdge(new Edge(voltaHall, balmeLibrary, 415, 5));

        graph.addEdge(new Edge(voltaHall, balmeLibrary, 415, 5));

        graph.addEdge(new Edge(busSchool, gcb, 433, 5, "balme library fountains"));
        graph.addEdge(new Edge(busSchool, nb, 424, 5, "balme library fountains"));
        graph.addEdge(new Edge(busSchool, csdepartment, 389, 4));

        graph.addEdge(new Edge(polictialScienceDepartment, csdepartment, 386, 4));
        graph.addEdge(new Edge(polictialScienceDepartment, nb, 204, 3));
        graph.addEdge(new Edge(nb, nnb, 330, 4));
        graph.addEdge(new Edge(nnb, gcb, 160, 3));

        // MAP GUI
        UgMap ugMap = new UgMap();
        JFrame mapFrame = new JFrame();
        mapFrame.add(ugMap);
        mapFrame.setTitle("UG MAP");
        mapFrame.setSize(850, 774);
        mapFrame.setVisible(true);

        // GUI
        final int WINDOW_WIDTH = 800;
        final int WINDOW_HEIGHT = 800;

        String[] places = new String[graph.getNodeSize()];
        int index = 0;
        for (Node node : graph.getNodes()) {
            places[index] = node.getName();
            index++;
        }

        JFrame frame = new JFrame();
        frame.setTitle("UG TRANSPORTATION MAP: GROUP 12 Controls UI");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Source
        JComboBox<String> sourceSelectionBox = new JComboBox<String>(places);
        JLabel sourceLabel = new JLabel("CurrentLocation:");
        sourceLabel.setForeground(Color.red);
        sourceLabel.setBounds(10, 10, WINDOW_WIDTH / 2 - 100, 20);
        sourceSelectionBox.setBounds(5, 40, WINDOW_WIDTH / 2 - 100, 40);
        frame.add(sourceSelectionBox);
        frame.add(sourceLabel);

        // Destination
        JComboBox<String> destinationSelectionBox = new JComboBox<String>(places);
        destinationSelectionBox.setSelectedIndex(2);
        JLabel detinationLabel = new JLabel("I'm going to:");
        detinationLabel.setForeground(Color.red);
        detinationLabel.setBounds(WINDOW_WIDTH / 2 + 50, 10, WINDOW_WIDTH / 2 - 100, 20);
        destinationSelectionBox.setBounds(WINDOW_WIDTH / 2 + 50, 40, WINDOW_WIDTH / 2 - 100, 40);
        frame.add(destinationSelectionBox);
        frame.add(detinationLabel);

        // Find Shortest Button
        JButton btnFindShortestPath = new JButton("Find Shortest Route");
        btnFindShortestPath.setBounds(WINDOW_WIDTH / 2 - 300, 90, 300, 60);
        frame.add(btnFindShortestPath);

        // Find Fastest Button
        JButton btnFindFasterPath = new JButton("Find Fastest Route");
        btnFindFasterPath.setBounds(WINDOW_WIDTH / 2 + 50, 90, 300, 60);
        frame.add(btnFindFasterPath);

        // Find lable
        JLabel shortestPathLabel = new JLabel("Best Routes: ");
        shortestPathLabel.setBounds(10, 110, WINDOW_WIDTH / 2 - 100, 20);
        shortestPathLabel.setForeground(Color.red);
        frame.add(shortestPathLabel);

        // Shortest Route Result
        JLabel shortestPathResultLabl = new JLabel();
        shortestPathResultLabl.setBounds(10, 160, WINDOW_WIDTH - 10, 20);
        frame.add(shortestPathResultLabl);

        // Shortest Distance Result
        JLabel shortestDistaneResultLabl = new JLabel();
        shortestDistaneResultLabl.setBounds(10, 175, WINDOW_WIDTH - 10, 20);
        frame.add(shortestDistaneResultLabl);

        // Landmarks
        JLabel landMarkResultLable = new JLabel("Land Marks:");
        landMarkResultLable.setBounds(10, 190, WINDOW_WIDTH - 10, 20);
        frame.add(landMarkResultLable);

        JSeparator sep = new JSeparator();
        sep.setBounds(5, 210, WINDOW_WIDTH - 5, 10);
        frame.add(sep);

        JLabel altPathLabel = new JLabel("Alternative Routes:");
        altPathLabel.setForeground(Color.red);
        altPathLabel.setBounds(10, 220, WINDOW_WIDTH - 10, 20);
        frame.add(altPathLabel);

        JTextArea area = new JTextArea();
        JScrollPane pane = new JScrollPane();
        pane.getViewport().setView(area);
        pane.setBounds(10, 240, WINDOW_WIDTH - 20, 120);
        frame.add(pane);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(5, 400, WINDOW_WIDTH - 5, 10);
        frame.add(sep2);

        // Landmarks
        JLabel landmarkLabel = new JLabel("Enter landmark query:");
        landmarkLabel.setForeground(Color.red);
        landmarkLabel.setBounds(10, 410, WINDOW_WIDTH - 10, 20);
        frame.add(landmarkLabel);

        JTextField landmarkInput = new JTextField();
        landmarkInput.setBounds(10, 430, WINDOW_WIDTH / 2, 50);
        frame.add(landmarkInput);

        // Find Landmark
        JButton btnFindLandmark = new JButton("Locate Landmark");
        btnFindLandmark.setBounds(WINDOW_WIDTH / 2 + 10, 430, 200, 50);
        frame.add(btnFindLandmark);

        btnFindShortestPath.addActionListener(event -> {
            String sourceName = sourceSelectionBox.getSelectedItem().toString();
            String destName = destinationSelectionBox.getSelectedItem().toString();

            Node sourceNode = graph.getNodeByName(sourceName);
            Node destNode = graph.getNodeByName(destName);
            ArrayList<Node> shortestPath = Dijkstra.findShortestPath(graph, sourceNode, destNode);

            shortestPathResultLabl.setText("The Shortest Route: " + shortestPath.toString());
            shortestDistaneResultLabl.setText("Total Distance: " + Dijkstra.getDistance(destNode));
            landMarkResultLable.setText("Landmarks : " + graph.getLandmarks(shortestPath));

            ArrayList<ArrayList<Node>> allPaths = DFS.findAllPaths(graph, sourceNode, destNode);

            StringBuilder builder = new StringBuilder();
            for (ArrayList<Node> nodes : allPaths.subList(allPaths.size() - 6, allPaths.size() - 1)) {
                String distance = String.format("%.3f", graph.calculateDistance(nodes) / 1000f) + "km";
                builder.append(nodes.toString() + ", " + distance + "\n");
            }
            area.setText(builder.toString());
        });

        btnFindFasterPath.addActionListener(event -> {
            String sourceName = sourceSelectionBox.getSelectedItem().toString();
            String destName = destinationSelectionBox.getSelectedItem().toString();

            Node sourceNode = graph.getNodeByName(sourceName);
            Node destNode = graph.getNodeByName(destName);
            Dijkstra.findShortestPath(graph, sourceNode, destNode);
            List<Node> fastesPath = AStar.findFastestPath(graph, sourceNode, destNode);

            shortestPathResultLabl.setText("The Fastest Route: " + fastesPath.toString());
            shortestDistaneResultLabl
                    .setText("Total Distance: " + VogelsApproximationMethod.getTotalCost(graph, sourceNode, destNode));
            landMarkResultLable.setText("Landmarks : " + graph.getLandmarks((ArrayList<Node>) fastesPath));

            ArrayList<ArrayList<Node>> allPaths = DFS.findAllPaths(graph, sourceNode, destNode);

            StringBuilder builder = new StringBuilder();
            for (ArrayList<Node> nodes : allPaths.subList(allPaths.size() - 6, allPaths.size() - 1)) {
                String distance = String.format("%.3f", graph.calculateDistance(nodes) / 1000f) + "km";
                builder.append(nodes.toString() + ", " + distance + "\n");
            }
            area.setText(builder.toString());
        });

        btnFindLandmark.addActionListener(event -> {
            String landmark = landmarkInput.getText().toString();
            List<Edge> edges = graph.findEdgesWithLandmark(landmark);

            StringBuilder builder = new StringBuilder();
            for (Edge edge : edges) {
                builder.append(edge.getSource().getName() + " --> " + edge.getDestination().getName() + "\n");
            }
            area.setText(builder.toString());
        });

        frame.setLayout(null);
        frame.setVisible(true);

    }
}