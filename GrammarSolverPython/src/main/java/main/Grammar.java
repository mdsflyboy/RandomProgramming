package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Grammar{
    public static void main(String[] args) throws Exception {
        Map<String, ArrayList<String>> list = readFile("src/main/resources/grammar.txt");
        ArrayList<String> output = formSentences(list,10);
        for(String sent:output){
            System.out.println(sent);
        }
    }

    public static Map<String, ArrayList<String>> readFile(String file) throws Exception{
        Map<String, ArrayList<String>> list = new TreeMap(); 

        File fileObj = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(fileObj));

        String line;
        while((line = br.readLine()) != null){
            readFileHelper(line, list);
        }
        return list;
    }

    public static void readFileHelper(String line, Map<String, ArrayList<String>> list){
        // line.replaceAll("\\s+", "");
        String[] splitLine = line.split(":");
        // System.out.println(splitLine[0]);
        // System.out.println(splitLine[1]);
        String var = splitLine[0];
        var.replaceAll("\\s+", "");
        // System.out.println(var);
        String[] out = splitLine[1].split("\\|");
        // System.out.println(out[0]);
        ArrayList<String> conditions = new ArrayList<>(Arrays.asList(out));
        list.put(var, conditions);
    }

    public static ArrayList<String> formSentences(Map<String, ArrayList<String>> list, int num){
        ArrayList<String> out = new ArrayList<>();
        for(int i = 0; i < num; i++){
            out.add(formSentence(list));
        }
        return out;
    }

    public static String formSentence(Map<String, ArrayList<String>> list){
        return formSentenceHelper("start", list);
    }
    public static String formSentenceHelper(String key, Map<String, ArrayList<String>> list){
        if(!list.containsKey(key))
            return key;
        else{
            ArrayList<String> possibilities = list.get(key);
            int choice = (int)(Math.random()*(possibilities.size()));
            String cString = possibilities.get(choice);
            String[] parts = cString.split("\\s");
            String out = "";
            for(String part:parts){
                out += formSentenceHelper(part, list)+" ";
            }
            return out;
        }
    }
}