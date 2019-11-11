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

    public Chromosome(String [][] genes,ArrayList<String> pairs) {
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
        int rate=0;

        //Enas kauhtghths kathe wra ths hmeras
        int noCoincidence=0;
        for(int i=0; i<7; i++) {
            String  profesorsPerHour="";
            for (int j = 0; j < 45; j++) {
                profesorsPerHour+=(genes[i][j].split(",")[0]);
            }
            String [] dailyHours=new String[5];
            for(int j=0;j<5;j++) {
                dailyHours[j]=java.util.Arrays.toString(profesorsPerHour.split("(?<=\\G..................)")).replace("[","").replace("]","").replaceAll(" ","").split(",")[j];
                noCoincidence+=uniqueCharacters(dailyHours[j]);
            }
        }
        rate=+noCoincidence;

        //kathe taksi to mathima
        int temp;
        int rightLessons=1;
        for(int i=0 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                temp=0;
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i+z].split(",")[1].indexOf("A") > 0) {
                        temp++;
                    }
                }
                if(temp==7) rightLessons++;
            }
        }
        for(int i=3 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                temp=0;
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i+z].split(",")[1].indexOf("B") > 0) {
                        temp++;
                    }
                }
                if(temp==7) rightLessons++;
            }
        }

        for(int i=6 ; i<45 ;i+=9){
            for(int z=0 ; z<3 ; z++) {
                temp=0;
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i+z].split(",")[1].indexOf("C") > 0) {
                        temp++;
                    }
                }
                if(temp==7) rightLessons++;
            }
        }

        rate+=rightLessons;


        // 6-7 wres th mera. elegxoume ana sthlh, max 45 fitness, ousiastika koitaei ana tmhma posa kena exei ka8e mera kai an einai 1 h 0 tote auxanei fitness
        int count;
        int fit3=0;
        for (int i=0; i<45; i++){
            count=0;
            for (int j=0; j<7; j++){
                if (genes[j][i].split(",")[1].indexOf("ABC00")>0)   count++;

            }
            if (count<2) fit3++;
        }
        rate+=fit3;

        //oxi kena stis endiamese wres. h teleutaia wra de mas noiazei ara i apo 0 ews 6. elegxw ana sthlh pali gia na vgalw 45 fitness, varuthta idia me ta alla
        int fit4 = 0;
        for(int i=0; i <45; i++){
            count=0;
            for(int j=0; j<6; j++){
                if (genes[j][i].split(",")[1].indexOf("C") > 0) {
                    count++; // einai etsi gia na kaneis elgxous me print
                }
            }
            if (count<1) fit4++;
        }//enw elegxei swsta kai bainei sthn if an uparxei ABC00, kapoio la8os ginetai kai to teliko exei ABC00 opou nanai
        rate+=fit4;


            System.out.println("_______________"+noCoincidence + " " + rightLessons + " "+fit3+" "+fit4);


        this.fitness=rate;
    }

    public void mutate() {
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
            for (int j = i + 1; j < str.length(); j+=2)
                if (str.charAt(i) == str.charAt(j))
                    return 0;
        // If no duplicate characters encountered,
        // return true
        return 1;
    }

}
