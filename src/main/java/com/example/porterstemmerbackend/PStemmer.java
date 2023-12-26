package com.example.porterstemmerbackend;
import java.util.HashMap;
import java.util.Map;


public class PStemmer {
    private static Boolean preProcessed = false;

    public static String PorterStemmer(String corpus) { 
        if (!preProcessed) 
             preProcess();    
        return RealPorterStemmer(corpus.toLowerCase());    
    }    
    
    public static void main (String args [])  {
        /***TO DO: CONVERT INITIAL CORPUS TO LOWERCASE!! AND REMOVE PUNCTUATION AND STOP WORDS */
        preProcess();


       // String myWord = "Consultantations";
       String myWord =  "Consultantative"; 
       System.out.println("initial word : " + myWord);
        System.out.println(RealPorterStemmer(myWord));
        


    }

    public static void preProcess () {
        UtilityClass.preProcess();
        preProcessed = true; 
    }

    public static String removePunctuation (String corpus) {
        return UtilityClass.removePunctuation(corpus);
    }


  public static HashMap<String, Integer> extractWords (String corpus) {
        return UtilityClass.extractWords(corpus);
  }
  
  public static String RealPorterStemmer (String word) {
        if (word.trim().isEmpty())
              return "";
         String stem = ApplyStemRules(word);
        // System.out.println("Stem is : " + stem);
         //At this point, we have the tag form and the measure, retrieve the stem 
         
        return stem;
  }

