package GraphicalUserInterface;

import BusinessLogic.PolynomialsOperations;
import DataModels.Polynomial;
import GraphicalUserInterface.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new View("Polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}