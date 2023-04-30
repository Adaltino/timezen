package faculdade.timezen.utils

class Translator {

    fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes =  ms / (1000 * 60) % 60
        val hours =    ms / (1000 * 60 * 60) % 24

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String
        = String.format("%02d:%02d:%02d", hours, minutes, seconds)

}