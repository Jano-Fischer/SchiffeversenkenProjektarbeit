
public class Feld {
    private int x;
    private Boat boat;
    private int y;
    private char status;

    /**
     * Status des Feldes setzten
     * @param x Koordinate
     * @param y Koordinate
     * @param status Getroffen, Wasser, Nicht Getroffen...
     */
    public Feld(int x, int y, char status) {        //Übergabe der Werte für x&y-koordinate und des status(freies feld, boot platziert, fehlschuss oder getroffenes Boot)
        this.x=x;                                   //der Konstruktor wird in initilize der GUI aufgerufen, dort wird für jedes Feld der Status water gesetzt
        this.y=y;
        this.status =status;
    }

    public int getXFeldNumber() {
        return x;
    }
    public int getYFeldNumber() {
        return y;
    }
    public char getStatus() {
        return status;
    }

    public void setStatus(char c) {
        this.status = c;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
        status = 'p';
    }

    public void removeBoat(){
        this.boat =null;
        status='w';
    }

    public Boat getBoat() {
            return boat;
    }

}