import javax.swing.*;
import java.awt.*;

public class Menu extends JDialog {
    // Anfang Attribute

    private JTextField jNumberField1 = new JTextField();
    private JSpinner jSpinner1 = new JSpinner();
    private SpinnerNumberModel jSpinner1Model = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinner2 = new JSpinner();
    private SpinnerNumberModel jSpinner2Model = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinner3 = new JSpinner();
    private SpinnerNumberModel jSpinner3Model = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinner4 = new JSpinner();
    private SpinnerNumberModel jSpinner4Model = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinner5 = new JSpinner();
    private SpinnerNumberModel jSpinner5Model = new SpinnerNumberModel(0, 0, 10, 1);
    private JLabel jLabel1 = new JLabel();
    private JSpinner jSpinner6 = new JSpinner();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    // Ende Attribute

    public Menu(JFrame owner, boolean modal,Configuration config) {
        // Dialog-Initialisierung
        super(owner, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 735;
        int frameHeight = 783;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Menu");
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        jNumberField1.setBounds(72, 72, 113, 49);
        jNumberField1.setText(String.valueOf(config.getSize()));
        cp.add(jNumberField1);
        jSpinner1.setModel(jSpinner1Model);
        jSpinner1.setValue(config.getGesamtBoote());
        jSpinner1.setBounds(288, 64, 64, 80);
        cp.add(jSpinner1);
        jSpinner2.setBounds(288, 160, 64, 80);
        jSpinner2.setModel(jSpinner2Model);
        jSpinner2.setValue(config.getFiveBoats());
        cp.add(jSpinner2);
        jSpinner3.setBounds(288, 256, 64, 80);
        jSpinner3.setModel(jSpinner3Model);
        jSpinner3.setValue(config.getFourBoats());
        cp.add(jSpinner3);
        jSpinner4.setBounds(288, 352, 64, 80);
        jSpinner4.setModel(jSpinner4Model);
        jSpinner4.setValue(config.getThreeBoats());
        cp.add(jSpinner4);
        jSpinner5.setBounds(288, 448, 64, 80);
        jSpinner5.setModel(jSpinner5Model);
        jSpinner5.setValue(config.getTwoBoats());
        cp.add(jSpinner5);
        jLabel1.setBounds(280, 24, 73, 33);
        jLabel1.setText("Schiffarten auswaehlen, oder feste Antzahl benutzen ");
        cp.add(jSpinner6);
        jLabel2.setBounds(88, 24, 96, 41);
        jLabel2.setText("Spielfeldgroeße veraendern:");
        cp.add(jLabel2);
        jLabel4.setBounds(208, 472, 65, 41);
        jLabel4.setText("2er Boot:");
        cp.add(jLabel4);
        jLabel5.setBounds(208, 376, 65, 41);
        jLabel5.setText("3er Boot:");
        cp.add(jLabel5);
        jLabel6.setBounds(208, 280, 65, 41);
        jLabel6.setText("4er Boot:");
        cp.add(jLabel6);
        jLabel7.setBounds(216, 176, 65, 41);
        jLabel7.setText("5er Boot:");
        cp.add(jLabel7);
        jLabel8.setBounds(208, 80, 65, 41);
        jLabel8.setText("Gesamtanzahl:");
        cp.add(jLabel8);

        jSpinner1.addChangeListener(e -> {
            config.setGesamtBoote((int) jSpinner1.getValue());
        });
        jSpinner2.addChangeListener(e -> {
            config.setFiveBoats((int) jSpinner2.getValue());
        });
        jSpinner3.addChangeListener(e -> {
            config.setFourBoats((int) jSpinner3.getValue());
        });
        jSpinner4.addChangeListener(e -> {
            config.setThreeBoats((int) jSpinner4.getValue());
        });
        jSpinner5.addChangeListener(e -> {
            config.setTwoBoats((int) jSpinner5.getValue());
        });
        jNumberField1.addActionListener(e -> {
            config.setSize(Integer.parseInt(jNumberField1.getText()));
        });
        // Ende Komponenten

        setResizable(false);
        setVisible(true);
    } // end of public Menu

    // Anfang Methoden


    // Ende Methoden

} // end of class Menu
