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
    private ArrayList<Point> intersections;

    public static void main(String[] args) {
        IntersectionFinder finder = new IntersectionFinder();
        finder.computeIntersections();
        int distance = finder.closestIntersectionManhattan();
        System.out.println("Closest intersection by distance: " +  distance);
        int delay = finder.closestIntersectionDelay();
        System.out.println("Closest intersection by delay: " + delay);
    }

    private void computeIntersections() {
        loadDescriptions();
        cable1Points = new ArrayList<>();
        cable2Points = new ArrayList<>();
        findTurningPoints(cable1, cable1Points);
        findTurningPoints(cable2, cable2Points);
        intersections = new ArrayList<>();
        findIntersections();
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
                    currentX += len;
                    break;
                case "U":
                    currentY += len;
                    break;
                case "L":
                    currentX -= len;
                    break;
                case "D":
                    currentY -= len;
                    break;
            }
            cablePoints.add(new Point(currentX, currentY));
        }
    }

    private void findIntersections() {

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
                                intersections.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        } else if (cable2point1.y > cable2point2.y && cable2point1.y >= cable1point1.y && cable2point2.y <= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersections.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        }
                    } else if (cable1point1.x > cable1point2.x && cable1point1.x >= cable2point1.x && cable1point2.x <= cable2point1.x) {
                        if (cable2point1.y < cable2point2.y && cable2point1.y <= cable1point1.y && cable2point2.y >= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersections.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        } else if (cable2point1.y > cable2point2.y && cable2point1.y >= cable1point1.y && cable2point2.y <= cable1point1.y) {
                            if (cable2point1.x != 0 || cable1point1.y != 0) {
                                intersections.add(new Point(cable2point1.x, cable1point1.y));
                            }
                        }
                    }
                //cable2 is horizontal and cable1 is vertical
                } else if (cable1point1.x == cable1point2.x && cable2point1.y == cable2point2.y) {
                    if (cable1point1.y < cable1point2.y && cable1point1.y <= cable2point1.y && cable1point2.y >= cable2point1.y) {
                        if (cable2point1.x < cable2point2.x && cable2point1.x <= cable1point1.x && cable2point2.x >= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersections.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        } else if (cable2point1.x > cable2point2.x && cable2point1.x >= cable1point1.x && cable2point2.x <= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersections.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        }
                    } else if (cable1point1.y > cable1point2.y && cable1point1.y >= cable2point1.y && cable1point2.y <= cable2point1.y) {
                        if (cable2point1.x < cable2point2.x && cable2point1.x <= cable1point1.x && cable2point2.x >= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersections.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        } else if (cable2point1.x > cable2point2.x && cable2point1.x >= cable1point1.x && cable2point2.x <= cable1point1.x) {
                            if (cable1point1.x != 0 || cable2point1.y != 0) {
                                intersections.add(new Point(cable1point1.x, cable2point1.y));
                            }
                        }
                    }
                }
            }
        }
    }

    private int closestIntersectionManhattan() {
        Point closest = intersections.get(0);
        int smallestDistance = Math.abs(closest.x) + Math.abs(closest.y);

        for (Point point: intersections) {
            int distance = Math.abs(point.x) + Math.abs(point.y);
            if (distance < smallestDistance) {
                smallestDistance = distance;
            }
        }
        return smallestDistance;
    }

    private int closestIntersectionDelay() {

        int smallestDelay = computeDelayToIntersection(intersections.get(0));

        for (Point point: intersections) {
            int delay = computeDelayToIntersection(point);
            if (delay < smallestDelay) {
                smallestDelay = delay;
            }
        }
        return smallestDelay;
    }

    private int computeDelayToIntersection(Point point) {
        return computeDelayForOneCable(point, cable1) + computeDelayForOneCable(point, cable2);
    }

    private int computeDelayForOneCable(Point point, String[] cable) {
        int cableDelay = 0;
        int currentX = 0;
        int currentY = 0;
        int index = 0;
        boolean found = false;
        while (!found) {
            String cablePath = cable[index];
            String direction = cablePath.substring(0, 1);
            Integer len = Integer.parseInt(cablePath.substring(1));

            switch (direction) {
                case "R":
                    if (currentY == point.y && point.x > currentX && Math.abs(point.x - currentX) <= len) {
                        found = true;
                        cableDelay += Math.abs(point.x - currentX);
                    } else {
                        currentX += len;
                        cableDelay += len;
                    }
                    break;
                case "U":
                    if (currentX == point.x && point.y > currentY && Math.abs(point.y - currentY) <= len) {
                        found = true;
                        cableDelay += Math.abs(point.y - currentY);
                    } else {
                        currentY += len;
                        cableDelay += len;
                    }
                    break;
                case "L":
                    if (currentY == point.y && point.x < currentX && Math.abs(point.x - currentX ) <= len) {
                        found = true;
                        cableDelay += Math.abs(point.x - currentX);
                    } else {
                        currentX -= len;
                        cableDelay += len;
                    }
                    break;
                case "D":
                    if (currentX == point.x && point.y < currentY && Math.abs(point.y - currentY) <= len) {
                        found = true;
                        cableDelay += Math.abs(point.y - currentY);
                    } else {
                        currentY -= len;
                        cableDelay += len;
                    }
                    break;
            }
            index++;
        }
        return cableDelay;
    }
}
