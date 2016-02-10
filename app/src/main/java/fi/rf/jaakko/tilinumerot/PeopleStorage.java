package fi.rf.jaakko.tilinumerot;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by jaakko on 10.2.2016.
 */
public class PeopleStorage {

    private ArrayList<Person> people;
    private Context context;
    private String filename;

    public PeopleStorage(Context context, String filename) {
        this.context = context;
        this.people = new ArrayList<>();
        this.filename = filename;

        File file = context.getFileStreamPath(filename);
        if (!file.exists()) {
            new File(file.getPath());
        }

        try {
            FileInputStream inputStream = context.openFileInput(filename);
            Scanner reader = new Scanner(inputStream);
            String line;

            while (reader.hasNextLine()) {
                line = reader.nextLine();

                String[] parts = line.split(";");
                this.people.add(new Person(parts[0], parts[1]));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void add(Person person) {
        this.people.add(person);

        Collections.sort(this.people);

        saveData();
    }

    public Person get(int i) {
        return this.people.get(i);
    }

    public int size() {
        return this.people.size();
    }

    public String toString() {
        return TextUtils.join("\n", this.people);
    }


    private void saveData() {
        try {
            FileOutputStream out = context.openFileOutput(this.filename, Context.MODE_PRIVATE);

            out.write(this.toString().getBytes());
            out.close();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
