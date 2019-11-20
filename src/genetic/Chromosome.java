package genetic;

import data.DataReader;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class Chromosome implements Comparable<Chromosome> {
    private String[][] genes;
    private int fitness;
    private ArrayList<String> pairs;
    private DataReader reader;
    int rate = 0;
    int lim1=0;
    int lim2=0;
    int lim3=0;
    int lim4=0;

    public Chromosome(DataReader reader) {
        this.reader=reader;
        this.pairs = reader.getPairs();
        this.genes = new String[7][45];
        String[] prof = new String[9];
        int z ;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {
                z = j % 9;
                if (z == 0) {
                    Arrays.fill(prof, "");
                }
                String pair=selectPair(j,prof,pairs);
                prof[z] = pair.split(",")[0];
                genes[i][j] = pair;
            }
        }
        this.calculateFitness();
    }

    public Chromosome(String[][] genes,DataReader reader) {
        this.reader=reader;
        this.genes = new String[7][45];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {
                this.genes[i][j] = genes[i][j];
            }
        }
        this.calculateFitness();
    }

    public String[][] getGenes() {
        return this.genes;
    }

    public int getFitness() {
        return this.fitness;
    }

    public void setGenes(String[][] genes) {
        this.genes = genes;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void calculateFitness() {

        //LIMATION 1: 6-7 HOURS PER DAY FOR EACH CLASS AND NOT GAPS
        String columnProf[] = new String[7];
        String columnSubj[] = new String[7];
        String [][] columnProfDaily=new String[7][9];
        int lim=0;
        for (int i = 0; i < 45; i++) {
            Arrays.fill(columnProf,"");
            for (int j = 0; j < 7; j++) {
                columnProf[j] = genes[j][i].split(",")[0];
                columnSubj[j]=genes[j][i].split(",")[1];

            }
            int gaps=countChar(columnProf,"00",columnProf.length-1);
            int hours=7-countChar(columnProf,"00",columnProf.length);
            if(hours>=6) lim1++;
            if(gaps==0) lim2++;
            if( maxDailyHoursSubjects(columnSubj)) lim3++;
        }

        //limitation for maxDailyProfessorHours
        for(int z=0 ;z<5 ;z++){
            for(int i =z*9 ; i<(z+1)*9 ; i++) {
                for(int j=0 ; j<7 ; j++) {
                    columnProfDaily[j][i%9]=genes[j][i].split(",")[0];
                }
            }
            lim4=maxDailyHoursProfessors(columnProfDaily);
        }


        rate=((lim+lim2)+(lim3*2)/(135)+lim4)/2;
 //       System.out.println(lim);
        System.out.println(lim1+" "+lim2+" "+lim3+" "+lim4+" " +rate);
        this.fitness = rate;
    }

    public void mutate(ArrayList<String> pairs) {
        Random r = new Random();

        int i = r.nextInt(7);
        int j = r.nextInt(5);
        int b = 0;
        String[] prof = new String[9];
        Arrays.fill(prof, "");

        for (int z = j * 9; z < (j + 1) * 9; z++) {
            String pair= selectPair(z,prof,pairs);
            prof[b] = pair.split(",")[0];
            b++;

            this.genes[i][z] = pair;
        }
        this.calculateFitness();
    }

    public void print() {

        String days[] = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        String classes[] = {"A1","A2","A3","B1","B2","B3","C1","C2","C3"};

        for(int z= 0 ; z<5 ;z++) {
            System.out.printf("%73s",StringUtils.center(days[z],70));

        }
        System.out.println("\n");
        for (int z = 0; z < 45; z++) {
            if(z%9==0)
                System.out.print("|");

            System.out.printf("%8s",classes[z%9]);


        }
        System.out.println("\n");

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {
                if(j%9==0)
                    System.out.print("|");

                System.out.printf("%8s", getGenes()[i][j]);
//                System.out.print("|");
            }
            System.out.println("\n");
        }
    }

    @Override
    public boolean equals(Object obj) {
        for (int i = 0; i < 7; i++) {
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


    private boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (String item : items) {
            if (item.contains(inputStr)) {
                return true;
            }
        }
        return false;
    }

    private int countChar(String [] str, String search,int length) {

        int count = 0;
        for (int i = 0; i < length; i++) {
            if (str[i].equals(search)) {
                count++;
            }
        }
        return count;
    }

    private boolean maxDailyHoursSubjects(String [] column) {
        for (int i =0;i<column.length;i++) {
            int hours=0;
            for(int j=i ; j<column.length ;j++) {
                if (column[i].equals(column[j])) {
                    hours++;
                }
                if(hours>1) return false;
            }
        }
        return true;
    }

    private String selectPair(int z,String [] prof,ArrayList<String> pairs) {
        Random r = new Random();
        String pair = pairs.get(r.nextInt(pairs.size()));

        if (z <= 2 || (z >= 9 && z < 12) || (z >= 18 && z < 21) || (z >= 27 && z < 30) || (z >= 36 && z < 39)) {

            while (!pair.split(",")[1].contains("A") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                pair = pairs.get(r.nextInt(pairs.size()));
            }
        } else if (z < 6 || (z >= 12 && z < 15) || (z >= 21 && z < 24) || (z >= 30 && z < 33) || (z >= 39 && z < 42)) {
            while (!pair.split(",")[1].contains("B") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                pair = pairs.get(r.nextInt(pairs.size()));
            }
        } else {
            while (!pair.split(",")[1].contains("C") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                pair = pairs.get(r.nextInt(pairs.size()));
            }
        }
        return pair;
    }

    private int maxDailyHoursProfessors(String [][]prof ) {

        HashMap <String,Integer> professorsHours=new HashMap<>();
        double count=0;
        for (String[] aProf : prof) {
            for (int j = 0; j < prof[0].length; j++) {
                if (!aProf[j].equals("00")) {
                    if (!professorsHours.containsKey(aProf[j])) {
                        professorsHours.put(aProf[j], 1);
                    } else {
                        professorsHours.put(aProf[j], professorsHours.get(aProf[j]) + 1);
                    }
                }
            }
        }
        for(Map.Entry<String, Integer> entry : professorsHours.entrySet()) {
            int maxDailyHours=reader.getProfessorMap().get(entry.getKey()).getMaxDailyHours();

            if(entry.getValue() <=maxDailyHours) {
                count++;
            }
        }

        count=(count/professorsHours.size())*100;
        return (int)count;
    }
}
