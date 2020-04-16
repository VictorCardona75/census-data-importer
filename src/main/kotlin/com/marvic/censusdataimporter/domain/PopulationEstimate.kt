package com.marvic.censusdataimporter.domain

import java.time.LocalDate

/**
 * The `PopulationEstimate` Class represents an estimate for the population of a
 * {@link StatisticalReportingArea} on the specified date.  The estimate also contains references
 * to all of the components used to make the estimate.
 *
 * @constructor creates a new instance using the specified values
 * @property date the date of the estimate
 * @property value the estimate value
 * @property numericChangeFromLast the numeric change from the previous estimate
 * @property fromCensus true if this is the value from last census
 * @property base true if this is the base year estimate
 * @property naturalIncrease the natural increase component
 * @property netMigration the net migration component
 * @property residual the residual component
 */
data class PopulationEstimate(
    val date: LocalDate,
    val value: Long,
    val numericChangeFromLast: Long = 0,
    val fromCensus: Boolean = false,
    val base: Boolean = false,
    val naturalIncrease: NaturalIncrease? = null,
    val netMigration: NetMigration? = null,
    val residual: Residual? = null
)