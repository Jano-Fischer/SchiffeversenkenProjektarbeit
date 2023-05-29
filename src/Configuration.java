public class Configuration {
    private int size = 80;
    private int totalNumber = 10;
    private int fiveBoats = 0;
    private int fourBoats = 0;
    private int threeBoats = 0;
    private int twoBoats = 0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getFiveBoats() {
        return fiveBoats;
    }

    public void setFiveBoats(int fiveBoats) {
        this.fiveBoats = fiveBoats;
    }

    public int getFourBoats() {
        return fourBoats;
    }

    public void setFourBoats(int fourBoats) {
        this.fourBoats = fourBoats;
    }

    public int getThreeBoats() {
        return threeBoats;
    }

    public void setThreeBoats(int threeBoats) {
        this.threeBoats = threeBoats;
    }

    public int getTwoBoats() {
        return twoBoats;
    }

    public void setTwoBoats(int twoBoats) {
        this.twoBoats = twoBoats;
    }


    public void preset(){
        switch (totalNumber){

            case 5:
                fiveBoats=0;fourBoats=0;threeBoats=2;twoBoats=3;
                break;
            case 6:
                fiveBoats=0;fourBoats=1;threeBoats=2;twoBoats=3;
                break;
            case 7:
                fiveBoats=1;fourBoats=1;threeBoats=2;twoBoats=3;
                break;
            case 8:
                fiveBoats=1;fourBoats=1;threeBoats=2;twoBoats=4;
                break;
            case 9:
                fiveBoats=1;fourBoats=1;threeBoats=3;twoBoats=4;
                break;
            case 10:
                fiveBoats=1;fourBoats=2;threeBoats=3;twoBoats=4;
                break;
           // default: fiveBoats=1;fourBoats=2;threeBoats=3;twoBoats=4;
        }

    }

    /*public int calcTotal(){
        totalNumber= fiveBoats+fourBoats+threeBoats+twoBoats;
        return totalNumber;
   }*/

}