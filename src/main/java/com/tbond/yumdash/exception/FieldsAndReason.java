package com.tbond.yumdash.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FieldsAndReason {
    String field;
    String reason;
}
