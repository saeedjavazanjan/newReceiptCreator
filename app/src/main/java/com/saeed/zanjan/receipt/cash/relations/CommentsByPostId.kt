package com.saeeed.devejump.project.tailoring.cash.relations

import androidx.room.Embedded
import androidx.room.Relation

data class CommentsByPostId(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<CommentEntity>
)