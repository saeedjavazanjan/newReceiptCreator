package com.saeed.zanjan.receipt.domain.util

import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

interface DomainMapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

    fun mapToDomainList(entityList:List<T>):List<DomainModel>

}
