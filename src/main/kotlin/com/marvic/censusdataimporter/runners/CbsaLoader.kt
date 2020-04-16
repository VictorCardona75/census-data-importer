package com.marvic.censusdataimporter.runners

import com.marvic.censusdataimporter.domain.CoreBasedStatisticalArea
import com.marvic.censusdataimporter.domain.CountyOrEquivalent
import com.marvic.censusdataimporter.domain.CountyTypeHolder
import com.marvic.censusdataimporter.domain.MetropolitanDivision
import com.marvic.censusdataimporter.domain.NaturalIncrease
import com.marvic.censusdataimporter.domain.NetMigration
import com.marvic.censusdataimporter.domain.PopulationEstimate
import com.marvic.censusdataimporter.domain.Residual
import com.marvic.censusdataimporter.domain.StatisticalAreaType
import com.marvic.censusdataimporter.util.getLogger
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.core.io.ResourceLoader
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.time.LocalDate
import kotlin.system.measureTimeMillis

@Component
@Order(2)
class CbsaLoader(
    val resourceLoader: ResourceLoader,
    val countyTypeHolder: CountyTypeHolder,
    val mongoTemplate: MongoTemplate
) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        logger.info("***** Started loading CBSA data...")
        val time = measureTimeMillis {
            val file = resourceLoader.getResource("data/cbsa-est2019-alldata.csv").file
            val lines = file.readLines().filterIndexed { index, _ -> index > 0 }
                .map { line -> line.replace("\"", "") }
                .map { line -> line.split(Regex(""",(?![ ])""")) }
            val countiesByCbsa = lines.filter { it[2].isNotBlank() }.groupBy { it[0] }
            val divisionsByCbsa = lines.filter { it[1].isNotBlank() }.groupBy { it[0] }
            val coreBasedStatisticalAreas = lines.filter { it[1].isBlank() && it[2].isBlank() }

            // Create core based statistical areas
            val cbsaList = mutableListOf<CoreBasedStatisticalArea>()
            for (line in coreBasedStatisticalAreas) {
                val areaType =
                    if (line[4].contains("Metropolitan")) StatisticalAreaType.METROPOLITAN
                    else StatisticalAreaType.MICROPOLITAN

                if (divisionsByCbsa.get(line[0]) != null) {
                    cbsaList.add(
                        CoreBasedStatisticalArea(
                            _code = line[0],
                            _title = line[3],
                            _populationEstimates = createPopulationEstimatesForLine(line),
                            areaType = areaType,
                            divisions = divisionsByCbsa.getValue(line[0]).map { divLine ->
                                createMetropolitanDivision(
                                    divLine,
                                    countiesByCbsa.getValue(line[0])
                                ) { it[1] == divLine[1] }
                            }
                        )
                    )
                } else {
                    cbsaList.add(
                        CoreBasedStatisticalArea(
                            _code = line[0],
                            _title = line[3],
                            _populationEstimates = createPopulationEstimatesForLine(line),
                            areaType = areaType,
                            counties = countiesByCbsa.getValue(line[0]).map { createCounty(it) }
                        )
                    )
                }
            }

            mongoTemplate.dropCollection(CoreBasedStatisticalArea::class.java)
            mongoTemplate.createCollection(CoreBasedStatisticalArea::class.java)
            mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, CoreBasedStatisticalArea::class.java)
                .insert(cbsaList).execute()

        }
        logger.info(
            "***** Finished seeding database with ${mongoTemplate.count(
                Query(),
                CoreBasedStatisticalArea::class.java
            )} records in $time ms."
        )
    }

    private fun createMetropolitanDivision(
        tokens: List<String>,
        countyTokens: List<List<String>>,
        filter: (List<String>) -> Boolean
    ): MetropolitanDivision {
        val filteredCountyTokens = countyTokens.filter(filter)
        return MetropolitanDivision(
            tokens[1],
            tokens[3],
            createPopulationEstimatesForLine(tokens),
            filteredCountyTokens.map { createCounty(it) })
    }

    private fun createCounty(tokens: List<String>) = CountyOrEquivalent(
        tokens[2],
        tokens[3],
        createPopulationEstimatesForLine(tokens),
        countyTypeHolder.countyDataMap.getValue(tokens[2])
    )

    private fun createPopulationEstimatesForLine(tokens: List<String>): List<PopulationEstimate> {
        val estimates = mutableListOf(
            createCensusPopulationEstimate(tokens[5].toLong()),
            createBasePopulationEstimate(tokens[6].toLong())
        )

        for (year in 2010..2019) {
            val estimate = when (year) {
                2010 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(7, 17, 27, 37, 47, 57, 67, 77, 87).contains(index)
                }
                2011 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(8, 18, 28, 38, 48, 58, 68, 78, 88).contains(index)
                }
                2012 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(9, 19, 29, 39, 49, 59, 69, 79, 89).contains(index)
                }
                2013 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(10, 20, 30, 40, 50, 60, 70, 80, 90).contains(index)
                }
                2014 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(11, 21, 31, 41, 51, 61, 71, 81, 91).contains(index)
                }
                2015 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(12, 22, 32, 42, 52, 62, 72, 82, 92).contains(index)
                }
                2016 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(13, 23, 33, 43, 53, 63, 73, 83, 93).contains(index)
                }
                2017 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(14, 24, 34, 44, 54, 64, 74, 84, 94).contains(index)
                }
                2018 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(15, 25, 35, 45, 55, 65, 75, 85, 95).contains(index)
                }
                2019 -> createPopulationEstimate(year, tokens) { index, _ ->
                    listOf(16, 26, 36, 46, 56, 66, 76, 86, 96).contains(index)
                }
                else -> null
            }

            if (estimate != null) estimates.add(estimate)
        }

        return estimates
    }

    private fun createCensusPopulationEstimate(value: Long) =
        PopulationEstimate(LocalDate.of(2010, 4, 1), value, fromCensus = true)

    private fun createBasePopulationEstimate(value: Long) =
        PopulationEstimate(LocalDate.of(2010, 4, 1), value, base = true)

    private fun createPopulationEstimate(
        year: Int,
        tokens: List<String>,
        filter: (Int, String) -> Boolean
    ): PopulationEstimate {
        val filteredTokens = tokens.filterIndexed(filter).map(String::toLong)
        val estimateDate = LocalDate.of(year, 7, 1)
        val periodStart = if (year > 2010) estimateDate.minusYears(1) else estimateDate.minusMonths(3)
        val periodEnd = estimateDate.minusDays(1)

        return PopulationEstimate(
            date = estimateDate,
            value = filteredTokens[0],
            numericChangeFromLast = filteredTokens[1],
            naturalIncrease = NaturalIncrease(
                periodStart,
                periodEnd,
                filteredTokens[2],
                filteredTokens[3],
                filteredTokens[4]
            ),
            netMigration = NetMigration(
                periodStart,
                periodEnd,
                filteredTokens[5],
                filteredTokens[6],
                filteredTokens[7]
            ),
            residual = Residual(periodStart, periodEnd, filteredTokens[8])
        )
    }

    companion object {
        @JvmStatic
        val logger = getLogger(CbsaLoader::class.java)
    }
}