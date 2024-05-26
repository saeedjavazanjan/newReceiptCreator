package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.LaundryReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class LaundryEntityMapper: DomainMapper<LaundryEntity, LaundryReceipt?> {
    override fun mapToDomainModel(model: LaundryEntity): LaundryReceipt? {
        return LaundryReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.loanerName,
            model.orderType,
            model.description,
            model.deliveryTime,
            model.receiptTime,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: LaundryReceipt?): LaundryEntity {
        return LaundryEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.loanerName,
            domainModel.orderType,
            domainModel.description,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.cost,
            domainModel.prepayment
        )

    }

    override fun generalMapper(generalReceipt: GeneralReceipt): LaundryEntity {
        return LaundryEntity(
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.laundryOrderType,
            generalReceipt.laundryDescription,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }
}