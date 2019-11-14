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
        this.calculateFitness();
    }

    public Chromosome(String [][] genes) {
        this.genes = new String[7][45];
        this.pairs=pairs;
        for(int i=0; i<7; i++) {
            for(int j=0 ;j<45;j++) {
                this.genes[i][j] = genes[i][j];
            }
        }

        this.calculateFitness();
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

    public void calculateFitness() {
        int rate = 1;

        //Enas kathhghths kathe wra ths hmeras
        int noCoincidence=0;

        for(int i=0 ; i<7; i++) {
            String  professorsPerHour="";

            for (int j = 0; j < 45; j++) {
                professorsPerHour+=(genes[i][j].split(",")[0]);
            }
            String [] dailyHours=new String[5];
            for(int j=0;j<5;j++) {
                dailyHours[j]=java.util.Arrays.toString(professorsPerHour.split("(?<=\\G..................)")).replace("[","").replace("]","").replaceAll(" ","").split(",")[j];
                noCoincidence+=uniqueCharacters(dailyHours[j]);
            }
        }
        noCoincidence*=9;
        rate+=noCoincidence;

        //kathe taksi to mathima
        int rightLessons=0;

        for(int i=0 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("A")) {
                        rightLessons++;
                    }
                }
            }
        }
        for(int i=3 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("B")) {
                        rightLessons++;
                    }
                }
            }
        }
        for(int i=6 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("C")) {
                        rightLessons++;
                    }
                }
            }
        }

        rate+=rightLessons;
        System.out.println("noCoincidence "+noCoincidence +" + " + "rightLessons " +rightLessons + " = " +rate);
        this.fitness=rate;
    }

    public void mutate(ArrayList<String> pairs) {
        Random r = new Random();
        this.genes[r.nextInt(7)][r.nextInt(45)] =  pairs.get(r.nextInt(pairs.size()));
        this.calculateFitness();
    }

    public void print() {
        for(int i =0 ; i<7 ;i++) {
            for(int j=0 ; j<45 ; j++) {
                System.out.print(getGenes()[i][j]);
                System.out.print("|");
            }
            System.out.println("\n");
        }
    }

    @Override
    public boolean equals(Object obj) {
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

    int uniqueCharacters(String str)
    {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i+=2)
            for (int j = i + 2; j < str.length(); j+=2)
                if (str.charAt(i) == str.charAt(j) && str.charAt(i+1)==str.charAt(j+1))
                    return 0;
        // If no duplicate characters encountered,
        // return true
        return 1;
    }

}
