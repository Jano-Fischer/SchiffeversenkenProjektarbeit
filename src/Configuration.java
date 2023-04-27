import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
public class Configuration {
  // Anfang Attribute
  private Menu menu;
  private int size = 50;
  // Ende Attribute
  
    public Configuration(){
  
    }                       
  // Anfang Methoden
  public int getSize() {
    return size;
  }

  public void setSize(int sizeNeu) {
    size = sizeNeu;
  }

  // Ende Methoden
  }