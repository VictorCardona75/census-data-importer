package com.marvic.censusdataimporter

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CensusDataImporterApplication

fun main(args: Array<String>) {
    runApplication<CensusDataImporterApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
