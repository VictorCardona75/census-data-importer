package com.marvic.censusdataimporter.domain

import java.time.LocalDate

/**
 * The `Residual` Class represents the change in population over the specified time range
 * which cannot be attributed to either natural increase or net migration.
 *
 * @constructor creates a new instance using the specified values
 * @property startDate the start date for the period
 * @property endDate the end date for the period
 * @property value the change in population which cannot be attributed to other factors
 */
data class Residual(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val value: Long
)