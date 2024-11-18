import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

public class Product {
    private String name;
    private String description;
    private String ID;
    private double cost;

    public static final int NAME_LENGTH = 35;
    public static final int DESCRIPTION_LENGTH = 75;
    public static final int ID_LENGTH = 6;

    public Product(String ID, String firstName, String lastName, String Title, int YOB) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    public Product(String id, String name, String desc, double cost) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String toCSV() {
        return name + "," + description + "," + ID + "," + cost;
    }

    public String toJSON() {
        String retString = "";
        char DQ = '"';
        retString = "{" + DQ + "name" + DQ + ":" + DQ + this.name + DQ + ",";
        retString += DQ + "description" + DQ + ":" + DQ + this.description + DQ + ",";
        retString += " " + DQ + "ID" + DQ + ":" + DQ + this.ID + DQ + ",";
        retString += " " + DQ + "cost" + DQ + ":" + DQ + this.cost + DQ + ",";

        return retString;
    }

    public String toXML() {
        String retString = "";

        retString = "<Product>" + "<name>" + this.name + "</name>";
        retString += "<description>" + this.description + "</description>";
        retString += "<ID>" + this.ID + "</ID>";
        retString += "<cost>" + this.cost + "</cost></Product>";

        return retString;
    }

    private static String formatField(String field, int length) {
        if (field == null) {
            field = ""; // Replace null with an empty string
        }

        if (field.length() > length) {
            return field.substring(0, length); // Truncate if too long
        } else {
            return String.format("%-" + length + "s", field); // Pad with spaces
        }
    }

    public void writeToFile(RandomAccessFile file) throws IOException {
        file.writeChars(formatField(name, NAME_LENGTH));
        file.writeChars(formatField(description, DESCRIPTION_LENGTH));
        file.writeChars(formatField(ID, ID_LENGTH));
        file.writeDouble(cost);
    }

    public static Product readFromFile(RandomAccessFile file) throws IOException {
        char[] nameChars = new char[NAME_LENGTH];
        for (int i = 0; i < NAME_LENGTH; i++) {
            nameChars[i] = file.readChar();
        }
        char[] descriptionChars = new char[DESCRIPTION_LENGTH];
        for (int i = 0; i < DESCRIPTION_LENGTH; i++) {
            descriptionChars[i] = file.readChar();
        }
        char[] IDChars = new char[ID_LENGTH];
        for (int i = 0; i < ID_LENGTH; i++) {
            IDChars[i] = file.readChar();
        }
        double cost = file.readDouble();
        return new Product(new String(nameChars).trim(), new String(descriptionChars).trim(),
                new String(IDChars).trim(), cost);
    }
}