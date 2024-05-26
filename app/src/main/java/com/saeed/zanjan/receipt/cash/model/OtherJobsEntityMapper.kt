package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.OtherJobsReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class OtherJobsEntityMapper: DomainMapper<OtherJobsEntity, OtherJobsReceipt?> {
    override fun mapToDomainModel(model: OtherJobsEntity): OtherJobsReceipt? {
        return OtherJobsReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.orderName,
            model.description,
            model.orderNumber,
            model.deliveryTime,
            model.receiptTime,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: OtherJobsReceipt?): OtherJobsEntity {
        return OtherJobsEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.orderName,
            domainModel.description,
            domainModel.orderNumber,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.cost,
            domainModel.prepayment
        )

    }

    override fun generalMapper(generalReceipt: GeneralReceipt): OtherJobsEntity {
        return OtherJobsEntity(
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.otherJobsDescription,
            generalReceipt.otherJobsOrderNumber,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }
}