import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

enum Opcode {
    NON  (0),
    ADD  (1),
    MUL  (2),
    HALT (99)
    ;

    private final int opcode;

    private static final Map<Integer, Opcode> lookup = new HashMap<Integer, Opcode>();

    static {
        for (Opcode d : Opcode.values()) {
            lookup.put(d.getAbbreviation(), d);
        }
    }

    private Opcode(int opcode) {
        this.opcode = opcode;
    }

    public int getAbbreviation() {
        return opcode;
    }

    public static Opcode get(int opcode) {
        if (lookup.get(opcode) != null) {
            return lookup.get(opcode);
        } else {
            return Opcode.NON;
        }
    }
}

public class IntcodeInterpreter {

    private final String INPUT_FILE_PATH = "./input.txt";
    private final Integer DESIRED_OUTPUT = 19690720;
    private ArrayList<Integer> theTape;
    private Integer headPosition;
    private Opcode currentInstruction;

    public static void main(String[] args) {
        IntcodeInterpreter interpreter = new IntcodeInterpreter();
        interpreter.initMemory();
        interpreter.setInputs(12, 2);
        interpreter.run();
        System.out.println("Result for inputs 12 and 2 is: " + interpreter.getResult());

        interpreter.findInputs();
    }

    private void initMemory() {
        theTape = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] instructions = line.split(",");
                for (String instruction: instructions) {
                    theTape.add(Integer.parseInt(instruction));
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
    }

    private void setInputs(Integer input1, Integer input2) {
        theTape.set(1, input1);
        theTape.set(2, input2);
    }

    private void run() {
        headPosition = 0;
        currentInstruction = Opcode.get(theTape.get(headPosition));

        while (currentInstruction != Opcode.HALT) {
            Integer firstOp = theTape.get(theTape.get(headPosition + 1));
            Integer secondOp = theTape.get(theTape.get(headPosition + 2));
            Integer outputPosition = theTape.get(headPosition + 3);

            switch (currentInstruction) {
                case ADD:
                    theTape.set(outputPosition, firstOp + secondOp);
                    break;
                case MUL:
                    theTape.set(outputPosition, firstOp * secondOp);
                    break;
            }

            headPosition += 4;
            currentInstruction = Opcode.get(theTape.get(headPosition));
            if (currentInstruction == Opcode.NON) {
                System.out.println("Program got into undefined state. Halting");
                break;
            }
        }
    }

    private void findInputs() {
        search:
        for (Integer input1 = 0; input1 < 100; input1++) {
            for (Integer input2 = 0; input2 < 100; input2++) {
                initMemory();
                setInputs(input1, input2);
                run();
                if (getResult().equals(DESIRED_OUTPUT)) {
                    System.out.println("Correct inputs for result " + DESIRED_OUTPUT + " are: " + input1 + " and " + input2);
                    break search;
                }
            }
        }
    }

    private void printTape() {
        System.out.println(Arrays.toString(theTape.toArray()));
    }

    private Integer getResult() {
        return theTape.get(0);
    }
}
