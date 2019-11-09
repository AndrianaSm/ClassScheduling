import java.util.ArrayList;
import java.util.List;

public class Subject {


    private String id;
    private String name;
    private int maxWeeklyHours;
    private  int maxDailyHour;
    private String grade;

    private List<Professor> professorList =new ArrayList<>();

    public Subject(String id, String name, int maxWeeklyHours, int maxDailyHour,String grade) {
        this.id = id;
        this.name = name;
        this.maxWeeklyHours = maxWeeklyHours;
        this.maxDailyHour = maxDailyHour;
        this.grade=grade;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxWeeklyHours() {
        return maxWeeklyHours;
    }

    public int getMaxDailyHour() {
        return maxDailyHour;
    }

    public String getGrade() {
        return grade;
    }

    public List<Professor> getProfessorList() {
        return professorList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxWeeklyHours(int maxWeeklyHours) {
        this.maxWeeklyHours = maxWeeklyHours;
    }

    public void setMaxDailyHour(int maxDailyHour) {
        this.maxDailyHour = maxDailyHour;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setProfessorList(List<Professor> professorList) {
        this.professorList = professorList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxWeeklyHours=" + maxWeeklyHours +
                ", maxDailyHour=" + maxDailyHour +
                ", professorList=" + professorList +
                '}';
    }
}
