
public class Professor {

    private String id;
    private String name;
    private String subjectsId;
    private int maxDailyHours;
    private int maxWeeklyHours;


    public Professor(String id, String name, String subjectsId, int maxDailyHours,int maxWeeklyHours) {
        this.id = id;
        this.name = name;
        this.subjectsId = subjectsId;
        this.maxDailyHours = maxDailyHours;
        this.maxWeeklyHours=maxWeeklyHours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getSubjectsId() {
        return subjectsId;
    }

    public int getMaxDailyHours() {
        return maxDailyHours;
    }

    public int getMaxWeeklyHours() {
        return maxWeeklyHours;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }

    public void setMaxDailyHours(int maxDailyHours) {
        this.maxDailyHours = maxDailyHours;
    }

    public void setMaxWeeklyHours(int maxWeeklyHours) {
        this.maxWeeklyHours = maxWeeklyHours;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjectsId='" + subjectsId + '\'' +
                ", maxDailyHours=" + maxDailyHours +
                ", maxWeeklyHours=" + maxWeeklyHours +
                '}';
    }
}
