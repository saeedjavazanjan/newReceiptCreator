package com.saeed.zanjan.receipt.network.model

import com.saeed.zanjan.receipt.domain.models.ProfileData
import com.saeed.zanjan.receipt.domain.util.DomainMapper

class ProfileDataDtoMapper:DomainMapper<ProfileDataDto,ProfileData> {
    override fun mapToDomainModel(model: ProfileDataDto): ProfileData {
        return ProfileData(
            model.companyName,
            model.companyPhone,
            model.companyAddress,
            model.companyLink,
            model.companyRules,
           model.jobTitle
        )

    }

    override fun mapFromDomainModel(domainModel: ProfileData): ProfileDataDto {
        return ProfileDataDto(
            domainModel.companyName,
            domainModel.companyPhone,
            domainModel.companyAddress,
            domainModel.companyLink,
            domainModel.companyRules,
            domainModel.jobType
        )
    }

    override fun mapToDomainList(entityList: List<ProfileDataDto>): List<ProfileData> {
        return emptyList()
    }


}