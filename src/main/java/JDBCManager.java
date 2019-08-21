import java.sql.*;
import java.util.ArrayList;

public class JDBCManager {
    private String url = "jdbc:sqlserver://localhost:1433;database=DB";
    private String user = "lg";
    private String password = "1234";

    public JDBCManager(String url, String user, String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }
    public int checkIfIsAnyDoctorByPassport(String passportNumber){
        int number=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Doctor where PassportNumber=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,passportNumber);
            Printer printer = new Printer();
            number = printer.printDoctor(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }
    public int checkIfIsAnyPatientByPassport(String passportNumber){
        int number=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Patient where PassportNumber=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,passportNumber);
            Printer printer = new Printer();
            number = printer.printPatient(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  number;
    }

    public int checkIfIsAnyDoctorByName(String name, String lastName){
        int number=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Doctor where [Name]=? and LastName =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,lastName);
            Printer printer = new Printer();
            number = printer.printDoctorandReturnId(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  number;
    }
    public int checkIfIsAnyPatientByName(String name, String lastName){
        int number=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Patient where [Name]=? and LastName =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,lastName);
            Printer printer = new Printer();
            number = printer.printPatient(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  number;
    }

    public int getIdOfDoctorByPassportNumber(String doctorPassportNumber){
        int id = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select [ID] from Doctor where PassportNumber=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,doctorPassportNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getIdOfDoctorByName(String doctorName, String doctorLastName){
        int id = 0;
        System.out.println(doctorLastName+" "+ doctorName);
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select [ID] from Doctor where [Name]=? and LastName=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,doctorName);
            statement.setString(2,doctorLastName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void getDoctorsFromDB() {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Doctor";
            PreparedStatement statement = connection.prepareStatement(query);
            Printer printer = new Printer();
            printer.printDoctor(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPatientsFromDB() {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Patient";
            PreparedStatement statement = connection.prepareStatement(query);
            Printer printer = new Printer();
            printer.printPatient(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean ifDoctorhasPatient(int doctorID) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="Select * from Patient where [DoctorId]=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,doctorID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getPatientsGroupByDoctorsFromDB() {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="SELECT Doctor.[Name], Doctor.LastName, Doctor.PassportNumber, Doctor.Specialization, Patient.[Name], " +
                    " Patient.LastName, Patient.PassportNumber, Patient.Illness " +
                    " FROM Doctor " +
                    " Left JOIN Patient ON Doctor.ID=Patient.DoctorID " +
                    " Order by Doctor.[Name], Doctor.LastName, Doctor.PassportNumber, Doctor.Specialization";
            PreparedStatement statement = connection.prepareStatement(query);
            Printer printer = new Printer();
            printer.printPatientsGroupByDoctors(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertDoctorToDB(Doctor doctor)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="INSERT INTO Doctor([Name],[LastName],Specialization, PassportNumber) values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,doctor.getName());
            statement.setString(2,doctor.getLastName());
            statement.setString(3,doctor.getSpecialization());
            statement.setString(4,doctor.getPassportNumber());
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }System.out.println("Inserted "+ result + " rows");
        return result;
    }

    public int insertPatientToDB(Patient patient)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="INSERT INTO Patient([Name],[LastName], Illness, PassportNumber, DoctorId) values (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,patient.getName());
            statement.setString(2,patient.getLastName());
            statement.setString(3,patient.getIllness());
            statement.setString(4,patient.getPassportNumber());
            statement.setInt(5,patient.getDoctorId());
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Inserted "+ result + " rows");
        return result;
    }

    public int deleteDoctorFromDBWithName(String name, String lastName)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="DELETE FROM Doctor WHERE [Name]=? AND [LastName]=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,lastName);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Deleted "+ result + " rows");
        return result;
    }

    public int deleteDoctorFromDBWithPassportNumber(String passportNumber)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="DELETE FROM Doctor WHERE [passportNumber]=? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,passportNumber);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Deleted "+ result + " rows");
        return result;
    }

    public int deletePatientFromDBByPassportNumber(String passportNumber)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="DELETE FROM Patient WHERE [PassportNumber]=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,passportNumber);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Deleted "+ result + " rows");
        return result;
    }

    public int deletePatientFromDBByName(String name, String lastName)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="DELETE FROM Patient WHERE [Name]=? AND [LastName]=? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,lastName);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Deleted "+ result + " rows");
        return result;
    }
    public int UpdatePatientsDoctorByPassportNumber(String doctorPassportNumber, int doctorId){
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="UPDATE Patient SET [DoctorID]=? WHERE [PassportNumber]=? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,doctorId);
            statement.setString(2,doctorPassportNumber);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated "+ result + " rows");
        return result;
    }

    public int UpdatePatientsDoctorByName(String name,String lastName, int doctorId){
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="UPDATE Patient SET [DoctorID]=? WHERE [Name]=? AND LastName=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,doctorId);
            statement.setString(2,name);
            statement.setString(3,lastName);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated "+ result + " rows");
        return result;
    }
    public int updatePatientInDB(Patient oldPatient, Patient newPatient)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="UPDATE Patient SET [Name]=?,[LastName]=?,Illness=? WHERE [Name]=? and [LastName]=? and Illness=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,oldPatient.getName());
            statement.setString(2,oldPatient.getLastName());
            statement.setString(3,oldPatient.getIllness());
            statement.setString(4,newPatient.getName());
            statement.setString(5,newPatient.getLastName());
            statement.setString(6,newPatient.getIllness());
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated "+ result + " rows");
        return result;
    }

    public int updateDoctorInDB(Doctor oldDoctor, Doctor newDoctor)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            String query="UPDATE Doctor SET [Name]=?,[LastName]=?,Specialization=? WHERE [Name]=? and [LastName]=? and Specialization=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,oldDoctor.getName());
            statement.setString(2,oldDoctor.getLastName());
            statement.setString(3,oldDoctor.getSpecialization());
            statement.setString(4,newDoctor.getName());
            statement.setString(5,newDoctor.getLastName());
            statement.setString(6,newDoctor.getSpecialization());
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated "+ result + " rows");
        return result;
    }

}
/*
preparedstatmenty dinamic query het uxarkelu res
ete tryum enq haytararum darnuma filnal vor reference chkkori
 toxelov next dbuic kardal

 delete aneluc studel primaty key chlini nor

 */
