package me.tylerbwong.stack.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.tylerbwong.stack.data.persistence.entity.QuestionEntity

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questions: List<QuestionEntity>)

    @Query("SELECT * FROM question WHERE questionId = :questionId")
    suspend fun get(questionId: Int): QuestionEntity?

    @Query("SELECT * FROM question WHERE favorited = 1")
    fun getBookmarks(): Flow<List<QuestionEntity>>

    @Query("DELETE FROM question WHERE questionId = :questionId")
    suspend fun delete(questionId: Int)

    @Query("DELETE FROM question")
    suspend fun clearQuestions()
}
