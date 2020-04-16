package com.marvic.censusdataimporter.domain

import java.time.LocalDate

/**
 * The `NaturalIncrease` Class represents the difference in births and deaths over the
 * specified period of time.
 *
 * @constructor creates a new instance using the specified values
 * @property startDate the start date for the period
 * @property endDate the end date for the period
 * @property value the difference between the number of births and deaths
 * @property births the number of births
 * @property deaths the number of deaths
 */
data class NaturalIncrease(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val value: Long,
    val births: Long,
    val deaths: Long
)