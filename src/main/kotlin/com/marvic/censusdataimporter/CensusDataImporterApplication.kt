package com.marvic.censusdataimporter

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class CensusDataImporterApplication

fun main(args: Array<String>) {
    runApplication<CensusDataImporterApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
