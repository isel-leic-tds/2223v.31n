
private val daysOfMonth = listOf(31,28,31,30,31,30,31,31,30,31,30,31)

class Date(val year: Int, val month: Int = 1, val day: Int = 1) {
    init {
        require(year in 0..4000){ "Invalid year=$year" }
        require(month in 1..12){ "Invalid month=$month" }
        require(day in 1..lastDayOfMonth){ "Invalid day=$day" }
    }
    val leapYear: Boolean
        get() = year%4 == 0 && (year%100!=0 || year%400==0 )
    val lastDayOfMonth
        get() = daysOfMonth[month-1] + if (month==2 && leapYear) 1 else 0
    override fun toString() = "$year-$month-$day"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is String) return toString() == other
        if (other !is Date) return false
        return year == other.year && month == other.month && day == other.day
    }
    override fun hashCode() = day + (month*31 + year) * 31
}

/**
 * 
 */
tailrec fun Date.addDays(days: Int): Date = when {
    day + days <= lastDayOfMonth -> Date(year, month, day + days)
    month < 12 -> Date(year, month + 1, 1).addDays(days - lastDayOfMonth + day - 1)
    else -> Date(year + 1, 1, 1).addDays(days - lastDayOfMonth + day - 1)
}
