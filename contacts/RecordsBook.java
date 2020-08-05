package contacts;

import java.io.Serializable;
import java.util.*;

public class RecordsBook implements Serializable {
    private ArrayList<Record> records = new ArrayList<>();

    public String getListRecords() {
        String listRecords = "";
        int counter = 1;
        for (Record rec : records) {
            listRecords += counter + ". ";
            listRecords += rec.getFullName();
            listRecords += "\n";
            counter++;
        }
        return listRecords;
    }

    public Record getRecordByIndex(int index) {
        return records.get(index);
    }

    public int getCountRecords() {
        return records.size();
    }

    public void addRecord(Record rec) {
        records.add(rec);
    }

    public void removeRecord(Record rec) {
        records.remove(rec);
    }

    public Record[] search(String query) {
        ArrayList<Record> results = new ArrayList<>();
        for (Record rec : records) {
            String[] fields = rec.getFields();
            String allInfo = "";
            for (String field : fields) {
                String info = rec.getFieldValue(field);
                allInfo += info == null ? "" : info + " ";
            }
            if (allInfo.toLowerCase().contains(query.toLowerCase())) {
                results.add(rec);
            }
        }
        return results.toArray(new Record[0]);
    }
}
