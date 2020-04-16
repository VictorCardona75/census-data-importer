package com.marvic.censusdataimporter.domain

/**
 * The `MetropolitanDivision` Class represents a county or group of counties (or equivalent
 * entities) delineated within a larger metropolitan statistical area, provided that the larger
 * metropolitan statistical area contains a single core with a population of at least 2.5 million
 * and other criteria are met.
 *
 * A Metropolitan Division consists of one or more main/secondary counties that represent an
 * employment center or centers, plus adjacent counties associated with the main/secondary county
 * or counties through commuting ties. Not all metropolitan statistical areas will contain
 * metropolitan divisions.
 *
 * @constructor creates a new instance using the specified values
 * @property counties the counties that make up this division
 */
class MetropolitanDivision(
    _code: String,
    _title: String,
    _populationEstimates: List<PopulationEstimate>,
    val counties: List<CountyOrEquivalent>
) : StatisticalReportingArea(_code, _title, _populationEstimates) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as MetropolitanDivision

        if (counties != other.counties) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + counties.hashCode()
        return result
    }

    override fun toString(): String {
        return "MetropolitanDivision(code='$code', title='$title', counties=$counties)"
    }
}