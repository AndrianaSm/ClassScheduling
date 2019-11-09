import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    private ArrayList<Chromosome> population;
    private ArrayList<Integer> fitnessBounds;

    public Genetic() {
        this.population = null;
        this.fitnessBounds = null;
    }

    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int minimumFitness, int maximumSteps,ArrayList<String> pairs) {
        initializePopulation(populationSize,pairs);
        Random r = new Random();
        for(int step=0; step < maximumSteps; step++) {
            //Initialize the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
            for(int i=0; i < populationSize; i++) {
                int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                Chromosome x = this.population.get(xIndex);
                int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));

                while(yIndex == xIndex)
                {
                    yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                }
                Chromosome y = this.population.get(yIndex);

                Chromosome child = this.reproduce(x, y,pairs);

                if(r.nextDouble() < mutationProbability)
                {
                    child.mutate();
                }
                newPopulation.add(child);
            }
            this.population = new ArrayList<Chromosome>(newPopulation);

            Collections.sort(this.population, Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minimumFitness)
            {
                System.out.println("Finished after " + step + " steps...");
                return this.population.get(0);
            }
            this.updateFitnessBounds();
        }

        System.out.println("Finished after " + maximumSteps + " steps...");
        return this.population.get(0);
    }

    public void initializePopulation(int populationSize,ArrayList<String> pairs) {
        this.population = new ArrayList<>();
        for(int i=0; i<=populationSize; i++)
        {
            this.population.add(new Chromosome(pairs));
        }
        this.updateFitnessBounds();
    }

    public void updateFitnessBounds() {
        this.fitnessBounds = new ArrayList<>();
        for (int i=0; i<this.population.size(); i++)
        {
            for(int j=0; j<this.population.get(i).getFitness(); j++)
            {
                fitnessBounds.add(i);
            }
        }
    }

    public Chromosome reproduce(Chromosome x, Chromosome y,ArrayList<String> pairs) {
        Random r = new Random();

        int rows =r.nextInt(7);
        int columns= r.nextInt(45);

        String [][] childGenes = new String[7][45];

        for(int i = 0;i<7;i++) {
            for(int j = 0 ; j<45;j++) {
                if(i<=rows && j<=columns) {
                    childGenes[i][j]=x.getGenes()[i][j];
                }else {
                    childGenes[i][j]=y.getGenes()[i][j];
                }
            }

        }
        return new Chromosome(childGenes,pairs);
        //return x;
    }
}
