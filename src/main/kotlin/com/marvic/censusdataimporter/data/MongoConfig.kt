package com.marvic.censusdataimporter.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {
    override fun mongoClient(): MongoClient = MongoClients.create("mongodb://localhost:27017")

    override fun getDatabaseName() = "census"

    override fun getMappingBasePackage() = "com.marvic.censusdataimporter"
}