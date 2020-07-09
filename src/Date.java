public class Date
{
    private int year, month, day;

    Date(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    Date(int year, int month)
    {
        this.year = year;
        this.month = month;
    }

    Date()
    {
    }

    // C:
    public boolean areDatesTheSame(Date date)
    {
        if(date.getYear() == this.year &&
                date.getMonth() == this.month &&
        date.getDay() == this.day)
            return true;
        else
            return false;
    }

    // F:
    public String formatNumber(int number)
    {
        if(number < 10)
            return "0" + number;
        else
            return String.valueOf(number);
    }

    // G:
    public int getDay(){return day;}

    public int getMonth(){return month;}

    public int getYear(){return year;}

    // S:
    public void setDay(int day){this.day = day;}

    public void setMonth(int month){this.month = month;}

    public void setYear(int year){this.year = year;}

    // T:
    public String toString()
    {
        if (day != 0)
            return year + "." + formatNumber(month) + "." + formatNumber(day);
        else
            return year + "." + formatNumber(month);
    }
}
