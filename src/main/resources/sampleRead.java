// Java Program to illustrate reading from FileReader
// using BufferedReader

import java.io.*;
import java.util.*;

public class ReadFromFile2 {
    public static void main(String[] args) throws Exception {
// We need to provide file path as the parameter:
// double backquote is to avoid compiler interpret words
// like \test as \t (ie. as a escape sequence)
        File file = new File("/Users/nitishkumar/Desktop/codecon_sandeep/codiecon/src/main/resources/out");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            String w[]=st.split(" ");
        System.out.println(w);

    }
}
