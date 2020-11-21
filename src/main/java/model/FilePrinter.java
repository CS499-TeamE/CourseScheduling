package model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FilePrinter
{
    private Department dept;
    private Schedule schedule;
    private File file;

    public FilePrinter(Department dept, Schedule finalSchedule, File file) throws IOException
    {
        this.dept = dept;
        this.schedule = finalSchedule;
        this.file = file;
    }


    public void csvPrinter(CSVFormat csvFormat)
    {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), csvFormat)) {
            printer.printRecord("Department: ", dept.getDepartmentName(), "\n");
            printer.printRecord("Location: ", dept.getDepartmentLocation(), "\n");
            printer.println();
            printer.println();
            printer.println();
            printer.printRecord("Meeting Times/Days", "Monday", "Tuesday", "Wednesday", "Thursday");
            for(String time : dept.getMeetingTimes().get(0).getTimes())
            {
                List<String> record = buildCSVRecord(time);
                printer.printRecord(record);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void tsvPrinter(CSVFormat tsvFormat)
    {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), tsvFormat)) {
            printer.printRecord("Department: ", dept.getDepartmentName(), "\n");
            printer.printRecord("Location: ", dept.getDepartmentLocation(), "\n");
            printer.println();
            printer.println();
            printer.println();
            printer.printRecord("Meeting Times/Days", "Monday", "Tuesday", "Wednesday", "Thursday");
            for(String time : dept.getMeetingTimes().get(0).getTimes())
            {
                List<String> record = buildCSVRecord(time);
                printer.printRecord(record);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List buildCSVRecord(String meetingTime)
    {
        List<String> record = new ArrayList<>();
        record.add(meetingTime);

        String cell1 = "Course  |" + "  Max Attendance  |" + "  Room    |" + "  Room Capacity   |" + "  Professor                |" + "  Meeting Time\n" +
                        "================================================================================\n";    // Format to a single cell
        String cell2 = "Course  |" + "  Max Attendance  |" + "  Room    |" + "  Room Capacity   |" + "  Professor                |" + "  Meeting Time\n" +
                        "================================================================================\n";
        String cell3 = "Course  |" + "  Max Attendance  |" + "  Room    |" + "  Room Capacity   |" + "  Professor                |" + "  Meeting Time\n" +
                        "================================================================================\n";
        String cell4 = "Course  |" + "  Max Attendance  |" + "  Room    |" + "  Room Capacity   |" + "  Professor                |" + "  Meeting Time\n" +
                        "================================================================================\n";

        for(PossibleClass course : schedule.getClassList()) // For each course in the schedule
        {
            if(course.getMeetingTime().contains(meetingTime)) // Narrow it down by meeting time to determine if it is in the right time frame
            {
                if(course.getMeetingTime().contains(dept.getMeetingTimes().get(0).getDay())) // M/W
                {
                    cell1 = cell1.concat(course.getClassInfo() + "\n");
                    cell3 = cell3.concat(course.getClassInfo() + "\n");
                }
                else if(course.getMeetingTime().contains(dept.getMeetingTimes().get(1).getDay())) // T/TH
                {
                    cell2 = cell2.concat(course.getClassInfo() + "\n");
                    cell4 = cell4.concat(course.getClassInfo() + "\n");
                }
            }
        }
        cell1.concat(",,,");      // Finish formatting to a single cell
        cell2.concat(",,,");
        cell3.concat(",,,");
        cell4.concat(",,,");

        record.add(cell1);
        record.add(cell2);
        record.add(cell3);
        record.add(cell4);

        return record;
    }


}
