package tcc.timezen.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBTimezen(context: Context) :
    SQLiteOpenHelper(context, "timezen.db", null, 1) {

    val sqlCreateTables = arrayOf(
        "CREATE TABLE Category " +
                "(cat_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cat_name TEXT NOT NULL)",
        "CREATE TABLE ImportanceLevel " +
                "(lvl_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lvl_name TEXT NOT NULL)",
        "CREATE TABLE Plan " +
                "(pla_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pla_name TEXT NOT NULL, " +
                "pla_work INTEGER NOT NULL, " +
                "pla_break INTEGER NOT NULL, " +
                "pla_task INTEGER NOT NULL, " +
                "pla_cat_id INTEGER NOT NULL, " +
                "pla_lvl_id INTEGER NOT NULL, " +
                "FOREIGN KEY (pla_cat_id) REFERENCES Category(cat_id), " +
                "FOREIGN KEY (pla_lvl_id) REFERENCES ImportanceLevel(lvl_id))",
        "CREATE TABLE Report " +
                "(rpt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rpt_pla_name TEXT NOT NULL, " +
                "rpt_pla_work INTEGER NOT NULL, " +
                "rpt_pla_break INTEGER NOT NULL, " +
                "rpt_pla_task INTEGER NOT NULL, " +
                "rpt_pla_cat_name TEXT NOT NULL, " +
                "rpt_pla_lvl_name TEXT NOT NULL)",
        "INSERT INTO Category (cat_name) VALUES ('Trabalho')",
        "INSERT INTO Category (cat_name) VALUES ('Estudos')",
        "INSERT INTO Category (cat_name) VALUES ('Hobbies')",
        "INSERT INTO Category (cat_name) VALUES ('Atividades Físicas')",
        "INSERT INTO ImportanceLevel (lvl_name) VALUES ('Muito Baixo')",
        "INSERT INTO ImportanceLevel (lvl_name) VALUES ('Baixo')",
        "INSERT INTO ImportanceLevel (lvl_name) VALUES ('Médio')",
        "INSERT INTO ImportanceLevel (lvl_name) VALUES ('Alto')",
        "INSERT INTO ImportanceLevel (lvl_name) VALUES ('Muito Alto')"
    )
    val sqlDropTables = arrayOf(
        "DROP TABLE Category",
        "DROP TABLE ImportanceLevel",
        "DROP TABLE Plan",
        "DROP TABLE Report"
    )

    override fun onCreate(db: SQLiteDatabase) {
        sqlCreateTables.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqlDropTables.forEach {
            db.execSQL(it)
        }
        onCreate(db)
    }

    fun getCategoryNames(): List<String> {
        val catNames = mutableListOf<String>()
        val selectQuery = "SELECT cat_name FROM Category"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val nameCat = cursor.getColumnIndex("cat_name")
                val catName = cursor.getString(nameCat)
                catNames.add(catName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return catNames
    }

    fun getImportanceLevelNames(): List<String> {
        val lvlNames = mutableListOf<String>()
        val selectQuery = "SELECT lvl_name FROM ImportanceLevel"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val nameLvl = cursor.getColumnIndex("lvl_name")
                val lvlName = cursor.getString(nameLvl)
                lvlNames.add(lvlName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lvlNames
    }
}