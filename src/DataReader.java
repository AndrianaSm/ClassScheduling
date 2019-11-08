import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataReader {


    private Map<Integer,Subject> subjectMap;
    private Map<Integer, Professor> professorMap;

    DataReader(){
        this.subjectMap=new HashMap<>();
        this.professorMap=new HashMap<>();

        loadSubjects();
        loadProfessor();;
    }

    private void loadSubjects() {
        try {
            Files.lines(Paths.get("data/Subjects.csv")).forEach(r-> {
                String [] splitline =r.split(";");
                subjectMap.put(Integer.valueOf(splitline[0]),new Subject(Integer.valueOf(splitline[0]),splitline[1],Integer.valueOf(splitline[2]),Integer.valueOf(splitline[3]),splitline[4]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfessor(){
        try {
            Files.lines(Paths.get("data/Professors.csv")).forEach(r-> {
                String [] splitline =r.split(";");
                professorMap.put(Integer.valueOf(splitline[0]),new Professor(Integer.valueOf(splitline[0]),splitline[1],splitline[2],Integer.valueOf(splitline[3]),Integer.valueOf(splitline[4])));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DataReader{" +
                "subjectMap=" + subjectMap.toString() + "\n" +
                ", professorMap=" + professorMap.toString() + "\n" +
                '}';
    }
}
