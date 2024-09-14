package me.glicz.airplane.util

import java.net.URI

const val MINECRAFT_LIBRARY = "minecraftLibrary"
val VERSION_MANIFEST_URI = URI("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json")

// .gradlew/airflow/mache
const val MACHE_DIR = "mache"
const val MACHE_DATA = "$MACHE_DIR/mache.json"
const val PATCHES_DIR = "$MACHE_DIR/patches"

// .gradlew/airflow/server
const val SERVER_DIR = "server"
const val SERVER_BOOSTRAP_JAR = "$SERVER_DIR/server_bootstrap.jar"
const val SERVER_MAPPINGS = "$SERVER_DIR/server_mappings.txt"

// build/airflow/server
const val SERVER_JAR = "server.jar"
const val REMAPPED_JAR = "remapped.jar"
const val SOURCES_JAR = "sources.jar"
const val PATCHED_JAR = "patched.jar"