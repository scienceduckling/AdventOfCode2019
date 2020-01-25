import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IntersectionFinder {

    private final String INPUT_FILE_PATH = "./input.txt";
    private String[] cable1;
    private String[] cable2;
    private ArrayList<Point> cable1Points;
    private ArrayList<Point> cable2Points;

    public static void main(String[] args) {
        IntersectionFinder finder = new IntersectionFinder();
        Point intersection = finder.find();
        System.out.println("Closest intersection has distance: " + (Math.abs(intersection.x) + Math.abs(intersection.y)));
    }

    private Point find() {
        loadDescriptions();
        cable1Points = new ArrayList<Point>();
        cable2Points = new ArrayList<Point>();
        findTurningPoints(cable1, cable1Points);
        findTurningPoints(cable2, cable2Points);
        ArrayList<Point> intersections = findIntersection();
        return findClosestIntersection(intersections);
    }

    private void loadDescriptions() {
        try {
            Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));
            cable1 = scanner.nextLine().split(",");
            cable2 = scanner.nextLine().split(",");
        } catch(FileNotFoundException e) {
            System.out.println("File " + INPUT_FILE_PATH + " not found.");
        }
    }

    private void findTurningPoints(String[] cable, ArrayList<Point> cablePoints) {

        int currentX = 0;
        int currentY = 0;
        cablePoints.add(new Point(0, 0));

        for (String cablePath: cable) {
            String direction = cablePath.substring(0, 1);
            Integer len = Integer.parseInt(cablePath.substring(1));

            switch (direction) {
                case "R":
                    //System.out.println("R");
                    currentX += len;
                    break;
                case "U":
                    //System.out.println("U");
                    currentY += len;
                    break;
                case "L":
                    //System.out.println("L");
                    currentX -= len;
                    break;
                case "D":
                    //System.out.println("D");
                    currentY -= len;
                    break;
            }
            cablePoints.add(new Point(currentX, currentY));
        }
    }

    private ArrayList<Point> findIntersection() {
        ArrayList<Point> intersection = new ArrayList<>();

        for (int i = 0; i < cable1Points.size() - 2; i++) {
            Point cable1point1 = cable1Points.get(i);
            Point cable1point2 = cable1Points.get(i + 1);

            for (int j = 0; j < cable2Points.size() - 2; j++) {
                Point cable2point1 = cable2Points.get(j);
                Point cable2point2 = cable2Points.get(j + 1);

                //cable1 is horizontal and cable2 is vertical
                if (cable1point1.y == cable1point2.y && cable2point1.x == cable2point2.x) {
                    if (cable1point1.x < cable1point2.x && cable1point1.x <= cable2point1.x && cable1point2.x >= cable2point1.x) {
                        if (cable2point1.y < cable2point2.y && cable2point1.y <= cable1point1.y && cable2point2.y >= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersection.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        } else if (cable2point1.y > cable2point2.y && cable2point1.y >= cable1point1.y && cable2point2.y <= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersection.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        }
                    } else if (cable1point1.x > cable1point2.x && cable1point1.x >= cable2point1.x && cable1point2.x <= cable2point1.x) {
                        if (cable2point1.y < cable2point2.y && cable2point1.y <= cable1point1.y && cable2point2.y >= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersection.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        } else if (cable2point1.y > cable2point2.y && cable2point1.y >= cable1point1.y && cable2point2.y <= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersection.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        }
                    }
                //cable2 is horizontal and cable1 is vertical
                } else if (cable1point1.x == cable1point2.x && cable2point1.y == cable2point2.y) {
                    if (cable1point1.y < cable1point2.y && cable1point1.y <= cable2point1.y && cable1point2.y >= cable2point1.y) {
                        if (cable2point1.x < cable2point2.x && cable2point1.x <= cable1point1.x && cable2point2.x >= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersection.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        } else if (cable2point1.x > cable2point2.x && cable2point1.x >= cable1point1.x && cable2point2.x <= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersection.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        }
                    } else if (cable1point1.y > cable1point2.y && cable1point1.y >= cable2point1.y && cable1point2.y <= cable2point1.y) {
                        if (cable2point1.x < cable2point2.x && cable2point1.x <= cable1point1.x && cable2point2.x >= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersection.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        } else if (cable2point1.x > cable2point2.x && cable2point1.x >= cable1point1.x && cable2point2.x <= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersection.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        }
                    }
                }
            }
        }
        return intersection;
    }

    private Point findClosestIntersection(ArrayList<Point> intersections) {
        Point closest = intersections.get(0);
        int smallestDistance = Math.abs(closest.x) + Math.abs(closest.y);

        for (Point point: intersections) {
            int distance = Math.abs(point.x) + Math.abs(point.y);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closest = point;
            }
        }
        return closest;
    }

}
