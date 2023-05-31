package Methods.nonLinearSystems;

import net.objecthunter.exp4j.Expression;

import java.util.HashMap;

import static Methods.utilities.matrixUtilities.MatrixUtilities.norm;

public class fixedPoint
{
    public static double[] fixedPointIteration(Expression[] g, double[] guess, int iteration, double error) throws Exception
    {
        if(iteration == 0) return guess;

        double[] newGuess = new double[guess.length];
        HashMap<String, Double> variableValues = new HashMap<>();
        for(int j=0; j<guess.length; j++) variableValues.put("x"+(j+1), guess[j]);

        for(int i=0; i<newGuess.length; i++)
        {
            //set up expressions for evaluation
            g[i].setVariables(variableValues); //x1 x2 ...
            newGuess[i] = g[i].evaluate();
        }
        if(norm(guess, newGuess) <= error) return newGuess;
        else if(iteration == 1) throw new Exception("FIP diverges");
        else return fixedPointIteration(g, newGuess, iteration - 1, error);
    }
}
