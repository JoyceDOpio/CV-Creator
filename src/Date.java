public class Date
{
    private int year, month, day = 1;

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

    // A:
    public boolean areDatesTheSame(Date date)
    {
        if(date.getYear() == this.year &&
                date.getMonth() == this.month &&
        date.getDay() == this.day)
            return true;
        else
            return false;
    }

    // C:
    public static Date createDateBasedOnString(String date)
    {
        if(date.split("\\.").length == 3)
        {
            return new Date(Integer.parseInt(date.split("\\.")[0]),
                    Integer.parseInt(date.split("\\.")[1]),
                    Integer.parseInt(date.split("\\.")[2]));
        }
        else if(date.split("\\.").length == 2)
        {
            return new Date(Integer.parseInt(date.split("\\.")[0]),
                    Integer.parseInt(date.split("\\.")[1]));
        }
        else return null;
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

    // T:
    public String toString()
    {
        return year + "." + formatNumber(month) + "." + formatNumber(day);
    }
}
