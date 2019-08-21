import java.io.Serializable;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor extends Human implements Serializable {

    private String specialization;
    private ArrayList<Patient> patientList = new ArrayList<Patient>();

    @JsonCreator
    public Doctor(@JsonProperty("name") String name, @JsonProperty("lastName") String lastName,
                  @JsonProperty("passportNumber") String passportNumber, @JsonProperty("specialization") String specialization){
        super(name, lastName, passportNumber);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

    public void print(){
        System.out.println("Doctor information - " + getName()+ " " + getLastName() + " " + specialization + " " + getPassportNumber());
    }

    public void printStatistics(){
        System.out.print(getName() + " " + getLastName() + " " + specialization + "-");
        for(int i = 0; i < patientList.size(); i++) {
            (patientList.get(i)).print();
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + getName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", passportNumber='" + getPassportNumber() + '\'' +
                '}'+",";
    }

}