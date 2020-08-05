package contacts;

public class Organization extends Record {
    private String name;
    private String address;

    private String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    private String getAddress() {
        return address;
    }
    private void setAddress(String address) {
        this.address = address;
    }

    public Organization() {
        super();
    }

    @Override
    public String getFullName() {
        return name;
    }
    @Override
    public String getInfo() {
        String info = "";
        info += "Organization name: " + name + "\n";
        info += "Address: " + address + "\n";
        info += "Number: " + getFieldValue("mobileNumber") + "\n";
        info += "Time created: " + getCreatedTime().toString() + "\n";
        info += "Time last edit: " + getEditedTime().toString() + "\n";
        return info;
    }
    @Override
    public String[] getFields() {
        String[] result = new String[] {
                "name",
                "address"
        };
        return Utils.concateStringArrays(result, super.getFields());
    }
    @Override
    public String[] getFieldsToEdit() {
        String[] result = new String[] {
                "name",
                "address"
        };
        return Utils.concateStringArrays(result, super.getFieldsToEdit());
    }
    @Override
    public boolean editField(String field, String value) {
        boolean result = super.editField(field, value);
        switch (field) {
            case "name":
                setName(value);
                result = true;
                break;
            case "address":
                setAddress(value);
                result = true;
                break;
        }
        return result;
    }
    @Override
    public String getFieldValue(String field) {
        String result = super.getFieldValue(field);
        switch (field) {
            case "name":
                result = getName();
                break;
            case "address":
                result = getAddress();
                break;
        }
        return result;
    }
}
