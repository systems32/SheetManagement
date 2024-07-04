import java.util.List;

public class StudentInfo {
    final String name;
    final int grade;
    final String dateJoined;
    final int idNumber;
    private List<String> contact;
    private List<String> forms;
    private List<Integer> dues;
    private List<String> attendence;

    public StudentInfo(String name, int grade, int idNumber, String dateJoined, List<String> contact,
                       List<String> forms, List<Integer> dues, List<String> attendance) {
        this.name = name;
        this.grade = grade;
        this.idNumber = idNumber;
        this.dateJoined = dateJoined;
        this.contact = contact;
        this.forms = forms;
        this.dues = dues;
        this.attendence = attendance;

    }

    public String studentProfile() {
        int spaceIndex = dateJoined.indexOf(' ');

        return "Student: " + name + " | " + grade + "th grade | ID: " + idNumber + "\n" +
                "   Joined: " + dateJoined.substring(0, spaceIndex) + "\n" +
                "   Contact Info: " + contact;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public List<String> getContact() {
        return contact;
    }

    public List<String> getForms() {
        return forms;
    }

    public List<Integer> getDues() {
        return dues;
    }

    public List<String> getAttendance() {
        return attendence;
    }

    public void changeContact(int type, String contactChange) {
        if (type == 0) {
            contact.set(0, contactChange);
        } else if (type == 1) {
            contact.set(1, contactChange);
        } else if (type == 2) {
            contact.set(2, contactChange);
        }
    }


}
