package com.example.demo;
import java.util.*;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class WordLadder {
    /**
     * wordladder
     * get the ladder("answer") back from "word2" to "word1" through the dictionary which is stored in the arraylist "words"
     */
    static private final ArrayList<String> words = new ArrayList<>();
    static private Stack<String> answer = new Stack<>();
    static private final String dicPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    static private final String defaultDicName = "dictionary.txt";
    static String word1;
    static String word2;
    private static StringBuffer AnswerReturn=new StringBuffer("");
    enum KEY{ERR,OK}

    private static Logger logger = LogManager.getLogger(WordLadder.class);

    WordLadder(String word1,String word2){
        WordLadder.word1 = word1;
        WordLadder.word2 = word2;
    }
    public static StringBuffer start(){
        if(!getDic()){
            return AnswerReturn;
        }
        KEY key = getWords();
        if(key==KEY.ERR)
            return AnswerReturn;
        if(wordLadder())
            return printout();
        return AnswerReturn;
    }
    static boolean getDic(){
        try {
            File file = new File(dicPath + defaultDicName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tmpWord;
            tmpWord = reader.readLine();
            while (tmpWord != null) {
                words.add(tmpWord);
                tmpWord = reader.readLine();
            }
            reader.close();
            return true;
        }
        catch(Exception e){
            AnswerReturn= new StringBuffer("No such file!");
            logger.info("No such file!");
            return false;
        }
    }
    private static KEY getWords(){
        try {
            checkWord(word1);
            checkWord(word2);
            checkTwoWords();
            return KEY.OK;
        }
        catch(Exception e){
            AnswerReturn=new StringBuffer(e.getMessage());
            logger.info(e.getMessage());
            return KEY.ERR;
        }
    }
    /*
    static boolean getAWord(String word)throws Exception{
        if("".equals(word)){
            System.out.println("Have a nice day!");
            return true;
        }
        checkWord(word);
        return false;
    }
    */
    static void checkWord(String word)throws Exception{
        char [] cWord = word.toCharArray();
        for(char ch : cWord){
            if( !((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
                throw new Exception("Not a word!");
        }
        word = word.toLowerCase();
        if(!checkHaveWord(word))
            throw new Exception("The two words must be found in the dictionary.");
    }
    private static boolean checkHaveWord(String word){
        int low = 0;
        int high = words.size() - 1;
        while (low <= high){
            int mid = (low + high) / 2;
            String w = words.get(mid);
            if(w.equals(word))
                return true;
            else if(word.compareTo(w) < 0)
                high = mid -1;
            else
                low = mid + 1;
        }
        return false;
    }
    private static void checkTwoWords()throws Exception{
    lengthCheck();
    sameCheck();
}
    static void lengthCheck()throws Exception{
        if(word1.length() != word2.length())
            throw new Exception("The two words must be the same length.");
    }
    static void sameCheck()throws Exception{
        if(word1.equals(word2))
            throw new Exception("The two words must be different.");
    }

    static boolean wordLadder(){
        Stack<String> s = new Stack<>();
        s.push(word1);

        Queue<Stack<String>> line = new LinkedList<>();
        line.offer(s);

        Set<String> wordsBeenUsed = new HashSet<>();
        wordsBeenUsed.add(word1);
        try {
            while (!line.isEmpty()) {
                String upWord = line.peek().peek();
                for (int i = 0; i < upWord.length(); i++) {
                    for (char j = 'a'; j <= 'z'; j++) {
                        String tmpWord = upWord.substring(0, i) + j + upWord.substring(i + 1);
                        if (checkHaveWord(tmpWord)) {
                            if (!wordsBeenUsed.contains(tmpWord)) {
                                if (tmpWord.equals(word2)) { //get the ladder
                                    line.peek().push(tmpWord);
                                    answer = line.peek();
                                    return true;
                                }
                                wordsBeenUsed.add(tmpWord);
                                //copy the stack
                                Stack<String> tmp = (Stack<String>) line.peek().clone();
                                tmp.push(tmpWord);
                                line.add(tmp);
                            }
                        }
                    }
                }
                line.poll();
            }
            throw new Exception("No word ladder found from " + word1 + " back to " + word2 + ".");
        }
        catch(Exception e){
            AnswerReturn=new StringBuffer(e.getMessage());
            logger.info(e.getMessage());
            return false;
        }
    }

    private static StringBuffer printout(){
        //System.out.println("A ladder from "+word1+" back to " +word2+":");
        StringBuffer ans= new StringBuffer("");
        while(!answer.empty()){
            ans.append(answer.pop());
            ans.append("\t");
        }
        //System.out.println(ans);
        return ans;
    }
}

