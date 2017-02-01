package albercht;



import java.util.ArrayList;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class jgap_fitness_function extends GPFitnessFunction {

	 	private ArrayList<Double> A;
	    private ArrayList<Double> B;
	    private ArrayList<Double> C;
	    private ArrayList<Double> D;
	    private ArrayList<Double> E;
	    private ArrayList<Double> F;
	    private ArrayList<Double> output;
	    private Variable VariableofA;
	    private Variable VariableofB;
	    private Variable VariableofC;
	    private Variable VariableofD;
	    private Variable VariableofE;
	    private Variable VariableofF;

	    private static Object[] NO_ARGS = new Object[0];
//creating the fitness function for all the variables
	    public jgap_fitness_function(ArrayList<Double> InputofA, ArrayList<Double> InputofB, ArrayList<Double> InputofC,
				ArrayList<Double> InputofD, ArrayList<Double> InputofE, ArrayList<Double> InputofF,
				ArrayList<Double> OUTPUT, Variable a, Variable b, Variable c,
				Variable d, Variable e, Variable f) {

	    	A = InputofA;
	        B = InputofB;
	        C = InputofC;
	        D = InputofD;
	        E = InputofE;
	        F = InputofF;
	        output = OUTPUT;
	        VariableofA = a;
	        VariableofB = b;
	        VariableofC = c;
	        VariableofD = d;
	        VariableofE = e;
	        VariableofF = f;
		}

		@Override
	    protected double evaluate(final IGPProgram program) {
	        double result = 0.0f;

	        long longResult = 0;
	        for (int i = 0; i < A.size(); i++) {
	            // giving the input datavalues to all variables
	        	VariableofA.set(A.get(i));
	        	VariableofB.set(B.get(i));
	        	VariableofC.set(C.get(i));
	        	VariableofD.set(D.get(i));
	        	VariableofE.set(E.get(i));
	        	VariableofF.set(F.get(i));
	            // Execute the genetic algorithm 
	            long datavalues =  (long) program.execute_double(0, NO_ARGS);
//	            System.out.println("datavalues: " +datavalues);
//	            System.out.println("Target:" +_output.get(i));
//	            System.out.println("------------------------");
	         // if  goes to zero, we get better result
	            longResult += Math.abs(datavalues - output.get(i));
	        }

	        result = longResult;

	        return result;
	    }

}
