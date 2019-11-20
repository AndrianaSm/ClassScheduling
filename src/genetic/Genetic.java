package genetic;

import data.DataReader;

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

    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int minimumFitness, int maximumSteps, DataReader data) {
        initializePopulation(populationSize,data);
        Random r = new Random();
        for(int step=0; step < maximumSteps; step++) {
            //Initialize the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<>();
            for(int i=0; i < populationSize; i++) {
                int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                Chromosome x = this.population.get(xIndex);
                int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));

                while(yIndex == xIndex)
                {
                    yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                }
                Chromosome y = this.population.get(yIndex);

                Chromosome child = this.reproduce(x, y,data);

                if(r.nextDouble() < mutationProbability)
                {
                    child.mutate(data.getPairs());
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

    public void initializePopulation(int populationSize,DataReader data) {
        this.population = new ArrayList<>();
        for(int i=0; i<=populationSize; i++)
        {
            this.population.add(new Chromosome(data));
   //b         System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + i +"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        }
        this.updateFitnessBounds();
    }

    public void updateFitnessBounds() {
        this.fitnessBounds = new ArrayList<>();
        for (int i=0; i<this.population.size(); i++) {
            int count= this.population.get(i).lim3*30 + this.population.get(i).lim1 + this.population.get(i).lim2 + this.population.get(i).lim4;
            for(int j=0; j<count; j++) {
                fitnessBounds.add(i);
            }
        }
    }

    public Chromosome reproduce(Chromosome x, Chromosome y,DataReader data) {

        Random r = new Random();
        int day= r.nextInt(5)+1;
        String [][] childGenes = new String[7][45];

        for(int j=0; j<7 ;j++) {
            for(int i=0 ; i<day*9 ;i++ ) {
                childGenes[j][i]=x.getGenes()[j][i];
            }
        }
        for(int j=0; j<7 ;j++) {
            for(int i=day*9 ; i<45 ;i++ ) {
                childGenes[j][i]=y.getGenes()[j][i];
            }
        }
        return new Chromosome(childGenes,data);
        //return x;
    }

}
