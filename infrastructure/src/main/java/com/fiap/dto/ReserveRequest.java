package com.fiap.dto;

import java.util.UUID;

public record ReserveRequest(UUID partId, int quantity) {

}