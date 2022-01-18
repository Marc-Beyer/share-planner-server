package com.vpr.server.json;

import com.vpr.server.data.UserEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static String ValidateEventName(String name) throws IllegalArgumentException {
        if (name.length() < 3) {
            System.out.println("NAME TO SHORT");
            throw new IllegalArgumentException("Der Name ist zu kurz");
        }
        Pattern pattern = Pattern.compile("[A-Za-z\u00e4\u00f6\u00fc\u00c4\u00d6\u00dc\u00df0-9 =!?+*/$.:,;_<>()-]*");
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()){
            System.out.println("NAME HAS ILLEGALCHARS");
            throw new IllegalArgumentException("Der Name enthält nicht erlaubte Zeichen");
        }
        return name;
    }

    public static Time ValidateEventTime(String time) throws IllegalArgumentException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            long ms = simpleDateFormat.parse(time).getTime();
            return new Time(ms);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date ValidateEventDate(String date) throws IllegalArgumentException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new Date(simpleDateFormat.parse(date).getTime());
        } catch (Exception e) {
            System.out.println("DATE FORMAT NOT CORRECT");
            throw new IllegalArgumentException("Datumformat nicht korrekt");
        }
    }
}
