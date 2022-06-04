package com.haginus.marketplace.mapper;

import com.haginus.marketplace.dto.Asset.AssetRequestDto;
import com.haginus.marketplace.dto.Asset.AssetResponseDto;
import com.haginus.marketplace.dto.Asset.FileAssetResponseDto;
import com.haginus.marketplace.dto.Asset.LinkAssetResponseDto;
import com.haginus.marketplace.model.Asset.Asset;
import com.haginus.marketplace.model.Asset.FileAsset;
import com.haginus.marketplace.model.Asset.LinkAsset;
import com.haginus.marketplace.service.AssetService;
import com.haginus.marketplace.utils.HashIds;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;

@Component
@AllArgsConstructor
public class AssetMapper {

  private final HashIds hashIds;
  private final AssetService assetService;

  public AssetResponseDto toDto(Asset entity) {
    AssetResponseDto dto = new AssetResponseDto();
    if(entity instanceof LinkAsset) {
      dto = new LinkAssetResponseDto();
      dto.setType("link");
      ((LinkAssetResponseDto) dto).setLink(((LinkAsset) entity).getLink());
    } else if(entity instanceof FileAsset) {
      dto = new FileAssetResponseDto();
      dto.setType("file");
      ((FileAssetResponseDto) dto).setName(((FileAsset) entity).getName());
      ((FileAssetResponseDto) dto).setMimeType(((FileAsset) entity).getMimeType());
    }
    dto.setId(hashIds.encodeId(entity.getId()));
    return dto;
  }

  public Asset toEntity(AssetRequestDto dto) {
    if(dto.getId() != null) {
      return assetService.get(hashIds.decodeId(dto.getId()));
    }
    if(Objects.equals(dto.getType(), "link")) {
      LinkAsset entity = new LinkAsset();
      entity.setLink(dto.getLink());
      return entity;
    } else if(Objects.equals(dto.getType(), "file")) {
      FileAsset entity = new FileAsset();
      entity.setName(dto.getName());
      entity.setMimeType(dto.getMimeType());
      entity.setContent(Base64.getDecoder().decode(dto.getContent()));
      return entity;
    }
    return null;
  }
}
