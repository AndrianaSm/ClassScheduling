
public class Teacher {

    private int id;
    private String name;
    private String classroom;
    private int hours;

    public Teacher(int id, String name, String classroom, int hours) {
        this.id = id;
        this.name = name;
        this.classroom = classroom;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getHours() {
        return hours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classroom='" + classroom + '\'' +
                ", hours=" + hours +
                '}';
    }
}
