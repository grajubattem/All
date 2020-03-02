
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

  private static final String DATE_FORMAT = "MM/dd/yyyy";

  private static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

  public static final String SHORT_DATE_FORMAT = "yyyy/MM/dd";

  private static final String TIMEZONE = "EST5EDT";

  private DateUtil() {

  }

  public static int compareDates(Date date1, Date date2) {
    return date1.compareTo(date2);
  }

  public static String convertDateToString(Date dosTo) {
    return getDateStrByFormat(dosTo, DATE_FORMAT);
  }

  public static Date covertToTimeZoneDate(Date date) {
    if (date == null) {
      return null;
    }
    TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE));
    SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE_FORMAT);
    format.format(date);
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    cal.setTime(date);
    return cal.getTime();
  }

  public static String getCurrentDate() {

    TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE));

    Date curDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
    String curDateStr = format.format(curDate);
    return curDateStr;
  }

  public static Date getDateFromString(String date) throws ParseException {
    if (date == null) {
      return null;
    }
    TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE));
    SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE_FORMAT);
    Date parse = format.parse(date);
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    cal.setTime(parse);
    return cal.getTime();
  }

  public static String getDateStrByFormat(Date date, String formatStr) {
    String dateStr = "";
    if (date != null) {
      TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE));
      SimpleDateFormat format = new SimpleDateFormat(formatStr != null ? formatStr
        : DATE_TIME_FORMAT);
      dateStr = format.format(date);
    }
    return dateStr;
  }

  public static Date getFirstDayOfNextMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    if (date != null) {
      cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
      cal.setTime(date);
      cal.add(Calendar.MONTH, 1);
      cal.set(Calendar.DAY_OF_MONTH, 1);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
    }
    return cal.getTime();
  }

  public static Date getLastDayOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    if (date != null) {
      cal.setTime(date);
      cal.add(Calendar.MONTH, 1);
      cal.set(Calendar.DAY_OF_MONTH, 1);
      cal.add(Calendar.DATE, -1);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
    }
    return cal.getTime();
  }

  public static Date getLastMonthLastDate() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    cal.add(Calendar.MONTH, -1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }

  public static String getQuarterName(Date inputDate) {
    Calendar instance = Calendar.getInstance();
    instance.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    instance.setTime(inputDate);
    instance.set(Calendar.HOUR_OF_DAY, 0);
    instance.set(Calendar.MINUTE, 0);
    instance.set(Calendar.SECOND, 0);
    instance.set(Calendar.MILLISECOND, 0);

    String quarter = "";
    int month = instance.get(Calendar.MONTH);
    if (month <= Calendar.MARCH) {
      quarter = "A";
    } else if (month <= Calendar.JUNE) {
      quarter = "B";
    } else if (month <= Calendar.SEPTEMBER) {
      quarter = "C";
    } else {
      quarter = "D";
    }
    String year = (instance.get(Calendar.YEAR) + "").substring(2, 4);
    return year + quarter;
  }

  public static String getStringFromDate(Date dosTo) {
    return getDateStrByFormat(dosTo, DATE_FORMAT);
  }

  @SuppressWarnings("boxing")
  public static Boolean isActiveDosTo(Date dosTo) {
    if (dosTo != null) {
      return dosTo.after(getLastMonthLastDate());
    } else {
      return false;
    }
  }

  public static boolean isDateWithInInputYears(Date dosTo, long years) {
    Calendar calender = Calendar.getInstance();
    calender.setTime(dosTo);
    LocalDate current = LocalDate.of(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH) + 1,
      calender.get(Calendar.DAY_OF_MONTH));
    LocalDate today = LocalDate.now();
    Period age;
    if (current.isAfter(today)) {
      age = Period.between(today, current);
    } else {
      age = Period.between(current, today);
    }
    age = Period.between(current, today);
    if (age.getYears() < years) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isEndDatedDate(Date inputDate) {
    return compareDates(inputDate, now()) < 0;
  }

  public static boolean isGapExistsBetweenDates(Date date1, Date date2) {
    long difference = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
    return Math.abs(difference) > 1;
  }

  public static Date nextQuarterDate(Date inputDate) {
    Calendar instance = Calendar.getInstance();
    instance.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    instance.setTime(inputDate);
    instance.add(Calendar.MONTH, 3);
    instance.set(Calendar.HOUR_OF_DAY, 0);
    instance.set(Calendar.MINUTE, 0);
    instance.set(Calendar.SECOND, 0);
    instance.set(Calendar.MILLISECOND, 0);
    return instance.getTime();
  }

  public static Date now() {
    Calendar instance = Calendar.getInstance();
    instance.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    instance.setTime(new Date());
    instance.set(Calendar.HOUR_OF_DAY, 0);
    instance.set(Calendar.MINUTE, 0);
    instance.set(Calendar.SECOND, 0);
    instance.set(Calendar.MILLISECOND, 0);
    return instance.getTime();
  }

  public static Date removeTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date substractDaysFromDate(Date date, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    cal.setTime(date);
    cal.add(Calendar.DATE, -days);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date substractedDate = cal.getTime();
    return substractedDate;
  }

  public static String subtractYearsFromNowAndFormat(long years) {
    LocalDate currentDate = LocalDate.now(TimeZone.getTimeZone(TIMEZONE).toZoneId());
    LocalDate minusYears = currentDate.minusYears(years);
    return minusYears.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
  }

};
