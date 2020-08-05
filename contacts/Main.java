package contacts;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static RecordsBook book;

    public static void main(String[] args) {
        if (args.length != 0) {
            book = load(args[0]);
        }

        book = new RecordsBook();
        while (Menu.selector()) {}

        if (args.length != 0) {
            try {
                save(book, args[0]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    static class Menu {
        static boolean selector() {
            System.out.print("[menu] Enter action(add, list, search, count, exit): >> ");
            String command = scan.nextLine();
            switch (command) {
                case "exit":
                    return false;
                case "add":
                    addRecord();
                    break;
                case "list":
                    ListMenu.listRecords();
                    break;
                case "search":
                    SearchMenu.search();
                    break;
                case "count":
                    RecordMenu.count();
                    break;
                default:
                    System.out.println("Command not found.");
                    break;
            }
            System.out.println();
            return true;
        }

        static void addRecord() {
            System.out.print("Enter a type (person, organization) >> ");
            String choose = scan.nextLine();
            contacts.Record rec;
            if (choose.equals("person")) {
                rec = new Person();
            } else if (choose.equals("organization")) {
                rec = new Organization();
            } else {
                System.out.println("Wrong type!");
                return;
            }
            String[] fields = rec.getFieldsToEdit();
            for (String field : fields) {
                System.out.printf("Enter a %s: >> ", field);
                String value = scan.nextLine();
                boolean result = rec.editField(field, value);
                if (!result) {
                    System.out.println("Something wrong!");
                }
            }
            rec.create();
            book.addRecord(rec);
            System.out.println("A record added.");
        }

        static class SearchMenu {
            static void selector(Record[] results) {
                System.out.print("[search] Enter action([number], back, again): >> ");
                String command = scan.nextLine();
                switch (command) {
                    case "back":
                        break;
                    case "again":
                        search();
                        break;
                    default:
                        if (command.matches("\\d+")) {
                            int index = Integer.parseInt(command) - 1;
                            if (index < 0 || index > results.length) {
                                System.out.println("Record not found!\n");
                                selector(results);
                                break;
                            }
                            RecordMenu.getInfo(results[index]);
                        } else {
                            System.out.println("Command not Found! Leave to menu!");
                        }
                }
            }

            static void search() {
                System.out.print("Enter search query: >> ");
                String query = scan.nextLine();
                Record[] results = book.search(query);
                if (results.length == 0) {
                    System.out.println("Results not found!");
                    return;
                }
                int counter = 1;
                for (Record rec : results) {
                    System.out.printf("%d. %s\n", counter, rec.getFullName());
                }
                System.out.println();
                selector(results);
            }
        }
        static class ListMenu {
            static void selector() {
                System.out.print("[list] Enter action ([number], back): >> ");
                String command = scan.nextLine();
                switch (command) {
                    case "back":
                        return;
                    default:
                        if (command.matches("\\d+")) {
                            RecordMenu.getInfo(Integer.parseInt(command) - 1);
                        } else {
                            System.out.println("Command not Found! Leave to menu!");
                        }
                }
            }

            static void listRecords() {
                String listRecords = book.getListRecords();
                if (listRecords.equals("")) {
                    System.out.println("No records added.");
                } else {
                    System.out.print(listRecords);
                    System.out.println();
                    selector();
                }
            }
        }
        static class RecordMenu {
            static void selector(Record rec) {
                System.out.print("[record] Enter action (edit, delete, menu): >> ");
                String command = scan.nextLine();
                switch (command) {
                    case "edit":
                        String[] fields = rec.getFieldsToEdit();
                        System.out.printf("Select field to edit (%s): >> ",
                                Arrays.toString(fields).replaceAll("[\\[\\]]", ""));
                        String field = scan.nextLine();
                        System.out.print("Enter value: >> ");
                        String value = scan.nextLine();
                        boolean result = rec.editField(field, value);
                        if (!result) {
                            System.out.println("Something wrong!");
                        } else {
                            System.out.println("Saved.");
                        }
                        getInfo(rec);
                        break;
                    case "delete":
                        book.removeRecord(rec);
                        System.out.println("Deleted successful");
                    case "menu":
                        break;
                    default:
                        System.out.println("Command not Found! Leave to menu!");
                }
            }

            static void getInfo(int index) {
                if (index >= book.getCountRecords() || index < 0) {
                    System.out.println("Record Not Found! Leave to menu.");
                    return;
                }
                contacts.Record rec = book.getRecordByIndex(index);
                getInfo(rec);
            }
            static void getInfo(Record rec) {
                System.out.print(rec.getInfo());
                System.out.println();
                selector(rec);
            }
            static void count() {
                int count = book.getCountRecords();
                System.out.printf("The Phone Book has %d records.\n", count);
            }
        }
    }

    static void save(Object obj, String fileName) throws IOException {
        FileOutputStream file = new FileOutputStream("./" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    static RecordsBook load(String fileName) {
        try {
            FileInputStream fis = new FileInputStream("./" + fileName);
            BufferedInputStream bos = new BufferedInputStream(fis);
            ObjectInputStream oos = new ObjectInputStream(bos);
            return (RecordsBook) oos.readObject();
        } catch (Exception ex) {
            return new RecordsBook();
        }
    }
}
