package com.saeed.zanjan.receipt.cash.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntity
import com.saeed.zanjan.receipt.cash.model.CustomerEntity
import com.saeed.zanjan.receipt.cash.model.JewelryEntity
import com.saeed.zanjan.receipt.cash.model.LaundryEntity
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntity
import com.saeed.zanjan.receipt.cash.model.PhotographyEntity
import com.saeed.zanjan.receipt.cash.model.TailoringEntity

@Database(
    entities = [
        RepairsEntity::class,
        TailoringEntity::class,
        JewelryEntity::class,
        PhotographyEntity::class,
        LaundryEntity::class,
        ConfectioneryEntity::class,
        OtherJobsEntity::class,
        CustomerEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun receiptDao(): ReceiptDao

    //  abstract fun bookMark():BookMarkedSewEntity

    companion object {
        val DATABASE_NAME: String = "receipt.sqlite"
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `customer` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `name` TEXT,
                        `phone` TEXT,
                        `prepayment` TEXT,
                        `cost` TEXT
                    )
                """.trimIndent()
                )





                //repairs
                // Create the new table
                database.execSQL(
                    """
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
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `repairs_new` (`id`, `status`, `name`, `phone`, `loanerName`, `loanerProblems`, `Risks`, `deliveryTime`, `receiptTime`, `accessories`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `loanerName`, `loanerProblems`, `Risks`, `deliveryTime`, `receiptTime`, `accessories`, `cost`, `prepayment`
                    FROM `repairs`
                """.trimIndent()
                )
                database.execSQL(
                    """
                    INSERT INTO `customer` (`id`, `name`, `phone`, `prepayment`, `cost`)
                    SELECT `id`, `name`, `phone`, `prepayment`, `cost`
                    FROM `repairs`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `repairs`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `repairs_new` RENAME TO `repairs`")


                //tailoring
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `tailoring_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `loanerName` TEXT,
                        `OrderSpecification` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `sizes` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `tailoring_new` (`id`, `status`, `name`, `phone`, `loanerName`, `OrderSpecification`,`deliveryTime`, `receiptTime`, `sizes`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `loanerName`, `OrderSpecification`, `deliveryTime`, `receiptTime`, `sizes`, `cost`, `prepayment`
                    FROM `tailoring`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `tailoring`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `tailoring_new` RENAME TO `tailoring`")


                //jewelry
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `jewelry_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `loanerName` TEXT,
                        `OrderSpecification` TEXT,
                        `loanerProblems` TEXT,
                        `loanerSpecification` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `jewelry_new` (`id`, `status`, `name`, `phone`, `loanerName`, `OrderSpecification`,`loanerProblems`,`loanerSpecification`,`deliveryTime`, `receiptTime`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `loanerName`, `OrderSpecification`,`loanerProblems`,`loanerSpecification`,`deliveryTime`, `receiptTime`,`cost`, `prepayment`
                    FROM `jewelry`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `jewelry`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `jewelry_new` RENAME TO `jewelry`")


                //photography
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `photography_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `OrderName` TEXT,
                        `OrderSize` TEXT,
                        `OrderNumber` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `photography_new` (`id`, `status`, `name`, `phone`, `OrderName`, `OrderSize`,`OrderNumber`,`deliveryTime`, `receiptTime`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `OrderName`, `OrderSize`,`OrderNumber`,`deliveryTime`, `receiptTime`,`cost`, `prepayment`
                    FROM `photography`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `photography`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `photography_new` RENAME TO `photography`")


                //laundry
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `laundry_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `loanerName` TEXT,
                        `OrderType` TEXT,
                        `Description` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `laundry_new` (`id`, `status`, `name`, `phone`, `loanerName`, `OrderType`,`Description`,`deliveryTime`, `receiptTime`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `loanerName`, `OrderType`,`Description`,`deliveryTime`, `receiptTime`,`cost`, `prepayment`
                    FROM `laundry`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `laundry`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `laundry_new` RENAME TO `laundry`")


                //confectionery
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `confectionery_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `OrderName` TEXT,
                        `OrderSpecification` TEXT,       
                        `OrderWeight` TEXT,
                        `Description` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `confectionery_new` (`id`, `status`, `name`, `phone`, `OrderName`,`OrderSpecification`,`OrderWeight`,`Description`,`deliveryTime`, `receiptTime`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `OrderName`, `OrderSpecification`,`OrderWeight`,`Description`,`deliveryTime`,`receiptTime`,`cost`, `prepayment`
                    FROM `confectionery`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `confectionery`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `confectionery_new` RENAME TO `confectionery`")


                //otherJobs
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `otherJobs_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `status` INTEGER,
                        `name` TEXT NOT NULL,
                        `phone` TEXT,
                        `OrderName` TEXT,
                        `Description` TEXT,
                        `OrderNumber` TEXT,
                        `deliveryTime` TEXT,
                        `receiptTime` TEXT,
                        `cost` TEXT,
                        `prepayment` TEXT
                    )
                """.trimIndent()
                )

                // Copy the data
                database.execSQL(
                    """
                    INSERT INTO `otherJobs_new` (`id`, `status`, `name`, `phone`, `OrderName`,`Description`,`OrderNumber`,`deliveryTime`, `receiptTime`, `cost`, `prepayment`)
                    SELECT `id`, `status`, `name`, `phone`, `OrderName`, `Description`,`OrderNumber`,`deliveryTime`, `receiptTime`,`cost`, `prepayment`
                    FROM `otherJobs`
                """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE `otherJobs`")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE `otherJobs_new` RENAME TO `otherJobs`")
            }
        }

    }


}

