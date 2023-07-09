import javax.swing.*;
import java.awt.*;

public class Menu extends JDialog {
    // Anfang Attribute
    private JButton fertig = new JButton();
    private JSpinner jSpinnerTotalBoats = new JSpinner();
    private SpinnerNumberModel jSpinnerPreset = new SpinnerNumberModel(10, 5, 10, 1);
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
    private String valueSize = "Klein";
    private String[] playerfieldsizes = {"Klein", "Mittel", "Groß"};
    private JComboBox sizeselecten = new JComboBox(playerfieldsizes);
    public String getValueSize() {
        return valueSize;
    }
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
        jSpinnerTotalBoats.setModel(jSpinnerPreset);
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
        jLabelText1.setText("Schiff arten auswählen, oder feste Anzahl benutzen ");
        cp.add(jSpinner6);
        sizeselecten.setBounds(72, 70, 113, 50);
        sizeselecten.setBackground(Color.WHITE);
        cp.add(sizeselecten);
        jLabelPlayFieldSize.setBounds(70, 24, 200, 40);
        jLabelPlayFieldSize.setText("Spielfeldgröße verändern:");
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
        jLabelTotalShips.setText("Voreingestellte Größen:");
        cp.add(jLabelTotalShips);

        fertig.setBounds(400,75,100,50);
        fertig.addActionListener(e -> {
            dispose();
           // strg.generateShipsToPlace();
        });
        cp.add(fertig);
        fertig.setText("Fertig");
        fertig.setVisible(true);
        fertig.setToolTipText("Bitte Mindestens ein Schiff auswählen");
        config.setSize(30); //Dient als Default Wert, falls nichts ausgewählt wird
        sizeselecten.addActionListener(e -> {
            valueSize = (String) sizeselecten.getSelectedItem();
            switch (valueSize) {
                case "Klein": {
                    config.setSize(30);
                    break;
                }
                case "Mittel": {
                    config.setSize(40);
                    break;
                }
                case "Groß": {
                    config.setSize(50);
                    break;
                }
            }
        });
        jSpinnerTotalBoats.addChangeListener(e -> {
            config.setTotalNumber((int) jSpinnerTotalBoats.getValue());
            config.preset();
            update(config);
        });
        jSpinnerFiveBoats.addChangeListener(e -> {
            config.setFiveBoats((int) jSpinnerFiveBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
            isNotNullandMax(config);
        });
        jSpinnerFourBoats.addChangeListener(e -> {
            config.setFourBoats((int) jSpinnerFourBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
            isNotNullandMax(config);
        });
        jSpinnerThreeBoats.addChangeListener(e -> {
            config.setThreeBoats((int) jSpinnerThreeBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
            isNotNullandMax(config);
        });
        jSpinnerTwoBoats.addChangeListener(e -> {
            config.setTwoBoats((int) jSpinnerTwoBoats.getValue());
            jSpinnerTotalBoats.setValue(config.getTotalNumber());
            update(config);
            isNotNullandMax(config);
        });
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
    } // end of public Menu


    /**
     * Aktualisiert die Werte auf den Anzeigefeldern
     */
    public void update(Configuration config){
        jSpinnerFiveBoats.setValue(config.getFiveBoats());
        jSpinnerFourBoats.setValue(config.getFourBoats());
        jSpinnerThreeBoats.setValue(config.getThreeBoats());
        jSpinnerTwoBoats.setValue(config.getTwoBoats());
        //jSpinnerTotalBoats.setValue(config.calcTotal());
    }
    /**
     * Schaut das mindestens ein Schiff ausgewählt wurde
     */
    public void isNotNullandMax(Configuration config){
        if(config.getFiveBoats() == 0 && config.getFourBoats() == 0 && config.getThreeBoats() == 0 && config.getTwoBoats() == 0){
            fertig.setEnabled(false);
            return;
        } else if (config.getFiveBoats()+config.getFourBoats()+config.getThreeBoats()+config.getTwoBoats() >=12) {
            fertig.setEnabled(false);
            return;
        }
        fertig.setEnabled(true);
    }
    // Ende Methoden
} // end of class Menu