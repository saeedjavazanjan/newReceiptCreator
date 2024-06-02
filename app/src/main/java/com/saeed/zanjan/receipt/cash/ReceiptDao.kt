package com.saeed.zanjan.receipt.cash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntity
import com.saeed.zanjan.receipt.cash.model.JewelryEntity
import com.saeed.zanjan.receipt.cash.model.LaundryEntity
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntity
import com.saeed.zanjan.receipt.cash.model.PhotographyEntity
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.model.TailoringEntity


@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConfectioneryReceipt(conf: ConfectioneryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJewelryReceipt(jew: JewelryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaundryReceipt(laundry: LaundryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOtherJobsReceipt(other: OtherJobsEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotoReceipt(photo: PhotographyEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepairReceipt(repair: RepairsEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTailoringReceipt(tailor: TailoringEntity): Long


    @Query("SELECT * FROM confectionery")
    fun getAllConfectioneryReceipts(): List<ConfectioneryEntity>

    @Query("SELECT * FROM jewelry")
    fun getAllJewelryReceipts(): List<JewelryEntity>

    @Query("SELECT * FROM laundry")
    fun getAllLaundryReceipts(): List<LaundryEntity>

    @Query("SELECT * FROM otherJobs")
    fun getAllOtherJobsReceipts(): List<OtherJobsEntity>

    @Query("SELECT * FROM photography")
    fun getAllPhotographyReceipts(): List<PhotographyEntity>

    @Query("SELECT * FROM repairs")
    fun getAllRepairsEntity(): List<RepairsEntity>

    @Query("SELECT * FROM tailoring")
    fun getAllTailoringReceipts(): List<TailoringEntity>


    @Query("SELECT * FROM confectionery WHERE id = :Id")
    suspend fun getConfectioneryReceipt(Id: Int): ConfectioneryEntity

    @Query("SELECT * FROM jewelry WHERE id = :Id")
    suspend fun getJewelryReceipt(Id: Int): JewelryEntity

    @Query("SELECT * FROM laundry WHERE id = :Id")
    suspend fun getLaundryReceipt(Id: Int): LaundryEntity

    @Query("SELECT * FROM otherJobs WHERE id = :Id")
    suspend fun getOtherJobsReceipt(Id: Int): OtherJobsEntity

    @Query("SELECT * FROM photography WHERE id = :Id")
    suspend fun getPhotographyReceipt(Id: Int): PhotographyEntity

    @Query("SELECT * FROM repairs WHERE id = :Id")
    suspend fun getRepairsReceipt(Id: Int): RepairsEntity

    @Query("SELECT * FROM tailoring WHERE id = :Id")
    suspend fun getTailoringReceipt(Id: Int): TailoringEntity

    @Update
    fun updateRepair(repair: RepairsEntity)

    @Update
    fun updateConfectionery(repair: ConfectioneryEntity)

    @Update
    fun updateJewelry(repair: JewelryEntity)

    @Update
    fun updatePhotography(repair: PhotographyEntity)

    @Update
    fun updateLaundry(repair: LaundryEntity)

    @Update
    fun updateTailoring(repair: TailoringEntity)

    @Update
    fun updateOtherJobs(repair: OtherJobsEntity)


    @Query("DELETE FROM confectionery WHERE id = :primaryKey")
    suspend fun deleteConfectionery(primaryKey: Int): Int
     @Query("DELETE FROM jewelry WHERE id = :primaryKey")
    suspend fun deleteJewelry(primaryKey: Int): Int
     @Query("DELETE FROM laundry WHERE id = :primaryKey")
    suspend fun deleteLaundry(primaryKey: Int): Int
     @Query("DELETE FROM otherJobs WHERE id = :primaryKey")
    suspend fun deleteOtherJobs(primaryKey: Int): Int
     @Query("DELETE FROM photography WHERE id = :primaryKey")
    suspend fun deletePhotography(primaryKey: Int): Int
     @Query("DELETE FROM repairs WHERE id = :primaryKey")
    suspend fun deleteRepair(primaryKey: Int): Int
     @Query("DELETE FROM tailoring WHERE id = :primaryKey")
    suspend fun deleteTailoring(primaryKey: Int): Int





    /* @Insert (onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertUserData(userData: UserDataEntity)
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertComment(comment: CommentEntity)
 */
    /*   @Transaction
       @Query("SELECT * FROM posts WHERE id = :postId")
       suspend fun getPostWithComment(postId: Int): List<CommentsByPostId>

      *//* @Transaction
    @Query("SELECT * FROM userData WHERE userid = :userID")
    suspend fun getUserFollowers(userID: Int): List<FollowersByConsideredUserId>

    @Transaction
    @Query("SELECT * FROM userData WHERE userid = :userID")
    suspend fun getUserFollowings(userID: Int): List<FollowingsByConsideredUserId>*//*
    @Query("SELECT * FROM userData")
    suspend fun getUserData():UserDataEntity


    @Update
    suspend fun updateUserData(user:List<UserDataEntity>):Int


    @Query("UPDATE userData SET bookMarks = :bookmarkState WHERE userid LIKE :id ")
    suspend fun updateBookmarks(bookmarkState:String,id:Int):Int

    @Query("UPDATE userData SET likes = :likedPostsId WHERE userid LIKE :userId ")
    suspend fun updateLikes(likedPostsId:String,userId:Int):Int

   *//* @Query("UPDATE userData SET comments = :comments WHERE userid LIKE :userId ")
    suspend fun updateUserComments(comments:String,userId:Int):Int*//*

 *//*   @Query("UPDATE sewMethods SET comments = :comments WHERE id LIKE :postId ")
    suspend fun updateCommentsOnPost(comments:String,postId:Int):Int*//*

    @Query("UPDATE posts SET `like` = :likeCount WHERE id LIKE :postId ")
    suspend fun updatePostLikeCount(likeCount:String,postId:Int):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSewMethods(post: List<PostEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFollowers(follower: List<FollowersEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFollowings(following: List<FollowingEntity>): LongArray
   *//* @Query("SELECT EXISTS(SELECT bookMars FROM userData WHERE id = :id)")
    fun isBookMarkedOrNot(id : Int) : Boolean*//*
    *//*@Query("SELECT id FROM bookMarkedSewMethods WHERE id = :id")
    suspend fun getBookMarkedSewById(id: Int): Int*//*
    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getSewById(id: Int): PostEntity?

   *//* @Query("DELETE FROM userData WHERE id IN (:ids)")
    suspend fun deleteSew(ids: List<Int>): Int*//*

    @Query("DELETE FROM posts")
    suspend fun deleteAllSewMethods()

    @Query("DELETE FROM posts WHERE id = :primaryKey")
    suspend fun deleteSew(primaryKey: Int): Int

    */
    /**
     * Retrieve recipes for a particular page.
     * Ex: page = 2 retrieves recipes from 30-60.
     * Ex: page = 3 retrieves recipes from 60-90
     *//*
    @Query("""
        SELECT * FROM posts 
        WHERE title LIKE '%' || :query || '%'
        OR description LIKE '%' || :query || '%'  
        OR category LIKE  '%' || :query || '%'
        ORDER BY date_added DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
    suspend fun searchSewMethods(
        query: String,
        page: Int,
        pageSize: Int = POSTS_PAGINATION_PAGE_SIZE
    ): List<PostEntity>


    @Query("""
        SELECT * FROM followers 
        WHERE user_name LIKE '%' || :query || '%'
        ORDER BY user_name DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
    suspend fun searchFollowers(
        query: String,
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowersEntity>

    */
    /**
     * Same as 'searchRecipes' function, but no query.
     *//*
    @Query("""
        SELECT * FROM posts 
        ORDER BY date_added DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllSewMethods(
        page: Int,
        pageSize: Int = POSTS_PAGINATION_PAGE_SIZE
    ): List<PostEntity>

    @Query("""
        SELECT * FROM followers 
        ORDER BY user_name DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllFollowers(
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowersEntity>

    @Query("""
        SELECT * FROM followings
        ORDER BY user_name DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllFollowings(
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowingEntity>

    */
    /**
     * Restore Recipes after process death
     *//*
    @Query("""
        SELECT * FROM posts 
        WHERE title LIKE '%' || :query || '%'
        OR description LIKE '%' || :query || '%' 
        OR category LIKE  '%' || :query || '%' 
        ORDER BY date_added DESC LIMIT (:page * :pageSize)
        """)
    suspend fun restoreSewMethods(
        query: String,
        page: Int,
        pageSize: Int = POSTS_PAGINATION_PAGE_SIZE
    ): List<PostEntity>


    @Query("""
        SELECT * FROM followers 
        WHERE user_name LIKE '%' || :query || '%'
        ORDER BY user_name DESC LIMIT (:page * :pageSize)
        """)
    suspend fun restoreFollowers(
        query: String,
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowersEntity>

    @Query("""
        SELECT * FROM followings 
        WHERE user_name LIKE '%' || :query || '%'
        ORDER BY user_name DESC LIMIT (:page * :pageSize)
        """)
    suspend fun restoreFollowings(
        query: String,
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowingEntity>

    */
    /**
     * Same as 'restoreRecipes' function, but no query.
     *//*
    @Query("""
        SELECT * FROM posts 
        ORDER BY date_added DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllSewMethods(
        page: Int,
        pageSize: Int = POSTS_PAGINATION_PAGE_SIZE
    ): List<PostEntity>

    @Query("""
        SELECT * FROM followers 
        ORDER BY user_name DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllFollowers(
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowersEntity>

    @Query("""
        SELECT * FROM followers 
        ORDER BY user_name DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllFollowings(
        page: Int,
        pageSize: Int = FOLLOWERS_OR_FOLLOWING_PAGINATION_PAGE_SIZE
    ): List<FollowingEntity>*/
}

