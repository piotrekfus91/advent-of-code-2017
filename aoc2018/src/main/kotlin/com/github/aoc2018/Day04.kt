package com.github.aoc2018

class Day04 {
    private val lineRegex = "\\[1518-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})] (.+)".toRegex()
    private val beginsShiftRegex = "Guard #(\\d+) begins shift".toRegex()

    fun first(input: String): Int {
        val sleeps = loadSleeps(input)

        val longestSleepingGuard = sleeps.groupBy { it.guard }.maxBy { it.value.size }!!.key
        val mostOftenSleepingMinute = sleeps.filter { it.guard == longestSleepingGuard }.map { it.time.minute }.groupBy { it }.maxBy { it.value.size }!!.key

        return longestSleepingGuard * mostOftenSleepingMinute
    }

    fun second(input: String): Int {
        val sleeps = loadSleeps(input)

        val (guard, minute) = sleeps.groupBy { it.guard to it.time.minute }.maxBy { it.value.size }!!.key

        return guard * minute
    }

    private fun loadSleeps(input: String): MutableList<SleepingGuardTime> {
        val entries = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { parse(it) }
            .sortedBy { it.dateTime }

        var currentGuard = -1
        var sleepStart = Time(0, 0)
        val sleeps = mutableListOf<SleepingGuardTime>()
        entries.forEach { entry ->
            when (entry.action) {
                is GuardBeginsShift -> currentGuard = entry.action.id
                is FallsAsleep -> sleepStart = entry.dateTime.time
                is WakesUp -> {
                    val minutesOfSleep = generateTimesAsleep(sleepStart, entry.dateTime.time)
                    minutesOfSleep.forEach { sleeps.add(SleepingGuardTime(it, currentGuard)) }
                }
            }
        }
        return sleeps
    }

    private fun generateTimesAsleep(start: Time, end: Time): List<Time> {
        val list = mutableListOf<Time>()
        var currentHour = start.hour
        var currentMinute = start.minute
        while (currentHour <= end.hour && currentMinute < end.minute) {
            list.add(Time(currentHour, currentMinute))
            if (currentMinute == 59) {
                currentHour++
                currentMinute = 0
            } else {
                currentMinute++
            }
        }
        return list
    }

    private fun parse(line: String): Entry {
        val (month, day, hour, minute, actionString) = lineRegex.matchEntire(line)!!.destructured
        val action = when (actionString) {
            "wakes up" -> WakesUp
            "falls asleep" -> FallsAsleep
            else -> beginsShiftRegex.matchEntire(actionString)!!.destructured.let { GuardBeginsShift(it.component1().toInt())}
        }
        return Entry(DateTime(month.toInt(), day.toInt(), Time(hour.toInt(), minute.toInt())), action)
    }

    data class Entry (val dateTime: DateTime, val action: Action)

    interface Action
    data class GuardBeginsShift(val id: Int) : Action
    object FallsAsleep : Action
    object WakesUp : Action

    data class SleepingGuardTime(val time: Time, val guard: Int)

    data class Time(val hour: Int, val minute: Int)
    data class DateTime(val month: Int, val day: Int, val time: Time) : Comparable<DateTime> {
        override fun compareTo(other: DateTime): Int {
            if (month != other.month) {
                return month - other.month
            }
            if (day != other.day) {
                return day - other.day
            }
            if (time.hour != other.time.hour) {
                return time.hour - other.time.hour
            }
            return time.minute - other.time.minute
        }
    }
}
