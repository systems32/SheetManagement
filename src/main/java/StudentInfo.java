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

    private List<String> warn;

    public StudentInfo(String name, int grade, int idNumber, String dateJoined, List<String> contact,
                       List<String> forms, List<Integer> dues, List<String> attendance, List<String> meeting,
                       List<String> warn) {
        this.name = name;
        this.grade = grade;
        this.idNumber = idNumber;
        this.dateJoined = dateJoined;
        this.contact = contact;
        this.forms = forms;
        this.dues = dues;
        this.attendence = formatAttendance(attendance, meeting);
        this.warn = warn;

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

    public String getDateJoined() { return dateJoined; }

    public int getIdNumber() { return idNumber; }

    public String getEmails() {return contact.get(0) + ", " + contact.get(1); }

    public String getSchoolEmail() {return contact.get(0); }

    public List<String> getContact() { return contact; }

    public List<String> getForms() {return forms;}

    public List<Integer> getDues() { return dues; }

    public List<String> getAttendance() { return attendence; }

    public List<String> getWarn() { return warn; }

    public void changeContact(int type, String contactChange) {
        if (type == 0) {
            contact.set(0, contactChange);
        } else if (type == 1) {
            contact.set(1, contactChange);
        } else if (type == 2) {
            contact.set(2, contactChange);
        }
    }

    private List<String> formatAttendance(List<String> attendance, List<String> meetings) {
        for (int i = 0; i < attendance.size(); i++) {
            if (isNumeric(attendance.get(i)) || attendance.get(i).equalsIgnoreCase("x")) {
                attendance.set(i, meetings.get(i) + " - " + "x");
            } else {
                if (attendance.get(i).equalsIgnoreCase("")) {
                    attendance.set(i, meetings.get(i) + " - " + "didn't attend");
                } else {
                    attendance.set(i, meetings.get(i) + " - " + attendance.get(i));
                }
            }
        }
        return attendance;
    }

    /*
    private List<String> formatWarnings (List<String> warn, List<String> meetings) {
        for (int i = 0; i < warn.size(); i++) {
            if (isNumeric(attendance.get(i)) || attendance.get(i).equalsIgnoreCase("x")) {

            } else {

            }
        }
        return attendance;
    }

     */

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // For integers
            // Double.parseDouble(str); // For floating point numbers
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}
