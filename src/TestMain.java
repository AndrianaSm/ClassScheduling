
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());
//
//        chromosome.print();
//        System.out.println("############################################################################################################################");
//        chromosome.calculateFitness();

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(1000, 0.03, 100, 100,reader.getPairs());
        x.print();
        System.out.println(x.getFitness());
    }
}
