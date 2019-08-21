import java.io.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.util.*;

public class JSONParser {

    public void addPatientToFile(String fileName, ArrayList<Patient> patients, ObjectMapper mapper){
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            mapper.writeValue(fileWriter, patients);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addDoctorToFile(String fileName, ArrayList<Doctor> doctors, ObjectMapper mapper){
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            mapper.writeValue(fileWriter, doctors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Patient> readPatientsFromFile(String fileName, ObjectMapper mapper){
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        File file = new File(fileName);
        Patient[] asArray = null;
        try{
            patientList = mapper.readValue(file, new TypeReference<ArrayList<Patient>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    public ArrayList<Doctor> readDoctorsFromFile(String fileName, ObjectMapper mapper){
        ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
        File file = new File(fileName);
        Doctor[] asArray = null;
        try{
            doctorList = mapper.readValue(file, new TypeReference<ArrayList<Doctor>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorList;
    }

}