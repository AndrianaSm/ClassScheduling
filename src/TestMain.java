
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());

        chromosome.print();
    }
}
