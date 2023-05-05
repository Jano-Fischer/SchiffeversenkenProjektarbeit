
public class Feld {
    private int x;
    private Boat[] boat = new Boat[2];
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

    public void setBoat(Boat boat, boolean valid) {
        if (status=='p') {
           this.boat[1] = boat;
           status = '2';
        }else {
            if (valid) {
                this.boat[0] = boat;
                status = 'p';
            }else{
                this.boat[0] = boat;
                status = 'i';
            }
        }
    }
    public void removeBoat(){
        if (status == '2') {
           status='p';
           this.boat[1] = null;
        }else {
            this.boat[0] = null;
            status = 'w';
        }
    }

    public Boat getBoat() {
        if (status == '2') {
            return boat[1];
        }else{
            return boat[0];
        }
    }

}