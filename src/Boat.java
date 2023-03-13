public class Boat {
    public int lenght;
    public BoatType boatType;                                      //enum für art des Bootes(länge)
    public Boat(BoatType boatType){                                 //art der methode?
        this.boatType = boatType;
        this.lenght = boatType.getValue();
    }
    public BoatType getBoatType(){
        return boatType;
    }              //gibt die Art des Bootes aus (enum mit länge)
    public boolean isDestroyed(){                                   //schlechter Name!
        lenght--;                                                   //kürzt Boot um 1(für wenn getroffen), gibt aus ob Boot zerstört oder nicht (Boolean)
        if(lenght<=0){
            return true;
        } else {
            return false;
        }
    }
}
