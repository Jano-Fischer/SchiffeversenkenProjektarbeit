public enum BoatType {
    ONEBOAT (1),
    TWOBOAT (2),
    THREEBOAT (3),
    FOURBOAT (4),
    FIVEBOOAT (5);


    private int value;
   private BoatType(int value){
       this.value = value;
   }
    public int getValue(){
        return value;
    }

}
