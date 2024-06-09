package com.saeed.zanjan.receipt.network.model

import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class OtpDataDtoMapper:DomainMapper<OtpDataDto,OtpData> {
    override fun mapToDomainModel(model: OtpDataDto): OtpData {
        return OtpData(
            phoneNumber = model.phoneNumber,
            password = model.password
        )


    }

    override fun mapFromDomainModel(domainModel: OtpData): OtpDataDto {
        return OtpDataDto(
            phoneNumber = domainModel.phoneNumber,
            password = domainModel.password
        )
    }

    override fun mapToDomainList(entityList: List<OtpDataDto>): List<OtpData> {
        return emptyList()
    }


}