package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.TailoringReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class TailoringEntityMapper :DomainMapper<TailoringEntity,TailoringReceipt>{
    override fun mapToDomainModel(model: TailoringEntity): TailoringReceipt {
        return TailoringReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.loanerName,
            model.orderSpecification,
            model.deliveryTime,
            model.receiptTime,
            model.sizes,
            model.cost,
            model.prepayment
        )

    }

    override fun mapFromDomainModel(domainModel: TailoringReceipt): TailoringEntity {
        return TailoringEntity(
            domainModel.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.loanerName,
            domainModel.orderSpecification,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.sizes,
            domainModel.cost,
            domainModel.prepayment
        )
    }


}