import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.Month;

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

    private static final Logger logger = LogManager.getLogger(StudentInfo.class);

    public StudentInfo(String name, int grade, int idNumber, String dateJoined, List<String> contact,
                       List<String> forms, List<Integer> dues, List<String> attendance, List<String> meeting,
                       List<String> warn) {
        this.name = name;
        this.grade = grade;
        this.idNumber = idNumber;
        int spaceIndex = dateJoined.indexOf(' ');
        this.dateJoined = dateJoined.substring(0, spaceIndex);
        this.contact = contact;
        this.forms = forms;
        this.dues = dues;
        this.warn = warn;
        this.attendence = formatAttendance(attendance, meeting);
        this.warn = formatWarnings(warn, meeting);

    }

    public String studentProfile() {
        int spaceIndex = dateJoined.indexOf(' ');

        return "Student: " + name + " | " + grade + "th grade | ID: " + idNumber + "\n" +
                "   Joined: " + dateJoined + "\n" +
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
        List<String> attendFormat = new ArrayList<>();

        String specifiedDate = "8/8/2023"; // date for comparison
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("M/d/")
                .appendValueReduced(ChronoField.YEAR, 2, 4, 1900)
                .toFormatter();
        int newMeetNum = 0;

        try {

            for (int i = 0; i < attendance.size(); i++) {
                if (isNumeric(attendance.get(i)) || attendance.get(i).equalsIgnoreCase("x")) {
                    attendFormat.add(meetings.get(i) + " - " + "X");
                } else {
                    if (attendance.get(i).equalsIgnoreCase("")) {
                        if (meetings.get(i).contains("new") || meetings.get(i).contains("workshop")) {

                            LocalDate joinedDate = LocalDate.parse(dateJoined, formatter);
                            LocalDate today = LocalDate.now();
                            LocalDate august8thCurrentYear = LocalDate.of(today.getYear(), Month.AUGUST, 10);
                            LocalDate specDate1 = today.isBefore(august8thCurrentYear) ?
                                    august8thCurrentYear : august8thCurrentYear.plusYears(1);
                            LocalDate jan5thCurrentYear = LocalDate.of(today.getYear(), Month.JANUARY, 5);
                            LocalDate specDate2 = today.isBefore(jan5thCurrentYear) ?
                                    jan5thCurrentYear : jan5thCurrentYear.plusYears(1);

                            if (joinedDate.isAfter(specDate1) && joinedDate.isBefore(specDate2) && newMeetNum == 0) {
                                attendFormat.add(meetings.get(i) + " - " + "didn't attend");
                                if (warn.get(i).equalsIgnoreCase("")) {
                                    warn.set(i, "warning not sent");
                                }
                            } else if (joinedDate.isAfter(specDate2) && newMeetNum == 1) {
                                attendFormat.add(meetings.get(i) + " - " + "didn't attend");
                                if (warn.get(i).equalsIgnoreCase("")) {
                                    warn.set(i, "warning not sent");
                                }
                            } else if (joinedDate.isAfter(specDate1) && joinedDate.isAfter(specDate2)) {
                                i += 3;
                            }
                            newMeetNum += 1;
                        } else {
                            attendFormat.add(meetings.get(i) + " - " + "didn't attend");
                            if (warn.get(i).equalsIgnoreCase("")) {
                                warn.set(i, "warning not sent");
                            }
                        }
                    } else {
                        attendFormat.add(meetings.get(i) + " - " + attendance.get(i));
                    }
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            System.exit(0);
        }

        return attendFormat;
    }


    private List<String> formatWarnings (List<String> warn, List<String> meetings) {
        for (int i = 0; i < warn.size(); i++) {
            if (!warn.get(i).equalsIgnoreCase("")) {
                warn.set(i, meetings.get(i) + " - " + warn.get(i));
            }
        }
        return warn;
    }



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
