package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

abstract class Record implements Serializable {
    private String mobileNumber = "";
    private final LocalDateTime createdTime;
    private LocalDateTime editedTime;
    private boolean created = false;

    private String getMobileNumber() {
        if (isHasNumber()) {
            return mobileNumber;
        } else {
            return "[no number]";
        }
    }
    private boolean setMobileNumber(String mobileNumber) {
        this.mobileNumber = "";
        String[] blocks = mobileNumber.split("[-\\s]");
        boolean error = false;
        for (int i = 0; i < blocks.length; i++) {
            if (i == 0) {
                if (!blocks[i].matches("\\+?(\\(\\w+\\)|\\w+)")) {
                    error = true;
                }
            } else if (i == 1) {
                if (!blocks[i].matches("(\\(\\w{2,}\\)|\\w{2,})")) {
                    error = true;
                }
            } else {
                if (!blocks[i].matches("\\w{2,}")) {
                    error = true;
                }
            }
        }
        if (mobileNumber.split("\\(").length > 2) {
            error = true;
        }
        if (!error) {
            this.mobileNumber = mobileNumber;
            return true;
        } else {
            return false;
        }
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public LocalDateTime getEditedTime() {
        return editedTime;
    }
    protected void setEditedTime() {
        editedTime = LocalDateTime.now();
    }
    public boolean isHasNumber() {
        return !"".equals(mobileNumber);
    }
    public void create() {
        created = true;
    }

    public Record() {
        createdTime = LocalDateTime.now();
        editedTime = createdTime;
    }

    abstract String getFullName();
    abstract String getInfo();
    public String[] getFields() {
        return new String[] {
                "mobileNumber",
                "createdTime",
                "editedTime"
        };
    }
    public String[] getFieldsToEdit() {
        return new String[] {
                "mobileNumber"
        };
    }
    public boolean editField(String field, String value) {
        if (created) {
            editedTime = LocalDateTime.now();
        }
        boolean result = false;
        switch (field) {
            case "mobileNumber":
                result = setMobileNumber(value);
                break;
        }
        return result;
    }
    public String getFieldValue(String field) {
        String result = null;
        switch (field) {
            case "mobileNumber":
                result = getMobileNumber();
                break;
        }
        return result;
    }
}
