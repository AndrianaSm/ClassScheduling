import java.util.ArrayList;
import java.util.List;

public class Subject {


    private int id;
    private String name;
    private int maxWeeklyHours;
    private  int maxDailyHour;
    private List<Subject> teacherList=new ArrayList<Subject>();

    public Subject(int id, String name, int maxWeeklyHours, int maxDailyHour, List<Subject> teacherList) {
        this.id = id;
        this.name = name;
        this.maxWeeklyHours = maxWeeklyHours;
        this.maxDailyHour = maxDailyHour;
        this.teacherList = teacherList;
    }


    public int getId() {
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

    public List<Subject> getTeacherList() {
        return teacherList;
    }

    public void setId(int id) {
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

    public void setTeacherList(List<Subject> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxWeeklyHours=" + maxWeeklyHours +
                ", maxDailyHour=" + maxDailyHour +
                ", teacherList=" + teacherList +
                '}';
    }
}
