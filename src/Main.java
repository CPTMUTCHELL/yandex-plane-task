import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        File input = new File("input.txt");
        File output = new File("output.txt");
        FileWriter writer = new FileWriter(output);
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
                if (rowNumber==size) br.readLine();
            }
            else{
                writer.write(plane.takeSeats(line));

            }
         }
        writer.close();

    }

}