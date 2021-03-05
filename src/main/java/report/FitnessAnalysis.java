package report;

import model.Scenario;
import model.ScenarioLoader;
import optimization.BruteForce;
import optimization.FastHillClimbing;
import optimization.GeneticAlgorithm;
import optimization.HillClimbing;

import java.util.ArrayList;

import static optimization.HillClimbingStrategy.BASIC;
import static optimization.HillClimbingStrategy.STOCHASTIC;

public class FitnessAnalysis {

    public static void main(String[] args) {
        String[] arguments = args[0].split(",");

        String algName = "";
        String scenarioName = "";
        int repeats = 10;

        for (int i = 0; i < arguments.length; i++) {
            String a = arguments[i];
            if(a.equals("-s")) {
                scenarioName = arguments[i+1];
                i++;
                continue;
            }
            if(a.equals("-a")) {
                algName = arguments[i+1];
                i++;
                continue;
            }
            if(a.equals("-n")) {
                repeats = Integer.parseInt(arguments[i+1]);
                i++;
            }
        }

        if (scenarioName.equals("") || algName.equals("")) {
            System.out.println("invalid arguments");
            System.out.println("Usage: run_algs -s <scenario_name> -a <alg_name> [-n <repeats>]");
            return;
        }

        Scenario s = ScenarioLoader.loadFromFile("data/" + scenarioName + "/");

        switch (algName) {
            case "hc": {
                double sumFitness = 0.0;
                int sumTime = 0;
                for (int i = 0; i < repeats; i++) {
                    HillClimbing hc = new HillClimbing(s, BASIC);
                    Report r = hc.solve();
                    sumFitness += r.getFitness();
                    sumTime += r.getTime();
                }
                System.out.println("-----------------------------");
                System.out.println("HillClimbing (" + repeats + " runs)");
                System.out.println("Avg. Fitness: " + sumFitness / repeats);
                System.out.println("Avg. Time: " + sumTime / repeats);
                System.out.println("-----------------------------");
                break;
            }
            case "hcs": {
                double sumFitness = 0.0;
                int sumTime = 0;
                for (int i = 0; i < repeats; i++) {
                    HillClimbing hc = new HillClimbing(s, STOCHASTIC);
                    Report r = hc.solve();
                    sumFitness += r.getFitness();
                    sumTime += r.getTime();
                }
                System.out.println("-----------------------------");
                System.out.println("HillClimbing (Stochastic) (" + repeats + " runs)");
                System.out.println("Avg. Fitness: " + sumFitness / repeats);
                System.out.println("Avg. Time: " + sumTime / repeats);
                System.out.println("-----------------------------");
                break;
            }
            case "fhc": {
                double sumFitness = 0.0;
                int sumTime = 0;
                for (int i = 0; i < repeats; i++) {
                    FastHillClimbing fhc = new FastHillClimbing(s, 6, BASIC);
                    Report r = fhc.solve();
                    sumFitness += r.getFitness();
                    sumTime += r.getTime();
                }
                System.out.println("-----------------------------");
                System.out.println("FastHillClimbing (" + repeats + " runs)");
                System.out.println("Avg. Fitness: " + sumFitness / repeats);
                System.out.println("Avg. Time: " + sumTime / repeats);
                System.out.println("-----------------------------");
                break;
            }
            case "fhcs": {
                double sumFitness = 0.0;
                int sumTime = 0;
                for (int i = 0; i < repeats; i++) {
                    FastHillClimbing fhc = new FastHillClimbing(s, 6, STOCHASTIC);
                    Report r = fhc.solve();
                    sumFitness += r.getFitness();
                    sumTime += r.getTime();
                }
                System.out.println("-----------------------------");
                System.out.println("FastHillClimbing (Stochastic) (" + repeats + " runs)");
                System.out.println("Avg. Fitness: " + sumFitness / repeats);
                System.out.println("Avg. Time: " + sumTime / repeats);
                System.out.println("-----------------------------");
                break;
            }
            case "ga": {
                double sumFitness = 0.0;
                int sumTime = 0;
                for (int i = 0; i < repeats; i++) {
                    GeneticAlgorithm ga = new GeneticAlgorithm(s, 100, 0.02, 0.1, 20);
                    Report r = ga.solve();
                    sumFitness += r.getFitness();
                    sumTime += r.getTime();
                }
                System.out.println("-----------------------------");
                System.out.println("Genetic (" + repeats + " runs)");
                System.out.println("Avg. Fitness: " + sumFitness / repeats);
                System.out.println("Avg. Time: " + sumTime / repeats);
                System.out.println("-----------------------------");
                break;
            }
            case "bf":
                BruteForce bf = new BruteForce(s);
                Report r = bf.solve();
                System.out.println("-----------------------------");
                System.out.println("Brute Force (1 run)");
                System.out.println("Fitness: " + r.getFitness());
                System.out.println("Time: " + r.getTime());
                System.out.println("-----------------------------");
                break;
            default:
                System.out.println("invalid algorithm " + algName);
        }

    }

}
