package com.lovable_clone_microservices.common_library.enums;


public enum ChatEventType {
    THOUGHT,         // "Thought for 2s"
    MESSAGE,        // Standard conversational text
    FILE_EDIT,      // Code generation <file>
    TOOL_LOG        // "Reading file..." <tool>
}
