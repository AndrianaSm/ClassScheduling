
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(100, 0.03, 150, 10000, reader.getPairs());
        x.print();
        System.out.println(x.getFitness());
    }



}
