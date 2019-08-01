package com.bd.gateway.scalar;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {

    public static final CopyOnWriteArrayList<DateTimeFormatter> DATE_FORMATTERS = new CopyOnWriteArrayList<>();

    static {
        //2018-05-31T14:20:12.894Z
        DATE_FORMATTERS.add(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()));
        //2018-05-31T22:10:59.254
        DATE_FORMATTERS.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()));
        //2018-05-31
        DATE_FORMATTERS.add(DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault()));
        //2018/05/31
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //2012-09-11
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //2012-09-11 11:30
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        //2012-09-11 11:30:00
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    // ISO_8601
    public static String toUsualString(LocalDateTime dateTime) {
        Objects.requireNonNull(dateTime, "dateTime");

        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(ZonedDateTime.of(dateTime, ZoneOffset.systemDefault()));
    }

    public static String toUsualString(LocalDate date) {
        Objects.requireNonNull(date, "date");

        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
    }

    public static String toISOString(LocalTime time) {
        Objects.requireNonNull(time, "time");

        return DateTimeFormatter.ISO_LOCAL_TIME.format(time);
    }

    public static String toUsualString(Date date) {
        Objects.requireNonNull(date, "date");

        return toUsualString(toLocalDateTime(date));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        Objects.requireNonNull(date, "date");

        return date.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDateTime();
    }

    public static Date toDate(LocalDate date) {
        Objects.requireNonNull(date, "date");

        return toDate(date.atStartOfDay());
    }

    public static Date toDate(LocalDateTime dateTime) {
        Objects.requireNonNull(dateTime, "dateTime");

        return Date.from(dateTime.atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public static LocalDateTime parseDate(String date) {
        Objects.requireNonNull(date, "date");

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                // equals ISO_LOCAL_DATE
                if (formatter.equals(DATE_FORMATTERS.get(2))) {
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    return localDate.atStartOfDay();
                } else {
                    return LocalDateTime.parse(date, formatter);
                }
            } catch (java.time.format.DateTimeParseException ignored) {
            }
        }

        return null;
    }

    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0, 0, 0);
    }

    public static Date createDate(int year, int month, int day, int hours, int min, int sec) {
        return createDate(year, month, day, hours, min, sec, 0);
    }

    public static Date createDate(int year, int month, int day, int hours, int min, int sec, int millis) {
        long nanos = TimeUnit.MILLISECONDS.toNanos(millis);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hours, min, sec, (int) nanos);
        return DateTimeHelper.toDate(localDateTime);
    }
}