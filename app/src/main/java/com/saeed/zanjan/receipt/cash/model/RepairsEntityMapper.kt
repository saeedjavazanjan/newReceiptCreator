package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class RepairsEntityMapper: DomainMapper<RepairsEntity, RepairsReceipt?> {
    override fun mapToDomainModel(model: RepairsEntity): RepairsReceipt? {
        return RepairsReceipt(
            model.id,
            model.status,
            model.name,
            model.phone,
            model.loanerName,
            model.loanerProblems,
            model.risks,
            model.deliveryTime,
            model.receiptTime,
            model.accessories,
            model.cost,
            model.prepayment
        )
    }

    override fun mapFromDomainModel(domainModel: RepairsReceipt?): RepairsEntity {
        return RepairsEntity(
            domainModel!!.id,
            domainModel.status,
            domainModel.name,
            domainModel.phone,
            domainModel.loanerName,
            domainModel.loanerProblems,
            domainModel.risks,
            domainModel.deliveryTime,
            domainModel.receiptTime,
            domainModel.accessories,
            domainModel.cost,
            domainModel.prepayment
        )

    }


}