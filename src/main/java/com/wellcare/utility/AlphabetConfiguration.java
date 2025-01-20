package com.wellcare.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@ConfigurationProperties
@PropertySource(value = {"classpath:alphabet.properties"})
@Getter
public class AlphabetConfiguration {

    @Value("${alphabet.upper.case}")
    private String alphabetUpperCase;

    @Value("${alphabet.lower.case}")
    private String alphabetLowerCase;

    @Value("${alphabet.numeric}")
    private String alphabetNumeric;

    @Value("${alphabet.special}")
    private String alphabetSpecial;

}