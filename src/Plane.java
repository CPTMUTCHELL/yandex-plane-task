import java.util.ArrayList;
import java.util.List;

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

    String showPlane() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 7; j++) {
                sb.append(plane[i][j].getStatus());
            }
            if (i != rows - 1) sb.append("\n");
        }
        return sb.toString();
    }

    String takeSeats(String group) {
        int passengersCounter = 0;
        String[] s = group.split(" ");
        int numOfPassengers = Integer.parseInt(s[0]);
        List<Cell> seatsToTake = new ArrayList<>();
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
                        if (passengersCounter < numOfPassengers) {
                            if (!plane[i][j].isTaken()) {
                                seatsToTake.add(plane[i][j]);
                                passengersCounter++;
                            } else break;
                        }
                    }
                } else {
                    for (int j = to - 1; j >= from; j--) {
                        if (passengersCounter < numOfPassengers) {
                            if (!plane[i][j].isTaken()) {
                                seatsToTake.add(plane[i][j]);
                                passengersCounter++;
                            }
                        } else break;
                    }
                }
                if (seatsToTake.size() != numOfPassengers) {
                    passengersCounter = 0;
                    seatsToTake.clear();
                } else break;

            }

        }

        return output(seatsToTake);

    }

    private String output(List<Cell> seatsToTake) {
        StringBuilder sb = new StringBuilder();
        if (!seatsToTake.isEmpty()) {
            sb.append("Passengers can take seats: ");
            String seatsOutput;
            seatsToTake.sort((o1, o2) -> o1.getLetter().compareTo(o2.getLetter()));
            for (int i1 = 0; i1 < seatsToTake.size(); i1++) {
                if (i1 == seatsToTake.size() - 1)
                    sb.append(seatsToTake.get(i1).getCoords()[0] + 1).append(seatsToTake.get(i1).getLetter());
                else
                    sb.append(seatsToTake.get(i1).getCoords()[0] + 1).append(seatsToTake.get(i1).getLetter()).append(" ");
            }

            seatsToTake.forEach(cell -> cell.setReserved(true));
            sb.append("\n").append(showPlane()).append("\n");
            seatsToTake.forEach(cell -> cell.setTaken(true));
            seatsToTake.clear();

        }
        else sb.append("Cannot fulfill passengers requirements" + "\n");
        return sb.toString();
    }

}