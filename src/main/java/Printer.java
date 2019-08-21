import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Printer {

    public int printPatient(ResultSet resultSet) {
        int number = 0;
        try {
            while (resultSet.next()) {
                number++;
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                String specialization = resultSet.getString("Illness");
                String passportNumber = resultSet.getString("PassportNumber");
                int doctorID = resultSet.getInt("DoctorID");
                System.out.println("Name - " + name + ", Lastname - " + lastName +
                        ", Specialization - " + specialization + ", PassportNumber - " + passportNumber
                        + ", DoctorID - " + doctorID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }

    public int printDoctor(ResultSet resultSet) {
        int number = 0;
        try {
            while (resultSet.next()) {
                number++;
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                String specialization = resultSet.getString("Specialization");
                String passportNumber = resultSet.getString("PassportNumber");
                System.out.println("Name - " + name + ", Lastname - " + lastName +
                        ", Specialization - " + specialization + ", PassportNumber - " + passportNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }

    public int printDoctorandReturnId(ResultSet resultSet) {
        int id = -1;
        int number = 0;
        try {
            while (resultSet.next()) {
                number++;
                id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                String specialization = resultSet.getString("Specialization");
                String passportNumber = resultSet.getString("PassportNumber");
                System.out.println("Name - " + name + ", Lastname - " + lastName +
                        ", Specialization - " + specialization + ", PassportNumber - " + passportNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(number > 1)
            return 0;
        return  id;
    }

    public int printPatientsGroupByDoctors(ResultSet resultSet) {
        int number = 0;
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            number = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= number; i++) {
                    if (i > 1) System.out.print(", ");
                    if (i > 4) System.out.print("Patient ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i)+" - "+columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;

    }
}