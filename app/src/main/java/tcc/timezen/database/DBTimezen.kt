package tcc.timezen.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBTimezen(context: Context) :
    SQLiteOpenHelper(context, "timezen.db", null, 1) {

    val sqlCreateTables = arrayOf(
        "CREATE IF NOT EXIST TABLE Category " +
                "(cat_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cat_name TEXT NOT NULL)",
        "CREATE IF NOT EXIST TABLE ImportanceLevel " +
                "(lvl_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lvl_name TEXT NOT NULL)",
        "CREATE IF NOT EXIST TABLE Plan " +
                "(pla_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pla_name TEXT NOT NULL, " +
                "pla_work INTEGER NOT NULL, " +
                "pla_break INTEGER NOT NULL, " +
                "pla_task INTEGER NOT NULL, " +
                "pla_cat_id INTEGER NOT NULL, " +
                "pla_lvl_id INTEGER NOT NULL, " +
                "FOREIGN KEY (pla_cat_id) REFERENCES Category(cat_id), " +
                "FOREIGN KEY (pla_lvl_id) REFERENCES ImportanceLevel(lvl_id))",
        "CREATE IF NOT EXIST TABLE Report " +
                "(rpt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rpt_pla_name TEXT NOT NULL, " +
                "rpt_pla_work INTEGER NOT NULL, " +
                "rpt_pla_break INTEGER NOT NULL, " +
                "rpt_pla_task INTEGER NOT NULL, " +
                "rpt_pla_cat_name TEXT NOT NULL, " +
                "rpt_pla_lvl_name TEXT NOT NULL)",
        "INSERT INTO Category (cat_name) VALUES ('Trabalho')",
        "INSERT INTO Category (cat_name) VALUES ('Estudos')",
        "INSERT INTO Category (cat_name) VALUES ('Meditação')",
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
}