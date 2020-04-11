package com.marvic.censusdataimporter.runners

import com.marvic.censusdataimporter.domain.CountyType
import com.marvic.censusdataimporter.domain.CountyTypeHolder
import com.marvic.censusdataimporter.util.getLogger
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
@Order(1)
class CountyDataLoader(val resourceLoader: ResourceLoader, val countyTypeHolder: CountyTypeHolder) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        logger.info("***** Started loading county information...")
        val time = measureTimeMillis {
            val file = resourceLoader.getResource("data/list1_Sep_2018.xls").file
            val workbook = WorkbookFactory.create(file)
            workbook?.first()?.let { sheet ->
                (3..1917).forEach { index ->
                    val row = sheet.getRow(index)
                    if (row != null) {
                        val stateCode = row.getCell(9).stringCellValue
                        val countyCode = row.getCell(10).stringCellValue
                        val countyType = row.getCell(11).stringCellValue
                        logger.debug("Adding $stateCode $countyCode -> $countyType")
                        countyTypeHolder.countyDataMap.put(
                            "$stateCode$countyCode", CountyType.valueOf(countyType.toUpperCase())
                        )
                    }
                }
            }
            workbook.close()
        }
        logger.info(
            "***** Seeded county map with ${countyTypeHolder.countyDataMap.size} records " +
                    "in $time ms"
        )
    }

    companion object {
        @JvmStatic
        private val logger = getLogger(CountyDataLoader::class.java)
    }
}