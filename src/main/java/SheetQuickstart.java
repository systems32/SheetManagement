import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class SheetQuickstart {
    private static final String APPLICATION_NAME = "Google Sheets API Java Sheet";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    //private static Hashtable<String, List<Object>> students = new Hashtable<>();

    private static List<StudentInfo> student = new ArrayList<>();
    private static List<StudentInfo> studentSoph = new ArrayList<>();
    private static List<StudentInfo> studentJr = new ArrayList<>();
    private static List<StudentInfo> studentSr = new ArrayList<>();

    private static int dateIndex = -1;
    private static int lastIndex = -1;
    private static int firstIndex = -1;
    private static int studentIndex = -1;
    private static int gradeIndex = -1;
    private static int schoolIndex = -1;
    private static int personalIndex = -1;
    private static int phoneIndex = -1;
    private static int submitIndex = -1;
    private static int meetingIndex = -1;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
        Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  
    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
        throws IOException {
      // Load client secrets.
      InputStream in = SheetQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
      if (in == null) {
        throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
      }
      GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
  
      // Build flow and trigger user authorization request.
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
          HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
          .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
          .setAccessType("offline")
          .build();
      LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
      return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1Gwa7WHjNi6IxzNxR66-woYGG1sY3k8MODRlpWMXMCew";
        final String range = "ALL Members!A2:AU";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();


        ValueRange sheetTitles = returnValues(spreadsheetId, "ALL Members!A1:AU1", service);
        ValueRange response = returnValues(spreadsheetId, range, service);

        List<List<Object>> values = response.getValues();
        List<List<Object>> titles = sheetTitles.getValues();

        List<Object> titleRow = titles.get(0);

        for (int i = 0; i < titleRow.size(); i++) {
            String column = titleRow.get(i).toString().toLowerCase();

            if (column.contains("date")) {
                dateIndex = i;
            } else if (column.contains("last")) {
                lastIndex = i;
            } else if (column.contains("first")) {
                firstIndex = i;
            } else if (column.contains("student id")) {
                studentIndex = i;
            } else if (column.contains("grade")) {
                gradeIndex = i;
            } else if (column.contains("school")) {
                schoolIndex = i;
            } else if (column.contains("personal")) {
                personalIndex = i;
            } else if (column.contains("phone")) {
                phoneIndex = i;
            } else if (column.contains("submitted")) {
                submitIndex = i;
            } else if (column.contains("meeting")) {
                meetingIndex = i;
            }
        }

        //List<StudentInfo> student = new ArrayList<>();

        if (values == null || values.isEmpty()) {
            System.out.println("No Data Found");
        } else {
            System.out.println(titles);
            try {
                for (List<Object> row : values) {
                    String name = row.get(firstIndex).toString() + " " + row.get(lastIndex).toString();
                    //System.out.println(row.get(4));
                    int grade = Integer.parseInt(row.get(gradeIndex).toString());
                    int id = Integer.parseInt(row.get(studentIndex).toString());
                    String date = row.get(dateIndex).toString();
                    List<String> contact = new ArrayList<>(
                            Arrays.asList(row.get(schoolIndex).toString(), row.get(personalIndex).toString(),
                                    row.get(phoneIndex).toString())
                    );
                    student.add(new StudentInfo(name, grade, id, date, contact, new ArrayList<>(), new ArrayList<>(),
                            new ArrayList<>()));
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Sheet not formatted correctly");
                System.exit(0);
            }
        }

        studentSoph = listByGrade(10);
        studentJr = listByGrade(11);
        studentSr = listByGrade(12);

        Collections.sort(studentSoph, Comparator.comparing(StudentInfo -> StudentInfo.name));
        Collections.sort(studentJr, Comparator.comparing(StudentInfo -> StudentInfo.name));
        Collections.sort(studentSr, Comparator.comparing(StudentInfo -> StudentInfo.name));
        for (StudentInfo stud : studentSoph) {
            System.out.println(stud.studentProfile());
        } for (StudentInfo stud : studentJr) {
            System.out.println(stud.studentProfile());
        } for (StudentInfo stud : studentSr) {
            System.out.println(stud.studentProfile());
        }

        System.out.println("Welcome to NHS Sheet Management");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("done") || userInput.equalsIgnoreCase("close")) {
                break;
            }


        }

    }

    public static ValueRange returnValues(String spreadsheetId, String range, Sheets service) throws IOException {
        return service.spreadsheets().values().get(spreadsheetId, range).execute();
    }

    private static List<StudentInfo> listByGrade(int gradeFind) {
        List<StudentInfo> grade = new ArrayList<>();
        for (StudentInfo stu : student) {
            if (stu.getGrade() == (gradeFind)) {
                grade.add(stu);
            }
        }
        return grade;
    }




}