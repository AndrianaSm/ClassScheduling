
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(100, 1, 1000, 100, reader.getPairs());
        x.print();
        System.out.println(x.getFitness());
    }



}
