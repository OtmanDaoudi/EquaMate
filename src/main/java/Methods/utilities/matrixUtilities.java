package Methods.utilities;

import java.util.HashMap;

import Methods.linearSystems.luDecomposition;

public class matrixUtilities {
    public static class MatrixUtilities
    {
        public static double eliminateEntry(double[][] A, double[] B, int line, int column) {
            double multiplier = -A[line][column] / A[column][column];

            for (int i = 0; i < A.length; i++) {
                A[line][i] = A[line][i] + multiplier * A[column][i];
            }
            if (B != null)
                B[line] = B[line] + multiplier * B[column];
            return multiplier;
        }

        public static double[] backSolve(double[][] A, double[] B) {
            double[] res = new double[B.length];
            for (int line = A.length - 1; line >= 0; line--) {
                res[line] = B[line];
                for (int column = A.length - 1; column > line; column--) {
                    res[line] -= A[line][column] * res[column];
                }
                res[line] = res[line] / A[line][line];
            }
            return res;
        }

        public static double[] backSolveReversed(double[][] A, double[] B) {
            double[] res = new double[B.length];
            for (int line = 0; line < A.length; line++) {
                res[line] = B[line];
                for (int column = 0; column < line; column++) {
                    res[line] = res[line] - A[line][column] * res[column];
                }
                res[line] = res[line] / A[line][line];
            }
            return res;
        }

        public static double norm(double[] point1, double[] point2)
        {
            double distance = 0;
            for(int i=0; i<point1.length; i++) distance += (point1[i] - point2[i])*(point1[i] - point2[i]);
            return Math.sqrt(distance);
        }

        //converts a double[n][n] matrix to a double[n] vector
        public static double[] toVector(double[][] matrix)
        {
            double[] res = new double[matrix.length];
            for(int i=0; i<matrix.length; i++) res[i] = matrix[i][0];
            return res; 
        }
        
        public static double[] toLineVector(double[][] matrix)
        {
            double[] res = new double[matrix.length];
            for(int i=0; i<matrix[0].length; i++) res[i] = matrix[0][i];
            return res; 
        }

        //converts from line vector (double[n]) to line matrix double[1][n]
        public static double[][] toLineMatrix(double[] vector)
        {
            double[][] res = new double[1][vector.length];
            for(int i=0; i<vector.length; i++) res[0][i] = vector[i]; 
            return res; 
        }

        public static double determinant(double[][] matrix)
        {
            double[] b = new double[matrix.length];
            for(int i=0; i<matrix.length; i++) b[i] = 0; 
            HashMap<String, Object> res =  luDecomposition.LU(matrix, b);
            double[][] U = (double[][])res.get("U");
            double determinant = 1;
            for(int i=0; i<U.length; i++) determinant *= U[i][i];
            return determinant; 
        }
    }
}
