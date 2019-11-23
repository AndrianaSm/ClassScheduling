package genetic;

import data.DataReader;
import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
    private String[][] genes;
    private int fitness;
    private ArrayList<String> pairs;
    private DataReader reader;
    private int rate = 0;
    int hours6_7=0;
    int noGaps=0;
    int maxDailySubjectHours=0;
    int maxDailyProfHours=0;
    int consecutiveHours=0;
    int weeklyProfessorHours =0;
    int weeklySubjectHours=0;
    int relationTeacherSubject =0;
    HashMap<String,Integer>weeklyProfHours;


    public Chromosome(DataReader reader) {
        this.reader=reader;
        this.pairs = reader.getPairs();
        this.genes = new String[7][45];
        String[] prof = new String[9];
        int z ;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 45; j++) {
                z = j % 9;
                if (z == 0) Arrays.fill(prof, "");
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

    private void calculateFitness() {
        String columnProf[] = new String[7];
        String columnSubj[] = new String[7];
        String [][] columnProfDaily=new String[7][9];
        //limitations
        hours6_7=0;
        noGaps=0;
        maxDailySubjectHours=0;
        maxDailyProfHours=0;
        consecutiveHours=0;
        weeklyProfessorHours =0;
        weeklySubjectHours=0;
        relationTeacherSubject=0;
        weeklyProfHours=new HashMap<>();

        //limitations : 1.no gaps
        //              2.6-7 hours per day
        //              3.maxDailyHours
        //
        for (int i = 0; i < 45; i++) {
            Arrays.fill(columnProf, "");
            //take the professors=columnProf and subjects=columnSubj  7 hours for each class
            for (int j = 0; j < 7; j++) {
                columnProf[j] = genes[j][i].split(",")[0];
                columnSubj[j] = genes[j][i].split(",")[1];
                if (!genes[j][i].split(",")[0].equals("00")) {

                    if (!weeklyProfHours.containsKey(genes[j][i].split(",")[0])) {
                        weeklyProfHours.put(genes[j][i].split(",")[0], 1);
                    } else {
                        weeklyProfHours.put(genes[j][i].split(",")[0], weeklyProfHours.get(genes[j][i].split(",")[0]) + 1);
                    }
                }
            }
            //count the gaps
            int gaps = countChar(columnProf, "00", columnProf.length - 1);
            //count for 6-7 hours per day
            int hours = 7 - countChar(columnProf, "00", columnProf.length);

            if (hours >= 6) hours6_7++;
            if (gaps == 0) noGaps++;
            //check the maxDailyHours for every subject
            if (maxDailyHoursSubjects(columnSubj)) maxDailySubjectHours++;
        }

        //check for maxWeekly ProfessorsHours
        for (Map.Entry<String, Integer> entry : weeklyProfHours.entrySet()) {
            if(entry.getValue()<=reader.getProfessorMap().get(entry.getKey()).getMaxWeeklyHours()) {
                weeklyProfessorHours++;
            }

        }

        //check for maxDaily professor hours and max 2 consecutive hours
        for(int z=0 ;z<5 ;z++){
            for(int i =z*9 ; i<(z+1)*9 ; i++) {
                for(int j=0 ; j<7 ; j++) {
                    columnProfDaily[j][i%9]=genes[j][i].split(",")[0];
                }
            }
            maxDailyProfHours+=maxDailyHoursProfessors(columnProfDaily);
            consecutiveHours+=consecutiveProfHours(columnProfDaily);
        }

        //check for WeeklySubject hours  and relation between teacher and class
        weeklySubjectHours=weeklySubjectHours();
        relationTeacherSubject=relationTeacherSubject();


        //convert to 100%
        maxDailyProfHours/=5;
        consecutiveHours/=5;
        hours6_7=(hours6_7*100)/45;
        noGaps=(noGaps*100)/45;
        weeklyProfessorHours =(weeklyProfessorHours *100)/weeklyProfHours.size();
        maxDailySubjectHours=(maxDailySubjectHours*100)/45;

        //calculate rate
        rate=hours6_7+noGaps+maxDailySubjectHours+maxDailyProfHours+consecutiveHours+ weeklyProfessorHours + weeklySubjectHours +relationTeacherSubject;
//        System.out.println(hours6_7+" "+noGaps+" "+maxDailySubjectHours+" "+maxDailyProfHours+" " +consecutiveHours+" "+" "+ weeklyProfessorHours +" "+weeklySubjectHours+" "+ relationTeacherSubject +" "+rate);
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
                String pair = selectPair(z, prof, pairs);
                prof[b] = pair.split(",")[0];
                b++;

                this.genes[i][z] = pair;
            }
            this.calculateFitness();
    }

    public void print() {

        String days[] = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        String classes[] = {"A1","A2","A3","B1","B2","B3","C1","C2","C3"};


        for(int d =0 ; d<5 ;d++) {
            System.out.println(days[d]);
            System.out.println("");
            for (int z = 0; z < 9; z++) {
                System.out.print(classes[z]);
                System.out.print("                                                          ");
            }
            System.out.println(" ");
            for (int i = 0; i < 7; i++) {
                for(int z = d*9 ; z<(d+1)*9 ; z++ ){
                    System.out.printf("%-60s", reader.getSubjectMap().get(genes[i][z].split(",")[1]).getName()+"("+reader.getProfessorMap().get(genes[i][z].split(",")[0]).getName()+")");

                }
                System.out.println("");
            }
            System.out.println("");
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

        double countMaxDaily = 0;

        HashMap<String,Integer> professorsHours = getMapProf(prof);
        for (Map.Entry<String, Integer> entry : professorsHours.entrySet()) {
            int maxDailyHours = reader.getProfessorMap().get(entry.getKey()).getMaxDailyHours();

            if (entry.getValue() <= maxDailyHours) {
                countMaxDaily++;
            }
        }


        countMaxDaily = (countMaxDaily / professorsHours.size()) * 100;
            return (int) countMaxDaily;
        }

        private int consecutiveProfHours (String [][] prof) {

            double consecutive=0;
            int [] hours=new int[7];
            HashMap<String,Integer> professorsHours =getMapProf(prof);

            for (Map.Entry<String, Integer> entry : professorsHours.entrySet()) {
                Arrays.fill(hours, 0);
                if (entry.getValue() > 5) {
                    consecutive++;
                } else if (entry.getValue() > 2) {

                    for (int i = 0; i < prof.length; i++) {
                        for (int j = 0; j < prof[0].length; j++) {
                            if (prof[i][j].equals(entry.getKey())) {
                                hours[i] = 1;
                            }
                        }
                    }

                    for (int z = 0;  z < hours.length - 2; z++) {
                        if ((hours[z]!=0) && (hours[z + 1] == hours[z] ) && (hours[z + 2] == hours[z])) {
                            consecutive++;
                            break;
                        }
                    }

                }

            }
            consecutive=(consecutive/professorsHours.size())*100;
            return (int) consecutive;

        }

        private HashMap<String,Integer> getMapProf(String [][] prof) {

            HashMap<String, Integer> professorsHours = new HashMap<>();
            for (int i = 0; i < prof.length; i++) {
                for (int j = 0; j < prof[0].length; j++) {
                    if (!prof[i][j].equals("00")) {

                        if (!professorsHours.containsKey(prof[i][j])) {
                            professorsHours.put(prof[i][j], 1);
                        } else {

                            professorsHours.put(prof[i][j], professorsHours.get(prof[i][j]) + 1);
                        }
                    }
                }
            }
            return professorsHours;
        }

        private int weeklySubjectHours() {
        int count=0;
        int x;
            HashMap<String,Integer> weeklySubjectHours=new HashMap<>();
            for(int i=0 ; i<45 ; i+=9) {
                for(int j=i ; j<i+9 ;j++) {
                    for( int z=0 ; z<7 ;z++) {

                        if (!weeklySubjectHours.containsKey(genes[z][j].split(",")[1])) {
                            weeklySubjectHours.put(genes[z][j].split(",")[1], 1);
                        } else {
                            weeklySubjectHours.put(genes[z][j].split(",")[1], weeklySubjectHours.get(genes[z][j].split(",")[1]) + 1);
                        }
                    }
                }
                x=0;
                for (Map.Entry<String, Integer> entry : weeklySubjectHours.entrySet()) {
                    if(entry.getValue()<=reader.getSubjectMap().get(entry.getKey()).getMaxWeeklyHours()) {
                        x++;
                    }
                }
                count+=(x*100)/weeklySubjectHours.size();
                weeklySubjectHours.clear();
            }
            return count/9;
        }

        private int relationTeacherSubject() {
        int count=0;
        int x;
            HashMap <String,ArrayList> subjects =new HashMap<>();
            for(int i=0 ; i<45 ; i+=9) {
                for (int j = i; j < i + 9; j++) {
                    for (int z = 0; z < 7; z++) {
                        if (!subjects.containsKey(genes[z][j].split(",")[1])) {
                            subjects.put(genes[z][j].split(",")[1],new ArrayList<String>());
                            subjects.get(genes[z][j].split(",")[1]).add(genes[z][i].split(",")[0]);

                        } else {
                                subjects.get(genes[z][j].split(",")[1]).add(genes[z][z].split(",")[0]);
                        }
                    }
                }
                x=0;
                for (Map.Entry<String, ArrayList >entry : subjects.entrySet()) {
                    if (entry.getValue().size() == 1) {
                        x++;
                    }
                }
                count+=(x*100)/subjects.size();
                subjects.clear();
            }
            return count/9;
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
