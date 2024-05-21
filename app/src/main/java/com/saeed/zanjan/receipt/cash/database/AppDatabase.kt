package com.saeed.zanjan.receipt.cash.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.ReceiptDao

@Database(
    entities = [
        RepairsEntity::class],
    version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun receiptDao(): ReceiptDao

  //  abstract fun bookMark():BookMarkedSewEntity

    companion object {
        val DATABASE_NAME: String = "receipt.sqlite"
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `repairs_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `loanerName` TEXT,
                        `loanerProblems` TEXT,
                        `Risks` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `accessories` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent())

                // Copy the data
                database.execSQL("""
                    INSERT INTO `repairs_new` (`id`, `status`, `name`, `phone`, `loanerName`, `loanerProblems`, `Risks`, `deliveryTime`, `receiptTime`, `accessories`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `loanerName`, `loanerProblems`, `Risks`, `deliveryTime`, `receiptTime`, `accessories`, `cost`, `prepayment`
                    FROM `repairs`
                """.trimIndent())

                // Remove the old table
                database.execSQL("DROP TABLE `repairs`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `repairs_new` RENAME TO `repairs`")
            }
        }

    }


}

