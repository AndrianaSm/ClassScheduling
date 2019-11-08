
import java.util.ArrayList;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
    private String[][] genes;
    private int fitness;
    private ArrayList<String> pairs;

    public Chromosome(ArrayList<String> pairs) {
        this.pairs=pairs;
        this.genes = new String[7][45];
        Random r = new Random();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {

                genes[i][j] = pairs.get(r.nextInt(pairs.size()));
            }
        }
        //this.calculateFitness();
    }

    public Chromosome(String [][] genes)
    {
        this.genes = new String[7][45];

        for(int i=0; i<7; i++)
        {
            for(int j=0 ;j<45;j++) {
                this.genes[i][j] = genes[i][j];
            }
        }
   //     this.calculateFitness();
    }

    public String [][] getGenes()
    {
        return this.genes;
    }

    public int getFitness()
    {
        return this.fitness;
    }

    public void setGenes(String[][] genes) {
        this.genes = genes;
    }

    public void setFitness(int fitness)
    {
        this.fitness = fitness;
    }

    //Calculates the fitness score of the chromosome as the number queen pairs that are NOT threatened
    //The maximum number of queen pairs that are NOT threatened is (n-1) + (n-2) + ... + (n-n) = 7 + 6 + 5 + 4 + 3 + 2 + 1 = 28
    public void calculateFitness()
    {
//        int non_threats = 0;
//        for(int i=0; i<this.genes.length; i++)
//        {
//            for(int j=i+1; j<this.genes.length; j++)
//            {
//                //If the queens are NOT on the same row or on the same diagonal, there is NO threat
//                if((this.genes[i] != this.genes[j]) && (Math.abs(i - j) != Math.abs(this.genes[i] - this.genes[j])))
//                {
//                    non_threats++;
//                }
//            }
//        }
//        this.fitness = non_threats;
    }

    //Mutate by randomly changing the position of a queen
    public void mutate()
    {
        Random r = new Random();
        this.genes[r.nextInt(7)][r.nextInt(45)] =  pairs.get(r.nextInt(pairs.size()));
        this.calculateFitness();
    }

    public void print()
    {
        for(int i =0 ; i<7 ;i++) {
            for(int j=0 ; j<45 ; j++) {
                System.out.print(getGenes()[i][j]);
                System.out.print("|");
            }
            System.out.println("\n");
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        for(int i=0; i<7; i++) {
            for (int j = 0; j < 45; j++) {
                if (this.genes[i][j] != ((Chromosome) obj).genes[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; i < 45; i++) {
                hashcode += Integer.valueOf(this.genes[i][j]);
            }
        }
        return hashcode;
    }

    @Override
    //The compareTo function has been overriden so sorting can be done according to fitness scores
    public int compareTo(Chromosome x) {
        return this.fitness - x.fitness;
    }
}
