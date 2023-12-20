package com.example.project1maxledlighting;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PowerManagement {
    private static PowerManagement instance;
    private File signalsFile;
    private int powerCount;
    private int[] ledsArray;
    private int[] powerArray;
    private String valuesTable;//القيم
    private String movementsTable;//الحركات
    private boolean isvalid;
    private ArrayList<Integer> onList = new ArrayList<>();//save number of leds it is on

    private PowerManagement(File signalsFile) {

        this.signalsFile = signalsFile;
        initiate();

    }

    public static PowerManagement getInstance(File signalsFile) {

        if (instance == null) {
            instance = new PowerManagement(signalsFile);
        }
        return instance;

    }

    public void reset() {

        this.instance = null;
        this.signalsFile = null;
        this.powerCount = 0;
        this.ledsArray = null;
        this.powerArray = null;
        this.valuesTable = null;
        this.movementsTable = null;
        this.onList = new ArrayList<>();

    }

    public String getValuesTable() {
        return valuesTable;
    }

    public String getMovementsTable() {
        return movementsTable;
    }

    public ArrayList<Integer> getOnList() {
        return onList;
    }

    public int getPowerCount() {
        return powerCount;
    }

    public int[] getLedsArray() {
        return ledsArray;
    }

    private void initiate() {

        List<String> lines = null;
        try {
            // Attempt to read all lines from the signalsFile and store them in the 'lines' list.
            lines = Files.readAllLines(signalsFile.toPath());
        } catch (IOException e) {
            // If there is an IOException (problem opening the file), print a message.
            System.out.println("Problem in opening the file");
        }
        // Parse the first line of the file as an integer and assign it to the variable 'powerCount'.
        powerCount = Integer.parseInt(lines.get(0));
        // Split the second line of the file using commas as separators and store the result in 'ledsStringArray'.
        String[] ledsStringArray = lines.get(1).split(",");
        // Convert the array of strings to an array of integers and store it in 'ledsArray'.
        ledsArray = Arrays.stream(ledsStringArray).mapToInt(Integer::parseInt).toArray();

        // Create an array 'powerArray' containing integers from 1 to 'powerCount'.
        powerArray = IntStream.rangeClosed(1, powerCount).toArray();
        // Check if there are duplicate values in 'ledsArray'.
        if(Arrays.stream(ledsArray).distinct().count()!=ledsArray.length){
            isvalid=false;
            return;
        }
//        else{
//            isvalid=true;
//        }


    else if (powerCount!=ledsArray.length) {
        // Check if the number of LEDs is less than the number of power sources.
        isvalid = false;
        System.out.println("Number of LEDs is less than the number of power sources");
        return;
    }
        else{

            isvalid=true;
        }
        //powerArray = IntStream.rangeClosed(1, powerCount).toArray();
        process();

    }
    public boolean isValid(){
        return isvalid;
    }
    public void process() {//ledscene
        // Create a 2D array 'b' with dimensions (powerCount + 1) x (powerCount + 1).
        String[][] b = new String[powerCount + 1][powerCount + 1];
        // Call the 'MLL' method with 'powerArray', 'ledsArray', and 'b' as parameters.
        // The result is stored in the 'length' variable.
        int length = MLL(powerArray, ledsArray, b);

        // Call the 'print_MLLtest' method with parameters 'b', 'powerArray', 'powerArray.length', and 'ledsArray.length'.
        // The result is stored in the 'resultString' variable.
        String resultString = print_MLLtest(b, powerArray, powerArray.length, ledsArray.length);
        // Split the 'resultString' into an array of strings using whitespace as the separator.
        String[] resultArray = resultString.trim().split(" "); // Split by whitespace

        // Convert the array of strings to a list of integers.
        List<Integer> list = Arrays.stream(resultArray).map(Integer::parseInt).toList();
        for (int i = 0; i < ledsArray.length; i++) {
            // If the 'list' contains the current element in 'ledsArray', add it to the 'onList'.
            if (list.contains(ledsArray[i])) {
                onList.add(ledsArray[i]);
            }
        }

    }

    private int MLL(int[] x, int[] y, String[][] b) {

        int[][] c = new int[x.length + 1][y.length + 1];
        int count = 0;
        c[0][0] = 0;
        if (x.length == 0 || y.length == 0) return 0;//initial case
        for (int i = 1; i <= x.length; i++) {
            // Initial values
            c[i][0] = 0;
            b[i][0] = "";
        }
        for (int j = 1; j <= y.length; j++) {
            // Initial values
            c[0][j] = 0;
            b[0][j] = "";
        }
        for (int i = 1; i <= x.length; i++) {
            for (int j = 1; j <= y.length; j++) {
                if (x[i - 1] == y[j - 1]) { // If equal values
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "\u2196";
                } else { // if x[i] != y[j]
                    if (c[i][j - 1] > c[i - 1][j]) {
                        c[i][j] = c[i][j - 1];
                        b[i][j] = "\u2190";
                    } else {
                        c[i][j] = c[i - 1][j];
                        b[i][j] = "\u2191";
                    }
                }
                count = c[i][j];
            }
        }
        StringBuilder sbValues = new StringBuilder();
        StringBuilder sbMovements = new StringBuilder();
        for (int i = 0; i <= x.length; i++) {//print the table of number
            for (int j = 0; j <= y.length; j++) {
                sbValues.append(String.format("%-8s", c[i][j]));
                sbMovements.append(String.format("%-8s", b[i][j]));
                System.out.print(String.format("%-8s", c[i][j]));
                System.out.print(String.format("%-8s", b[i][j]));
            }
            System.out.println();
            sbMovements.append("\n");
            sbValues.append("\n");
        }
        valuesTable = sbValues.toString();
        movementsTable = sbMovements.toString();
        return count;

    }

    private String print_MLLtest(String[][] arr, int[] x, int i, int j) {//to print the result string

        if (i == 0 || j == 0) return "";
        StringBuilder result = new StringBuilder();
        if (arr[i][j].equals("\u2196")) {
            result.append(print_MLLtest(arr, x, i - 1, j - 1));
            result.append(x[i - 1]).append(" ");
        } else if (arr[i][j].equals("\u2191")) {
            result.append(print_MLLtest(arr, x, i - 1, j));
        } else {
            result.append(print_MLLtest(arr, x, i, j - 1));
        }
        return result.toString();

    }

}