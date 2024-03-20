package utils;

import Exceptions.InvalidArraySizeException;
import Exceptions.NullMatrixException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import static java.util.Objects.isNull;

public class Matrix {
    public static double[][] setMatrixManually() {
        //double nullMatrix[][] = null;
        Scanner input = new Scanner(System.in);

        int n;
        double[][] matrix;

        System.out.println("Введите размер матрицы: ");
        try {
            n = input.nextInt();
            if (n <= 0) throw new InvalidArraySizeException("Введенная строка не может быть размером матрицы! Требуется целое положительное число.");
        } catch (InputMismatchException e) {
            System.out.println("Введенная строка не может быть размером матрицы! Требуется число.");
            input.close();
            return null;
        } catch (NoSuchElementException | IllegalStateException | InvalidArraySizeException e) {
            System.out.println(e.getMessage());
            input.close();
            return null;
        }

        try {
            matrix = new double[n][n];
        } catch (NegativeArraySizeException e) {
            System.out.println(e.getMessage());
            input.close();
            return null;
        }

        System.out.println("Введите исходную матрицу: ");
        for (int i = 0; i < n; i++)
        {
            System.out.println("Строка "+ (i + 1) +" (" + n + " чисел)");
            for (int j = 0; j < n; j++) {
                try {
                    matrix[i][j] = input.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Введенная строка не является числом!");
                    input.close();
                    return null;
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.println(e.getMessage());
                    input.close();
                    return null;
                }
            }
        }

        System.out.println("Конец ввода матрицы.\n");
        return matrix;
    }

    public static double[] setMatrixExtensionManually(double[][] matrix) {
        if (isNull(matrix)) {
            System.out.println("Матрица не была задана!");
            System.exit(1);
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Введите дополнение: ");
        double[] matrixExtension = new double[matrix.length];
        for (int i = 0; i < matrixExtension.length; i++) {
            try {
                double n = input.nextDouble();
                matrixExtension[i] = n;
            } catch (InputMismatchException e) {
                System.out.println("Введенная строка не является числом!");
                input.close();
                return null;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println(e.getMessage());
                input.close();
                return null;
            }
        }
        input.close();
        System.out.println("Конец ввода матричного дополнения.\n");
        return matrixExtension;
    }

    public static Result setViaFile() {
        Scanner input = new Scanner(System.in);
        String pathExample = "C:\\Users\\danis\\OneDrive\\Рабочий стол\\untitled\\src\\main\\resources\\file.txt";
        System.out.println("Введите путь к файлу в формате\n" + pathExample);
        String path = input.nextLine();
        try(FileInputStream reader = new FileInputStream(path)) {

            String everything = IOUtils.toString(reader);
            String[] modded = everything.split("\n");
            int size = modded.length;
            String[][] modded2 = new String[size][0];

            double[][] matrix = new double[size][size];
            double[] matrixExtension = new double[size];

            for(int i = 0; i < size; i++) {
                modded2[i] = modded[i].trim().split(" ");
            }
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    matrix[i][j] = Double.parseDouble(modded2[i][j]);
                }
                matrixExtension[i] = Double.parseDouble(modded2[i][size]);
            }
            reader.close();
            return new Result(matrix, matrixExtension);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new Result(null, null);
        }
    }

    public static void printMatrix(double[][] matrixA, double[] matrixB) {

        if (isNull(matrixA) && isNull(matrixB)) {
            System.out.println("Матрица и матричное дополнение не были заданы!");
            System.exit(1);
        } else if (isNull(matrixA)) {
            System.out.println("Матрица не была задана!");
            System.exit(1);
        } else if (isNull(matrixB)) {
            System.out.println("Матричное дополнение не было задано!");
            System.exit(1);
        }

        System.out.println("Расширенная матрица:");
        for (int i = 0; i < matrixA.length; i++) {
            for (double j : matrixA[i]){
                System.out.printf("%7.2f", j);
            }
            System.out.println(" | " + String.format("%7.2f", matrixB[i]));
        }
        System.out.println();

    }

    public static void matrixToTriangle(double[][] matrix, double[] matrixExtension) {

        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] /= matrix[0][0];
        }
        matrixExtension[0] /= matrix[0][0];

        printMatrix(matrix, matrixExtension);

        for (double[] row : matrix) {

        }
    }

/*    public static boolean isZeroDeterminant(double[][] matrix) {
        return (isNoZeroRows(matrix) && isNoZeroColumns(matrix));
    }*/

    public static boolean isNoZeroColumns(double[][] matrix) {
        int MATRIX_SIZE = matrix.length;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            boolean flag = false;
            for (double[] row : matrix) {
                if (row[i] != 0) {flag = true; break;}
            }
            if (flag) {continue;}
            return false;
        }
        return true;
    }

    public static boolean isNoZeroRows(double[][] matrix) {
        int MATRIX_SIZE = matrix.length;
        for (double[] row : matrix) {
            boolean flag = false;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                if (row[i] != 0) {flag = true; break;}
            }
            if (flag) {continue;}
            return false;
        }
        return true;
    }

}