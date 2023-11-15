package org.example.project

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

private val database by lazy {
    val db = Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
    
    transaction(db) { 
        SchemaUtils.create(Users)
    }

    db
}

suspend fun <T> tr(body: suspend Transaction.() -> T): T {
    return newSuspendedTransaction(db = database, statement = body)
}

object Users : Table("Users") {
    val username = varchar("username", 64).uniqueIndex()
    val password = varchar("password", 64) // NEVER EVER EVER EVER DO THIS
    val duckType = varchar("duckType", 32).nullable().default(null)
    val counter = integer("counter").default(0)

    override val primaryKey: PrimaryKey = PrimaryKey(username)
}
