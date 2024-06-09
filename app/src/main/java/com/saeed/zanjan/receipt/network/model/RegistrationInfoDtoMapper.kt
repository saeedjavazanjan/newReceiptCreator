package com.saeed.zanjan.receipt.network.model

import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class RegistrationInfoDtoMapper: DomainMapper<RegistrationInfoDto, RegistrationInfo> {
    override fun mapToDomainModel(model: RegistrationInfoDto): RegistrationInfo {
        return RegistrationInfo(
            companyName = model.companyName,
            password=model.password,
            address = model.address,
            phone = model.phoneNumber,
            userId = model.pageId,
            jobType = model.jobTitle
        )
    }

    override fun mapFromDomainModel(domainModel: RegistrationInfo): RegistrationInfoDto {
        return RegistrationInfoDto(
            companyName = domainModel.companyName,
            password=domainModel.password,
            address = domainModel.address,
            phoneNumber = domainModel.phone,
            pageId = domainModel.userId,
            jobTitle = domainModel.jobType
        )
    }

    override fun mapToDomainList(entityList: List<RegistrationInfoDto>): List<RegistrationInfo> {
        return emptyList()
    }


    //only implemented for run app

}