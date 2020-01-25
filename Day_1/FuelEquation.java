import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function;

public class FuelEquation {

    private final String INPUT_FILE_PATH = "./input.txt";

    public static void main(String[] args) {
        FuelEquation fuelEquation = new FuelEquation();
        System.out.println("Fuel needed for trip: " + fuelEquation.computeFuelForTrip(computeFuel));
        System.out.println("Fuel needed for trip with improved equation: " + fuelEquation.computeFuelForTrip(computeFuelImproved));
    }

    private static Function<Integer, Integer> computeFuel = mass -> (int) Math.floor(mass / 3) - 2;
    private static Function<Integer, Integer> computeFuelImproved = mass -> {
        Integer fuelSum = 0;
        Integer fuel = computeFuel.apply(mass);
        while (fuel > 0) {
            fuelSum += fuel;
            fuel = computeFuel.apply(fuel);
        }
        return fuelSum;
    };

    private Integer computeFuelForTrip(Function<Integer, Integer> computeFunction) {
        Integer fuelSum = 0;
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Integer mass = Integer.parseInt(line);
                fuelSum += computeFunction.apply(mass);
            }
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
        return fuelSum;
    }
}