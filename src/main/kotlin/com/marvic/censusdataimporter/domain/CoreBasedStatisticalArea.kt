package com.marvic.censusdataimporter.domain

import org.springframework.data.mongodb.core.mapping.Document

enum class StatisticalAreaType {
    METROPOLITAN, MICROPOLITAN
}

/**
 * The `CoreBasedStatisticalArea` Class also known as a CBSA represents either metropolitan
 * statistical areas or micropolitan statistical areas. CBSAs consist of the county or counties
 * (or equivalent entities) associated with at least one core (urbanized area or urban cluster)
 * of at least 10,000 population, plus adjacent counties having a high degree of social and
 * economic integration with the core as measured through commuting ties.
 *
 * @constructor creates a new instance using the specified values
 */
@Document(collection = "coreBasedStatisticalAreas")
class CoreBasedStatisticalArea(
    _code: String,
    _title: String,
    _populationEstimates: List<PopulationEstimate>,
    val areaType: StatisticalAreaType,
    val divisions: List<MetropolitanDivision> = listOf(),
    val counties: List<CountyOrEquivalent> = listOf()
) : StatisticalReportingArea(_code, _title, _populationEstimates) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as CoreBasedStatisticalArea

        if (areaType != other.areaType) return false
        if (divisions != other.divisions) return false
        if (counties != other.counties) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + areaType.hashCode()
        result = 31 * result + divisions.hashCode()
        result = 31 * result + counties.hashCode()
        return result
    }

    override fun toString(): String {
        return "CoreBasedStatisticalArea(code='$code', title='$title', areaType=$areaType, divisions=$divisions, counties=$counties)"
    }
}