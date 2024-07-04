import java.util.ArrayList;
import java.util.List;

public class StudentList {

    private List<StudentInfo> studentSet = new ArrayList<>();

    public StudentList(List<StudentInfo> studentSet) {
        this.studentSet = studentSet;
    }

    public List<StudentInfo> findNames(String nameFind) {
        List<StudentInfo> nameStart = new ArrayList<>();
        for (StudentInfo stu : studentSet) {
            if (stu.getName().toLowerCase().contains(nameFind.toLowerCase())) {
                nameStart.add(stu);
            }
        }
        return nameStart;
    }

    public List<StudentInfo> findGrade(int gradeFind) {
        List<StudentInfo> grade = new ArrayList<>();
        for (StudentInfo stu : studentSet) {
            if (stu.getGrade() == (gradeFind)) {
                grade.add(stu);
            }
        }
        return grade;
    }




}
