package com.saeed.zanjan.receipt.cash.model

import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class CustomerEntityMapper:DomainMapper<CustomerEntity,Customer> {
    override fun mapToDomainModel(model: CustomerEntity): Customer {

        return Customer(
            model.id,
            model.name,
            model.phoneNumber,
            dept = calculateDept(model.totalAmount,model.payedAmount)
        )
    }


    override fun mapFromDomainModel(domainModel: Customer): CustomerEntity {
        return CustomerEntity(
            domainModel.id,
            domainModel.name,
            domainModel.phoneNumber,
            "",
            ""
        )
    }

    override fun mapToDomainList(entityList: List<CustomerEntity>): List<Customer> {
        val list= mutableListOf<Customer>()
        entityList.forEach {
            list.add(mapToDomainModel(it))
        }
        return list

    }

    fun calculateDept(totalAmount: String?, payedAmount: String?): Int {

        if(totalAmount=="***"||payedAmount=="***"){
            return 0
        }else{
            val totalAmountInteger = totalAmount!!.replace(",", "").toInt()
            val payedAmountInteger = payedAmount!!.replace(",", "").toInt()
            return totalAmountInteger - payedAmountInteger
        }

    }
}