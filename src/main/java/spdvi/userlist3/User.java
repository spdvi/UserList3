package spdvi.userlist3;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    public URL getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(URL profilePicture) {
        this.profilePicture = profilePicture;
    }
    
    public User(int id, String firstName, String lastName, LocalDate birthDate, String gender, boolean isAlive, URL imageFileName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.isAlive = isAlive;
        this.profilePicture = imageFileName;
        
    }
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private boolean isAlive;
    private URL profilePicture;
    
    
    public String toString(){
        StringBuilder strUser = new StringBuilder();
        strUser.append(this.id + ": " + this.lastName + ", " + this.firstName + " - ");
        int age = LocalDateTime.now().getYear() - this.birthDate.getYear();
        strUser.append(age + " years old - " + this.gender);
        String alive = this.isAlive ? alive = ", Alive" : ", Dead";
        strUser.append(alive + ",");
//        strUser.append(profilePicture.toString());        
        strUser.append(System.lineSeparator());
        return strUser.toString();
    }
}
