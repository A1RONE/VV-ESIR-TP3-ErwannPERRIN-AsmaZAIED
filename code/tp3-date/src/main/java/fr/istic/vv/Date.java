package fr.istic.vv;

class Date implements Comparable<Date> {

    private int m_day;
    private int m_month;
    private int m_year;
    static int[] max_days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public Date(int day, int month, int year) {
        m_day = day;
        m_month = month;
        m_year = year;
    }

    public int getDay() {
        return m_day;
    }

    public int getMonth() {
        return m_month;
    }

    public int getYear() {
        return m_year;
    }
    

    public String getDate(){
        return m_day+"/"+m_month+"/"+m_year;
    }

    public static boolean isValidDate(int day, int month, int year) {
        return day > 0 && ( ( day <= max_days[month - 1] ) || ( isLeapYear(year) && day == 29 ));
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0;
    }

    public Date nextDate() {
        int next_month = m_month;
        int next_year = m_year;
        int next_day = m_day + 1;
        if ( next_day > max_days[m_month - 1] && !(m_month == 2 && isLeapYear(m_year) && next_day == 29) ){
            next_day = 1;
            next_month += 1;
            if(next_month > 12){
                next_month = 1;
                next_year += 1;
            }
        }

        return new Date(next_day, next_month, next_year);
    }

    public Date previousDate() {
        int prev_month = m_month;
        int prev_year = m_year;
        int prev_day = m_day - 1;

        if (prev_day <= 0){
            prev_month -= 1;
            if (prev_month <= 0){
                prev_month = 12;
                prev_year -= 1;
            }
            if (prev_month == 2 && isLeapYear(m_year)){
                prev_day = 29;
            }
            else {
                prev_day = max_days[prev_month - 1];
            }
        }
        return new Date(prev_day, prev_month, prev_year);
    }

    public int compareTo(Date other) {
        return approxToDays() - other.approxToDays();
    }

    public int approxToDays()
    {
        return m_day + 31 * ( m_month + 12 * m_year );
    }

    public boolean equals(Date other)
    {
        return (other.getDay() == m_day) && (other.getMonth() == m_month) && (other.getYear() == m_year);
    }



}