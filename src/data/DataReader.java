package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataReader {
    private Map<String, Subject> subjectMap;
    private Map<String, Professor> professorMap;

    public DataReader(){
        this.subjectMap=new HashMap<>();
        this.professorMap=new HashMap<>();

        loadSubjects();
        loadProfessor();
    }
    private void loadSubjects() {
        try {
            Files.lines(Paths.get("data/Subjects.csv")).forEach(r-> {
                String [] splitline =r.split(";");
                subjectMap.put(splitline[0],new Subject(splitline[0],splitline[1],Integer.valueOf(splitline[2]),Integer.valueOf(splitline[3]),splitline[4]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfessor(){
        try {
            Files.lines(Paths.get("data/Professors.csv")).forEach(r-> {
                String [] splitline =r.split(";");
                professorMap.put(splitline[0],new Professor(splitline[0],splitline[1],splitline[2],Integer.valueOf(splitline[3]),Integer.valueOf(splitline[4])));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPairs(){
        ArrayList<String> pairs = new ArrayList<>();

        for(Map.Entry<String,Professor> professorEntry :professorMap.entrySet()) {
            String profoserorSubjects=professorEntry.getValue().getSubjectsId();
            String [] subjectsId= profoserorSubjects.split(",");
            for(int i =0;i<subjectsId.length;i++) {
                String pair= professorEntry.getKey() +","+subjectsId[i];
                pairs.add(pair);

            }
        }
        return pairs;
    }

    public Map<String, Subject> getSubjectMap() {
        return subjectMap;
    }

    public Map<String, Professor> getProfessorMap() {
        return professorMap;
    }

    @Override
    public String toString() {
        return "data.DataReader{" +
                "subjectMap=" + subjectMap.toString() + "\n" +
                ", professorMap=" + professorMap.toString() + "\n" +
                '}';
    }
}
