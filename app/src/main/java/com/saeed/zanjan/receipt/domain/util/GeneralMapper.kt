package com.saeed.zanjan.receipt.domain.util

import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntity
import com.saeed.zanjan.receipt.cash.model.JewelryEntity
import com.saeed.zanjan.receipt.cash.model.LaundryEntity
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntity
import com.saeed.zanjan.receipt.cash.model.PhotographyEntity
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.model.TailoringEntity
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

interface GeneralMapper
    {
    fun mapToConfectioneryEntity(model: GeneralReceipt): ConfectioneryEntity
    fun mapToJewelryEntity(model: GeneralReceipt): JewelryEntity
    fun mapLaundryEntity(model: GeneralReceipt): LaundryEntity
    fun mapToOtherJobsEntity(model: GeneralReceipt): OtherJobsEntity
    fun mapToPhotographyEntity(model: GeneralReceipt): PhotographyEntity
    fun mapToRepairsEntity(model: GeneralReceipt): RepairsEntity
    fun mapToTailoringEntity(model: GeneralReceipt): TailoringEntity

    fun mapFromConfectioneryEntity(model:ConfectioneryEntity): GeneralReceipt
    fun mapFromJewelryEntity(model:JewelryEntity): GeneralReceipt
    fun mapFromLaundryEntity(model:LaundryEntity): GeneralReceipt
    fun mapFromOtherJobsEntity(model:OtherJobsEntity): GeneralReceipt
    fun mapFromPhotographyEntity(model:PhotographyEntity): GeneralReceipt
    fun mapFromRepairsEntity(model:RepairsEntity): GeneralReceipt
    fun mapFromTailoringEntity(model:TailoringEntity): GeneralReceipt

}