package com.marvic.censusdataimporter.domain

import org.springframework.data.mongodb.core.mapping.MongoId

abstract class StatisticalReportingArea(
    @MongoId
    val code: String,
    val title: String,
    val populationEstimates: List<PopulationEstimate>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StatisticalReportingArea

        if (code != other.code) return false
        if (title != other.title) return false
        if (populationEstimates != other.populationEstimates) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + populationEstimates.hashCode()
        return result
    }

    override fun toString(): String {
        return "StatisticalReportingArea(code='$code', title='$title', populationEstimates=$populationEstimates)"
    }
}