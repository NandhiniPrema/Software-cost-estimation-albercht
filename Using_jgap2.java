package albercht;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.GPPopulation;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class Using_jgap2 extends GPProblem {
	// creating the file
	private static String DATA_SET = "albercht.txt";
	private ArrayList<ArrayList<Double>> listOLists = new ArrayList<ArrayList<Double>>();
	private Variable a;
	private Variable b;
	private Variable c;
	private Variable d;
	private Variable e;
	private Variable f;

	private ArrayList<ArrayList<Double>> parser() {
		try {
			// reading the file
			FileReader fp = new FileReader(DATA_SET);
			BufferedReader bf = new BufferedReader(fp);
			if (bf == null) {
				System.out.println("Problem creating stream...");
			} else
				System.out.println("File found!");

			String line = "";
			String blanks = " ";
			int i = 0;

			while ((line = bf.readLine()) != null) {
				List<Double> dataStore = new ArrayList<Double>();
				// splitting the file in tokens
				String[] tokens = line.toString().split(blanks);
				int tokenCount = tokens.length;

				for (int j = 0; j < tokenCount; j++) {
					dataStore.add(Double.parseDouble(tokens[j]));
				}

				listOLists.add((ArrayList<Double>) dataStore);
				i++;
			}
			// System.out.println(listOLists);

			bf.close();
			// streamReader.close();
			fp.close();
		} catch (IOException e) {
			System.out.println("Error in file" + e.getMessage());
		}
		return listOLists;
	}

	public Using_jgap2() throws InvalidConfigurationException {

		super(new GPConfiguration());
		// creating the configuration object
		GPConfiguration config = getGPConfiguration();
		ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
		temp = parser();
		// System.out.println(temp);
		ArrayList<Double> InputofA = new ArrayList<Double>();
		ArrayList<Double> InputofB = new ArrayList<Double>();
		ArrayList<Double> InputofC = new ArrayList<Double>();
		ArrayList<Double> InputofD = new ArrayList<Double>();
		ArrayList<Double> InputofE = new ArrayList<Double>();
		ArrayList<Double> InputofF = new ArrayList<Double>();
		ArrayList<Double> OUTPUT = new ArrayList<Double>();

		for (int i = 0; i < temp.size(); i++) {

			for (int j = 0; j < temp.get(j).size(); j++) {

				if (j == 0) {
					InputofA.add(temp.get(i).get(j));
				} else if (j == 1) {
					InputofB.add(temp.get(i).get(j));
				} else if (j == 2) {
					InputofC.add(temp.get(i).get(j));
				} else if (j == 3) {
					InputofD.add(temp.get(i).get(j));
				} else if (j == 4) {
					InputofE.add(temp.get(i).get(j));
				} else if (j == 5) {
					InputofF.add(temp.get(i).get(j));
				} else if (j == 6) {
					OUTPUT.add(temp.get(i).get(j));
				}
			}
		}
		System.out.println("DATA1: " + InputofA);
		System.out.println("DATA2: " + InputofB);
		System.out.println("DATA3: " + InputofC);
		System.out.println("DATA4: " + InputofD);
		System.out.println("DATA5: " + InputofE);
		System.out.println("DATA6: " + InputofF);
		System.out.println("OUTPUT: " + OUTPUT);
		System.out.println("\n");
		a = Variable.create(config, "A", CommandGene.DoubleClass);
		b = Variable.create(config, "B", CommandGene.DoubleClass);
		c = Variable.create(config, "C", CommandGene.DoubleClass);
		d = Variable.create(config, "D", CommandGene.DoubleClass);
		e = Variable.create(config, "E", CommandGene.DoubleClass);
		f = Variable.create(config, "F", CommandGene.DoubleClass);
		// setup the function by using configuration object
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(4);
		config.setPopulationSize(1000);
		config.setMaxCrossoverDepth(8);
		config.setFitnessFunction(new jgap_fitness_function(InputofA, InputofB, InputofC, InputofD, InputofE, InputofF,
				OUTPUT, a, b, c, d, e, f));
		config.setStrictProgramCreation(true);
	}

	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration config = getGPConfiguration();

		// The return type of the GP program.
		Class[] types = { CommandGene.DoubleClass };

		// Arguments of result-producing chromosome: none
		Class[][] argTypes = { {} };

		// Next, we define the set of available GP commands and terminals to
		// use.
		CommandGene[][] nodeSets = { { a, b, c, d, e, f,
				// manipulating the mathematical function
				new Add(config, CommandGene.DoubleClass), new Multiply(config, CommandGene.DoubleClass),
				new Subtract(config, CommandGene.DoubleClass), new Divide(config, CommandGene.DoubleClass),
				new Terminal(config, CommandGene.DoubleClass, 0.0, 10.0, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

		return result;
	}

	public static void main(String[] args) throws InvalidConfigurationException {
		// creating the object for the GP problem
		GPProblem problem = new Using_jgap2();
		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		// Random
		// for(int i=0;i<5;i++)
		// {
		// System.out.println("ITERATIONS for random solution:"+i);
		gp.evolve(0);
		System.out.println("Best Random solution :");
		gp.calcFitness();
		gp.outputSolution(gp.getAllTimeBest());
		System.out.println("Random COMPLETE ");

		// TODO : FIX THIS TO GET A REAL RANDOM INDIVIDUAL FROM THE RANDOM
		// POPULATION
		System.out.println("Randomly picked solution :");
		gp.evolve(0);

		GPPopulation pop = gp.getGPPopulation();
		IGPProgram randIndividual = pop.getGPProgram(0);
		randIndividual.toStringNorm(0);
		gp.outputSolution(gp.getAllTimeBest());
		System.out.println("Random COMPLETE ");
		// }

		// Evolved
		for (int i = 0; i < 10; i++) {

			System.out.println("ITERATIONS for genetic:" + i);
			System.out.println("Genetic solution :");
			gp.evolve(90);

			gp.outputSolution(gp.getAllTimeBest());
			System.out.println("Genetic COMPLETE ");
		}
	}
}

// }
