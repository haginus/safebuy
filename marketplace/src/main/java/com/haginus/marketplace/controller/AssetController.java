package com.haginus.marketplace.controller;

import com.haginus.marketplace.model.Asset.FileAsset;
import com.haginus.marketplace.service.AssetService;
import com.haginus.marketplace.utils.HashIds;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
@AllArgsConstructor
public class AssetController {

  private final HashIds hashIds;
  private final AssetService assetService;

  @GetMapping("/{hashId}/content")
  public ResponseEntity<byte[]> getFile(@PathVariable String hashId) {
    Long id = this.hashIds.decodeId(hashId);
    FileAsset asset = this.assetService.getFileAsset(id);
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + asset.getName() + "\"")
      .body(asset.getContent());
  }
}
