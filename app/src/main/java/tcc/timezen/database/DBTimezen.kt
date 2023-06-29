package tcc.timezen.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tcc.timezen.model.Plan
import tcc.timezen.model.PomodoroTimer
import tcc.timezen.model.Report
import tcc.timezen.utils.Translator

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
                "rpt_pla_id INTEGER NOT NULL, " +
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
    val t = Translator()

    val sqlInsertTeste = arrayOf(
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Correr', ${t.getMsFromMinute(30)}, ${t.getMsFromMinute(10)}, 5, 4, 1)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (1, 'Correr', 300, 10, 5, 'Atividades Físicas', 'Muito Baixo')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Ler', ${t.getMsFromMinute(35)}, ${t.getMsFromMinute(15)}, 3, 3, 1)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (2, 'Ler', 210, 15, 3, 'Hobbies', 'Muito Baixo')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Inglês', ${t.getMsFromMinute(40)}, ${t.getMsFromMinute(5)}, 2, 2, 4)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (3, 'Inglês', 400, 5, 2, 'Estudos', 'Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Jogar', ${t.getMsFromMinute(25)}, ${t.getMsFromMinute(20)}, 2, 3, 3)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (4, 'Jogar', 100, 20, 2, 'Hobbies', 'Médio')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Programar', ${t.getMsFromMinute(50)}, ${t.getMsFromMinute(5)}, 3, 3, 5)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (5, 'Programar', 600, 5, 2, 'Hobbies', 'Muito Alto')"
        )
    val sqlInsertDecio = arrayOf(
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Lua', ${t.getMsFromMinute(30)}, ${t.getMsFromMinute(10)}, 5, 1, 4)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (1, 'Lua', 90, 10, 3, 'Trabalho', 'Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Química', ${t.getMsFromMinute(20)}, ${t.getMsFromMinute(5)}, 3, 2, 1)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (2, 'Química', 60, 5, 3, 'Estudos', 'Muito Baixo')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Relatório', ${t.getMsFromMinute(45)}, ${t.getMsFromMinute(15)}, 2, 1, 4)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (3, 'Relatório', 90, 15, 2, 'Trabalho', 'Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Correr', ${t.getMsFromMinute(35)}, ${t.getMsFromMinute(10)}, 2, 4, 3)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (4, 'Correr', 175, 10, 5, 'Atividades Físicas', 'Médio')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Programar', ${t.getMsFromMinute(50)}, ${t.getMsFromMinute(15)}, 3, 2, 5)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (5, 'Programar', 150, 15, 3, 'Estudos', 'Muito Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Matemática', ${t.getMsFromMinute(30)}, ${t.getMsFromMinute(10)}, 5, 2, 3)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (6, 'Matemática', 150, 10, 5, 'Estudos', 'Médio')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Linux', ${t.getMsFromMinute(30)}, ${t.getMsFromMinute(5)}, 3, 1, 4)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (7, 'Linux', 300, 5, 5, 'Trabalho', 'Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Elixir', ${t.getMsFromMinute(45)}, ${t.getMsFromMinute(15)}, 2, 1, 4)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (8, 'Elixir', 180, 15, 2, 'Trabalho', 'Alto')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Jogar', ${t.getMsFromMinute(25)}, ${t.getMsFromMinute(10)}, 2, 3, 1)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (9, 'Jogar', 50, 10, 2, 'Hobbies', 'Muito Baixo')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Rede Social', ${t.getMsFromMinute(10)}, ${t.getMsFromMinute(15)}, 3, 3, 1)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (10, 'Rede Social', 40, 15, 2, 'Hobbies', 'Muito Baixo')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('C++', ${t.getMsFromMinute(35)}, ${t.getMsFromMinute(10)}, 2, 2, 3)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (11, 'C++', 140, 10, 2, 'Estudos', 'Médio')",
        "INSERT INTO Plan (pla_name, pla_work, pla_break, pla_task, pla_cat_id, pla_lvl_id) VALUES ('Provas', ${t.getMsFromMinute(50)}, ${t.getMsFromMinute(15)}, 3, 2, 5)",
        "INSERT INTO Report (rpt_pla_id, rpt_pla_name, rpt_pla_work, rpt_pla_break, rpt_pla_task, rpt_pla_cat_name, rpt_pla_lvl_name) VALUES (12, 'Provas', 500, 15, 2, 'Estudos', 'Muito Alto')"
    )

    fun insertTest() {
        val db = writableDatabase
        sqlInsertTeste.forEach {
            db.execSQL(it)
        }
        db.close()
    }

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

    fun getCategoryById(name: String): Int {
        val selectQuery = "SELECT cat_id FROM Category WHERE cat_name = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(name))
        var catId = -1

        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex("cat_id")
            catId = cursor.getInt(id)
        }
        cursor.close()
        db.close()
        return catId
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

    fun getImportanceLevelById(name: String): Int {
        val selectQuery = "SELECT lvl_id FROM ImportanceLevel WHERE lvl_name = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(name))
        var lvlId = -1

        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex("lvl_id")
            lvlId = cursor.getInt(id)
        }
        cursor.close()
        db.close()
        return lvlId
    }

    fun insertPlan(
        name: String,
        workTime: Int,
        breakTime: Int,
        task: Int,
        categoryId: Int,
        importanceLevelId: Int
    ) {
        println("New Plan: $name")
        val db = writableDatabase
        val values = ContentValues()
        values.put("pla_name", name)
        values.put("pla_work", workTime)
        values.put("pla_break", breakTime)
        values.put("pla_task", task)
        values.put("pla_cat_id", categoryId)
        values.put("pla_lvl_id", importanceLevelId)

        db.insert("Plan", null, values)
        db.close()
    }

    fun getReportList(): List<Report> {
        val reports = mutableListOf<Report>()
        val db = readableDatabase
        val selectQuery = "SELECT rpt_pla_name, rpt_pla_work FROM Report"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getColumnIndex("rpt_pla_name")
                val work = cursor.getColumnIndex("rpt_pla_work")

                val rptName = cursor.getString(name)
                val rptWork = cursor.getInt(work)

                reports.add(Report(rptName, rptWork))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reports
    }

    fun getPlanList(): List<Plan> {
        val plans = mutableListOf<Plan>()
        val db = readableDatabase
        val selectQuery =
            "SELECT pla_name, pla_work, pla_break, pla_task, Category.cat_name, ImportanceLevel.lvl_name " +
                    "FROM Plan JOIN Category ON Plan.pla_cat_id = Category.cat_id JOIN ImportanceLevel ON Plan.pla_lvl_id = ImportanceLevel.lvl_id"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getColumnIndex("pla_name")
                val workTime = cursor.getColumnIndex("pla_work")
                val breakTime = cursor.getColumnIndex("pla_break")
                val task = cursor.getColumnIndex("pla_task")
                val catName = cursor.getColumnIndex("cat_name")
                val lvlName = cursor.getColumnIndex("lvl_name")

                val namePlan = cursor.getString(name)
                val workPlan = cursor.getLong(workTime)
                val breakPlan = cursor.getLong(breakTime)
                val taskPlan = cursor.getInt(task)
                val catPlan = cursor.getString(catName)
                val lvlPlan = cursor.getString(lvlName)

                plans.add(Plan(
                    namePlan,
                    PomodoroTimer(workPlan, breakPlan, taskPlan),
                    catPlan,
                    lvlPlan
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return plans
    }

    fun getPlanById(id: Int): Plan = getPlanList()[id]

    fun getPlanId(name: String): Int {
        val selectQuery = "SELECT pla_id FROM Plan WHERE pla_name = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(name))
        var plaId = -1

        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex("pla_id")
            plaId = cursor.getInt(id)
        }
        cursor.close()
        db.close()
        return plaId
    }

    fun deletePlanById(planoId: Int): Boolean {
        println("Delete id $planoId")
        val db = writableDatabase
        val whereClause = "pla_id = ?"
        val whereArgs = arrayOf(planoId.toString())
        val deletedRows = db.delete("Plan", whereClause, whereArgs)
        db.close()
        return deletedRows > 0
    }

    fun updatePlan(
        id: Int,
        name: String,
        workTime: Int,
        breakTime: Int,
        task: Int,
        categoryId: Int,
        importanceLevelId: Int
    ) {
        println("id for update is $id")
        val db = writableDatabase
        val values = ContentValues()
        values.put("pla_name", name)
        values.put("pla_work", workTime)
        values.put("pla_break", breakTime)
        values.put("pla_task", task)
        values.put("pla_cat_id", categoryId)
        values.put("pla_lvl_id", importanceLevelId)
        println("updating table")
        db.update("Plan", values, "pla_id=?", arrayOf(id.toString()))
        db.close()
    }

    fun hasPlan(): Boolean {
        val db = readableDatabase
        val selectQuery = "SELECT COUNT(*) FROM  Plan"
        val cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count > 0
    }

    fun hasNameInPlan(name: String): Boolean {
        val db = readableDatabase
        val selectQuery = "SELECT COUNT(*) FROM Plan WHERE pla_name = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count > 0
    }

    fun insertReport(
        id: Int,
        name: String,
        workTime: Int,
        breakTime: Int,
        task: Int,
        category: String,
        importanceLevel: String
    ) {
        println("New Plan Report: $id - $name ")
        val db = writableDatabase
        val values = ContentValues()
        values.put("rpt_pla_id", id)
        values.put("rpt_pla_name", name)
        values.put("rpt_pla_work", workTime)
        values.put("rpt_pla_break", breakTime)
        values.put("rpt_pla_task", task)
        values.put("rpt_pla_cat_name", category)
        values.put("rpt_pla_lvl_name", importanceLevel)

        db.insert("Report", null, values)
        db.close()
    }

    fun getReportById(name: String): Int {
        val selectQuery = "SELECT rpt_id FROM Report WHERE rpt_pla_name = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(name))
        var rptId = -1

        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex("rpt_id")
            rptId = cursor.getInt(id)
        }
        cursor.close()
        db.close()
        return rptId
    }

    fun deleteReportById(reportId: Int): Boolean {
        println("Delete id $reportId")
        val db = writableDatabase
        val whereClause = "rpt_id = ?"
        val whereArgs = arrayOf(reportId.toString())
        val deletedRows = db.delete("Report", whereClause, whereArgs)
        db.close()
        return deletedRows > 0
    }

    fun hasNameInReport(name: String): Boolean {
        val db = readableDatabase
        val selectQuery = "SELECT COUNT(*) FROM Report WHERE rpt_pla_name = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count > 0
    }

    fun getWorkTimeFromReport(name: String): Int {
        val db = readableDatabase
        val selectQuery = "SELECT rpt_pla_work FROM Report WHERE rpt_pla_name = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        cursor.moveToFirst()
        val work = cursor.getColumnIndex("rpt_pla_work")
        val workPlan = cursor.getInt(work)
        cursor.close()
        db.close()
        return workPlan
    }

    fun updateWorkInReport(name: String, value: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("rpt_pla_work", value)
        }

        val whereClause = "rpt_pla_name = ?"
        val whereArgs = arrayOf(name)
        db.update("Report", values, whereClause, whereArgs)
        db.close()
    }

    fun updateNameInReport(name: String, id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("rpt_pla_name", name)
        }
        val whereClause = "rpt_pla_id = ?"
        val whereArgs = arrayOf(id.toString())
        db.update("Report", values, whereClause, whereArgs)
        db.close()
    }

    fun getFilterPlans(category: String, level: String): List<Plan> {
        val db = readableDatabase
        val plans = mutableListOf<Plan>()
        val projection = arrayOf("pla_name", "pla_work", "pla_break", "pla_task", "Category.cat_name", "ImportanceLevel.lvl_name")

        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (!category.isEmpty() && level.isEmpty()) {
            selection = "Category.cat_name = ?"
            selectionArgs = arrayOf(category)
        } else if (category.isEmpty() && !level.isEmpty()) {
            selection = "ImportanceLevel.lvl_name = ?"
            selectionArgs = arrayOf(level)
        } else if (!category.isEmpty() && !level.isEmpty()) {
            selection = "Category.cat_name = ? AND ImportanceLevel.lvl_name = ?"
            selectionArgs = arrayOf(category, level)
        }

        val join = "Plan " +
                "LEFT JOIN Category ON Plan.pla_cat_id = Category.cat_id LEFT JOIN ImportanceLevel ON Plan.pla_lvl_id = ImportanceLevel.lvl_id"

        val cursor = db.query(join, projection, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getColumnIndex("pla_name")
                val workTime = cursor.getColumnIndex("pla_work")
                val breakTime = cursor.getColumnIndex("pla_break")
                val task = cursor.getColumnIndex("pla_task")
                val catName = cursor.getColumnIndex("cat_name")
                val lvlName = cursor.getColumnIndex("lvl_name")

                val namePlan = cursor.getString(name)
                val workPlan = cursor.getLong(workTime)
                val breakPlan = cursor.getLong(breakTime)
                val taskPlan = cursor.getInt(task)
                val catPlan = cursor.getString(catName)
                val lvlPlan = cursor.getString(lvlName)

                plans.add(Plan(
                    namePlan,
                    PomodoroTimer(workPlan, breakPlan, taskPlan),
                    catPlan,
                    lvlPlan
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return plans
    }

    fun reset() {
        val db = writableDatabase
        sqlDropTables.forEach {
            db.execSQL(it)
        }
        onCreate(db)
    }
}
