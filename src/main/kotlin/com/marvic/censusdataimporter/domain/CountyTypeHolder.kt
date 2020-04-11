package com.marvic.censusdataimporter.domain

import org.springframework.stereotype.Component

@Component
class CountyTypeHolder {
    val countyDataMap = mutableMapOf<String, CountyType>()
}