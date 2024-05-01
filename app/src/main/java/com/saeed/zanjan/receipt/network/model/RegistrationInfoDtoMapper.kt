package com.saeed.zanjan.receipt.network.model

import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class RegistrationInfoDtoMapper: DomainMapper<RegistrationInfoDto, RegistrationInfo> {
    override fun mapToDomainModel(model: RegistrationInfoDto): RegistrationInfo {
        return RegistrationInfo(
            companyName = model.companyName,
            address = model.address,
            phone = model.phone,
            userId = model.userId,
            jobType = model.jobType
        )
    }

    override fun mapFromDomainModel(domainModel: RegistrationInfo): RegistrationInfoDto {
        return RegistrationInfoDto(
            companyName = domainModel.companyName,
            address = domainModel.address,
            phone = domainModel.phone,
            userId = domainModel.userId,
            jobType = domainModel.jobType
        )
    }
}