package com.marvic.censusdataimporter.domain

enum class CountyType {
    CENTRAL, OUTLYING
}

/**
 * The `CountyOrEquivalent` Class represents the smallest component of core based
 * statistical areas for which population estimates are made.
 *
 * Counties are classified as either central or outlying.  Central counties comprise all or most
 * of the core urban area.  Outlying counties are counties where one-quarter or more of employed
 * residents work in central counties, or where one-quarter or more of employment is composed of
 * workers who live in central counties.
 *
 * @constructor creates a new instance using the specified values
 * @property countyType whether the county is central or outlying
 */
class CountyOrEquivalent(
    _code: String,
    _title: String,
    _populationEstimates: List<PopulationEstimate>,
    val countyType: CountyType
) : StatisticalReportingArea(_code, _title, _populationEstimates) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as CountyOrEquivalent

        if (countyType != other.countyType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + countyType.hashCode()
        return result
    }

    override fun toString(): String {
        return "CountyOrEquivalent(code='$code', title='$title', countyType=$countyType)"
    }
}