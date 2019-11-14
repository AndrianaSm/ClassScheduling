
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(1000, 0.03, 1000, 10000, reader.getPairs());
   //     x.print();
    }



}
