package com.wellcare.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomicPassword {

    @Autowired
    private AlphabetConfiguration alphabetPropConf;

    @Value("${randomic.password.length}")
    private int passwordLength;

    public String getRandomicPassword() {

        // 4 is the number of total alphabet
        int pwdNumberSubstring = passwordLength/4;
        String finalPwd = "";

        for(int i = 0; i < pwdNumberSubstring; i++) {

            finalPwd += getRandomicCharByAlphabet(alphabetPropConf.getAlphabetUpperCase());
            finalPwd += getRandomicCharByAlphabet(alphabetPropConf.getAlphabetLowerCase());
            finalPwd += getRandomicCharByAlphabet(alphabetPropConf.getAlphabetNumeric());
            finalPwd += getRandomicCharByAlphabet(alphabetPropConf.getAlphabetSpecial());

        }

        return finalPwd;

    }

    private String getRandomicCharByAlphabet(String alphabet) {

        if(alphabet == null) return "";

        Random rand = new Random();
        int position = rand.nextInt(alphabet.length());

        return String.valueOf(alphabet.charAt(position));

    }
}