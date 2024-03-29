package GraphicalUserInterface;

import BusinessLogic.PolynomialsOperations;
import DataModels.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View view;

    public Controller(View v) {
        this.view = v;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Polynomial polynomial1 = null;
        Polynomial polynomial2 = null;
        PolynomialsOperations op;
        switch (command) {
            case "Add":
                if (processInput()) return;
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.add();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Substract":
                if (processInput()) return;
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.substract();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Multiply":
                if (processInput()) return;
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.multiply();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Divide":
                if (processInput()) return;
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (polynomial2.toString().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Illegal division by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                Polynomial[] result = op.divide();
                view.getResultLabel().setText("Result: Q = " + result[0] + " R = " + result[1]);
                break;
            case "Integrate P":
                if (view.getFirstPolynomialTextField().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please type the first polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.integrateFirstPolynomial();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Integrate Q":
                if (view.getSecondPolynomialTextField().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please type the second polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.integrateSecondPolynomial();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Differentiate P":
                if (view.getFirstPolynomialTextField().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please type the first polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial1 = new Polynomial(view.getFirstPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The first polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.differentiateFirstPolynomial();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Differentiate Q":
                if (view.getSecondPolynomialTextField().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please type the second polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    polynomial2 = new Polynomial(view.getSecondPolynomialTextField().getText());
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "The second polynomial is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                op = new PolynomialsOperations(polynomial1, polynomial2);
                op.differentiateSecondPolynomial();
                view.getResultLabel().setText("Result: " + op.getResult().toString());
                break;
            case "Reset":
                view.getFirstPolynomialTextField().setText("");
                view.getSecondPolynomialTextField().setText("");
                view.getResultLabel().setText("Result:");
                break;
            case "Reverse":
                String polynom1 = view.getFirstPolynomialTextField().getText();
                view.getFirstPolynomialTextField().setText(view.getSecondPolynomialTextField().getText());
                view.getSecondPolynomialTextField().setText(polynom1);
                view.getResultLabel().setText("Result:");
                break;
        }
    }

    private boolean processInput() {
        if (view.getFirstPolynomialTextField().getText().isEmpty() && view.getSecondPolynomialTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please type the polynomials.", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (view.getFirstPolynomialTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please type the first polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (view.getSecondPolynomialTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please type the second polynomial.", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
}
