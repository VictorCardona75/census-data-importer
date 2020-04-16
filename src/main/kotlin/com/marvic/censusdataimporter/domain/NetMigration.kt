package com.marvic.censusdataimporter.domain

import java.time.LocalDate

/**
 * The `NetMigration` Class represents the populational change that is attributed to
 * migration over the specified time range.  Net migration includes both international and
 * domestic components.
 *
 * @constructor creates a new instance using the specified values
 * @property startDate the start date for the period
 * @property endDate the end date for the period
 * @property value the sum of the international and domestic components
 * @property netInternationalMigration the number of people that have migrated from abroad
 * @property netDomesticMigration the number of people that have migrated from inside the U.S.
 */
data class NetMigration(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val value: Long,
    val netInternationalMigration: Long,
    val netDomesticMigration: Long
)