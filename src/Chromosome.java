import java.util.*;


public class Chromosome implements Comparable<Chromosome> {
    private String[][] genes;
    private int fitness;
    private ArrayList<String> pairs;
    private DataReader reader;

    public Chromosome(ArrayList<String> pairs) {
        reader=new DataReader();
        this.pairs = pairs;
        this.genes = new String[7][45];
        Random r = new Random();
        String[] prof = new String[9];
        int z = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {

                String pair = pairs.get(r.nextInt(pairs.size()));

                z = j % 9;
                if (z == 0) {
                    Arrays.fill(prof, "");
                }

                if (j <= 2 || (j >= 9 && j < 12) || (j >= 18 && j < 21) || (j >= 27 && j < 30) || (j >= 36 && j < 39)) {

                    while (!pair.split(",")[1].contains("A") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                        pair = pairs.get(r.nextInt(pairs.size()));
                    }
                } else if (j > 2 && j < 6 || (j >= 12 && j < 15) || (j >= 21 && j < 24) || (j >= 30 && j < 33) || (j >= 39 && j < 42)) {
                    while (!pair.split(",")[1].contains("B") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                        pair = pairs.get(r.nextInt(pairs.size()));
                    }
                } else {
                    while (!pair.split(",")[1].contains("C") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                        pair = pairs.get(r.nextInt(pairs.size()));
                    }
                }
                prof[z] = pair.split(",")[0];


                genes[i][j] = pair;
            }

        }
        this.calculateFitness();
      //  this.print();
    }

    public Chromosome(String[][] genes) {
        this.genes = new String[7][45];
//        this.pairs = pairs;
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

        int rate = 0;
        int lim1=0;
        int lim2=0;
        int lim3=0;

        //LIMATION 1: 6-7 HOURS PER DAY FOR EACH CLASS AND NOT GAPS
        String columnProf[] = new String[7];
        String columnSubj[] = new String[7];

        for (int i = 0; i < 45; i++) {

            Arrays.fill(columnProf,"");

            for (int j = 0; j < 7; j++) {
                columnProf[j] = genes[j][i].split(",")[0];
                columnSubj[j]=genes[j][i].split(",")[1];
            }
            int gaps=countChar(columnProf,"00",columnProf.length-1);
            int hours=7-countChar(columnProf,"00",columnProf.length);

            if(hours>=6 && gaps==0 && maxDailyHours(columnSubj)) {
                rate++;
            }
        }
        this.fitness = rate;
     //   System.out.println(fitness);
    }

    public void mutate(ArrayList<String> pairs) {
        Random r = new Random();

        int i = r.nextInt(7);
        int j = r.nextInt(5);
        int b = 0;
        String[] prof = new String[9];
        Arrays.fill(prof, "");

        for (int z = j * 9; z < (j + 1) * 9; z++) {

            String pair = pairs.get(r.nextInt(pairs.size()));

            if (z <= 2 || (z >= 9 && z < 12) || (z >= 18 && z < 21) || (z >= 27 && z < 30) || (z >= 36 && z < 39)) {

                while (!pair.split(",")[1].contains("A") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                    pair = pairs.get(r.nextInt(pairs.size()));
                }
            } else if (z > 2 && z < 6 || (z >= 12 && z < 15) || (z >= 21 && z < 24) || (z >= 30 && z < 33) || (z >= 39 && z < 42)) {
                while (!pair.split(",")[1].contains("B") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                    pair = pairs.get(r.nextInt(pairs.size()));
                }
            } else {
                while (!pair.split(",")[1].contains("C") || stringContainsItemFromList(pair.split(",")[0], prof)) {
                    pair = pairs.get(r.nextInt(pairs.size()));
                }
            }
            prof[b] = pair.split(",")[0];
            b++;

            this.genes[i][z] = pair;
        }
        this.calculateFitness();
    }

    public void print() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {
                System.out.printf("%8s", getGenes()[i][j]);
                System.out.print("|");
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

    int uniqueCharacters(String str) {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i += 2)
            for (int j = i + 2; j < str.length(); j += 2)
                if (str.charAt(i) == str.charAt(j) && str.charAt(i + 1) == str.charAt(j + 1))
                    return 0;
        // If no duplicate characters encountered,
        // return true
        return 1;
    }

    public boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].contains(inputStr)) {
                return true;
            }
        }
        return false;
    }

    public int checkTheProgramm() {
        int rate = 1;

        //Enas kathhghths kathe wra ths hmeras
        int noCoincidence = 0;

        for (int i = 0; i < 7; i++) {
            String professorsPerHour = "";

            for (int j = 0; j < 45; j++) {
                professorsPerHour += (genes[i][j].split(",")[0]);
            }
            String[] dailyHours = new String[5];
            for (int j = 0; j < 5; j++) {
                dailyHours[j] = java.util.Arrays.toString(professorsPerHour.split("(?<=\\G..................)")).replace("[", "").replace("]", "").replaceAll(" ", "").split(",")[j];
                noCoincidence += uniqueCharacters(dailyHours[j]);
            }
        }
        noCoincidence *= 9;
        rate += noCoincidence;

        //kathe taksi to mathima
        int rightLessons = 0;

        for (int i = 0; i < 45; i += 9) {
            for (int z = 0; z < 3; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("A")) {
                        rightLessons++;
                    }
                }
            }
        }
        for (int i = 3; i < 45; i += 9) {
            for (int z = 0; z < 3; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("B")) {
                        rightLessons++;
                    }
                }
            }
        }
        for (int i = 6; i < 45; i += 9) {
            for (int z = 0; z < 3; z++) {
                for (int j = 0; j < 7; j++) {
                    if (genes[j][i + z].split(",")[1].contains("C")) {
                        rightLessons++;
                    }
                }
            }
        }

        rate += rightLessons;
        return rate;
    }

    private int chooseDay() {
        int[] days = {0, 9, 18, 27, 36};
        return days[new Random().nextInt(5)];
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

    private boolean maxDailyHours(String [] column) {
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
}
