import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FuelEquation {

    private final String INPUT_FILE_PATH = "./input.txt";

    public static void main(String[] args) {
        FuelEquation fuelEquation = new FuelEquation();
        System.out.println(fuelEquation.computeFuel());
    }

    private Integer computeFuel() {
        Integer fuelSum = 0;
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Integer mass = Integer.parseInt(line);
                fuelSum += (int) Math.floor(mass / 3) - 2;
            }
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
        return fuelSum;
    }
}