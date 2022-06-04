package com.haginus.marketplace.utils;

import org.hashids.Hashids;
import org.springframework.stereotype.Component;

@Component
public class HashIds {
  private final Hashids hashids;

  public HashIds() {
    this.hashids = new Hashids("This is a salt.", 20);
  }

  public String encodeId(Long id) {
    return this.hashids.encode(id);
  }

  public Long decodeId(String hash) {
    return this.hashids.decode(hash)[0];
  }
}
