package contacts;

import java.time.LocalDate;
import java.util.Arrays;

public class Person extends Record {
    public static enum Gender {
        M, F
    }

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;

    private String getFirstName() {
        return firstName;
    }
    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    private String getLastName() {
        return lastName;
    }
    private void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private String getBirthday() {
        return birthday == null ? "[no data]" : birthday.toString();
    }
    private boolean setBirthday(String birthday) {
        try {
            this.birthday = LocalDate.parse(birthday);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    private String getGender() {
        return gender == null ? "[no data]" : gender.toString();
    }
    private boolean setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Person() {
        super();
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }
    @Override
    public String getInfo() {
        String info = "";
        info += "Name: " + firstName + "\n";
        info += "Surname: " + lastName + "\n";
        info += "Birth date: " + (birthday == null ? "[no data]" : birthday) + "\n";
        info += "Gender: " + (gender == null ? "[no data]" : gender) + "\n";
        info += "Number: " + getFieldValue("mobileNumber") + "\n";
        info += "Time created: " + getCreatedTime().withNano(0).withSecond(0).toString() + "\n";
        info += "Time last edit: " + getEditedTime().withNano(0).withSecond(0).toString() + "\n";
        return info;
    }
    @Override
    public String[] getFields() {
        String[] result = new String[] {
                "firstName",
                "lastName",
                "birthday",
                "gender"
        };
        return Utils.concateStringArrays(result, super.getFields());
    }
    @Override
    public String[] getFieldsToEdit() {
        String[] result = new String[] {
                "firstName",
                "lastName",
                "birthday",
                "gender"
        };
        return Utils.concateStringArrays(result, super.getFieldsToEdit());
    }
    @Override
    public boolean editField(String field, String value) {
        boolean result = super.editField(field, value);
        switch (field) {
            case "firstName":
                setFirstName(value);
                result = true;
                break;
            case "lastName":
                setLastName(value);
                result = true;
                break;
            case "birthday":
                result = setBirthday(value);
                break;
            case "gender":
                result = setGender(value);
                break;
        }
        return result;
    }
    @Override
    public String getFieldValue(String field) {
        String result = super.getFieldValue(field);
        switch (field) {
            case "firstName":
                result = getFirstName();
                break;
            case "lastName":
                result = getLastName();
                break;
            case "birthday":
                result = getBirthday();
                break;
            case "gender":
                result = getGender();
                break;
        }
        return result;
    }
}
