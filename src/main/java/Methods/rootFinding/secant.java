package Methods.rootFinding;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class secant {
    public static double Secant(Expression f, double x0, double x1, int iteration, double error) throws Exception {
        if (iteration == 0)
            return x1;

        double x2 = x1 - (f.setVariable("x1", x1).evaluate() * (x1 - x0))
                / (f.setVariable("x1", x1).evaluate() - f.setVariable("x1", x0).evaluate());
        if (Math.abs(x1 - x2) <= error)
            return x2;
        else if (iteration == 1)
            throw new Exception("Secant doesn't converge");
        else
            return Secant(f, x1, x2, iteration - 1, error);
    }

    public static void main(String[] args) throws Exception {
        Expression f = new ExpressionBuilder("x1^3 + x1 - 1").variable("x1").build(); // params -> Math.pow(params[0],
                                                                                      // 3) + params[0] - 1;
        double root = Secant(f, 0.0, 1.0, 7, 0.00000001);
        System.out.println(root);
    }
}