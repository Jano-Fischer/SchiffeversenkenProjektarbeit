import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JDialog {
    // Anfang Attribute
    private JButton fertig = new JButton();
    private JTextField jNumberField1 = new JTextField();
    private JSpinner jSpinnerTotalBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerBoatTotal = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinnerFiveBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerBoat5 = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinnerFourBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerBoat4 = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinnerThreeBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerBoat3 = new SpinnerNumberModel(0, 0, 10, 1);
    private JSpinner jSpinnerTwoBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerBoat2 = new SpinnerNumberModel(0, 0, 10, 1);
    private JLabel jLabelText1 = new JLabel();
    private JSpinner jSpinner6 = new JSpinner();
    private JLabel jLabelPlayFieldSize = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel2Boat = new JLabel();
    private JLabel jLabel3Boat = new JLabel();
    private JLabel jLabel4Boat = new JLabel();
    private JLabel jLabel5Boat = new JLabel();
    private JLabel jLabelTotalShips = new JLabel();
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
        jSpinnerTotalBoats.setModel(jSpinnerBoatTotal);
        jSpinnerTotalBoats.setValue(config.getTotalNumber());
        jSpinnerTotalBoats.setBounds(288, 64, 64, 80);
        cp.add(jSpinnerTotalBoats);
        jSpinnerFiveBoats.setBounds(288, 160, 64, 80);
        jSpinnerFiveBoats.setModel(jSpinnerBoat5);
        jSpinnerFiveBoats.setValue(config.getFiveBoats());
        cp.add(jSpinnerFiveBoats);
        jSpinnerFourBoats.setBounds(288, 256, 64, 80);
        jSpinnerFourBoats.setModel(jSpinnerBoat4);
        jSpinnerFourBoats.setValue(config.getFourBoats());
        cp.add(jSpinnerFourBoats);
        jSpinnerThreeBoats.setBounds(288, 352, 64, 80);
        jSpinnerThreeBoats.setModel(jSpinnerBoat3);
        jSpinnerThreeBoats.setValue(config.getThreeBoats());
        cp.add(jSpinnerThreeBoats);
        jSpinnerTwoBoats.setBounds(288, 448, 64, 80);
        jSpinnerTwoBoats.setModel(jSpinnerBoat2);
        jSpinnerTwoBoats.setValue(config.getTwoBoats());
        cp.add(jSpinnerTwoBoats);
        jLabelText1.setBounds(280, 24, 73, 33);
        jLabelText1.setText("Schiffarten auswaehlen, oder feste Antzahl benutzen ");
        cp.add(jSpinner6);
        jLabelPlayFieldSize.setBounds(88, 24, 200, 41);
        jLabelPlayFieldSize.setText("Spielfeldgroeße veraendern:");
        cp.add(jLabelPlayFieldSize);
        jLabel2Boat.setBounds(208, 472, 65, 41);
        jLabel2Boat.setText("2er Boot:");
        cp.add(jLabel2Boat);
        jLabel3Boat.setBounds(208, 376, 65, 41);
        jLabel3Boat.setText("3er Boot:");
        cp.add(jLabel3Boat);
        jLabel4Boat.setBounds(208, 280, 65, 41);
        jLabel4Boat.setText("4er Boot:");
        cp.add(jLabel4Boat);
        jLabel5Boat.setBounds(216, 176, 65, 41);
        jLabel5Boat.setText("5er Boot:");
        cp.add(jLabel5Boat);
        jLabelTotalShips.setBounds(208, 80, 150, 41);
        jLabelTotalShips.setText("Gesamtanzahl:");
        cp.add(jLabelTotalShips);

        fertig.setBounds(400,75,100,50);
        fertig.addActionListener((e ->
        {
                this.dispose();
        }));
        cp.add(fertig);
        fertig.setText("Fertig");
        fertig.setVisible(true);

        jSpinnerTotalBoats.addChangeListener(e -> {
            config.setTotalNumber((int) jSpinnerTotalBoats.getValue());
            config.preset();
            update(config);
        });
        jSpinnerFiveBoats.addChangeListener(e -> {
            config.setFiveBoats((int) jSpinnerFiveBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
        });
        jSpinnerFourBoats.addChangeListener(e -> {
            config.setFourBoats((int) jSpinnerFourBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
        });
        jSpinnerThreeBoats.addChangeListener(e -> {
            config.setThreeBoats((int) jSpinnerThreeBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
        });
        jSpinnerTwoBoats.addChangeListener(e -> {
            config.setTwoBoats((int) jSpinnerTwoBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
        });
        jNumberField1.addActionListener(e -> {
            config.setSize(Integer.parseInt(jNumberField1.getText()));
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
        });


        // Ende Komponenten
        setResizable(false);
        setVisible(true);
    } // end of public Menu

    // Anfang Methoden
    public void update(Configuration config){
        jSpinnerFiveBoats.setValue(config.getFiveBoats());
        jSpinnerFourBoats.setValue(config.getFourBoats());
        jSpinnerThreeBoats.setValue(config.getThreeBoats());
        jSpinnerTwoBoats.setValue(config.getTwoBoats());
        jSpinnerTotalBoats.setValue(config.calcTotal());
    }

    // Ende Methoden

} // end of class Menu
