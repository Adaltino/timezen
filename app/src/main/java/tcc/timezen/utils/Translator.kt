package tcc.timezen.utils

class Translator {

    fun timeStringFromLong(ms: Long): String {
        val seconds = toLimitedSeconds(ms)
        val minutes = toLimitedMinutes(ms)
        val hours = toLimitedHours(ms)

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)

    fun makeTimeStringForReport(minutes: Int): String {
        return if (minutes >= 60) {
            String.format(
                "%02d horas e %02d minutos",
                toLimitedHours((minutes*1000*60).toLong()),
                toLimitedMinutes((minutes*1000*60).toLong())
            )
        } else {
            String.format("%02d minutos", minutes)
        }
    }

    fun getAbsoluteHumanTime(type: String, ms: Long): String {
        var time = "?"

        when (type) {
            "second" -> time = String.format("%02dsecs", toSeconds(ms))
            "minute" -> {
                time = if (ms < 6000000) {
                    String.format("%02dmins", toMinutes(ms))
                } else {
                    String.format("%03dmins", toMinutes(ms))
                }
            }

            "minuteNumbersOnly" -> {
                time = if (ms < 6000000) {
                    String.format("%02d", toMinutes(ms))
                } else {
                    String.format("%03d", toMinutes(ms))
                }
            }

            "hour" -> time = String.format("%02dhours", toHours(ms))
        }
        return time
    }

    fun getMsFromSecond(timeInSeconds: Long): Long = timeInSeconds * 1000
    fun getMsFromMinute(timeInMinutes: Long): Long = timeInMinutes * 1000 * 60
    fun getMsFromHour(timeInHours: Long): Long = timeInHours * 1000 * 60 * 60

    fun toLimitedSeconds(ms: Long): Long = (ms / 1000) % 60
    fun toLimitedMinutes(ms: Long): Long = ms / (1000 * 60) % 60
    fun toLimitedHours(ms: Long): Long = ms / (1000 * 60 * 60) % 24
    fun toSeconds(ms: Long): Long = (ms / 1000)
    fun toMinutes(ms: Long): Long = ms / (1000 * 60)
    fun toHours(ms: Long): Long = ms / (1000 * 60 * 60)
}