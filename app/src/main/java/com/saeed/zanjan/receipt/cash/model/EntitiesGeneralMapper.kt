package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.util.GeneralMapper

class EntitiesGeneralMapper:GeneralMapper {
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
            generalReceipt!!.id,
            generalReceipt.status,
            generalReceipt.name,
            generalReceipt.phone,
            generalReceipt.orderName,
            generalReceipt.repairLoanerProblems,
            generalReceipt.repairRisks,
            generalReceipt.deliveryTime,
            generalReceipt.receiptTime,
            generalReceipt.repairAccessories,
            generalReceipt.cost,
            generalReceipt.prepayment
        )    }

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
            model!!.id,
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
        )    }

    override fun mapFromJewelryEntity(model: JewelryEntity): GeneralReceipt {
        return GeneralReceipt(
            model!!.id,
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
        )     }

    override fun mapFromLaundryEntity(model: LaundryEntity): GeneralReceipt {
        return GeneralReceipt(
            model!!.id,
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
        )      }

    override fun mapFromOtherJobsEntity(model: OtherJobsEntity): GeneralReceipt {
        return GeneralReceipt(
            model!!.id,
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
        )    }

    override fun mapFromPhotographyEntity(model: PhotographyEntity): GeneralReceipt {
        return GeneralReceipt(
            model!!.id,
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
        )    }

    override fun mapFromRepairsEntity(model: RepairsEntity): GeneralReceipt {
        return GeneralReceipt(
            model!!.id,
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
        )     }

    override fun mapFromTailoringEntity(model: TailoringEntity): GeneralReceipt {
        return GeneralReceipt(
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
        )      }
}