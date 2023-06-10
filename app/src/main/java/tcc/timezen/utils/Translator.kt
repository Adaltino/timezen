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

    // don't touch these ever again
    fun getMsFromSecond(timeInSeconds: Long): Long = timeInSeconds * 1000
    fun getMsFromMinute(timeInMinutes: Long): Long = timeInMinutes * 1000 * 60
    fun getMsFromHour(timeInHours: Long): Long = timeInHours * 1000 * 60 * 60

    private fun toLimitedSeconds(ms: Long): Long = (ms / 1000) % 60
    private fun toLimitedMinutes(ms: Long): Long = ms / (1000 * 60) % 60
    private fun toLimitedHours(ms: Long): Long = ms / (1000 * 60 * 60) % 24
    private fun toSeconds(ms: Long): Long = (ms / 1000)
    private fun toMinutes(ms: Long): Long = ms / (1000 * 60)
    private fun toHours(ms: Long): Long = ms / (1000 * 60 * 60)
}