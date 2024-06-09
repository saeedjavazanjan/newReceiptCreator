package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.JewelryReceipt
import com.saeed.zanjan.receipt.domain.models.PhotographyReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class PhotographyEntityMapper: DomainMapper<PhotographyEntity, PhotographyReceipt?> {
    override fun mapToDomainModel(model: PhotographyEntity): PhotographyReceipt? {
        return PhotographyReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.orderName,
            model.orderSize,
            model.orderNumber,
            model.deliveryTime,
            model.receiptTime,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: PhotographyReceipt?): PhotographyEntity {
        return PhotographyEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.orderName,
            domainModel.orderSize,
            domainModel.orderNumber,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.cost,
            domainModel.prepayment
        )

    }

    override fun mapToDomainList(entityList: List<PhotographyEntity>): List<PhotographyReceipt?> {
        val list= mutableListOf<PhotographyReceipt?>()
        entityList.forEach {
            list.add(mapToDomainModel(it))
        }
        return list
    }
}