package com.communitas.store.app.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetResponse {
    private byte[] content;
    private String contentType;
}
