package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private JPanel contentPanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel numbersPanel;
    private JLabel firstPolynomialLabel;
    private JTextField firstPolynomialTextField;
    private JLabel secondPolynomialLabel;
    private JTextField secondPolynomialTextField;
    private JPanel operationPanel;
    private JButton addButton;
    private JButton substractButton;
    private JButton divideButton;
    private JButton multiplyButton;
    private JButton derivatePButton;
    private JButton derivateQButton;
    private JButton integratePButton;
    private JButton integrateQButton;
    private JPanel resetReversePanel;
    private JButton resetButton;
    private JButton reverseButton;
    private JPanel resultPanel;
    private JLabel resultLabel;

    Controller controller = new Controller(this);

    public View(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui() {
        this.setSize(600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new FlowLayout());
        this.contentPanel.setBackground(Color.PINK);
        this.prepareTitlePanel();
        this.prepareNumbersPanel();
        this.prepareOperationPanel();
        this.prepareResetReversePanel();
        this.prepareResultPanel();
        this.setContentPane(this.contentPanel);
    }

    private void prepareTitlePanel() {
        this.titlePanel = new JPanel();
        this.titlePanel.setPreferredSize(new Dimension(600,50));
        this.titleLabel = new JLabel("Polynomial Calculator", JLabel.CENTER);
        Font font = new Font("Times New Roman", Font.PLAIN, 26);
        this.titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.titleLabel.setFont(font);
        this.titleLabel.setOpaque(false);
        this.titleLabel.setForeground(Color.WHITE);
        this.titlePanel.add(this.titleLabel);
        this.titlePanel.setOpaque(false);
        this.contentPanel.add(this.titlePanel);
    }

    private void prepareNumbersPanel() {
        this.numbersPanel = new JPanel();
        this.numbersPanel.setLayout(new GridLayout(2, 2));
        this.numbersPanel.setOpaque(false);
        this.numbersPanel.setPreferredSize(new Dimension(600,100));

        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Font fontPolynom = new Font("Times New Roman", Font.PLAIN, 16);
        this.firstPolynomialLabel = new JLabel("     First polynomial  P(x) = ", JLabel.CENTER);
        this.firstPolynomialLabel.setFont(font);
        this.firstPolynomialLabel.setOpaque(false);
        this.firstPolynomialLabel.setForeground(Color.WHITE);
        this.numbersPanel.add(this.firstPolynomialLabel);
        this.firstPolynomialTextField = new JTextField();
        this.firstPolynomialTextField.setFont(fontPolynom);
        this.firstPolynomialTextField.setForeground(Color.PINK);
        this.firstPolynomialTextField.setBackground(Color.WHITE);
        this.numbersPanel.add(this.firstPolynomialTextField);

        this.secondPolynomialLabel = new JLabel("Second polynomial  Q(x) = ", JLabel.CENTER);
        this.secondPolynomialLabel.setFont(font);
        this.secondPolynomialLabel.setOpaque(false);
        this.secondPolynomialLabel.setForeground(Color.WHITE);
        this.numbersPanel.add(secondPolynomialLabel);
        this.secondPolynomialTextField = new JTextField();
        this.secondPolynomialTextField.setFont(fontPolynom);
        this.secondPolynomialTextField.setForeground(Color.PINK);
        this.secondPolynomialTextField.setBackground(Color.WHITE);
        this.numbersPanel.add(secondPolynomialTextField);

        this.contentPanel.add(this.numbersPanel);
    }

    private void prepareOperationPanel() {
        this.operationPanel = new JPanel();
        this.operationPanel.setLayout(new GridLayout(2, 4));
        this.operationPanel.setOpaque(false);
        this.operationPanel.setPreferredSize(new Dimension(600,150));
        Font font = new Font("Times New Roman", Font.PLAIN, 16);

        this.addButton = new JButton("Add");
        this.addButton.setBackground(Color.WHITE);
        this.addButton.setFont(font);
        this.addButton.setForeground(Color.PINK);
        this.addButton.setActionCommand("Add");
        this.addButton.addActionListener(this.controller);
        this.operationPanel.add(this.addButton);

        this.substractButton = new JButton("Substract");
        this.substractButton.setBackground(Color.WHITE);
        this.substractButton.setFont(font);
        this.substractButton.setForeground(Color.PINK);
        this.substractButton.setActionCommand("Substract");
        this.substractButton.addActionListener(this.controller);
        this.operationPanel.add(this.substractButton);

        this.multiplyButton = new JButton("Multiply");
        this.multiplyButton.setBackground(Color.WHITE);
        this.multiplyButton.setFont(font);
        this.multiplyButton.setForeground(Color.PINK);
        this.multiplyButton.setActionCommand("Multiply");
        this.multiplyButton.addActionListener(this.controller);
        this.operationPanel.add(this.multiplyButton);

        this.divideButton = new JButton("Divide");
        this.divideButton.setBackground(Color.WHITE);
        this.divideButton.setFont(font);
        this.divideButton.setForeground(Color.PINK);
        this.divideButton.setActionCommand("Divide");
        this.divideButton.addActionListener(this.controller);
        this.operationPanel.add(this.divideButton);

        this.derivatePButton = new JButton("Differentiate P");
        this.derivatePButton.setBackground(Color.WHITE);
        this.derivatePButton.setFont(font);
        this.derivatePButton.setForeground(Color.PINK);
        this.derivatePButton.setActionCommand("Differentiate P");
        this.derivatePButton.addActionListener(this.controller);
        this.operationPanel.add(this.derivatePButton);

        this.derivateQButton = new JButton("Differentiate Q");
        this.derivateQButton.setBackground(Color.WHITE);
        this.derivateQButton.setFont(font);
        this.derivateQButton.setForeground(Color.PINK);
        this.derivateQButton.setActionCommand("Differentiate Q");
        this.derivateQButton.addActionListener(this.controller);
        this.operationPanel.add(this.derivateQButton);

        this.integratePButton = new JButton("Integrate P");
        this.integratePButton.setBackground(Color.WHITE);
        this.integratePButton.setFont(font);
        this.integratePButton.setForeground(Color.PINK);
        this.integratePButton.setActionCommand("Integrate P");
        this.integratePButton.addActionListener(this.controller);
        this.operationPanel.add(this.integratePButton);

        this.integrateQButton = new JButton("Integrate Q");
        this.integrateQButton.setBackground(Color.WHITE);
        this.integrateQButton.setFont(font);
        this.integrateQButton.setForeground(Color.PINK);
        this.integrateQButton.setActionCommand("Integrate Q");
        this.integrateQButton.addActionListener(this.controller);
        this.operationPanel.add(this.integrateQButton);

        this.contentPanel.add(this.operationPanel);
    }

    private void prepareResetReversePanel(){
        this.resetReversePanel = new JPanel();
        this.resetReversePanel.setLayout((new GridLayout(1,2)));
        this.resetReversePanel.setOpaque(false);
        this.resetReversePanel.setPreferredSize(new Dimension(600,50));
        Font font = new Font("Times New Roman", Font.PLAIN, 16);

        this.resetButton = new JButton("Reset");
        this.resetButton.setBackground(Color.WHITE);
        this.resetButton.setFont(font);
        this.resetButton.setForeground(Color.PINK);
        this.resetButton.setActionCommand("Reset");
        this.resetButton.addActionListener(this.controller);
        this.resetReversePanel.add(this.resetButton);

        this.reverseButton = new JButton("Reverse");
        this.reverseButton.setBackground(Color.WHITE);
        this.reverseButton.setFont(font);
        this.reverseButton.setForeground(Color.PINK);
        this.reverseButton.setActionCommand("Reverse");
        this.reverseButton.addActionListener(this.controller);
        this.resetReversePanel.add(this.reverseButton);

        this.contentPanel.add(this.resetReversePanel);
    }

    private void prepareResultPanel() {
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(new GridLayout(1, 1));
        this.resultPanel.setOpaque(false);
        this.resultPanel.setPreferredSize(new Dimension(600,75));
        Font font = new Font("Times New Roman", Font.PLAIN, 20);

        this.resultLabel = new JLabel("Result :", JLabel.CENTER);
        this.resultLabel.setFont(font);
        this.resultLabel.setForeground(Color.WHITE);
        this.resultPanel.add(this.resultLabel);

        this.contentPanel.add(this.resultPanel);
    }

    public JTextField getFirstPolynomialTextField() {
        return firstPolynomialTextField;
    }

    public JTextField getSecondPolynomialTextField() {
        return secondPolynomialTextField;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }
}
