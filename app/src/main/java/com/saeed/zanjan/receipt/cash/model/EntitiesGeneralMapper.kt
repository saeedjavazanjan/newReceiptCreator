package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.util.GeneralMapper

class EntitiesGeneralMapper : GeneralMapper {
    override fun mapToConfectioneryEntity(generalReceipt: GeneralReceipt): ConfectioneryEntity {
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

    override fun mapToJewelryEntity(generalReceipt: GeneralReceipt): JewelryEntity {
        return JewelryEntity(
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.jewelryOrderSpecification,
            generalReceipt.jewelryLoanerProblems,
            generalReceipt.jewelryLoanerSpecification,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }

    override fun mapLaundryEntity(generalReceipt: GeneralReceipt): LaundryEntity {
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

    override fun mapToOtherJobsEntity(generalReceipt: GeneralReceipt): OtherJobsEntity {
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

    override fun mapToPhotographyEntity(generalReceipt: GeneralReceipt): PhotographyEntity {
        return PhotographyEntity(
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.photographyOrderSize,
            generalReceipt.photographyOrderNumber,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }

    override fun mapToRepairsEntity(generalReceipt: GeneralReceipt): RepairsEntity {
        return RepairsEntity(
            id = generalReceipt!!.id,
            status = generalReceipt.status,
            name = generalReceipt.name,
            phone = generalReceipt.phone,
            loanerName = generalReceipt.orderName,
            loanerProblems = generalReceipt.repairLoanerProblems,
            risks = generalReceipt.repairRisks,
            deliveryTime = generalReceipt.deliveryTime,
            receiptTime = generalReceipt.receiptTime,
            accessories = generalReceipt.repairAccessories,
            cost = generalReceipt.cost,
            prepayment = generalReceipt.prepayment
        )
    }

    override fun mapToTailoringEntity(generalReceipt: GeneralReceipt): TailoringEntity {
        return TailoringEntity(
            generalReceipt.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.tailoringOrderSpecification,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.tailoringSizes,
            generalReceipt.cost,
            generalReceipt.prepayment
        )
    }

    override fun mapFromConfectioneryEntity(model: ConfectioneryEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.orderName,
            confectioneryOrderSpecification = model.orderSpecification,
            confectioneryOrderWeight = model.orderWeight,
            confectioneryDescription = model.description,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromJewelryEntity(model: JewelryEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.loanerName,
            jewelryOrderSpecification = model.orderSpecification,
            jewelryLoanerProblems = model.loanerProblems,
            jewelryLoanerSpecification = model.loanerSpecification,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromLaundryEntity(model: LaundryEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.loanerName,
            laundryDescription = model.description,
            laundryOrderType = model.orderType,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromOtherJobsEntity(model: OtherJobsEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.orderName,
            otherJobsDescription = model.description,
            otherJobsOrderNumber = model.orderNumber,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromPhotographyEntity(model: PhotographyEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.orderName,
            photographyOrderNumber = model.orderNumber,
            photographyOrderSize = model.orderSize,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromRepairsEntity(model: RepairsEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.loanerName,
            repairLoanerProblems = model.loanerProblems,
            repairRisks = model.risks,
            repairAccessories = model.accessories,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }

    override fun mapFromTailoringEntity(model: TailoringEntity): GeneralReceipt {
        return GeneralReceipt(
            id = model!!.id,
            status = model.status,
            name = model.name,
            phone = model.phone,
            orderName = model.loanerName,
            tailoringOrderSpecification = model.orderSpecification,
            tailoringSizes = model.sizes,
            deliveryTime = model.deliveryTime,
            receiptTime = model.receiptTime,
            cost = model.cost,
            prepayment = model.prepayment
        )
    }
}