
import java.util.ArrayList;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome>
{
    private String [][] genes;
    private int fitness;

    public Chromosome(ArrayList<String> pairs)
    {
        this.genes = new String[7][45];
        Random r = new Random();

        for(int i =0 ; i<7 ;i++) {
            for(int j=0 ; j<45 ; j++) {
                genes[i][j]=pairs.get(r.nextInt(pairs.size()));
            }
        }
        this.calculateFitness();
    }

    //Constructs a copy of a chromosome
    public Chromosome(int [] genes)
    {
        this.genes = new int[8];
        for(int i=0; i<8; i++)
        {
            this.genes[i] = genes[i];
        }
        this.calculateFitness();
    }

    public int[] getGenes()
    {
        return this.genes;
    }

    public int getFitness()
    {
        return this.fitness;
    }

    public void setGenes(int[] genes)
    {
        for(int i=0; i<this.genes.length; i++)
        {
            this.genes[i] = genes[i];
        }
    }

    public void setFitness(int fitness)
    {
        this.fitness = fitness;
    }

    //Calculates the fitness score of the chromosome as the number queen pairs that are NOT threatened
    //The maximum number of queen pairs that are NOT threatened is (n-1) + (n-2) + ... + (n-n) = 7 + 6 + 5 + 4 + 3 + 2 + 1 = 28
    public void calculateFitness()
    {
        int non_threats = 0;
        for(int i=0; i<this.genes.length; i++)
        {
            for(int j=i+1; j<this.genes.length; j++)
            {
                //If the queens are NOT on the same row or on the same diagonal, there is NO threat
                if((this.genes[i] != this.genes[j]) && (Math.abs(i - j) != Math.abs(this.genes[i] - this.genes[j])))
                {
                    non_threats++;
                }
            }
        }
        this.fitness = non_threats;
    }

    //Mutate by randomly changing the position of a queen
    public void mutate()
    {
        Random r = new Random();
        this.genes[r.nextInt(8)] = r.nextInt(8);
        this.calculateFitness();
    }

    public void print()
    {
        System.out.print("|");
        for(int i=0; i<genes.length; i++)
        {
            System.out.print(this.genes[i]);
            System.out.print("|");
        }
        System.out.print(" : ");
        System.out.println(this.fitness);

        System.out.println("------------------------------------");
        for(int i=0; i< genes.length; i++)
        {
            for(int j=0; j < genes.length; j++)
            {
                if(genes[j] == i)
                {
                    System.out.print("|Q");
                }
                else
                {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
        System.out.println("------------------------------------");
    }

    @Override
    public boolean equals(Object obj)
    {
        for(int i=0; i<this.genes.length; i++)
        {
            if(this.genes[i] != ((Chromosome)obj).genes[i])
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hashcode = 0;
        for(int i=0; i<this.genes.length; i++)
        {
            hashcode += this.genes[i];
        }
        return hashcode;
    }

    @Override
    //The compareTo function has been overriden so sorting can be done according to fitness scores
    public int compareTo(Chromosome x)
    {
        return this.fitness - x.fitness;
    }
}
