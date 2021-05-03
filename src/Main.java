import java.io.*;
import java.util.*;

class Cell  {
    private String letter;
    private boolean taken;
    private boolean reserved;
    private boolean aisle;
    private final int[] coords;

    public Cell(int x, int y) {
        this.coords = new int[]{x, y};
    }

    public String getStatus() {
        if (taken) return "#";
        if (aisle) return "_";
        if (reserved) return "X";
        return ".";

    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int[] getCoords() {
        return coords;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getLetter() {
        switch (coords[1]){
            case 0:
                letter= "A";
                break;
            case 1:
                letter= "B";
                break;
            case 2:
                letter= "C";
                break;
            case 4:
                letter= "D";
                break;
            case 5:
                letter= "E";
                break;
            case 6:
                letter= "F";
                break;
        }
        return letter;
    }

    public void setAisle(boolean aisle) {
        this.aisle = aisle;
    }



}

class Plane {
    private final Cell[][] plane;
    private final int rows;


    Plane(int rows) {
        this.rows = rows;
        plane = new Cell[rows][7];
    }

     void initPlaneRows(int rowNumber, String row) {
        String[] split = row.split("");
        for (int i = 0; i < row.length(); i++) {
            Cell cell = new Cell(rowNumber, i);
            if (split[i].equals("#")) cell.setTaken(true);
            else if (split[i].equals("_")) cell.setAisle(true);
            else cell.setTaken(false);
            plane[rowNumber][i] = cell;
        }
    }

    void showPlane() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(plane[i][j].getStatus());
            }
            System.out.println();
        }
    }

    void takeSeats(String group) {
        int passengersCounter=0;
        String[] s = group.split(" ");
        int numOfPassengers = Integer.parseInt(s[0]);
        List<Cell> seatsToTake=new ArrayList<>();
        String side = s[1];
        String criteria = s[2];
        int from = 0;
        int to = 0;
        int criteriaSeat = 0;
        if (side.equals("right")) {
            from = 4;
            to = 6;
            if (criteria.equals("aisle")) criteriaSeat = from;
            else criteriaSeat = to;
        }
        if (side.equals("left")) {
            from = 0;
            to = 2;
            if (criteria.equals("aisle")) criteriaSeat = to;
            else criteriaSeat = from;
        }
        for (int i = 0; i < rows; i++) {


            if (!plane[i][criteriaSeat].isTaken()) {

                seatsToTake.add(plane[i][criteriaSeat]);
                passengersCounter++;

                    if (criteriaSeat == from) {
                        for (int j = from + 1; j <= to; j++) {
                            if (passengersCounter<numOfPassengers) {
                                if (!plane[i][j].isTaken()) {
                                    seatsToTake.add(plane[i][j]);
                                    passengersCounter++;
                                }
                                else break;
                            }
                        }
                    }
                    else {
                        for (int j = to - 1; j >= from; j--) {
                            if (passengersCounter<numOfPassengers) {
                                if (!plane[i][j].isTaken()) {
                                    seatsToTake.add(plane[i][j]);
                                    passengersCounter++;
                                }
                            }
                            else break;
                        }
                    }



                if (seatsToTake.size()!=numOfPassengers){
                    passengersCounter=0;
                    seatsToTake.clear();
                }
                else {
                    break;
                }
            }

        }

          output(seatsToTake);


    }
    private void output(List<Cell> seatsToTake){
        String output;
        if (!seatsToTake.isEmpty()) {
            System.out.print("Passengers can take seats: ");
            String seatsOutput;
            seatsToTake.sort((o1, o2) -> o1.getLetter().compareTo(o2.getLetter()));
            for (int i1 = 0; i1 < seatsToTake.size(); i1++) {
                if (i1 == seatsToTake.size() - 1)
                    System.out.print(seatsToTake.get(i1).getCoords()[0] + 1 + "" + seatsToTake.get(i1).getLetter());
                else  System.out.print(seatsToTake.get(i1).getCoords()[0] + 1 + "" + seatsToTake.get(i1).getLetter() + " ");

            }
            System.out.println();
            seatsToTake.forEach(cell -> cell.setReserved(true));
            showPlane();
            seatsToTake.forEach(cell -> cell.setTaken(true));
            seatsToTake.clear();
        }
        else System.out.println("Cannot fulfill passengers requirements");
    }

}

public class Main {
    public static void main(String[] args) throws IOException {
        File input = new File("input.txt");
        File output = new File("output.txt");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        Plane plane = null;
        int rowNumber=0;
        int size = 0;
        int c=0;
        while ((line = br.readLine())!=null){
            if (c == 0) {
                c++;
                size=Integer.parseInt(line);
                plane= new Plane(size);
            }
            else if (c<size+1 && rowNumber<size){

                plane.initPlaneRows(rowNumber,line);
                rowNumber++;
                c++;
                if (rowNumber==size){
                    br.readLine();
                }

            }
            else {
               plane.takeSeats(line);
            }
         }

    }

}