    public static String ApplyStemRules(String word) {
                       System.out.println("Before ANY STEP! , word is : "+ word);

        String stem = word;

        if (word.charAt(word.length() - 1) == 's') { 
             stem = applyStep1a(word);
        }
        stem = applyStep1b(stem);
        System.out.println(stem);
        
        System.out.println("stem is : " + stem);
        if (stem.charAt(stem.length() - 1) == 'y') {
          stem = applyStep1c(stem);
        }


       stem = applyStep2(stem);

       stem = applyStep3(stem);    

       stem = applyStep4(stem);    

       stem = applyStep5a(stem);

        stem = applyStep5b(stem);
        

        return stem;
    }
    private static String applyStep5b (String word) {
         String truncatedWord = word;
        if (word.endsWith("l")) {
            if (getTag(word.length() -2, word) == 'C') {
                //indicating double consonant
                int m = getMeasure(word, word.length());
                if (m > 1) {
                   truncatedWord = word.substring(0, word.length() - 1);
                  UtilityClass.addStep("5b", word,"(m > 1 and *d and *L) -> single letter",truncatedWord);
                   return truncatedWord;

                }
            }
        }
        return word;
    }
    private static Boolean followsVowelConsonantPattern (String word) {
        if (word.length() < 2) 
            return false;
        return (getTag(word.length() -1, word) == 'C'
            && getTag(word.length() - 2, word) == 'V');
    }
    private static String applyStep5a (String word) {
        String truncatedWord = word;
        int m = 0;
        if (word.charAt(word.length() - 1) == 'e') {
             m = getMeasure(word, word.length() -1);
             if (m > 1) {
                truncatedWord = word.substring(0, word.length() -1);
                UtilityClass.addStep("5a", word, "(m>1) E -> ",
                        truncatedWord);
                return truncatedWord;
             }
             else if (m == 1) {   
                Boolean vowelConsonantPattern = followsVowelConsonantPattern(word);
                if (!(vowelConsonantPattern)) {
                    truncatedWord = word.substring(0, word.length() -1);
                    UtilityClass.addStep("5a",word, "(m=1 and not *o) E -> ", truncatedWord);
                    return truncatedWord;

               }
            }
        } 
        return truncatedWord;
    }
    private static String applyStep4 (String word) {
        String truncatedWord = word;
                int m = 0;
            
        switch(word.charAt(word.length() - 1)) {
            case 'l'  :
                if (word.endsWith("al")) {
                    m = getMeasure(word, word.length() - 2);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() - 2);
                UtilityClass.addStep("4", word, "(m>1) AL -> ", truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            case 'e':
                if (word.endsWith("ance") || 
                word.endsWith("ence") ||
                word.endsWith("able") ||
                word.endsWith("ible")) {
                    m = getMeasure(word,word.length()  - 4);
                if (m >1) {
                    truncatedWord = word.substring(0, word.length() - 4);
                    UtilityClass.addStep("4", word, "(m>1) " +
                            word.substring(word.length()-4, word.length()).toUpperCase()+" -> ", truncatedWord);
                    return truncatedWord;
                }
                }

               else if (word.endsWith("ate") ||
                        word.endsWith("ive")        || 
                        word.endsWith("ize")) {
                            m = getMeasure(word,word.length() - 3);
                            if (m > 1) {
                                truncatedWord = word.substring(0, 
                                word.length() - 3);
          UtilityClass.addStep("4", word, "(m>1) " +
                  word.substring(word.length() - 3, word.length()) + " -> ", truncatedWord);

                                return truncatedWord;
                            }
                        }
            break;

            case 'r':
                   if (word.endsWith("er")) {
                    m = getMeasure(word, word.length() - 2);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -2);
                        UtilityClass.addStep("4", word, "(m>1) ER -> ", truncatedWord);
                        return truncatedWord;

                    }
                   }
            break;

            case 'c':
                   if (word.endsWith("ic")) {
                        m = getMeasure(word, word.length() - 2);
                    if (m > 1) {
                        truncatedWord  = word.substring(0, word.length() -2);
                        UtilityClass.addStep("4", word, "(m>1) IC -> ", truncatedWord);
                        return truncatedWord;
                    
                       }   
                   }
            break;

            case 't':
                if (word.endsWith("ement")) {
                    m = getMeasure(word, word.length() - 5);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -5);
                        UtilityClass.addStep("4", word, "(m>1) EMENT -> ", truncatedWord);
                        return truncatedWord;
                    }
                }
                else if (word.endsWith("ment")) {
                    m = getMeasure(word, word.length() - 4);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -4);
                        UtilityClass.addStep("4", word,"(m>1) MENT -> ", truncatedWord);
                        return truncatedWord;
                    }
                }
                else if (word.endsWith("ent")
                || (word.endsWith("ant"))) {
                    m = getMeasure(word, word.length() - 3);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -3);
                        UtilityClass.addStep("4", word, "(m>1) " +
                                word.substring(word.length() - 3, word.length())+ " -> ",truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            case 'n' :
                if (word.endsWith("ion")) {
                  String truncString = word.substring(0, word.length() - 3);
                  if (truncString.charAt(truncString.length() - 1)
                  == 't' ||
                  truncString.charAt(truncString.length() - 1) == 's') {

                    m = getMeasure(truncString, truncString.length() - 2);
                    if (m > 1) {
                    UtilityClass.addStep("4",word,"(m>1 and (*S or *T)) ION -> ",
                            truncString);
                        return truncString;
                    }

                  }
                }
            break; //compound rules

            case 'u':
                if (word.endsWith("ou")) {
                    m = getMeasure(word, word.length() - 2);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -2);
                        UtilityClass.addStep("4", word, "(m>1) OU -> ",
                                truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            case 'm':
                if (word.endsWith("ism")) {
                    m = getMeasure(word, word.length() - 3);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -3);
                        UtilityClass.addStep("4", word,"(m>1) ISM -> ",
                                truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            case 'i':
                if (word.endsWith("iti")) {
                    m = getMeasure(word, word.length() - 3);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length() -3);
                        UtilityClass.addStep("4", word, "(m>1) ITI -> ",
                                truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            case 's':
                if (word.endsWith("ous")) {
                    m = getMeasure(word, word.length() - 3);
                    if (m > 1) {
                        truncatedWord = word.substring(0, word.length()- 3);
                        UtilityClass.addStep("4",word, "(m>1) OUS -> ",truncatedWord);
                        return truncatedWord;
                    }
                }
            break;

            default :
            break;
        }   
        return truncatedWord;
    }

    private static String applyStep3 (String word) {
        String truncatedWord = word;
        Boolean none = false;
        int m = 0;
        switch (word.charAt(word.length() - 1)) {

            case 'e' :
                if (word.endsWith("icate")) {
                  m = getMeasure(word, word.length() - 5);
                  
                  if (m > 0) {
                    truncatedWord = word.substring(0, word.length() - 3);
                    UtilityClass.addStep("3", word,"(m>0) ICATE -> IC",truncatedWord);
                    return truncatedWord;
                  }
                }
                else if (word.endsWith("ative")) {
                    m = getMeasure(word, word.length() - 5);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 5);
                        UtilityClass.addStep("3", word,"(m>0) ATIVE -> ",truncatedWord);

                        return truncatedWord;
                    }
                } 
                else if (word.endsWith("alize")) {
                    m = getMeasure(word, word.length() - 5);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 3);
                        UtilityClass.addStep("3", word,"(m>0) ALIZE -> AL",truncatedWord);

                        return truncatedWord;
                    }
                }
                break;
             case 'i' :
                if (word.endsWith("iciti")) {
                    m = getMeasure(word, word.length() - 5);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 3);
                        UtilityClass.addStep("3", word,"(m>0) ICITI -> IC",truncatedWord);
                        return truncatedWord;
                    }
                }
                break;
            case 'l' :   
                if (word.endsWith("ical")) {
                    m = getMeasure(word, word.length() -4);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 2);
                        UtilityClass.addStep("3", word,"(m>0) ICAL -> IC",truncatedWord);
                        return truncatedWord;
                    }
                }
                else if (word.endsWith("ful")) {
                    m = getMeasure(word, word.length() - 3);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 3);
                        UtilityClass.addStep("3", word,"(m>0) FUL -> ",truncatedWord);
                        return truncatedWord;
                    }
                }
                break;
                
             default : 
                none = true;
                break;   

        }
        if (none) {
            if (word.endsWith("ness")) {
                System.out.println("Yes, ends with ness");
                m = getMeasure(word, word.length() - 7);
                System.out.println(" m is " + m);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() - 4);
                    System.out.println("when i remove ness , I get " + truncatedWord);
                    UtilityClass.addStep("3", word,"(m>0) NESS -> ",truncatedWord);
                    return truncatedWord;
                }
            }
        }
        return truncatedWord;
    }
    private static String applyStep2 (String word) {
        Boolean none = false;
        String truncatedWord = word;
        switch(word.charAt(word.length() - 1)) {
            case 'l' :
            //rules l
          //   if (word.length() > 6) {

              if (word.endsWith("tional")) {
            System.out.println("ends with tional");                
                 if (word.charAt(word.length() - 7) == 'a') {
            System.out.println("ends with ational");
                    //case for ational
                    //calculate measure here
                    int m = getMeasure(word,
                    word.length() - 7);
                        System.out.println("measure is "+ m);        
                    if (m > 0) {
                     truncatedWord = word.substring(0, word.length() - 7);
                     truncatedWord += "ate";
                     UtilityClass.addStep("2",word, "(m>0) ATIONAL -> ATE", truncatedWord);
                     return truncatedWord;
                    }
                 }
                 else {
                 //calculate measure here
                 //case for tional
                    int m = getMeasure(word,
                    word.length() - 6);
                    if (m > 0) {
                     truncatedWord = word.substring(0, word.length() - 6);
                     truncatedWord += "tion";
                     UtilityClass.addStep("2",word, "(m>0) TIONAL -> TION", truncatedWord);
                        return truncatedWord;
                    }

                 }
               }
            // }
            break;

            case 'i':
            //rules for i;
             if (word.endsWith("nci")) {
                Character tmpLetter = word.charAt(word.length() -4);
                if (tmpLetter == 'a' 
                || tmpLetter == 'e') {
                  int m  = getMeasure(word, word.length() - 4);
                  if (m > 0) {  
                    truncatedWord = truncatedWord.substring(0, truncatedWord.length() - 1);
                    truncatedWord += tmpLetter;
                    UtilityClass.addStep("2",word,
                            "(m>0) "+ tmpLetter.toString().toUpperCase()+ "NCI -> "+ tmpLetter.toString().toUpperCase() + "NCE", truncatedWord);
                      return truncatedWord;
                  }
                }
             }
             else if (word.endsWith("li")) {
            
                if (word.substring(0, word.length() - 2)
                .endsWith("al")
                || 
                (word.substring(0, word.length() - 2).endsWith("ab"))) {
                    int m = getMeasure(word, word.length() - 4);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 2);

                     if (truncatedWord.charAt(truncatedWord.length() - 1) == 'b')   {
                        truncatedWord += "le";
                        UtilityClass.addStep("2", word, "(m>0) ABLI -> ABLE", truncatedWord);
                     }
                     else {
                         UtilityClass.addStep("2", word, "(m>0) ALLI -> AL", truncatedWord);
                     }
                     return truncatedWord;
                    }
                }
                else if (
                 word.substring(0, word.length()- 2)
                .endsWith("e")) {
                    int m = getMeasure(word,word.length() - 3);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() -2);
                        UtilityClass.addStep("2", word, "(m>0) ELI -> E", truncatedWord);
                        return truncatedWord;
                    }
                }
                else if (word.substring(0, word.length() - 2)
                .endsWith("ent")) {
                   int m = getMeasure(word,word.length() - 5);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length()-2);
                        UtilityClass.addStep("2", word, "(m>0) ENTLI -> ENT", truncatedWord);

                        return truncatedWord;
                    }
                }
                


             }
             else if (word.endsWith("iti")) {
                if (word.substring(0, word.length() -3)
              .endsWith("al")
              || word.substring(0,word.length()-3)
              .endsWith("iv")) {
                int m = getMeasure(word, word.length() -5);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() -3);
                    if (truncatedWord.endsWith("iv")) {
                        truncatedWord += "e";
                        UtilityClass.addStep("2", word, "(m>0) IVITI -> IVE", truncatedWord);

                        return truncatedWord;
                    }
                    UtilityClass.addStep("2", word, "(m>0) ALITI -> AL", truncatedWord);
                    return  truncatedWord;
                }
              }
              else if (word.substring(0, word.length() - 3)
              .endsWith("bil")) {
                //biliti
                int m = getMeasure(word, word.length() -6);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() -5);
                    truncatedWord += "le";
                    UtilityClass.addStep("2", word, "(m>0) BILITI -> BLE", truncatedWord);

                    return truncatedWord;
                }
              }
             }
            break;

            case 'n':
             if (word.endsWith("ation")) {
                if (word.substring(0, word.length() -5)
                .endsWith("iz")) {
                    //ization
                    int m = getMeasure(word, word.length() -5);

                    if (m > 0) {
                    truncatedWord = word.substring(0, word.length()- 5);
                    truncatedWord += 'e';
                        UtilityClass.addStep("2", word, "(m>0) IZATION -> IZE", truncatedWord);

                        return truncatedWord;
                    }
                }
                else {
                    //ation
                    int m = getMeasure(word, word.length() - 5);
                    if(m > 0)  {
                        truncatedWord = word.substring(0, word.length()- 3);
                        truncatedWord += 'e';
                        UtilityClass.addStep("2", word, "(m>0) ATION -> ATE", truncatedWord);

                        return truncatedWord;
                    }
                }
             }
            break;

            case 'r':
             if (word.endsWith("ator")) {
                //and m > 0 
                int m = getMeasure(word, word.length() - 4);
                if (m > 0) {
                truncatedWord = word.substring(0, word.length() - 2);
                truncatedWord += 'e';
                    UtilityClass.addStep("2", word, "(m>0) ATOR -> ATE", truncatedWord);

                    return truncatedWord;
                }
             }
            break;

            case 'm':
             if (word.endsWith("alism")) {
                int m = getMeasure(word, word.length() - 5);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() -3);
                    UtilityClass.addStep("2", word, "(m>0) ALISM -> AL", truncatedWord);
                    return truncatedWord;
                }
            }
            break;

            default :
             none = true;
             break;
        }
                //cases for ness and itis outside of the previous switch

        // if (none) {
             if(word.charAt(word.length() - 1) == 's') {
                 String suff  = word.substring(0, word.length() -4);
                    if (word.endsWith("ness")) {
                        System.out.println("YEET!!");
                        System.out.println(suff);
                        if (suff
                        .endsWith("ive") 
                        ||suff
                        .endsWith("ful")
                        || suff
                        .endsWith("ous")) {
                            int m = getMeasure(suff, suff.length());
                            if (m > 0) {
                                truncatedWord = suff;
                                UtilityClass.addStep("2", word, "(m>0) "
                                        + word.substring(word.length() -7,word.length()).toUpperCase() + " -> " + suff.substring(suff.length() - 3, suff
                                        .length()).toUpperCase(),truncatedWord);


                                return truncatedWord;
                            }
                        }
                    }
             }
          // }
        
           return truncatedWord;
    }
    private static String applyStep1c (String word) {
        String truncatedWord = word;
        String tag = getTag(word, word.length() -1);
        System.out.println("Tag is " + tag);
        if (tag.contains("V")) {
            truncatedWord = truncatedWord.substring(0, truncatedWord.length() -1);
            truncatedWord += 'i';
            UtilityClass.addStep("1c", word, "(*v*) Y -> I", truncatedWord);

        }
        return truncatedWord;
    }
    private static String applyStep1a (String word) {
        Character tmpChar = word.charAt(word.length() - 2);
        String truncatedWord = word;

        switch (tmpChar) { 
            case 'e' :
                if (word.endsWith("esses")
                || word.endsWith("ies")) {
                    truncatedWord = word.substring(0, word.length() - 2);
                    UtilityClass.addStep("1",word, (word.endsWith("ies")? "ies -> " : "esses -> "),truncatedWord);
                    return truncatedWord;
                }
            break;

            case 's': //double s
                UtilityClass.addStep("1", word, "ss -> ss", word);
                return word;
        }
        truncatedWord =  word.substring(0, word.length() - 1);
        UtilityClass.addStep("1", word, "s -> ", truncatedWord);
        return truncatedWord;
    }

    private static String getTag (String word, int length_lim) {
        Boolean isVowel = isLetterVowel(0, word);
        if (word.length() == 1) {
            return (isVowel)? "V" : "C";
        }
        String form = "";
        if (!isVowel) {
            //Starts with C 
            form +=  "C";
        }
        else { 
               //Starts with V 
            form +=  "V";
        }
        
       int j = 0; 
       for (int i = 1; i<length_lim; ++i) {
           char tag = getTag(i, word);
            if (tag == word.charAt(j));
           else  {
               form += tag;  
               ++j;
              }
            }
        String cleanedForm = "";
        cleanedForm += form.charAt(0);
        //We have got the form at this point, we will now shrink it.
         for (int i = 1; i<form.length(); ++i) {
            if (form.charAt(i) == form.charAt(i-1)) ;
            else 
                cleanedForm += form.charAt(i);
         }

         return cleanedForm;
    }
    private static int getMeasure (String word, int length_lim) {
        int measure = 0;  
        String cleanedForm = getTag(word, length_lim); //tag
//        System.out.println("Cleaned form tag is :  " + cleanedForm);
         if (cleanedForm.length() == 2) { //trivial case
            if (cleanedForm.charAt(0) == 'V' && cleanedForm.charAt(1) == 'C') {
                //measure is "1"
                measure = 1; 
            }
         } 
         else { //non trivial case
            //
            for (int  i = (cleanedForm.charAt(0) == 'V' ? 0 : 1); i<cleanedForm.length() - 1; ++i) {
                //if ((i+1) != (cleanedForm.length() - 1)) {
                    if (cleanedForm.charAt(i) == 'V' && cleanedForm.charAt(i+1) == 'C')
                        measure +=1;                    
                //}
            }
         }


        return measure;
    }
    public static String applyStep1b (String word) {
        if (word.length() <= 2) return word;

        String truncatedWord = word;
        Boolean containsVowel = false;
        Boolean thirdStep = false;
        if ((word.charAt(word.length() - 1) == 'd') && word.charAt(word.length()-2) == 'e') {

            //case 1
            if (word.charAt(word.length() - 3) == 'e') {
                int measure = getMeasure(word, word.length() - 2);
                System.out.println("Measure is  " + measure);

                if (measure > 0)  {
                    System.out.println("About to truncate the word "+ word  + " with measure : " + measure);
                    truncatedWord = word.substring(0, word.length() - 1); //agree[d] -> //agree
                                                                                  //if this was -2 , the window would be //agre[ed] 
                    System.out.println("Word truncated to "+ truncatedWord);
                    UtilityClass.addStep("1b", word, "eed -> e", truncatedWord);
                }
            }
            else {
            //case 2
             System.out.println("About to apply Case 2");
            for (int i =0; i<word.length() - 2; ++i) {
                if (isLetterVowel(i, word)) {
                    containsVowel = true;
                    //truncate word
                    truncatedWord = word.substring(0, word.length() - 2);
                    UtilityClass.addStep("1b", word, "(*v*)ed -> ",truncatedWord);
                    break;
                }
            }
        }


        }
       
       else if ((word.charAt(word.length() - 1) == 'g') && (word.charAt(word.length() - 2) == 'n') && word.charAt(word.length()- 3) == 'i') {
            //case 3
            for (int i = 0; i<word.length() - 3; ++i) {
                if (isLetterVowel(i, word))  {
                    containsVowel = true;
                    truncatedWord = word.substring(0, word.length() - 3);
                    UtilityClass.addStep("1b", word, "(*v*)ing -> ",truncatedWord);
                    break;
                }
            }
            thirdStep = true;
        }
    
     if(containsVowel) { //indicating that 2nd or 3rd step were successful
            System.out.println("Current Word is : " + truncatedWord);
            String suffix = truncatedWord.substring(truncatedWord.length()-2, truncatedWord.length());
           if (suffix.equals("at") || suffix.equals("bl")
              || suffix.equals("iz")) {

               UtilityClass.addStep("1b",truncatedWord, suffix +
                       " -> " + suffix + "e", "");
                suffix += "e";

                truncatedWord = truncatedWord.substring(0, truncatedWord.length()-2);
                truncatedWord += suffix;
                UtilityClass.getStepRecorderArrayList().get(UtilityClass.getStepRecorderArrayList().size() -1).setTransformation(truncatedWord);
                return truncatedWord;
            }
            //if it hasn't returned until now, that is an indicated that the previous cleanup wans't applied, so we move on to the proceeding cleanup
            System.out.println("About to perform further cleanup");
            if (suffix.charAt(0) == (suffix.charAt(1))) {
                containsVowel = false;
                
                
                for (int i =0; i<suffix.length(); ++i) {
                    char tmpTag = getTag(i, suffix);
                    if (tmpTag == 'V')
                        containsVowel = true;
                }


                if (!containsVowel) {
                    if (suffix.charAt(suffix.length() - 1) != 'l'
                        && suffix.charAt(suffix.length() - 1) != 's'
                        && suffix.charAt(suffix.length() - 1) != 'z') {

                            truncatedWord = truncatedWord.substring(0, truncatedWord.length()-2);
                            truncatedWord += suffix.charAt(0);
                            UtilityClass.addStep("1b",word,"(*d and not (*L or *S or *Z)) -> single letter", truncatedWord);
                            System.out.println("No vowel and yet truncated word is : " + truncatedWord);
                        }
                
                    }
        }
                        //stem ends with cvc where the 2nd c is not 
             if (thirdStep && truncatedWord.length() == 3) {           
                String tmpTag = getTag(truncatedWord, truncatedWord.length());
                System.out.println("Second tag is : " + tmpTag);
                if (tmpTag.equalsIgnoreCase("cvc")) {
                    Character lastChar = tmpTag.charAt(tmpTag.length() - 1);
                    if (((lastChar) != 'w')
                        || (lastChar != 'x') 
                        || (lastChar != 'y')) {
                            UtilityClass.addStep("1b", truncatedWord,"(m=1 and *o) -> E", "");
                            truncatedWord += 'e';
                            UtilityClass.getStepRecorderArrayList().get(UtilityClass.getStepRecorderArrayList().size() - 1).setTransformation(truncatedWord);
                        }
                }
            }


        }
 
 
        return truncatedWord; //temporarily returning this. But this is to be continued. WE need to follow up the cleaning up steps after 1b
    }
    
    public static char getTag (int indexOfLetter, String word) {

        return isLetterVowel(indexOfLetter, word)? 'V' : 'C';
    }

    public static boolean isLetterVowel(int indexOfLetter, String word) { //because in case of "Y", it can function as both depending on the context    
          return UtilityClass.isLetterVowel(indexOfLetter, word);
    }

    
}
