package com.br.teste.attus.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

        public static java.sql.Date parseDate(String date) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return new java.sql.Date(formatter.parse(date).getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format.", e);
            }
        }



}
