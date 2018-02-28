package com.github.gibbrich.todo.model

import android.arch.persistence.room.*
import java.util.*

/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */
@Entity(tableName = "Tasks")
class Task(
        @PrimaryKey
        @ColumnInfo(name = "entryid")
        val id: String,

        val title: String?,

        val description: String?,

        @ColumnInfo(name = "completed")
        val isCompleted: Boolean
)
{
    @Ignore
    constructor(title: String?, description: String?, isCompleted: Boolean) : this(
            UUID.randomUUID().toString(),
            title,
            description,
            isCompleted
    )

    @Ignore
    constructor(id: String, title: String?, description: String?): this(id, title, description, false)

    @Ignore
    constructor(title: String?, description: String?): this(
            UUID.randomUUID().toString(),
            title,
            description,
            false
    )

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (other == null || javaClass != other.javaClass)
        {
            return false
        }

        val otherTask = other as Task
        return id == otherTask.id
                && title == otherTask.title
                && description == otherTask.description
    }

    override fun hashCode() = Objects.hash(id, title, description)

    override fun toString() = "Task with title $title"
}