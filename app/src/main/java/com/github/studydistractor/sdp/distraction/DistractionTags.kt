package com.github.studydistractor.sdp.distraction

enum class DistractionTags(var type: String) {
    INDOORS("Indoors"), OUTDOORS("Outdoors"), ONLINE("Online"), OFFLINE("Offline"), GROUP("Group"), SOLO("Solo"), NONE("None");

    override fun toString(): String {
        return type
    }
}