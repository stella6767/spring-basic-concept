package com.kang.MusicApiServer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Track, Long> {

}
