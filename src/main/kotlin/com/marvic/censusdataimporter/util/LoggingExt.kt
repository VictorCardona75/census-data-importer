package com.marvic.censusdataimporter.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun getLogger(forClass: Class<*>): Logger  = LoggerFactory.getLogger(forClass)