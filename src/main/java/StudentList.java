import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentList {

    private List<StudentInfo> studentSet = new ArrayList<>();

    public StudentList(List<StudentInfo> studentSet) {
        this.studentSet = studentSet;
    }

    public Boolean userIn() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter first name followed by last");

            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("done") || userInput.equalsIgnoreCase("close")) {
                return true;
            } else if (userInput.equalsIgnoreCase("exit")) {
                return false;
            }

            List<StudentInfo> foundStudents = findNames(userInput);
            if (foundStudents.isEmpty()) {
                System.out.println("No student found.");
            } else {
                infoStudent(foundStudents);
            }
        }
    }

    public void infoStudent(List<StudentInfo> students)
    {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What info do you want");

            String userInput = scanner.nextLine().strip().toLowerCase();
            switch (userInput) {
                case "exit":
                    return;
                case "info":
                case "":
                    students.forEach(student -> System.out.println(student.studentProfile()));
                    break;
                case "name":
                    students.forEach(student -> System.out.print(student.getName() + ", "));
                    System.out.println();
                    break;
                case "emails":
                    students.forEach(student -> System.out.print(student.getEmails() + ", "));
                    System.out.println();
                    break;
                case "schoolemail":
                    students.forEach(student -> System.out.print(student.getSchoolEmail() + ", "));
                    System.out.println();
                    break;
                case "contact":
                    students.forEach(student -> System.out.print(student.getContact() + ", "));
                    System.out.println();
                    break;
                case "forms":
                    students.forEach(student -> System.out.print(student.getForms() + ", "));
                    System.out.println();
                    break;
                case "attendance":
                    students.forEach(student -> System.out.print(student.getAttendance() + ", "));
                    System.out.println();
                    break;
                case "warnings":
                    students.forEach(student -> System.out.print(student.getWarn() + ", "));
                    System.out.println();
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
                    break;
            }
        }
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
