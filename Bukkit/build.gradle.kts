import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven {
        name = "PlaceholderAPI"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "EssentialsX"
        url = uri("https://repo.essentialsx.net/releases/")
    }
}

dependencies {
    api(projects.plotSquaredCore)

    // Metrics
    implementation("org.bstats:bstats-bukkit")

    // Paper
    compileOnly(libs.paper)
    implementation("io.papermc:paperlib")

    // Plugins
    compileOnly(libs.worldeditBukkit) {
        exclude(group = "org.bukkit")
        exclude(group = "org.spigotmc")
    }
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }
    testImplementation("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI") {
        exclude(group = "org.bukkit")
    }
    compileOnly(libs.placeholderapi)
    compileOnly(libs.luckperms)
    compileOnly(libs.essentialsx)
    compileOnly(libs.mvdwapi) { isTransitive = false }

    // Other libraries
    implementation(libs.squirrelid) { isTransitive = false }
    implementation("dev.notmyfault.serverlib:ServerLib")

    // Our libraries
    implementation(libs.arkitektonika)
    implementation(libs.http4j)
    implementation("com.intellectualsites.paster:Paster")
    implementation("com.intellectualsites.informative-annotations:informative-annotations")

    // Adventure
    implementation("net.kyori:adventure-platform-bukkit")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    dependencies {
        exclude(dependency("org.checkerframework:"))
    }

    relocate("net.kyori.adventure", "com.plotsquared.core.configuration.adventure")
    relocate("net.kyori.examination", "com.plotsquared.core.configuration.examination")
    relocate("io.papermc.lib", "com.plotsquared.bukkit.paperlib")
    relocate("org.bstats", "com.plotsquared.metrics")
    relocate("org.enginehub", "com.plotsquared.squirrelid")
    relocate("org.khelekore.prtree", "com.plotsquared.prtree")
    relocate("com.google.inject", "com.plotsquared.google")
    relocate("org.aopalliance", "com.plotsquared.core.aopalliance")
    relocate("cloud.commandframework.services", "com.plotsquared.core.services")
    relocate("io.leangen.geantyref", "com.plotsquared.core.geantyref")
    relocate("com.intellectualsites.arkitektonika", "com.plotsquared.core.arkitektonika")
    relocate("com.intellectualsites.http", "com.plotsquared.core.http")
    relocate("com.intellectualsites.paster", "com.plotsquared.core.paster")
    relocate("org.incendo.serverlib", "com.plotsquared.bukkit.serverlib")
    relocate("org.jetbrains", "com.plotsquared.core.annotations")
    relocate("org.intellij.lang", "com.plotsquared.core.intellij.annotations")
    relocate("javax.annotation", "com.plotsquared.core.annotation")
    relocate("com.github.spotbugs", "com.plotsquared.core.spotbugs")
    relocate("javax.inject", "com.plotsquared.core.annotation.inject")
    relocate("net.jcip", "com.plotsquared.core.annotations.jcip")
    relocate("edu.umd.cs.findbugs", "com.plotsquared.core.annotations.findbugs")
    relocate("com.intellectualsites.informative-annotations", "com.plotsquared.core.annotation.informative")

    // Get rid of all the libs which are 100% unused.
    minimize()

    mergeServiceFiles()
}

tasks {
    withType<Javadoc> {
        val opt = options as StandardJavadocDocletOptions
        opt.links("https://jd.papermc.io/paper/1.18/")
        opt.links("https://docs.enginehub.org/javadoc/com.sk89q.worldedit/worldedit-bukkit/" + libs.worldeditBukkit.get().versionConstraint.toString())
        opt.links("https://intellectualsites.github.io/plotsquared-javadocs/core/")
        opt.links("https://jd.adventure.kyori.net/api/4.9.3/")
        opt.links("https://google.github.io/guice/api-docs/" + libs.guice.get().versionConstraint.toString() + "/javadoc/")
        opt.links("https://checkerframework.org/api/")
        opt.encoding("UTF-8")
    }
}
