import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FuelEquation {

    private final String INPUT_FILE_PATH = "./input.txt";

    public static void main(String[] args) {
        FuelEquation fuelEquation = new FuelEquation();
        System.out.println("Fuel needed for trip: " + fuelEquation.computeFuelForTrip());
        System.out.println("Fuel needed for trip with improved equation: " + fuelEquation.computeFuelForTripImproved());
    }

    private Integer computeFuelForTrip() {
        Integer fuelSum = 0;
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Integer mass = Integer.parseInt(line);
                fuelSum += computeFuelForMass(mass);
            }
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
        return fuelSum;
    }

    private Integer computeFuelForMass(Integer mass) {
        return (int) Math.floor(mass / 3) - 2;
    }

    private Integer computeFuelForTripImproved() {
        Integer fuelSum = 0;
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Integer mass = Integer.parseInt(line);
                Integer fuel = computeFuelForMass(mass);
                while (fuel > 0) {
                    fuelSum += fuel;
                    fuel = computeFuelForMass(fuel);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
        return fuelSum;
    }
}