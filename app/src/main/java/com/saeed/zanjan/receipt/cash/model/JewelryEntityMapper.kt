package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.ConfectioneryReceipt
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.JewelryReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class JewelryEntityMapper: DomainMapper<JewelryEntity, JewelryReceipt?> {
    override fun mapToDomainModel(model: JewelryEntity): JewelryReceipt? {
        return JewelryReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.loanerName,
            model.orderSpecification,
            model.loanerProblems,
            model.loanerSpecification,
            model.deliveryTime,
            model.receiptTime,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: JewelryReceipt?): JewelryEntity {
        return JewelryEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.loanerName,
            domainModel.orderSpecification,
            domainModel.loanerProblems,
            domainModel.loanerSpecification,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.cost,
            domainModel.prepayment
        )

    }
    override fun mapToDomainList(entityList: List<JewelryEntity>): List<JewelryReceipt?> {
        val list= mutableListOf<JewelryReceipt?>()
        entityList.forEach {
            list.add(mapToDomainModel(it))
        }
        return list
    }

}