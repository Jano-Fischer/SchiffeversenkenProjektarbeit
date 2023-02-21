public class Steuerung {
    private final Gui gui;
    private BoatThings theBoatThing;
    private boolean lock = false;
    private boolean player1 = true;
    private int fieldHight = 50;
    private int fieldWidth = 50;
    private final int boatNummber = 1;
    private int placedShips = 1;
    private int player1DestroyedBoats = 0;
    private int player2DestroyedBoats = 0;
    public Gui getGui() {
        return gui;
    }
    private int shipX;
    private int shipY;
    public int getShipY() {
        return shipY;
    }
    public int getShipX() {
        return shipX;
    }
    public void setShipX(int shipX) {
        this.shipX = shipX;
    }
    public void setShipY(int shipY) {
        this.shipY = shipY;
    }
    public Steuerung(Gui gui) {
        this.gui = gui;
        theBoatThing = new BoatThings(this);
    }
    public int getBoatNummber() {
        return boatNummber;
    }
    public int getFieldHeight() {
        return fieldHight;
    }
    public int getFieldWidth() {
        return fieldWidth;
    }
    public void pregame(boolean spieler1) {
        boolean schiffeSetzten = false;

    }
    public void click(int x, int y) {
        if (lock) return;
        System.out.println("Feld geklickt: " + x + ":" + y + " Spieler:" + ((player1) ? 1 : 2));     //Ausgabe zur Kontrolle des geklickten feldes mit Koordinaten und Spieler
        boolean hit = gui.attack(x, y, player1);
        if (hit) {
            System.out.println("Treffer bei:"+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            boolean destroyed = gui.hitAtIsDestroyed(x, y, player1);
            if (destroyed) {
                winChecker();
            }
        } else {
            System.out.println("Nicht getroffen bei: "+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            gui.missAt(x, y, player1);
            lock = true;
            System.out.println("Nächster Spieler");
        }
    }
    public void goOne() {

        player1 = !player1;
        gui.showPlayerField(player1);
        gui.setActivePlayerText("Spieler " + ((player1) ? 1 : 2));
        lock = false;
    }   //Wechseln des Spielfeldes und entsperren des Schiessens
    public void winChecker() {
        if (player1) {
            player2DestroyedBoats++;
            if (player2DestroyedBoats == boatNummber) {
                System.out.println("Spieler 1 Gewonnen");
                gui.setActivePlayerText("Spieler 1 hat gewonnen");
                lock = true;
            }
        } else {
            player1DestroyedBoats++;
            if (player1DestroyedBoats == boatNummber) {
                System.out.println("Spieler 2 Gewonnen");
                gui.setActivePlayerText("Spieler 2 hat gewonnen");
                lock = true;

            }
        }
    }   //Methode zur erkennung welcher Spieler gewonnen hat
}
