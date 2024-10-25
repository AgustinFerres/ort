import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        int[][] m = generateMatrix(3, 6);
//
//        System.out.println(Arrays.deepToString(m));
//
//        int rowToRemove = 2;
//        int colToRemove = 2;
//        System.out.println("Removing " + rowToRemove + " row and " + colToRemove + " col");
//
//        int[][] newM = removeMatrixRowAndCol(m, rowToRemove, colToRemove);
//
//        System.out.println(Arrays.deepToString(newM));

//        int[][] triMatrix = generateTriMatrix(5);
//        printMatrix(triMatrix);
//
//        System.out.println(isTridiagonal(triMatrix));

//        int[][] m = generateRandomMatrixWithoutDiagonal(5, new Random(1));
//
//        printMatrix(m);
//
//        int from = 3;
//        int to = 4;
//
//        System.out.println("Cheapest fly from " + from + " to " + to + " is " + calculateCheapestFly(m, from, to));

//        int[][] m = generateRandomMatrix(5, new Random(1));
//
//        printMatrix(m);
//
//        int[][] newM = detectZeros(m);
//
//        printMatrix(newM);

//        int[][] m = generateMatrix(10, 10);
//
//        printMatrix(m);
//
//        int value = 32;
//
//        int[][] newM = addValue(m, value);
//
//        printMatrix(newM);

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        String word = scanner.next();
        scanner.close();

        int min = n;
        int max = n;

        int vCount = 0;

        for (int i = 0; i < n; i++) {
            if (word.charAt(i) == 'w') {
                max++;
            } else if (word.charAt(i) == 'v') {
                if (vCount >= 1) {
                    min--;
                    vCount = 0;
                } else {
                    vCount++;
                }
            }
        }

        System.out.println(min + " " + max);
    }

    private static int [][] generateMatrix (int rows, int cols) {
        int[][] m = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = i * cols + j + 1;
            }
        }

        return m;
    }

    private static int[][] generateTriMatrix(int n) {
        int[][] m = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.abs(i - j) <= 1) {
                    m[i][j] = 1;
                } else {
                    m[i][j] = 0;
                }
            }
        }

        return m;
    }

    private static int[][] removeMatrixRowAndCol (int[][] m, int row, int col) {
        int[][] newM = new int[m.length - 1][m[0].length - 1];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (i == row || j == col) continue;

                int newI = i > row ? i - 1 : i;
                int newJ = j > col ? j - 1 : j;

                newM[newI][newJ] = m[i][j];
            }
        }

        return newM;
    }

    private static boolean isTridiagonal (int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (i == j) {
                    boolean hasJMinusOne = j - 1 >= 0;
                    boolean hasJPlusOne = j + 1 < m[0].length;

                    if (hasJMinusOne) {
                        if (m[i][j - 1] <= 0) {
                            return false;
                        }
                    }

                    if (hasJPlusOne) {
                        if (m[i][j + 1] <= 0) {
                            return false;
                        }
                    }

                    if (m[i][j] <= 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static int calculateCheapestFly (int[][] m, int from, int to) {
        int cheapest = m[from][to];
        List<Integer> path = new ArrayList<>(List.of(from, to));

        for (int j = 0; j < m[0].length; j++) {

            int current = m[from][j] + m[j][to];
            if (current < cheapest) {
                cheapest = current;
                path = new ArrayList<>(List.of(from, j, to));
            }
        }

        System.out.println("Cheapest path: " + path);
        return cheapest;
    }

    private static int[][] generateRandomMatrixWithoutDiagonal  (int n, Random random) {
        int[][] m = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    m[i][j] = 0;
                    continue;
                }

                m[i][j] = (int) (random.nextDouble() * 90) + 10;
            }
        }

        return m;
    }

    private static int[][] generateRandomMatrix (int n, Random random) {
        int[][] m = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = (int) (random.nextDouble() * 100);
            }
        }

        return m;
    }

    private static int[][] detectZeros (int[][] m) {
        int[][] zeros = m.clone();
        List<Integer[]> zerosPositions = new ArrayList<>();


        for (int i = 0; i < m.length ; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == 0) {
                    zerosPositions.add(new Integer[]{i, j});
                }
            }
        }

        for (Integer[] pos : zerosPositions) {
            int row = pos[0];
            int col = pos[1];

            for (int i = 0; i < zeros.length; i++) {
                zeros[i][col] = 0;
            }

            for (int j = 0; j < zeros[0].length; j++) {
                zeros[row][j] = 0;
            }
        }

        return zeros;
    }

    private static int[][] addValue(int[][] m, int value) {
        int[][] newM = m.clone();

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] > value) {
                    int aux = m[i][j];
                    newM[i][j] = value;
                    value = aux;
                }

            }
        }

        return newM;
    }

    private static void printMatrix(int[][] matrix) {
        int maxElementLength = Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .mapToObj(String::valueOf)
                .mapToInt(String::length)
                .max()
                .orElse(1);

        String format = "%" + (maxElementLength + 1) + "d";

        System.out.println("+" + "-".repeat((maxElementLength + 1) * matrix[0].length + 1) + "+");

        for (int[] row : matrix) {
            System.out.print("|");
            for (int element : row) {
                System.out.printf(format, element);
            }
            System.out.println(" |");
        }

        System.out.println("+" + "-".repeat((maxElementLength + 1) * matrix[0].length + 1) + "+");
    }
}