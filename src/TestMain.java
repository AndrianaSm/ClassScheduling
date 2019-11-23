import data.DataReader;
import genetic.Chromosome;
import genetic.Genetic;

public class TestMain {

    public static void main(String[] args) {

        DataReader data = new DataReader();

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(30, 0.5, 700, 5000, data);
        x.print();
        double finalFitness=(double)x.getFitness()/8;
        System.out.println(finalFitness);
    }
}
