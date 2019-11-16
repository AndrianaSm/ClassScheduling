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

                Chromosome child = this.reproduce(x, y);

                if(r.nextDouble() < mutationProbability)
                {
                    child.mutate(pairs);
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
      //      System.out.println("******************************************" +step +"***********************************************************");
       //     System.out.println("******************************************" +step +"***********************************************************");
            //updatePopulation;
        }

        System.out.println("Finished after " + maximumSteps + " steps...");
        return this.population.get(0);
    }

    public void initializePopulation(int populationSize,ArrayList<String> pairs) {
        this.population = new ArrayList<>();
        for(int i=0; i<=populationSize; i++)
        {
            this.population.add(new Chromosome(pairs));
   //b         System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + i +"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

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

    public Chromosome reproduce(Chromosome x, Chromosome y) {


//        int cout = new Random().nextInt(36);
//        int day;
//        int hour;
//
//        for(int i = 0 ; i<cout ;i++){
//            day = chooseDay();
//            hour = chooseHour();
//            for(int j = day ; j<day+9 ;j++) {
//                x.getGenes()[hour][day]=y.getGenes()[hour][day];
//            }
//        }
//
//        return new Chromosome(x.getGenes());


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


        return new Chromosome(childGenes);
        //return x;
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
