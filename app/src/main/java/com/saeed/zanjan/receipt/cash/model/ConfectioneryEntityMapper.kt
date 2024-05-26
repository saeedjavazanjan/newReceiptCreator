package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.ConfectioneryReceipt
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class ConfectioneryEntityMapper: DomainMapper<ConfectioneryEntity, ConfectioneryReceipt?> {
    override fun mapToDomainModel(model: ConfectioneryEntity): ConfectioneryReceipt? {
        return ConfectioneryReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.orderName,
            model.orderSpecification,
            model.orderWeight,
            model.description,
            model.deliveryTime,
            model.receiptTime,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: ConfectioneryReceipt?): ConfectioneryEntity {
        return ConfectioneryEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.orderName,
            domainModel.orderSpecification,
            domainModel.orderWeight,
            domainModel.description,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.cost,
            domainModel.prepayment
        )

    }

    override fun generalMapper(generalReceipt: GeneralReceipt): ConfectioneryEntity {
        return ConfectioneryEntity(
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.confectioneryOrderSpecification,
            generalReceipt.confectioneryOrderWeight,
            generalReceipt.confectioneryDescription,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }
}