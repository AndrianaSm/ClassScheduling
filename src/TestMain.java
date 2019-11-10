import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.management.ThreadInfo;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();

        Chromosome chromosome = new Chromosome(reader.getPairs());
//
//        chromosome.print();
//        chromosome.calculateFitness();

        Genetic g = new Genetic();
        Chromosome x = g.geneticAlgorithm(1000, 0.03, 1000, 1000, reader.getPairs());
        x.print();
        System.out.println(x.getFitness());
    }

}
