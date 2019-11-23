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
                int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()/4));
                Chromosome x = this.population.get(xIndex);
                int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size())/4);

                while(yIndex == xIndex)
                {
                    yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size())/4);
                }
                Chromosome y = this.population.get(yIndex);

                Chromosome child = this.reproduce(x, y,data);

                if(r.nextDouble() < mutationProbability)
                {
                    child.mutate(data.getPairs());
                }
                newPopulation.add(child);
            }
            this.population = new ArrayList<>(newPopulation);

            Collections.sort(this.population, Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minimumFitness)
            {
                System.out.println("Finished after " + step + " steps...");
                return this.population.get(0);
            }
            System.out.println(step+" :" +(double)this.population.get(0).getFitness()/8);

            this.updateFitnessBounds();
      //      System.out.println(step+"***********************************************");

        }
//        System.out.println("Finished after " + maximumSteps + " steps...");
        return this.population.get(0);
    }

    public void initializePopulation(int populationSize,DataReader data) {
        this.population = new ArrayList<>();
        for(int i=0; i<=populationSize; i++) {
            this.population.add(new Chromosome(data));
        }
        this.updateFitnessBounds();
    }

    public void updateFitnessBounds() {
        this.fitnessBounds = new ArrayList<>();
        for (int i=0; i<this.population.size(); i++) {
            int count= this.population.get(i).hours6_7 +
                    this.population.get(i).noGaps +
                    this.population.get(i).maxDailySubjectHours +
                    this.population.get(i).maxDailyProfHours +
                    this.population.get(i).consecutiveHours+
                    this.population.get(i).weeklyProfessorHours +
                    this.population.get(i).weeklySubjectHours +
                    this.population.get(i).relationTeacherSubject*1000;

            for(int j=0; j<this.population.get(i).getFitness(); j++) {
                fitnessBounds.add(i);
            }
        }
    }

    public Chromosome reproduce(Chromosome x, Chromosome y,DataReader data) {

        int count = new Random().nextInt(36);
        int day;
        int hour;

        for(int i = 0 ; i<count ;i++){
            day = chooseDay();
            hour = chooseHour();
            for(int j = day ; j<day+9 ;j++) {
                x.getGenes()[hour][day]=y.getGenes()[hour][day];
            }
        }

        return new genetic.Chromosome(x.getGenes(),data);
    }
    private int chooseDay() {
        int [] days ={0,9,18,27,36};
        return days[new Random().nextInt(5)];
    }
    private  int chooseHour() {
        int [] hours={0,1,2,3,4,5,6};
        return hours[new Random().nextInt(7)];
    }


}
